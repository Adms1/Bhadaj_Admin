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
import anandniketan.com.bhadajadmin.Adapter.HrSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.databinding.FragmentHrBinding;


public class HRFragment extends Fragment {

    private FragmentHrBinding fragmentHrBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Map<String, PermissionDataModel.Detaill> permissionMap;

    public HRFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHrBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hr, container, false);

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "HR");

        rootView = fragmentHrBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 5;
        Glide.with(mContext)
                .load( AppConfiguration.BASEURL_IMAGES + "HR/" + "hr_inside.png")
                .fitCenter()
                .into(fragmentHrBinding.circleImageView);
        fragmentHrBinding.hrSubmenuGridView.setAdapter(new HrSubMenuAdapter(mContext));

    }

    public void setListners() {
        fragmentHrBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentHrBinding.btnBackhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentHrBinding.hrSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Search Staff").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SearchStaffFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Search Staff").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Search Staff").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Search Staff").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                             AppConfiguration.firsttimeback = true;
                 AppConfiguration.position = 51;
                } else if (position == 1 && permissionMap.get("Staff Leave").getStatus().equalsIgnoreCase("true")) {
                    fragment = new StaffLeaveFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("status", permissionMap.get("Staff Leave").getIsuserview());
//                    bundle.putString("updatestatus", permissionMap.get("Staff Leave").getIsuserupdate());
//                    bundle.putString("deletestatus", permissionMap.get("Staff Leave").getIsuserdelete());
//
//                    bundle.putString("requeststatus", permissionMap.get("Leave Request").getIsuserview());
//                    bundle.putString("requestupdatestatus", permissionMap.get("Leave Request").getIsuserupdate());
//                    bundle.putString("requestdeletestatus", permissionMap.get("Leave Request").getIsuserdelete());
//
//                    bundle.putString("balancestatus", permissionMap.get("Leave Balance").getIsuserview());
//                    bundle.putString("balanceupdatestatus", permissionMap.get("Leave Balance").getIsuserupdate());
//                    bundle.putString("balancedeletestatus", permissionMap.get("Leave Balance").getIsuserdelete());
//
//                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 51;
                } else if (position == 2 && permissionMap.get("Menu Permission").getStatus().equalsIgnoreCase("true")) {
                    fragment = new MenuPermissionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Menu Permission").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Menu Permission").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Menu Permission").getIsuserdelete());
                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 51;
                } else if (position == 3 && permissionMap.get("Attendance Report").getStatus().equalsIgnoreCase("true")) {
                    fragment = new AttendenceReportFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("status", permissionMap.get("Attendance Report").getIsuserview());
                    bundle.putString("updatestatus", permissionMap.get("Attendance Report").getIsuserupdate());
                    bundle.putString("deletestatus", permissionMap.get("Attendance Report").getIsuserdelete());

                    bundle.putString("inoutstatus", permissionMap.get("In Out Summary Details").getStatus());
                    bundle.putString("inoutviewstatus", permissionMap.get("In Out Summary Details").getIsuserview());
                    bundle.putString("inoutupdatestatus", permissionMap.get("In Out Summary Details").getIsuserupdate());
                    bundle.putString("inoutdeletestatus", permissionMap.get("In Out Summary Details").getIsuserdelete());

                    bundle.putString("employeestatus", permissionMap.get("In Out Summary Details").getStatus());
                    bundle.putString("employeeviewstatus", permissionMap.get("Employee In Out Details").getIsuserview());
                    bundle.putString("employeeupdatestatus", permissionMap.get("Employee In Out Details").getIsuserupdate());
                    bundle.putString("employeedeletestatus", permissionMap.get("Employee In Out Details").getIsuserdelete());

                    bundle.putString("emppresentstatus", permissionMap.get("In Out Summary Details").getStatus());
                    bundle.putString("emppresentviewstatus", permissionMap.get("Employee Present Details").getIsuserview());
                    bundle.putString("emppresentupdatestatus", permissionMap.get("Employee Present Details").getIsuserupdate());
                    bundle.putString("emppresentdeletestatus", permissionMap.get("Employee Present Details").getIsuserdelete());

                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 51;
                } else if (position == 4 && permissionMap.get("Daily Report").getStatus().equalsIgnoreCase("true")) {
                    fragment = new DailyReportFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 51;
                }
            }
        });
    }
}

