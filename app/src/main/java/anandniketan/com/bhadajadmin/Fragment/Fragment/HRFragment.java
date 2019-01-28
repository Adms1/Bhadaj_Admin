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

import java.util.ArrayList;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.HrSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.IconHeaderModel;
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
    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "HR/" + "Search%20Staff.png",
            AppConfiguration.BASEURL_IMAGES + "HR/" + "Staff%20Leave.png",
            AppConfiguration.BASEURL_IMAGES + "HR/" + "Menu%20Permission.png",
            AppConfiguration.BASEURL_IMAGES + "HR/" + "Attendance%20Report.png",
            AppConfiguration.BASEURL_IMAGES + "HR/" + "Daily%20Report.png",
    };
    private Map<String, PermissionDataModel.Detaill> permissionMap;
    public String[] mThumbNames = {"Search Staff", "Staff Leave", "Menu Permission", "Attendance Report", "Daily Report"};
    private ArrayList<IconHeaderModel> newArr;

    public HRFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHrBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hr, container, false);

        newArr = new ArrayList<>();
        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "HR");

        rootView = fragmentHrBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        for (int i = 0; i < mThumbNames.length; i++) {
            if (permissionMap.containsKey(mThumbNames[i]) && permissionMap.get(mThumbNames[i]).getStatus().equalsIgnoreCase("true")) {

                IconHeaderModel iconHeaderModel = new IconHeaderModel();
                iconHeaderModel.setName(mThumbNames[i]);
                iconHeaderModel.setUrl(mThumbIds[i]);
                newArr.add(iconHeaderModel);
            }
        }

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 1;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "HR.png")
                .fitCenter()
                .into(fragmentHrBinding.circleImageView);
        fragmentHrBinding.hrSubmenuGridView.setAdapter(new HrSubMenuAdapter(mContext, newArr));

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
                    AppConfiguration.HRstaffseachviewstatus = permissionMap.get("Search Staff").getIsuserview();
//                    bundle.putString("status", "true");
//                    bundle.putString("updatestatus", permissionMap.get("Search Staff").getIsuserupdate());
//                    bundle.putString("deletestatus", permissionMap.get("Search Staff").getIsuserdelete());
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

