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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StaffSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStaffBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StaffFragment extends Fragment {

    int Year, Month, Day;
    Calendar calendar;
    private FragmentStaffBinding fragmentStaffBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String Datestr;

    public StaffFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStaffBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff, container, false);

        rootView = fragmentStaffBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 2;
        fragmentStaffBinding.staffSubmenuGridView.setAdapter(new StaffSubMenuAdapter(mContext));
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Staff/" + "staff_inside.png")
                .fitCenter()
                .into(fragmentStaffBinding.circleImageView);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        Datestr = Utils.getTodaysDate();
        Log.d("TodayDate", Datestr);
        callStaffApi();
    }

    public void setListners() {
        fragmentStaffBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStaffBinding.btnBackstaffAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStaffBinding.staffSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new ClassTeacherFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else if (position == 1) {
                    fragment = new AssignSubjectFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else if (position == 2) {
                    fragment = new TimeTableFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else if (position == 3) {
                    fragment = new ExamsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                }/*else if (position == 4) {
                    fragment = new HomeworkNotDoneFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                            AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                }*/ else if (position == 5) {
                    fragment = new MyLeaveFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } /*else if (position == 6) {
                    fragment = new ViewMarksFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                            AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;L̥
                }*/
            }
        });
    }


    // CALL Staff Attendace API HERE
    private void callStaffApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStaffAttendace(getStaffDetail(), new retrofit.Callback<StaffAttendaceModel>() {

            @Override
            public void success(StaffAttendaceModel staffUser, Response response) {
                Utils.dismissDialog();
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
                            fragmentStaffBinding.totalAbsentstaffCount.setText(studentObj.getStaffAbsent());
                            fragmentStaffBinding.totalLeavestaffCount.setText(studentObj.getStaffLeave());
                            fragmentStaffBinding.totalPresentstaffCount.setText(studentObj.getStaffPresent());
                            fragmentStaffBinding.totalStaffCount.setText(studentObj.getTotalStaff());
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

    private Map<String, String> getStaffDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", Datestr);
        return map;
    }
}

