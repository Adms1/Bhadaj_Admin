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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.koushikdutta.ion.builder.Builders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ClassTeacherDetailListAdapter;
import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Interface.onDeleteButton;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentClassTeacherBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ClassTeacherFragment extends Fragment {

    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    List<FinalArrayStaffModel> finalArrayTeachersModelList;
    HashMap<Integer, String> spinnerTeacherMap;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStaffModel> finalArrayClassTeacherDetailModelList;
    List<FinalArrayStaffModel> finalArrayInsertClassTeachersModelList;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    String finalStandardIdStr, finalTeacherIdStr, finalClassIdStr, standardName, finalTermIdStr, finalClassTeacherIdStr, editClassteacherStr="", editGradeStr="", editSectionStr="";
    ClassTeacherDetailListAdapter classTeacherDetailListAdapter;
    ArrayList<String> getEditValuearray;
    private FragmentClassTeacherBinding fragmentClassTeacherBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private RadioGroup radioGroup;
    private int selectedPosition = -1;

    String[] spinnerteacherIdArray;
    String[] spinnerstandardIdArray;
    //Use for get the Final selected ClassID
    private RadioGroup.OnCheckedChangeListener mCheckedListner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (group.findViewById(checkedId) != null) {
                RadioButton btn = (RadioButton) getActivity().findViewById(checkedId);
                finalClassIdStr = btn.getTag().toString();
                Log.d("Your selected ", finalClassIdStr);
            }
        }
    };

    public ClassTeacherFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentClassTeacherBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_class_teacher, container, false);

        rootView = fragmentClassTeacherBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        callTermApi();
        setListners();
        callTeacherApi();


        return rootView;
    }

    public void setListners() {
        fragmentClassTeacherBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentClassTeacherBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentClassTeacherBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentClassTeacherBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", finalTermIdStr);

                callClassTeacherApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentClassTeacherBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentClassTeacherBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalStandardIdStr = getid.toString();
                Log.d("FinalStandardIdStr", finalStandardIdStr);
                standardName = name;
                Log.d("StandardName", standardName);
                fillSection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentClassTeacherBinding.teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentClassTeacherBinding.teacherSpinner.getSelectedItem().toString();
                String getid = spinnerTeacherMap.get(fragmentClassTeacherBinding.teacherSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalTeacherIdStr = getid.toString();
                Log.d("FinalTeacherIdStr", finalTeacherIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentClassTeacherBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInsertClassTeacherApi();
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
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
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

    // CALL ClassTeacher API HERE
    private void callClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getClassTeacherDetail(getClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel getClassTeacherDetailModel, Response response) {
                Utils.dismissDialog();
                if (getClassTeacherDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    if (getClassTeacherDetailModel.getFinalArray().size() == 0) {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentClassTeacherBinding.classTeacherDetailList.setVisibility(View.GONE);
                        fragmentClassTeacherBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentClassTeacherBinding.listHeader.setVisibility(View.GONE);
                    }
                    return;
                }
                if (getClassTeacherDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayClassTeacherDetailModelList = getClassTeacherDetailModel.getFinalArray();
                    if (finalArrayClassTeacherDetailModelList != null) {
                        fillDataList();
                        Utils.dismissDialog();
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", finalTermIdStr);
        return map;
    }

    // CALL InsertClassTeacher API HERE
    private void callInsertClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertClassTeachers(getInsertClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertClassTeachersModel, Response response) {
                Utils.dismissDialog();
                if (insertClassTeachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Record Inserted Successfully...!!");
                    finalArrayInsertClassTeachersModelList = insertClassTeachersModel.getFinalArray();
                    if (finalArrayInsertClassTeachersModelList != null) {
                        callClassTeacherApi();
                        Utils.dismissDialog();
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getInsertClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StandardID", finalStandardIdStr);
        map.put("ClassId", finalClassIdStr);
        map.put("Pk_EmployeID", finalTeacherIdStr);
        map.put("TermID", finalTermIdStr);
        map.put("Pk_ClsTeacherID", finalClassTeacherIdStr);
        return map;
    }

    //Use for fill the TeachetList with subject
    public void fillDataList() {
        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.GONE);
        fragmentClassTeacherBinding.classTeacherDetailList.setVisibility(View.VISIBLE);
        fragmentClassTeacherBinding.recyclerLinear.setVisibility(View.VISIBLE);
        fragmentClassTeacherBinding.listHeader.setVisibility(View.VISIBLE);

        classTeacherDetailListAdapter = new ClassTeacherDetailListAdapter(mContext, finalArrayClassTeacherDetailModelList, new onDeleteButton() {
            @Override
            public void deleteSentMessage() {
                finalClassTeacherIdStr = String.valueOf(classTeacherDetailListAdapter.getId());
                finalClassTeacherIdStr = finalClassTeacherIdStr.substring(1, finalClassTeacherIdStr.length() - 1);

                if (!finalClassTeacherIdStr.equalsIgnoreCase("")) {
                    callDeleteClassTeacherApi();
                }

            }
        }, new getEditpermission() {
            @Override
            public void getEditpermission() {
                getEditValuearray = new ArrayList<>();
                getEditValuearray = classTeacherDetailListAdapter.getEditId();
                updateTeacher();



            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        fragmentClassTeacherBinding.classTeacherDetailList.setLayoutManager(mLayoutManager);
        fragmentClassTeacherBinding.classTeacherDetailList.setItemAnimator(new DefaultItemAnimator());
        fragmentClassTeacherBinding.classTeacherDetailList.setAdapter(classTeacherDetailListAdapter);
    }

    //Use for fill the Standard Spinner
    public void fillGradeSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> StandardId = new ArrayList<Integer>();
        for (int m = 0; m < firstValueId.size(); m++) {
            StandardId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                StandardId.add(finalArrayStandardsList.get(i).getStandardID());
            }
        }
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");
        ArrayList<String> Standard = new ArrayList<String>();
        for (int z = 0; z < firstValue.size(); z++) {
            Standard.add(firstValue.get(z));

            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                Standard.add(finalArrayStandardsList.get(j).getStandard());
            }
        }

        spinnerstandardIdArray = new String[StandardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < StandardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(StandardId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentClassTeacherBinding.gradeSpinner.setAdapter(adapterTerm);

    }

    //Use for fill the TeacherName Spinner
    public void fillTeacherSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> TeacherId = new ArrayList<Integer>();
        for (int m = 0; m < firstValueId.size(); m++) {
            TeacherId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayTeachersModelList.size(); i++) {
                TeacherId.add(finalArrayTeachersModelList.get(i).getPkEmployeeID());
            }
        }

        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<String> Teacher = new ArrayList<String>();
        for (int z = 0; z < firstValue.size(); z++) {
            Teacher.add(firstValue.get(z));
            for (int j = 0; j < finalArrayTeachersModelList.size(); j++) {
                Teacher.add(finalArrayTeachersModelList.get(j).getTeacher());
            }
        }
        spinnerteacherIdArray = new String[TeacherId.size()];

        spinnerTeacherMap = new HashMap<Integer, String>();
        for (int i = 0; i < TeacherId.size(); i++) {
            spinnerTeacherMap.put(i, String.valueOf(TeacherId.get(i)));
            spinnerteacherIdArray[i] = Teacher.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.teacherSpinner);

            popupWindow.setHeight(spinnerteacherIdArray.length > 4 ? 500 : spinnerteacherIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerteacherIdArray);
        fragmentClassTeacherBinding.teacherSpinner.setAdapter(adapterTerm);


    }

    //Use for fill the Class Spinner
    public void fillSection() {
        ArrayList<String> classname = new ArrayList<>();
        ArrayList<String> classId = new ArrayList<>();


        if (!standardName.equalsIgnoreCase("--Select--")) {
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                if (standardName.equalsIgnoreCase(finalArrayStandardsList.get(i).getStandard())) {
                    for (int j = 0; j < finalArrayStandardsList.get(i).getSectionDetail().size(); j++) {
                        classname.add(finalArrayStandardsList.get(i).getSectionDetail().get(j).getSection() + "|"
                                + String.valueOf(finalArrayStandardsList.get(i).getSectionDetail().get(j).getSectionID()));
                    }
                }
            }
        }

        if (fragmentClassTeacherBinding.sectionLinear.getChildCount() > 0) {
            fragmentClassTeacherBinding.sectionLinear.removeAllViews();
        }
        try {
            for (int i = 0; i < 1; i++) {
                View convertView = LayoutInflater.from(mContext).inflate(R.layout.list_checkbox, null);
                radioGroup = (RadioGroup) convertView.findViewById(R.id.radiogroup);

                for (int k = 0; k < classname.size(); k++) {
                    RadioButton radioButton = new RadioButton(mContext);
                    radioButton.setButtonDrawable(R.drawable.check_uncheck);
                    radioButton.setPadding(10, 10, 10, 10);
                    radioButton.setTextColor(getResources().getColor(R.color.black));

                    String[] splitValue = classname.get(k).split("\\|");
                    radioButton.setText(splitValue[0]);
                    radioButton.setTag(splitValue[1]);
                    if (editSectionStr.equalsIgnoreCase(radioButton.getText().toString())) {
                        radioButton.setChecked(true);
                    }
                    radioGroup.addView(radioButton);
                }
                radioGroup.setOnCheckedChangeListener(mCheckedListner);


                fragmentClassTeacherBinding.sectionLinear.addView(convertView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentClassTeacherBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentClassTeacherBinding.termSpinner.setAdapter(adapterTerm);
        finalTermIdStr = spinnerTermMap.get(0);
    }


    // CALL DeleteClassTeacher API HERE
    private void callDeleteClassTeacherApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().DeleteClassTeacher(getDeleteClassTeacherDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel insertClassTeachersModel, Response response) {
                Utils.dismissDialog();
                if (insertClassTeachersModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    return;
                }
                if (insertClassTeachersModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext, "Record Deleted Successfully...!!");
                    finalArrayInsertClassTeachersModelList = insertClassTeachersModel.getFinalArray();
                    if (finalArrayInsertClassTeachersModelList != null) {
                        callClassTeacherApi();
                        Utils.dismissDialog();
                    } else {
                        fragmentClassTeacherBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getDeleteClassTeacherDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Pk_ClsTeacherID", finalClassTeacherIdStr);
        return map;
    }


    public void updateTeacher(){

        for (int i = 0; i < getEditValuearray.size(); i++) {
            String[] spiltValue = getEditValuearray.get(i).split("\\|");
            finalClassTeacherIdStr = spiltValue[0];
            editClassteacherStr = spiltValue[1];
            editGradeStr = spiltValue[2];
            editSectionStr = spiltValue[3];
        }

        if (!editClassteacherStr.equalsIgnoreCase("")){
                for (int i = 0; i < spinnerteacherIdArray.length; i++) {
                    if (spinnerteacherIdArray[i].trim().equalsIgnoreCase(editClassteacherStr.trim())) {
                        fragmentClassTeacherBinding.teacherSpinner.setSelection(i);
                    }
                }
        }
        if (!editGradeStr.equalsIgnoreCase("")){
            for (int i = 0; i < spinnerstandardIdArray.length; i++) {
                if (spinnerstandardIdArray[i].trim().equalsIgnoreCase(editGradeStr.trim())) {
                    fragmentClassTeacherBinding.gradeSpinner.setSelection(i);
                }
            }
        }
        if (!editSectionStr.equalsIgnoreCase("")){
            fillSection();
        }
    }
}

