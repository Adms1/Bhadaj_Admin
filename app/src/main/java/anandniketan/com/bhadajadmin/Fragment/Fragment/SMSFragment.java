package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AccountSubMenuAdapter;
import anandniketan.com.bhadajadmin.Adapter.ExapandableListAdapterSMSRepoetData;
import anandniketan.com.bhadajadmin.Adapter.SMSSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentSmBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SMSFragment extends Fragment {

    private FragmentSmBinding fragmentSmBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;


    public SMSFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sm, container, false);

        rootView = fragmentSmBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 3;
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "SMS/" + "sms_inside.png")
                .fitCenter()
                .into(fragmentSmBinding.circleImageView);
        fragmentSmBinding.smsSubmenuGridView.setAdapter(new SMSSubMenuAdapter(mContext));
        callSMSReportDataApi();
    }

    public void setListners() {
        fragmentSmBinding.btnBackSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentSmBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentSmBinding.smsSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new StudentAbsentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                } else if (position == 1) {
                    fragment = new BullkSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                } else if (position == 2) {
                    fragment = new SingleSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                } else if (position == 3) {
                    fragment = new EmployeeSmsFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }else if (position == 4) {
                    fragment = new AppSMSFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }else if (position == 5) {
                    fragment = new SMSStudentTransportFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }else if (position == 6) {
                    fragment = new SMSStudentMarksFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }else if (position == 7) {
                    fragment = new SMSReportFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 31;
                }
            }
        });
    }

    // CALL SMSReportData API HERE
    private void callSMSReportDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAllSMSDetail(getSMSReportDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                  //  Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();

                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
                    fragmentSmBinding.totalSmsCount.setText(inquiryDataModel.getTotal());
                    fragmentSmBinding.totalDeliveredSmsCount.setText(inquiryDataModel.getDelivered());
                    fragmentSmBinding.totalOtherSmsCount.setText(inquiryDataModel.getOther());


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

    private Map<String, String> getSMSReportDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("StartDate", Utils.getTodaysDate());
        map.put("EndDate", Utils.getTodaysDate());

        return map;
    }

}

