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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ConsistentAbsentTeacherAdapter;
import anandniketan.com.bhadajadmin.Adapter.StandardwiseStudentAttendaceAdapter;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAttendaceSummaryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AttendaceSummaryFragment extends Fragment {

    public AttendaceSummaryFragment() {
    }

    private FragmentAttendaceSummaryBinding fragmentAttendaceSummaryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    int Year, Month, Day;
    Calendar calendar;
    private String Datestr;
    private StandardwiseStudentAttendaceAdapter standardwiseStudentAttendaceAdapter;
    private ConsistentAbsentTeacherAdapter consistentAbsentTeacherAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAttendaceSummaryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendace_summary, container, false);

        rootView = fragmentAttendaceSummaryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initViews();
        setListner();

        return rootView;
    }

    public void initViews() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);

        callStudentApi();
        callStaffApi();
    }

    public void setListner() {
        fragmentAttendaceSummaryBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAttendaceSummaryBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new StudentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    // CALL Staff Attendace API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffAttendace(getStaffDetail(), new retrofit.Callback<StaffAttendaceModel>() {

            @Override
            public void success(StaffAttendaceModel staffUser, Response response) {
//                Utils.dismissDialog();
                if (staffUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (staffUser.getSuccess().equalsIgnoreCase("True")) {
                    List<FinalArrayStaffModel> staffArray = staffUser.getFinalArray();
                    for (int i = 0; i < staffArray.size(); i++) {
                        FinalArrayStaffModel studentObj = staffArray.get(i);
                        if (studentObj != null) {
                            fragmentAttendaceSummaryBinding.absentstaffCountTxt.setText(studentObj.getStaffAbsent());
                            fragmentAttendaceSummaryBinding.leavestaffCountTxt.setText(studentObj.getStaffLeave());
                            fragmentAttendaceSummaryBinding.presentstaffCountTxt.setText(studentObj.getStaffPresent());
                            fragmentAttendaceSummaryBinding.totalstaffCountTxt.setText(studentObj.getTotalStaff());
                        }
                    }
                    if (staffUser.getFinalArray().size() > 0) {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.GONE);

                        consistentAbsentTeacherAdapter = new ConsistentAbsentTeacherAdapter(mContext, staffUser);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentAttendaceSummaryBinding.consistentAbsentTeacherList.setLayoutManager(mLayoutManager);
                        fragmentAttendaceSummaryBinding.consistentAbsentTeacherList.setItemAnimator(new DefaultItemAnimator());
                        fragmentAttendaceSummaryBinding.consistentAbsentTeacherList.setAdapter(consistentAbsentTeacherAdapter);
                    } else {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getStaffDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }

    // CALL Student Attendace API HERE
    private void callStudentApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStudentAttendace(getStudentDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentUser, Response response) {
                Utils.dismissDialog();
                if (studentUser == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {

                    List<FinalArrayStudentModel> studentArray = studentUser.getFinalArray();
                    for (int i = 0; i < studentArray.size(); i++) {
                        FinalArrayStudentModel studentObj = studentArray.get(i);
                        if (studentObj != null) {
                            fragmentAttendaceSummaryBinding.absentstudentCountTxt.setText(studentObj.getStudentAbsent());
                            fragmentAttendaceSummaryBinding.leavestudentCountTxt.setText(studentObj.getStudentLeave());
                            fragmentAttendaceSummaryBinding.presentstudentCountTxt.setText(studentObj.getStudentPresent());
                            fragmentAttendaceSummaryBinding.totalstudentCountTxt.setText(studentObj.getTotalStudent());
                        }
                    }
                    if (studentUser.getFinalArray().size() > 0) {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.GONE);
                        standardwiseStudentAttendaceAdapter = new StandardwiseStudentAttendaceAdapter(mContext, studentUser);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setLayoutManager(mLayoutManager);
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setItemAnimator(new DefaultItemAnimator());
                        fragmentAttendaceSummaryBinding.standardwiseStudentAttendacelist.setAdapter(standardwiseStudentAttendaceAdapter);
                    } else {
                        fragmentAttendaceSummaryBinding.txtNoRecords.setVisibility(View.VISIBLE);
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

    private Map<String, String> getStudentDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }
}
