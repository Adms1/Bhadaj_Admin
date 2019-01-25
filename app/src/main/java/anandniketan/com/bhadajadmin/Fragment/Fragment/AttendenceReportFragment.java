package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAttendenceReportBinding;


public class AttendenceReportFragment extends Fragment {

    private FragmentAttendenceReportBinding fragmentAttendenceReportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String status, updatestatus, deletestatus, inoutstatus, inoutviewstatus, inoutupdatestatus, inoutdeletestatus, employeestatus, employeeviewstatus, employeeupdatestatus, employeedeletestatus, emppresentstatus, emppresentviewstatus, emppresentupdatestatus, emppresentdeletestatus;

    public AttendenceReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAttendenceReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendence_report, container, false);

        rootView = fragmentAttendenceReportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        status = bundle.getString("status");
        updatestatus = bundle.getString("updatestatus");
        deletestatus = bundle.getString("deletestatus");

        inoutstatus = bundle.getString("inoutstatus");
        inoutviewstatus = bundle.getString("inoutviewstatus");
        inoutupdatestatus = bundle.getString("inoutupdatestatus");
        inoutdeletestatus = bundle.getString("inoutdeletestatus");

        employeestatus = bundle.getString("employeestatus");
        employeeviewstatus = bundle.getString("employeeviewstatus");
        employeeupdatestatus = bundle.getString("employeeupdatestatus");
        employeedeletestatus = bundle.getString("employeedeletestatus");

        emppresentstatus = bundle.getString("emppresentstatus");
        emppresentviewstatus = bundle.getString("emppresentviewstatus");
        emppresentupdatestatus = bundle.getString("emppresentupdatestatus");
        emppresentdeletestatus = bundle.getString("emppresentdeletestatus");

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
                if (position == 0 && inoutstatus.equalsIgnoreCase("true")) {
                    fragment = new InOutSummaryFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("inoutstatus", inoutviewstatus);
                    bundle.putString("inoutupdatestatus", inoutupdatestatus);
                    bundle.putString("inoutdeletestatus", inoutdeletestatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;

                } else if (position == 1 && employeestatus.equalsIgnoreCase("true")) {
                    fragment = new InOutSummaryDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("employeestatus", employeeviewstatus);
                    bundle.putString("employeeupdatestatus", employeeupdatestatus);
                    bundle.putString("employeedeletestatus", employeedeletestatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;

                } else if (position == 2 && emppresentstatus.equalsIgnoreCase("true")) {
                    fragment = new EmployeePresentDetail();

                    Bundle bundle = new Bundle();
                    bundle.putString("emppresentstatus", emppresentviewstatus);
                    bundle.putString("emppresentupdatestatus", emppresentupdatestatus);
                    bundle.putString("emppresentdeletestatus", emppresentdeletestatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;
                } else {
                    Utils.ping(getContext(), "Access Denied");
                }
            }
        });
    }
}

