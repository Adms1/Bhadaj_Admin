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
import anandniketan.com.bhadajadmin.Adapter.MyLeaveSubMenuAdapter;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.databinding.FragmentMyLeaveBinding;


public class MyLeaveFragment extends Fragment {

    private FragmentMyLeaveBinding fragmentMyLeaveBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String viewstatus, updatestatus, deletestatus, applyviewstatus, applyupdatestatus, applydeletestatus, balanceviewstatus, balanceupdatestatus, balancedeletestatus;

    public MyLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMyLeaveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_leave, container, false);

        rootView = fragmentMyLeaveBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");
        updatestatus = bundle.getString("updatestatus");
        deletestatus = bundle.getString("deletestatus");

        applyviewstatus = bundle.getString("applyviewstatus");
        applyupdatestatus = bundle.getString("applyupdatestatus");
        applydeletestatus = bundle.getString("applydeletestatus");

        balanceviewstatus = bundle.getString("balanceviewstatus");
        balanceupdatestatus = bundle.getString("balanceupdatestatus");
        balancedeletestatus = bundle.getString("balancedeletestatus");

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 21;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "Permission.png")
                .fitCenter()
                .into(fragmentMyLeaveBinding.circleImageView);
        fragmentMyLeaveBinding.staffMyLeaveSubmenuGridView.setAdapter(new MyLeaveSubMenuAdapter(mContext));

    }

    public void setListners() {
        fragmentMyLeaveBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentMyLeaveBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentMyLeaveBinding.staffMyLeaveSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position == 0) {
                    fragment = new ApplyLeaveFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                   AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 22;
                } else if (position == 1) {
                    fragment = new MyLeaveBalanceFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 22;
                }*/
            }
        });
    }
}

