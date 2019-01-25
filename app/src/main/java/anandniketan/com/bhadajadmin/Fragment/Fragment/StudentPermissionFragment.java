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
import anandniketan.com.bhadajadmin.Adapter.StudentPermissionSubmenuAdapter;
import anandniketan.com.bhadajadmin.Model.PermissionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentPermissionBinding;


public class StudentPermissionFragment extends Fragment {

    private FragmentStudentPermissionBinding fragmentStudentPermissionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String status, reportcardstatus, reportcarddeletestatus, reportcardupdatestatus, reportcardviewstatus, onlinepaystatus, onlinepaydeletestatus, onlinepayupdatestatus, onlinepayviewstatus, markstatus, markdeletestatus, markupdatestatus, markviewstatus, suggestionstatus, suggestiondeletestatus, suggestionupdatestatus, suggestionviewstatus;

    private Map<String, PermissionDataModel.Detaill> permissionMap;

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

        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student");

        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Student/" + "Permission.png")
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
                fragment = new StudentPermissionFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentStudentPermissionBinding.studentPermissionSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Report Card").getStatus().equalsIgnoreCase("true")) {

                    fragment = new ResultPermisssionFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("reportcarddeletestatus", permissionMap.get("Report Card").getIsuserdelete());
                    bundle.putString("reportcardupdatestatus", permissionMap.get("Report Card").getIsuserupdate());
                    bundle.putString("reportcardviewstatus", permissionMap.get("Report Card").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 1 && permissionMap.get("Online Payment").getStatus().equalsIgnoreCase("true")) {
                    fragment = new OnlinePaymentFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("onlinepaydeletestatus", permissionMap.get("Online Payment").getIsuserdelete());
                    bundle.putString("onlinepayupdatestatus", permissionMap.get("Online Payment").getIsuserupdate());
                    bundle.putString("onlinepayviewstatus", permissionMap.get("Online Payment").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 2 && permissionMap.get("Marks/Syllabus").getStatus().equalsIgnoreCase("true")) {
                    fragment = new MarkSyllabusPermission();

                    Bundle bundle = new Bundle();

                    bundle.putString("markdeletestatus", permissionMap.get("Marks/Syllabus").getIsuserdelete());
                    bundle.putString("markupdatestatus", permissionMap.get("Marks/Syllabus").getIsuserupdate());
                    bundle.putString("markviewstatus", permissionMap.get("Marks/Syllabus").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 3 && permissionMap.get("Suggestion").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SuggestionFragment();

                    Bundle bundle = new Bundle();

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
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }
}

