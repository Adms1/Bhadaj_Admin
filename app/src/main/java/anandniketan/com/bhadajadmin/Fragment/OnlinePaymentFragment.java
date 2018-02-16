package anandniketan.com.bhadajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.OnlinePaymentPermissionAdapter;
import anandniketan.com.bhadajadmin.Adapter.StandardAdapter;
import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayResultPermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.GetResultPermissionModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentOnlinePaymentBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OnlinePaymentFragment extends Fragment {

    private FragmentOnlinePaymentBinding fragmentOnlinePaymentBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //Use for fill AcademicYear spinner
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    HashMap<Integer, String> spinnerTermDetailIdMap;

    //Use for fill Standard
    List<FinalArrayStandard> finalArrayStandardsList;
    StandardAdapter standardAdapter;

    //Use for fill List
    List<FinalArrayResultPermissionModel> finalArrayResultPermissionList;
    OnlinePaymentPermissionAdapter onlinePaymentPermissionAdapter;

    String FinalTermIdStr, FinalGradeIsStr = "", FinalTermDetailIdStr = "",  FinalStatusStr = "1";


    public OnlinePaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentOnlinePaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_payment, container, false);

        rootView = fragmentOnlinePaymentBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callStandardApi();

        return rootView;
    }


    public void setListners() {

        fragmentOnlinePaymentBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentOnlinePaymentBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentPermissionFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentOnlinePaymentBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentOnlinePaymentBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentOnlinePaymentBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callOnlinePaymentPermission();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fragmentOnlinePaymentBinding.termDetailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentOnlinePaymentBinding.termDetailSpinner.getSelectedItem().toString();
                String getid = spinnerTermDetailIdMap.get(fragmentOnlinePaymentBinding.termDetailSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermDetailIdStr = getid.toString();
                Log.d("FinalTermDetailIdStr", FinalTermDetailIdStr);
                callOnlinePaymentPermission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentOnlinePaymentBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.done_chk:
                            FinalStatusStr = fragmentOnlinePaymentBinding.doneChk.getTag().toString();
                            break;
                        case R.id.pendding_chk:
                            FinalStatusStr = fragmentOnlinePaymentBinding.penddingChk.getTag().toString();
                            break;
                    }
                }
            }
        });
        fragmentOnlinePaymentBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFinalIdStr();
                if (!FinalGradeIsStr.equalsIgnoreCase("")) {
                    callInsertOnlinePaymentPermission();
                } else {
                    Utils.ping(mContext, "Please Select Grade.");
                }

            }
        });

    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        fillTermDetailSpinner();

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

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            finalArrayStandardsList.get(i).setCheckedStatus("0");
                        }
                        standardAdapter = new StandardAdapter(mContext, finalArrayStandardsList);
                        fragmentOnlinePaymentBinding.gradeGridView.setAdapter(standardAdapter);
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

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL OnlinePaymentPermission API HERE
    private void callOnlinePaymentPermission() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getOnlinePaymentPermission(getOnlinePaymentPermission(), new retrofit.Callback<GetResultPermissionModel>() {
            @Override
            public void success(GetResultPermissionModel resultPermissionModel, Response response) {
                Utils.dismissDialog();
                if (resultPermissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentOnlinePaymentBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentOnlinePaymentBinding.lvHeader.setVisibility(View.GONE);
                    fragmentOnlinePaymentBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (resultPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayResultPermissionList = resultPermissionModel.getFinalArray();
                    if (resultPermissionModel.getFinalArray().size() > 0) {
                        fragmentOnlinePaymentBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentOnlinePaymentBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentOnlinePaymentBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        onlinePaymentPermissionAdapter = new OnlinePaymentPermissionAdapter(mContext, resultPermissionModel, new getEditpermission() {
                            @Override
                            public void getEditpermission() {
                                UpdatePermission();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentOnlinePaymentBinding.onlinePermissionList.setLayoutManager(mLayoutManager);
                        fragmentOnlinePaymentBinding.onlinePermissionList.setItemAnimator(new DefaultItemAnimator());
                        fragmentOnlinePaymentBinding.onlinePermissionList.setAdapter(onlinePaymentPermissionAdapter);
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

    private Map<String, String> getOnlinePaymentPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        map.put("TermDetailId", FinalTermDetailIdStr);
        return map;
    }

    // CALL InsertOnlinePaymentPermission
    public void callInsertOnlinePaymentPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertOnlinePaymentPermission(getInsertOnlinePaymentPermission(), new retrofit.Callback<InsertMenuPermissionModel>() {
            @Override
            public void success(InsertMenuPermissionModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
//                    Utils.ping(mContext, getString(R.string.true_msg));
                    callOnlinePaymentPermission();
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

    private Map<String, String> getInsertOnlinePaymentPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("TermDetailID", FinalTermDetailIdStr);
        map.put("GradeID", FinalGradeIsStr);
        map.put("Status", FinalStatusStr);
        return map;
    }

    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentOnlinePaymentBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentOnlinePaymentBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
    }

    public void fillTermDetailSpinner() {
        ArrayList<Integer> termdetailId = new ArrayList<>();
        termdetailId.add(1);
        termdetailId.add(2);


        ArrayList<String> termdetail = new ArrayList<>();
        termdetail.add("Term 1");
        termdetail.add("Term 2");


        String[] spinnertermdetailIdArray = new String[termdetailId.size()];

        spinnerTermDetailIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < termdetailId.size(); i++) {
            spinnerTermDetailIdMap.put(i, String.valueOf(termdetailId.get(i)));
            spinnertermdetailIdArray[i] = termdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentOnlinePaymentBinding.termDetailSpinner);

            popupWindow.setHeight(spinnertermdetailIdArray.length > 4 ? 500 : spinnertermdetailIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermdetailIdArray);
        fragmentOnlinePaymentBinding.termDetailSpinner.setAdapter(adapterTerm);

        FinalTermDetailIdStr = spinnerTermDetailIdMap.get(0);
    }

    // Use for get the AllFinalValue for InsertPermission
    public void getFinalIdStr() {
        ArrayList<String> gradeArray = new ArrayList<String>();
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        for (int i = 0; i < standardArray.size(); i++) {
            if (standardArray.get(i).getCheckedStatus().equalsIgnoreCase("1")) {
                gradeArray.add(standardArray.get(i).getStandardID().toString());
            }
        }
        FinalGradeIsStr = String.valueOf(gradeArray);
        FinalGradeIsStr = FinalGradeIsStr.substring(1, FinalGradeIsStr.length() - 1);
        Log.d("FinalGradeIsStr", FinalGradeIsStr);
    }

    // Use For UpdatePermission
    public void UpdatePermission() {
        fragmentOnlinePaymentBinding.searchBtn.setText("Update");
        ArrayList<String> academicYearArray = new ArrayList<String>();
        String statusArray = "", gradeArray = "";

        for (int k = 0; k < onlinePaymentPermissionAdapter.getRowValue().size(); k++) {
            String rowValueStr = onlinePaymentPermissionAdapter.getRowValue().get(k);
            Log.d("rowValueStr", rowValueStr);
            String[] spiltString = rowValueStr.split("\\|");
            academicYearArray.add(spiltString[0]);
            gradeArray = spiltString[1];
            statusArray = spiltString[2];
//            statusArray = statusArray.substring(0, statusArray.length() - 1);

            Log.d("statusArray", statusArray);
        }
        if (statusArray.equalsIgnoreCase(fragmentOnlinePaymentBinding.doneChk.getText().toString())) {
            fragmentOnlinePaymentBinding.doneChk.setChecked(true);
        } else if (statusArray.equalsIgnoreCase(fragmentOnlinePaymentBinding.penddingChk.getText().toString())) {
            fragmentOnlinePaymentBinding.penddingChk.setChecked(true);

        }
        List<FinalArrayStandard> standardArray = standardAdapter.getDatas();
        for (int i = 0; i < standardArray.size(); i++) {
            if (gradeArray.equalsIgnoreCase(standardArray.get(i).getStandard())) {
                standardArray.get(i).setCheckedStatus("1");
                standardAdapter.notifyDataSetChanged();
            }
        }

    }
}

