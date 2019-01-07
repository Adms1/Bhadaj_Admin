package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AttendenceReportSubMenuAdapter;
import anandniketan.com.bhadajadmin.Adapter.StaffLeavesubmenuAdapter;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.databinding.FragmentAttendenceReportBinding;
import anandniketan.com.bhadajadmin.databinding.FragmentStaffLeaveBinding;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentAttendaneBinding;


public class AttendenceReportFragment extends Fragment {

    private FragmentAttendenceReportBinding fragmentAttendenceReportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;


    public AttendenceReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAttendenceReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendence_report, container, false);

        rootView = fragmentAttendenceReportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 51;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "HR/" + "Attendance%20Report.png")
                .fitCenter()
                .into(fragmentAttendenceReportBinding.circleImageView);
        fragmentAttendenceReportBinding.attendenceReportSubmenuGridView.setAdapter(new AttendenceReportSubMenuAdapter(mContext));

    }

    public void setListners() {
        fragmentAttendenceReportBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAttendenceReportBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HRFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentAttendenceReportBinding.attendenceReportSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (position == 0) {
                    fragment = new InOutSummaryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;
                } else if (position == 1) {
                    fragment = new InOutSummaryDetailsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;
                }else if (position == 2) {
                    fragment = new EmployeePresentDetail();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;
                }
            }
        });
    }
}

