package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentFilteredDataAdapter;
import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentSearchStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchStudentFragment extends Fragment {

    public SearchStudentFragment() {
    }

    private FragmentSearchStudentBinding fragmentSearchStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private ArrayList parentName = new ArrayList();
    private ArrayList studentName = new ArrayList();
    private String searchtypeStr, studentNameStr = "", parentNameStr = "", grnoStr = "";
    private StudentFilteredDataAdapter studentFilteredDataAdapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSearchStudentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_student, container, false);

        rootView = fragmentSearchStudentBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();
        fillsearchType();
        setListner();


        return rootView;
    }

    public void initViews() {


    }

    public void setListner() {
        fragmentSearchStudentBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentSearchStudentBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentSearchStudentBinding.searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchtypeStr = parent.getItemAtPosition(position).toString();
                Log.d("searchtypestr", searchtypeStr);
                AppConfiguration.StudentStatus=searchtypeStr;

                callParentNameApi();
                callStudentNameApi();
                callStudentShowFilteredDataApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentSearchStudentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentShowFilteredDataApi();
            }
        });
    }

    public void fillsearchType() {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSearchStudentBinding.searchTypeSpinner);

            popupWindow.setHeight(getResources().getStringArray(R.array.SearchStudent).length > 4 ? 500 : getResources().getStringArray(R.array.SearchStudent).length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, getResources().getStringArray(R.array.SearchStudent));
        fragmentSearchStudentBinding.searchTypeSpinner.setAdapter(adapterYear);

    }

    // CALL Parent Name API HERE
    private void callParentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getParentName(getParentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel parentsNameModel, Response response) {
                Utils.dismissDialog();
                if (parentsNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (parentsNameModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (parentsNameModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    parentName.clear();
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, parentName);
                    fragmentSearchStudentBinding.parentsnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.parentsnameTxt.setAdapter(adb);
                    fragmentSearchStudentBinding.studentnameTxt.setText("");
                    fragmentSearchStudentBinding.parentsnameTxt.setText("");
                    fragmentSearchStudentBinding.grnoTxt.setText("");
                    return;
                }
                if (parentsNameModel.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < parentsNameModel.getFinalArray().size(); i++) {
                        parentName.add(parentsNameModel.getFinalArray().get(i).getName());
                    }
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, parentName);
                    fragmentSearchStudentBinding.parentsnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.parentsnameTxt.setAdapter(adb);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getParentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("InputValue", parentNameStr);
        return map;
    }


    // CALL Student Name API HERE
    private void callStudentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentName(getStudentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentNameModel, Response response) {
//                Utils.dismissDialog();
                if (studentNameModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    studentName.clear();
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, studentName);
                    fragmentSearchStudentBinding.studentnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.studentnameTxt.setAdapter(adb);
                    fragmentSearchStudentBinding.studentnameTxt.setText("");
                    fragmentSearchStudentBinding.parentsnameTxt.setText("");
                    fragmentSearchStudentBinding.grnoTxt.setText("");
                    return;
                }
                if (studentNameModel.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < studentNameModel.getFinalArray().size(); i++) {
                        studentName.add(studentNameModel.getFinalArray().get(i).getName());
                    }
                    final ArrayAdapter adb = new ArrayAdapter(mContext, R.layout.spinner_layout, studentName);
                    fragmentSearchStudentBinding.studentnameTxt.setThreshold(1);
                    fragmentSearchStudentBinding.studentnameTxt.setAdapter(adb);
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("InputValue", studentNameStr);
        return map;
    }


    // CALL StudentShowFilteredData API HERE
    private void callStudentShowFilteredDataApi() {
        parentNameStr = fragmentSearchStudentBinding.parentsnameTxt.getText().toString();
        studentNameStr = fragmentSearchStudentBinding.studentnameTxt.getText().toString();
        grnoStr = fragmentSearchStudentBinding.grnoTxt.getText().toString();
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentFilterData(getStudentShowFilteredDataDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel filteredDataModel, Response response) {
//                Utils.dismissDialog();
                if (filteredDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (filteredDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (filteredDataModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (filteredDataModel.getFinalArray().size() == 0) {
                       fragmentSearchStudentBinding.studentSearchList.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.listHeader.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (filteredDataModel.getSuccess().equalsIgnoreCase("True")) {
                    if (filteredDataModel.getFinalArray().size() > 0) {
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSearchStudentBinding.studentSearchList.setVisibility(View.VISIBLE);
                        fragmentSearchStudentBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentSearchStudentBinding.listHeader.setVisibility(View.VISIBLE);
                        studentFilteredDataAdapter = new StudentFilteredDataAdapter(mContext, filteredDataModel, new onViewClick() {
                            @Override
                            public void getViewClick() {
                                fragment = new AllDepartmentDetailsFragment();
                                fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                        .replace(R.id.frame_container, fragment).commit();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentSearchStudentBinding.studentSearchList.setLayoutManager(mLayoutManager);
                        fragmentSearchStudentBinding.studentSearchList.setItemAnimator(new DefaultItemAnimator());
                        fragmentSearchStudentBinding.studentSearchList.setAdapter(studentFilteredDataAdapter);
                    } else {
                        fragmentSearchStudentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getStudentShowFilteredDataDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("SearchType", searchtypeStr);
        map.put("ParentName", parentNameStr);
        map.put("StudentName", studentNameStr);
        map.put("GRNO", grnoStr);
        return map;
    }

}

