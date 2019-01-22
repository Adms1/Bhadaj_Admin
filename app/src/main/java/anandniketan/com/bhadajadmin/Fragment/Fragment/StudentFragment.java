package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StudentSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudentFragment extends Fragment {

    int Year, Month, Day;
    Calendar calendar;
    ArrayList<PermissionDataModel.Detaill> permission = new ArrayList<>();
    private FragmentStudentBinding fragmentStudentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String Datestr;
    private Map<String, PermissionDataModel.Detaill> permissionMap;

    public StudentFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppConfiguration.position = 1;
        AppConfiguration.firsttimeback = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student, container, false);

        rootView = fragmentStudentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 1;
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student");

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Student/" + "student_inside.png")
                .fitCenter()
                .into(fragmentStudentBinding.circleImageView);
        fragmentStudentBinding.studentSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, permissionMap));
        callStudentApi();
    }

    public void setListners() {

        fragmentStudentBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentBinding.btnBackAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentBinding.viewSummayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
                fragment = new AttendaceSummaryFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentBinding.studentSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Search Student").getStatus().equalsIgnoreCase("true")) {

                    fragment = new SearchStudentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Search Student").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 1 && permissionMap.get("View Inquiry").getStatus().equalsIgnoreCase("true")) {
                    //add fragment in backstack.
                    fragment = new StudentViewInquiryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("View Inquiry").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 2 && permissionMap.get("Student Transport").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentTranspotFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Student Transport").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 3 && permissionMap.get("Permission").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentPermissionFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("status", permissionMap.get("Permission").getIsuserview());

                    bundle.putString("reportcardstatus", permissionMap.get("Report Card").getStatus());
                    bundle.putString("reportcarddeletestatus", permissionMap.get("Report Card").getIsuserdelete());
                    bundle.putString("reportcardupdatestatus", permissionMap.get("Report Card").getIsuserupdate());
                    bundle.putString("reportcardviewstatus", permissionMap.get("Report Card").getIsuserview());

                    bundle.putString("onlinepaystatus", permissionMap.get("Online Payment").getStatus());
                    bundle.putString("onlinepaydeletestatus", permissionMap.get("Online Payment").getIsuserdelete());
                    bundle.putString("onlinepayupdatestatus", permissionMap.get("Online Payment").getIsuserupdate());
                    bundle.putString("onlinepayviewstatus", permissionMap.get("Online Payment").getIsuserview());

                    bundle.putString("markstatus", permissionMap.get("Marks/Syllabus").getStatus());
                    bundle.putString("markdeletestatus", permissionMap.get("Marks/Syllabus").getIsuserdelete());
                    bundle.putString("markupdatestatus", permissionMap.get("Marks/Syllabus").getIsuserupdate());
                    bundle.putString("markviewstatus", permissionMap.get("Marks/Syllabus").getIsuserview());

                    bundle.putString("suggestionstatus", permissionMap.get("Suggestion").getStatus());
                    bundle.putString("suggestiondeletestatus", permissionMap.get("Suggestion").getIsuserdelete());
                    bundle.putString("suggestionupdatestatus", permissionMap.get("Suggestion").getIsuserupdate());
                    bundle.putString("suggestionviewstatus", permissionMap.get("Suggestion").getIsuserview());

                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 4 && permissionMap.get("Attendance").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StudentAttendaneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Attendance").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Attendance").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Attendance").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 5 && permissionMap.get("Left/Active").getStatus().equalsIgnoreCase("true")) {
                    fragment = new LeftDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Left/Active").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 6 && permissionMap.get("New Register").getStatus().equalsIgnoreCase("true")) {
                    fragment = new GRRegisterFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("New Register").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 7 && permissionMap.get("Announcement").getStatus().equalsIgnoreCase("true")) {
                    fragment = new AnnoucementListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Announcement").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Announcement").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Announcement").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 8 && permissionMap.get("Circular").getStatus().equalsIgnoreCase("true")) {
                    fragment = new CircularListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Circular").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Circular").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Circular").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 9 && permissionMap.get("Planner").getStatus().equalsIgnoreCase("true")) {
                    fragment = new FragmentPlanner();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Planner").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Planner").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Planner").getIsuserdelete());

                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
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
                if (studentUser.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (studentUser.getSuccess().equalsIgnoreCase("True")) {
                    List<StudentAttendanceFinalArray> studentArray = studentUser.getFinalArray();
                    for (int i = 0; i < studentArray.size(); i++) {
                        StudentAttendanceFinalArray studentObj = studentArray.get(i);
                        if (studentObj != null) {
                            fragmentStudentBinding.totalStudentCount.setText(studentObj.getTotalStudent());
                            fragmentStudentBinding.totalPresentstudentCount.setText(studentObj.getStudentPresent());
                            fragmentStudentBinding.totalAbsentstudentCount.setText(studentObj.getStudentAbsent());
                            fragmentStudentBinding.totalLeavestudentCount.setText(studentObj.getStudentLeave());
                        }
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

