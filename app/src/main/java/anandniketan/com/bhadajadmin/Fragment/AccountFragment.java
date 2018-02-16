package anandniketan.com.bhadajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AccountSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAccountBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding fragmentAccountBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //Use for Store Resopnse
    List<AccountFeesCollectionModel> collectionModelList;
    String FinalTermtermdetailId = "1";

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAccountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);

        rootView = fragmentAccountBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        initViews();
        setListners();
        SelectTerm();
        callAccountFeesStatusApi();
        return rootView;
    }

    public void initViews() {
        Glide.with(mContext)
                .load(AppConfiguration.BASEURL_IMAGES + "Account/" + "account_inside.png")
                .fitCenter()
                .into(fragmentAccountBinding.circleImageView);
        fragmentAccountBinding.accountSubmenuGridView.setAdapter(new AccountSubMenuAdapter(mContext));
        AppConfiguration.TermDetailName = "Term 1";
    }

    public void setListners() {
        fragmentAccountBinding.btnBackAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.ReverseTermDetailId = "";
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentAccountBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAccountBinding.accountSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new AccountSummaryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 2) {
                    fragment = new FeeStructureFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 3) {
                    fragment = new StudentDiscountFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 4) {
                    fragment = new DailyFeesCollectionFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 5) {
                    fragment = new ImprestFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (position == 6) {
                    fragment = new StudentLedgerFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }else if (position == 7) {
                    fragment = new CheckPaymentFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });
        fragmentAccountBinding.termRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                {
                    if (null != rb && checkedId > -1) {
                        switch (checkedId) {
                            case R.id.term1_radio_button:
                                FinalTermtermdetailId = fragmentAccountBinding.term1RadioButton.getTag().toString();
                                AppConfiguration.TermDetailName = fragmentAccountBinding.term1RadioButton.getText().toString();
                                break;
                            case R.id.term2_radio_button:
                                FinalTermtermdetailId = fragmentAccountBinding.term2RadioButton.getTag().toString();
                                AppConfiguration.TermDetailName = fragmentAccountBinding.term2RadioButton.getText().toString();
                                break;
                        }
                    }
                    callAccountFeesStatusApi();
                }
            }
        });

    }

    // CALL AccountFeesStatus API HERE
    private void callAccountFeesStatusApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAccountFeesStatusDetail(getAccountDetail(), new retrofit.Callback<AccountFeesStatusModel>() {

            @Override
            public void success(AccountFeesStatusModel accountFeesStatusModel, Response response) {
                Utils.dismissDialog();
                if (accountFeesStatusModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStatusModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("True")) {
                    for (int i = 0; i < accountFeesStatusModel.getFinalArray().size(); i++) {
                        collectionModelList = accountFeesStatusModel.getFinalArray().get(i).getCollection();
                    }
                    if (collectionModelList != null) {
                        fillData();
                    }
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

    private Map<String, String> getAccountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("Term_ID", "");
        map.put("TermDetailID", FinalTermtermdetailId);
        return map;
    }

    // Use for fill data account data
    public void fillData() {
        for (int i = 0; i < collectionModelList.size(); i++) {
            AppConfiguration.TermId = String.valueOf(collectionModelList.get(i).getTermID());
            fragmentAccountBinding.totalAmountCount.setText("₹" + " " + String.valueOf(collectionModelList.get(i).getTotalAmt()));
            fragmentAccountBinding.totalReceiveAmountCount.setText("₹" + " " + String.valueOf(collectionModelList.get(i).getTotalRcv()));
            fragmentAccountBinding.totalDueAmountCount.setText("₹" + " " + String.valueOf(collectionModelList.get(i).getTotalDue()));
        }
        Log.d("termid", AppConfiguration.TermId);
        AppConfiguration.TermDetailId = FinalTermtermdetailId;
    }

    //Use for get Selected Term value for next page
    public void SelectTerm() {
        if (!AppConfiguration.ReverseTermDetailId.equalsIgnoreCase("")) {
            if (AppConfiguration.ReverseTermDetailId.equalsIgnoreCase(fragmentAccountBinding.term1RadioButton.getTag().toString())) {
                fragmentAccountBinding.term1RadioButton.setChecked(true);
            } else if (AppConfiguration.ReverseTermDetailId.equalsIgnoreCase(fragmentAccountBinding.term2RadioButton.getTag().toString())) {
                fragmentAccountBinding.term2RadioButton.setChecked(true);
            }
        }
    }
}

