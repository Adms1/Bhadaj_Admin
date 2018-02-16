package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import anandniketan.com.bhadajadmin.Adapter.AssignSubjectDetailListAdapter;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAssignSubjectBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AssignSubjectFragment extends Fragment {

    private FragmentAssignSubjectBinding fragmentAssignSubjectBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStaffModel> finalArrayTeachersModelList;
    HashMap<Integer, String> spinnerTeacherMap;
    List<FinalArrayStaffModel> finalArraySubjectModelList;
    HashMap<Integer, String> spinnerSubjectMap;
    List<FinalArrayStaffModel> finalArrayAssignSubjectModelList;
    List<FinalArrayStaffModel> finalArrayInsertAssignSubjectModelList;
    String FinalTermIdStr, FinalTeacherIdStr, FinalSubjectIdStr;

    AssignSubjectDetailListAdapter assignSubjectDetailListAdapter;

    public AssignSubjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAssignSubjectBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_assign_subject, container, false);

        rootView = fragmentAssignSubjectBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callTeacherApi();
        callSubjectApi();

        return rootView;
    }


    public void setListners() {
        fragmentAssignSubjectBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAssignSubjectBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentAssignSubjectBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentAssignSubjectBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(fragmentAssignSubjectBinding.teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTeacherIdStr = getid.toString();
                Log.d("FinalTeacherIdStr", FinalTeacherIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAssignSubjectBinding.subjectSpinner.getSelectedItem().toString();
                String getid = spinnerSubjectMap.get(fragmentAssignSubjectBinding.subjectSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSubjectIdStr = getid.toString();
                Log.d("FinalSubjectIdStr", FinalSubjectIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAssignSubjectBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInsertAssignSubjectApi();
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

    // CALL Teacher API HERE
    private void callTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTeachers(getTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel teachersModel, Response response) {
                Utils.dismissDialog();
                if (teachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (teachersModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayTeachersModelList = teachersModel.getFinalArray();
                    if (finalArrayTeachersModelList != null) {
                        fillTeacherSpinner();
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

    private Map<String, String> getTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Subject API HERE
    private void callSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSubject(getSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel subjectModel, Response response) {
                Utils.dismissDialog();
                if (subjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (subjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (subjectModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (subjectModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArraySubjectModelList = subjectModel.getFinalArray();
                    if (finalArraySubjectModelList != null) {
                        fillSubjectSpinner();
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

    private Map<String, String> getSubjectDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL AssignSubject API HERE
    private void callAssignSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSubjectAssgin(getAssignSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel getSubjectAssginModel, Response response) {
                Utils.dismissDialog();
                if (getSubjectAssginModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getSubjectAssginModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getSubjectAssginModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getSubjectAssginModel.getFinalArray().size() == 0) {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getSubjectAssginModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayAssignSubjectModelList = getSubjectAssginModel.getFinalArray();
                    if (finalArrayAssignSubjectModelList != null) {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.GONE);
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

    private Map<String, String> getAssignSubjectDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        return map;
    }

    // CALL InsertAssignSubject API HERE
    private void callInsertAssignSubjectApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertAssignSubject(getInsertAssignSubjectDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertAssignSubjectModel, Response response) {
                Utils.dismissDialog();
                if (insertAssignSubjectModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    return;
                }
                if (insertAssignSubjectModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayInsertAssignSubjectModelList = insertAssignSubjectModel.getFinalArray();
                    if (finalArrayInsertAssignSubjectModelList != null) {
                        callAssignSubjectApi();
                        Utils.dismissDialog();
                    } else {
                        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getInsertAssignSubjectDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term", FinalTermIdStr);
        map.put("SubjectID", FinalSubjectIdStr);
        map.put("Pk_EmployeID", FinalTeacherIdStr);
        return map;
    }

    //Use for fill the Term Spinner
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentAssignSubjectBinding.termSpinner.setAdapter(adapterTerm);
        FinalTermIdStr = spinnerTermMap.get(0);
        callAssignSubjectApi();
    }

    //Use for fill the Teacher Name Spinner
    public void fillTeacherSpinner() {
        ArrayList<Integer> TeacherId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayTeachersModelList.size(); i++) {
            TeacherId.add(finalArrayTeachersModelList.get(i).getPkEmployeeID());
        }
        ArrayList<String> Teacher = new ArrayList<String>();
        for (int j = 0; j < finalArrayTeachersModelList.size(); j++) {
            Teacher.add(finalArrayTeachersModelList.get(j).getTeacher());
        }

        String[] spinnerteacherIdArray = new String[TeacherId.size()];

        spinnerTeacherMap = new HashMap<Integer, String>();
        for (int i = 0; i < TeacherId.size(); i++) {
            spinnerTeacherMap.put(i, String.valueOf(TeacherId.get(i)));
            spinnerteacherIdArray[i] = Teacher.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
        fragmentAssignSubjectBinding.teacherSpinner.setAdapter(adapterTerm);

    }

    //Use for fill the Subject Spinner
    public void fillSubjectSpinner() {
        ArrayList<Integer> SubjectId = new ArrayList<Integer>();
        for (int i = 0; i < finalArraySubjectModelList.size(); i++) {
            SubjectId.add(finalArraySubjectModelList.get(i).getPkSubjectID());
        }
        ArrayList<String> Subject = new ArrayList<String>();
        for (int j = 0; j < finalArraySubjectModelList.size(); j++) {
            Subject.add(finalArraySubjectModelList.get(j).getSubject());
        }

        String[] spinnersubjectIdArray = new String[SubjectId.size()];

        spinnerSubjectMap = new HashMap<Integer, String>();
        for (int i = 0; i < SubjectId.size(); i++) {
            spinnerSubjectMap.put(i, String.valueOf(SubjectId.get(i)));
            spinnersubjectIdArray[i] = Subject.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAssignSubjectBinding.subjectSpinner);

            popupWindow.setHeight(spinnersubjectIdArray.length > 4 ? 500 : spinnersubjectIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        fragmentAssignSubjectBinding.subjectSpinner.setAdapter(adapterTerm);

    }

    //Use for fill the Subject with Teacher data
    public void fillDataList() {
        fragmentAssignSubjectBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentAssignSubjectBinding.assignSubjectDetailList.setVisibility(View.VISIBLE);
        fragmentAssignSubjectBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentAssignSubjectBinding.lvExpHeader.setVisibility(View.VISIBLE);

        assignSubjectDetailListAdapter = new AssignSubjectDetailListAdapter(mContext, finalArrayAssignSubjectModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentAssignSubjectBinding.assignSubjectDetailList.setLayoutManager(mLayoutManager);
        fragmentAssignSubjectBinding.assignSubjectDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentAssignSubjectBinding.assignSubjectDetailList.setAdapter(assignSubjectDetailListAdapter);
    }
}

