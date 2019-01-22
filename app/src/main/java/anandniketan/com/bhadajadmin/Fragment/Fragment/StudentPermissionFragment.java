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
import anandniketan.com.bhadajadmin.Adapter.StudentPermissionSubmenuAdapter;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentPermissionBinding;


public class StudentPermissionFragment extends Fragment {

    private FragmentStudentPermissionBinding fragmentStudentPermissionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String status, reportcardstatus, reportcarddeletestatus, reportcardupdatestatus, reportcardviewstatus, onlinepaystatus, onlinepaydeletestatus, onlinepayupdatestatus, onlinepayviewstatus, markstatus, markdeletestatus, markupdatestatus, markviewstatus, suggestionstatus, suggestiondeletestatus, suggestionupdatestatus, suggestionviewstatus;

    public StudentPermissionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentPermissionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_permission, container, false);

        rootView = fragmentStudentPermissionBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;

        Bundle bundle = this.getArguments();

        status = bundle.getString("status");

        reportcardstatus = bundle.getString("reportcardstatus");
        reportcarddeletestatus = bundle.getString("reportcarddeletestatus");
        reportcardupdatestatus = bundle.getString("reportcardupdatestatus");
        reportcardviewstatus = bundle.getString("reportcardviewstatus");

        onlinepaystatus = bundle.getString("onlinepaystatus");
        onlinepaydeletestatus = bundle.getString("onlinepaydeletestatus");
        onlinepayupdatestatus = bundle.getString("onlinepayupdatestatus");
        onlinepayviewstatus = bundle.getString("onlinepayviewstatus");

        markstatus = bundle.getString("markstatus");
        markdeletestatus = bundle.getString("markdeletestatus");
        markupdatestatus = bundle.getString("markupdatestatus");
        markviewstatus = bundle.getString("markviewstatus");

        suggestionstatus = bundle.getString("suggestionstatus");
        suggestiondeletestatus = bundle.getString("suggestiondeletestatus");
        suggestionupdatestatus = bundle.getString("suggestionupdatestatus");
        suggestionviewstatus = bundle.getString("suggestionviewstatus");

        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "permission_inside.png")
                .fitCenter()
                .into(fragmentStudentPermissionBinding.circleImageView);

        fragmentStudentPermissionBinding.studentPermissionSubmenuGridView.setAdapter(new StudentPermissionSubmenuAdapter(mContext, reportcardstatus, onlinepaystatus, markstatus, suggestionstatus));

    }

    public void setListners() {
        fragmentStudentPermissionBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentPermissionBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentStudentPermissionBinding.studentPermissionSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && reportcardstatus.equalsIgnoreCase("true")) {

                    fragment = new ResultPermisssionFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("reportcarddeletestatus", reportcarddeletestatus);
                    bundle.putString("reportcardupdatestatus", reportcardupdatestatus);
                    bundle.putString("reportcardviewstatus", reportcardviewstatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 1 && onlinepaystatus.equalsIgnoreCase("true")) {
                    fragment = new OnlinePaymentFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("onlinepaydeletestatus", onlinepaydeletestatus);
                    bundle.putString("onlinepayupdatestatus", onlinepayupdatestatus);
                    bundle.putString("onlinepayviewstatus", onlinepayviewstatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 2 && markstatus.equalsIgnoreCase("true")) {
                    fragment = new MarkSyllabusPermission();

                    Bundle bundle = new Bundle();
                    bundle.putString("markdeletestatus", markdeletestatus);
                    bundle.putString("markupdatestatus", markupdatestatus);
                    bundle.putString("markviewstatus", markviewstatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 3 && suggestionstatus.equalsIgnoreCase("true")) {
                    fragment = new SuggestionFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("suggestiondeletestatus", suggestiondeletestatus);
                    bundle.putString("suggestionupdatestatus", suggestionupdatestatus);
                    bundle.putString("suggestionviewstatus", suggestionviewstatus);
                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }
}

