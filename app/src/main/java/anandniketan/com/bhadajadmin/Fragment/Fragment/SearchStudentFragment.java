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
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentFilteredDataAdapter;
import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentSearchStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchStudentFragment extends Fragment {

    private String status;

    public SearchStudentFragment() {
    }

    private FragmentSearchStudentBinding fragmentSearchStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private ArrayList parentName = new ArrayList();
    private ArrayList studentName = new ArrayList();
    private String searchtypeStr="Current Student", studentNameStr = "", parentNameStr = "", grnoStr = "",FinalTermIdStr,FinalClassIdStr;
    private StudentFilteredDataAdapter studentFilteredDataAdapter;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentSearchStudentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_student, container, false);

        rootView = fragmentSearchStudentBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();
        callTermApi();
        setListner();


        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;

        Bundle bundle = this.getArguments();
        status = bundle.getString("status");

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
        fragmentSearchStudentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStudentShowFilteredDataApi();
            }
        });
        fragmentSearchStudentBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSearchStudentBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentSearchStudentBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callParentNameApi();
                callStudentNameApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentSearchStudentBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentSearchStudentBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentSearchStudentBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalClassIdStr = getid.toString();
                Log.d("FinalStandardIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        callStandardApi();

                    }
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

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardSectionCombine(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        fillGradeSpinner();
                        callParentNameApi();
                        callStudentNameApi();
                    }
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
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

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSearchStudentBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext,R.layout.spinner_layout,spinnertermIdArray);
        fragmentSearchStudentBinding.termSpinner.setAdapter(adapterTerm);

    }

    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");
//
        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
            standardname.add(finalArrayStandardsList.get(i).getStandardClass());
        }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
            standardId.add(finalArrayStandardsList.get(j).getClassID());
        }
        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentSearchStudentBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentSearchStudentBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerStandardMap.get(0);
    }

    // CALL Student Name API HERE
    private void callStudentNameApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
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
        ApiHandler.getApiService().getAdmin_SearchStudent(getStudentShowFilteredDataDetail(), new retrofit.Callback<StudentAttendanceModel>() {
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
                                AppConfiguration.firsttimeback = true;
                                AppConfiguration.position = 58;
                                fragment = new AllDepartmentDetailsFragment();
                                fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container,fragment).addToBackStack(null).commit();
                            }
                        }, status);
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
        map.put("TermID", FinalTermIdStr);
        map.put("ParentName", parentNameStr);
        map.put("StudentName", studentNameStr);
        map.put("GRNO", grnoStr);
        map.put("ClassID",FinalClassIdStr);
        return map;
    }

}

