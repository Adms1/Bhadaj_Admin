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

import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AttendenceReportSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAttendenceReportBinding;


public class AttendenceReportFragment extends Fragment {

    private FragmentAttendenceReportBinding fragmentAttendenceReportBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    private Map<String, PermissionDataModel.Detaill> permissionMap;

    public AttendenceReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAttendenceReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendence_report, container, false);

        rootView = fragmentAttendenceReportBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "HR");

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
                if (position == 0 && permissionMap.get("In Out Summary").getStatus().equalsIgnoreCase("true")) {
                    fragment = new InOutSummaryFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("inoutstatus", permissionMap.get("In Out Summary").getStatus());
                    bundle.putString("inoutviewstatus", permissionMap.get("In Out Summary").getIsuserview());
                    bundle.putString("inoutupdatestatus", permissionMap.get("In Out Summary").getIsuserupdate());
                    bundle.putString("inoutdeletestatus", permissionMap.get("In Out Summary").getIsuserdelete());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;

                } else if (position == 1 && permissionMap.get("Employee In Out Details").getStatus().equalsIgnoreCase("true")) {
                    fragment = new InOutSummaryDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("employeestatus", permissionMap.get("Employee In Out Details").getStatus());
                    bundle.putString("employeeviewstatus", permissionMap.get("Employee In Out Details").getIsuserview());
                    bundle.putString("employeeupdatestatus", permissionMap.get("Employee In Out Details").getIsuserupdate());
                    bundle.putString("employeedeletestatus", permissionMap.get("Employee In Out Details").getIsuserdelete());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;

                } else if (position == 2 && permissionMap.get("Employee Present Details").getStatus().equalsIgnoreCase("true")) {
                    fragment = new EmployeePresentDetail();

                    Bundle bundle = new Bundle();
                    bundle.putString("emppresentstatus", permissionMap.get("Employee Present Details").getStatus());
                    bundle.putString("emppresentviewstatus", permissionMap.get("Employee Present Details").getIsuserview());
                    bundle.putString("emppresentupdatestatus", permissionMap.get("Employee Present Details").getIsuserupdate());
                    bundle.putString("emppresentdeletestatus", permissionMap.get("Employee Present Details").getIsuserdelete());
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

