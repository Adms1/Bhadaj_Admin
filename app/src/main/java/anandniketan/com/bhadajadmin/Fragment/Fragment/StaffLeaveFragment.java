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

import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.StaffLeavesubmenuAdapter;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.databinding.FragmentStaffLeaveBinding;


public class StaffLeaveFragment extends Fragment {

    private FragmentStaffLeaveBinding fragmentstaffleaveBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;
    private String FinalTermIdStr = "";
//    private String viewstatus, updatestatus, deletestatus, requeststatus, requestupdatestatus, requestdeletestatus, balancestatus, balanceupdatestatus,balancedeletestatus;

    public StaffLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentstaffleaveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_leave, container, false);

        rootView = fragmentstaffleaveBinding.getRoot();
        mContext = getActivity().getApplicationContext();
//
//        Bundle bundle = this.getArguments();
//        viewstatus = bundle.getString("viewstatus");
//        updatestatus = bundle.getString("updatestatus");
//        deletestatus = bundle.getString("deletestatus");
//
//        requeststatus = bundle.getString("requeststatus");
//        requestupdatestatus = bundle.getString("requestupdatestatus");
//        requestdeletestatus = bundle.getString("requestdeletestatus");
//
//        balancestatus = bundle.getString("balancestatus");
//        balanceupdatestatus = bundle.getString("balanceupdatestatus");
//        balancedeletestatus = bundle.getString("balancedeletestatus");

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 51;
        Glide.with(mContext).load(AppConfiguration.BASEURL_IMAGES + "Main/" + "permission_inside.png").fitCenter().into(fragmentstaffleaveBinding.circleImageView);
        fragmentstaffleaveBinding.staffLeaveSubmenuGridView.setAdapter(new StaffLeavesubmenuAdapter(mContext));

    }

    public void setListners() {
        fragmentstaffleaveBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentstaffleaveBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HRFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentstaffleaveBinding.staffLeaveSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new LeaveRequestFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("requeststatus", requeststatus);
//                    bundle.putString("requestupdatestatus", requestupdatestatus);
//                    bundle.putString("requestdeletestatus", requestdeletestatus);
//                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 52;

                } else if (position == 1) {
                    fragment = new LeaveBalanceFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("balancestatus", balancestatus);
//                    bundle.putString("balanceupdatestatus", balanceupdatestatus);
//                    bundle.putString("balancedeletestatus", balancedeletestatus);
//                    fragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 52;
                }
            }
        });

    }


}

