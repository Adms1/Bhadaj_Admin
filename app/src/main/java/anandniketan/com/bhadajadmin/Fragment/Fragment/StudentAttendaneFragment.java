package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AttendanceAdapter;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentAttendaneBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentAttendaneFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentStudentAttendaneBinding fragmentStudentAttendaneBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    List<FinalArrayStudentModel> finalArrayStudentNameModelList;
    String FinalStandardIdStr, FinalClassIdStr, StandardName, FinalStandardStr, FinalSectionStr, FinalDataStr;
    AttendanceAdapter attendanceAdapter;

    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private static String dateFinal;
    private DatePickerDialog datePickerDialog;

    public StudentAttendaneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentAttendaneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_attendane, container, false);

        rootView = fragmentStudentAttendaneBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callStandardApi();

        return rootView;
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentStudentAttendaneBinding.dateButton.setText(Utils.getTodaysDate());

        fragmentStudentAttendaneBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentAttendaneBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentAttendaneBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentAttendaneBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentStudentAttendaneBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid.toString();
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                FinalStandardStr = name;
                Log.d("StandardName", StandardName);
                fillSection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentStudentAttendaneBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentStudentAttendaneBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentStudentAttendaneBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid.toString();
                FinalSectionStr = selectedsectionstr;
                Log.d("FinalClassIdStr", FinalClassIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentAttendaneBinding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(StudentAttendaneFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentStudentAttendaneBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAttendence_AdminApi();
            }
        });
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

    // CALL Attendence_Admin API HERE
    private void callAttendence_AdminApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAttendence_Admin(getAttendence_AdminDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel attendanceModel, Response response) {
//                Utils.dismissDialog();
                if (attendanceModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    if (attendanceModel.getFinalArray().size() == 0) {
                        fragmentStudentAttendaneBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentStudentAttendaneBinding.listHeader.setVisibility(View.GONE);
                        fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (attendanceModel.getSuccess().equalsIgnoreCase("True")) {
                    if (attendanceModel.getFinalArray().size() > 0) {
                        finalArrayStudentNameModelList = attendanceModel.getFinalArray();
                        fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentAttendaneBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.listHeader.setVisibility(View.VISIBLE);
                        fragmentStudentAttendaneBinding.totalTxt.setText(Html.fromHtml("Total Student : " + "<font color='#1B88C8'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotal() + "</b>"));
                        fragmentStudentAttendaneBinding.presentTxt.setText(Html.fromHtml("Present : " + "<font color='#a4c639'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotalPresent() + "</b>"));
                        fragmentStudentAttendaneBinding.absentTxt.setText(Html.fromHtml("Absent : " + "<font color='#ff0000'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotalAbsent() + "</b>"));
                        fragmentStudentAttendaneBinding.leaveTxt.setText(Html.fromHtml("Leave : " + "<font color='#ff9623'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotalLeave() + "</b>"));
                        fragmentStudentAttendaneBinding.ondutyTxt.setText(Html.fromHtml("OnDuty : " + "<font color='#d8b834'>" + "<b>" + attendanceModel.getFinalArray().get(0).getTotalOnDuty() + "</b>"));
                        SetData();
                        attendanceAdapter = new AttendanceAdapter(mContext, attendanceModel);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentStudentAttendaneBinding.studentAttendanceList.setLayoutManager(mLayoutManager);
                        fragmentStudentAttendaneBinding.studentAttendanceList.setItemAnimator(new DefaultItemAnimator());
                        fragmentStudentAttendaneBinding.studentAttendanceList.setAdapter(attendanceAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentStudentAttendaneBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getAttendence_AdminDetail() {
        Map<String, String> map = new HashMap<>();
        FinalDataStr = fragmentStudentAttendaneBinding.dateButton.getText().toString();
        map.put("AttDate", FinalDataStr);
        map.put("StdID", FinalStandardIdStr);
        map.put("ClsID", FinalClassIdStr);

        return map;
    }

    public void fillGradeSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("All");
//
        ArrayList<String> standardname = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            standardname.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                standardname.add(finalArrayStandardsList.get(i).getStandard());
            }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            standardId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                standardId.add(finalArrayStandardsList.get(j).getStandardID());
            }
//        }
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAttendaneBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentStudentAttendaneBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
//        firstSectionValue.add("All");
        ArrayList<Integer> firstSectionId = new ArrayList<>();
//        firstSectionId.add(0);

//        if (StandardName.equalsIgnoreCase("All")) {
//            for (int j = 0; j < firstSectionValue.size(); j++) {
//                sectionname.add(firstSectionValue.get(j));
//            }
//            for (int i = 0; i < firstSectionId.size(); i++) {
//                sectionId.add(firstSectionId.get(i));
//            }
//
//        }
        for (int z = 0; z < finalArrayStandardsList.size(); z++) {
            if (StandardName.equalsIgnoreCase(finalArrayStandardsList.get(z).getStandard())) {
//                for (int j = 0; j < firstSectionValue.size(); j++) {
//                    sectionname.add(firstSectionValue.get(j));
                    for (int i = 0; i < finalArrayStandardsList.get(z).getSectionDetail().size(); i++) {
                        sectionname.add(finalArrayStandardsList.get(z).getSectionDetail().get(i).getSection());
//                    }
                }
//                for (int j = 0; j < firstSectionId.size(); j++) {
//                    sectionId.add(firstSectionId.get(j));
                    for (int m = 0; m < finalArrayStandardsList.get(z).getSectionDetail().size(); m++) {
                        sectionId.add(finalArrayStandardsList.get(z).getSectionDetail().get(m).getSectionID());
                    }
//                }
            }
        }

        String[] spinnersectionIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<Integer, String>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentAttendaneBinding.sectionSpinner);

            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentStudentAttendaneBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalClassIdStr = spinnerSectionMap.get(0);
        callAttendence_AdminApi();
    }

    public void SetData() {

        for (int j = 0; j < finalArrayStudentNameModelList.size(); j++) {
            for (int k = 0; k < finalArrayStudentNameModelList.get(j).getStudentDetail().size(); k++) {
                if (finalArrayStudentNameModelList.get(j).getStudentDetail().get(k).getAttendenceStatus().equalsIgnoreCase("-2")) {
                    finalArrayStudentNameModelList.get(j).getStudentDetail().get(j).setAttendenceStatus("1");
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Selected Date : " + Day + "/" + Month + "/" + Year;
        String datestr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        mDay = dayOfMonth;
        mMonth = monthOfYear + 1;
        mYear = year;
        String d, m, y;
        d = Integer.toString(mDay);
        m = Integer.toString(mMonth);
        y = Integer.toString(mYear);

        if (mDay < 10) {
            d = "0" + d;
        }
        if (mMonth < 10) {
            m = "0" + m;
        }

        fragmentStudentAttendaneBinding.dateButton.setText(d + "/" + m + "/" + y);


    }

}

