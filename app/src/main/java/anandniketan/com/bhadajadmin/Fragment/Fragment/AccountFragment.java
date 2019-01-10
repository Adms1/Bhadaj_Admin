package anandniketan.com.bhadajadmin.Fragment.Fragment;

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

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AccountSubMenuAdapter;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAccountBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountFragment extends Fragment {

    //Use for Store Resopnse
    List<AccountFeesCollectionModel> collectionModelList;
    AccountFeesModel feesModelList;
    String FinalTermtermdetailId = "1";
    private FragmentAccountBinding fragmentAccountBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

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
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 4;
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
                    fragment = new DailyFeesCollectionFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 41;
                } else if (position == 1) {
                    fragment = new TallyTranscationFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 41;
                } else if (position == 2) {
                    fragment = new OnlineTransactionFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 41;
                }
//                }else if (position == 7) {
//                    fragment = new CheckPaymentFragment();
//                    fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                            .replace(R.id.frame_container, fragment).commit();
//                            AppConfiguration.firsttimeback = true;
//                   AppConfiguration.position = 41;
//                }*/
            }
        });
        fragmentAccountBinding.termRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                {
                    if (null != rb && checkedId > -1) {
                        switch (checkedId) {
                            case R.id.term1_radio_button:
                                FinalTermtermdetailId = fragmentAccountBinding.term1RadioButton.getTag().toString();
                                AppConfiguration.TermDetailName = fragmentAccountBinding.term1RadioButton.getText().toString();
                                fillData2();

                                break;
                            case R.id.term2_radio_button:
                                FinalTermtermdetailId = fragmentAccountBinding.term2RadioButton.getTag().toString();
                                AppConfiguration.TermDetailName = fragmentAccountBinding.term2RadioButton.getText().toString();
                                fillData2();
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

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().totalFeesCollectionByTerm(getAccountDetail(), new retrofit.Callback<AccountFeesModel>() {

            @Override
            public void success(AccountFeesModel accountFeesStatusModel, Response response) {
                Utils.dismissDialog();
                if (accountFeesStatusModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (accountFeesStatusModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    Utils.dismissDialog();
                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (accountFeesStatusModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.dismissDialog();
//                    for (int i = 0; i < accountFeesStatusModel.getFinalArray().size(); i++) {
//                        collectionModelList = accountFeesStatusModel.getFinalArray().get(i).getCollection();
//                    }
                    feesModelList = accountFeesStatusModel;
                    if (feesModelList != null) {
                        fillData2();
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
        map.put("TermID", "3");
        // map.put("TermDetailID", FinalTermtermdetailId);
        return map;
    }

    public void fillData2() {
        String amount1 = "", amount2 = "", amount3 = "";
        Double longval1 = null, longval2 = null, longval3 = null;
        Format formatter = new DecimalFormat("##,##,###");
        String formattedString1, formattedString2, formattedString3;

        if (fragmentAccountBinding.term1RadioButton.isChecked()) {
            AppConfiguration.TermId = "1";
            amount1 = String.valueOf(feesModelList.getTerm1().get(0).getTotalAmount());
            amount2 = String.valueOf(feesModelList.getTerm1().get(0).getRecievedAmount());
            amount3 = String.valueOf(feesModelList.getTerm1().get(0).getDueAmount());

//            longval1 = Double.parseDouble(amount1);
//            longval2 = Double.parseDouble(amount2);
//            longval3 = Double.parseDouble(amount3);
//            formattedString1 = formatter.format(longval1);
//            formattedString2 = formatter.format(longval2);
//            formattedString3 = formatter.format(longval3);
            fragmentAccountBinding.totalAmountCount.setText("₹" + " " + String.valueOf(amount1));
            fragmentAccountBinding.totalReceiveAmountCount.setText("₹" + " " + String.valueOf(amount2));
            fragmentAccountBinding.totalDueAmountCount.setText("₹" + " " + String.valueOf(amount3));

        }

        if (fragmentAccountBinding.term2RadioButton.isChecked()) {
            AppConfiguration.TermId = "2";
            amount1 = String.valueOf(feesModelList.getTerm2().get(0).getTotalAmount());
            amount2 = String.valueOf(feesModelList.getTerm2().get(0).getRecievedAmount());
            amount3 = String.valueOf(feesModelList.getTerm2().get(0).getDueAmount());

//            longval1 = Double.parseDouble(amount1);
//            longval2 = Double.parseDouble(amount2);
//            longval3 = Double.parseDouble(amount3);
//            formattedString1 = formatter.format(longval1);
//            formattedString2 = formatter.format(longval2);
//            formattedString3 = formatter.format(longval3);
            fragmentAccountBinding.totalAmountCount.setText("₹" + " " + String.valueOf(amount1));
            fragmentAccountBinding.totalReceiveAmountCount.setText("₹" + " " + String.valueOf(amount2));
            fragmentAccountBinding.totalDueAmountCount.setText("₹" + " " + String.valueOf(amount3));

        }


        Log.d("termid", AppConfiguration.TermId);
        AppConfiguration.TermDetailId = FinalTermtermdetailId;
    }

    // Use for fill data account data
    public void fillData() {
        String amount1 = "", amount2 = "", amount3 = "";
        Double longval1 = null, longval2 = null, longval3 = null;
        Format formatter = new DecimalFormat("##,##,###");
        String formattedString1, formattedString2, formattedString3;

        for (int i = 0; i < collectionModelList.size(); i++) {
            AppConfiguration.TermId = String.valueOf(collectionModelList.get(i).getTermID());
            amount1 = String.valueOf(collectionModelList.get(i).getTotalAmt());
            amount2 = String.valueOf(collectionModelList.get(i).getTotalRcv());
            amount3 = String.valueOf(collectionModelList.get(i).getTotalDue());
            longval1 = Double.parseDouble(amount1);
            longval2 = Double.parseDouble(amount2);
            longval3 = Double.parseDouble(amount3);
            formattedString1 = formatter.format(longval1);
            formattedString2 = formatter.format(longval2);
            formattedString3 = formatter.format(longval3);
            fragmentAccountBinding.totalAmountCount.setText("₹" + " " + formattedString1);
            fragmentAccountBinding.totalReceiveAmountCount.setText("₹" + " " + formattedString2);
            fragmentAccountBinding.totalDueAmountCount.setText("₹" + " " + formattedString3);
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

