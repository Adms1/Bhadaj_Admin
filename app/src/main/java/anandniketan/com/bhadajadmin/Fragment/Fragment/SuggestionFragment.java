package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ProfilePermissionAdapter;
import anandniketan.com.bhadajadmin.Adapter.SuggestionPermissionAdapter;
import anandniketan.com.bhadajadmin.Interface.onDeleteButton;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentSuggestionBinding;
import okhttp3.internal.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;


public class SuggestionFragment extends Fragment {

    //
    protected ArrayList<CharSequence> selectedmonthForString = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedmonth = new ArrayList<CharSequence>();
    //Use for employee spinner
    List<StudentAttendanceFinalArray> finalArrayStandardsList;
    HashMap<Integer, String> spinnerEmployeeMap;
    //Use for fill SuggestionPermission List
    SuggestionPermissionAdapter suggestionPermissionAdapter;
    String FinalEmployeeIdStr, FinaltypeIdStr, FinalEmployeeStr, FinaltypeStr, FinalDeleteIdStr;
    CharSequence[] m_months;
    ArrayList<String> month_no = new ArrayList<String>();
    ArrayList<String> MonthID_Arr;
    private FragmentSuggestionBinding fragmentsuggestionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public SuggestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentsuggestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestion, container, false);

        rootView = fragmentsuggestionBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        fillType();
        callEmployeeApi();
        callSuggestionPermissionApi();
        return rootView;
    }


    public void setListners() {
        fragmentsuggestionBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentsuggestionBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentPermissionFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentsuggestionBinding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentsuggestionBinding.typeSpinner.getSelectedItem().toString();


                Log.d("value", name);
                //  FinaltypeIdStr = getid.toString();


                FinaltypeStr = name;
                Log.d("FinaltypeStr", FinaltypeStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        fragmentsuggestionBinding.assignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedsectionstr = fragmentsuggestionBinding.assignSpinner.getSelectedItem().toString();
//                String getid = spinnerEmployeeMap.get(fragmentsuggestionBinding.assignSpinner.getSelectedItemPosition());
//
//                Log.d("value", selectedsectionstr + " " + getid);
//                FinalEmployeeIdStr = getid.toString();
//                Log.d("FinalEmployeeIdStr", FinalEmployeeIdStr);
//
//                FinalEmployeeStr = selectedsectionstr;
//                Log.d("FinalEmployeeStr", FinalEmployeeStr);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        fragmentsuggestionBinding.assignSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedmonthForString.clear();
                boolean[] checkedMOnths = new boolean[m_months.length];
                int count = m_months.length;

                for (int i = 0; i < count; i++)
                    checkedMOnths[i] = selectedmonth.contains(m_months[i]);

                DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedmonth.add(m_months[which]);
                            month_no.add(MonthID_Arr.get(which));
                        } else {
                            selectedmonth.remove(m_months[which]);
                            month_no.remove(MonthID_Arr.get(which));
                            fragmentsuggestionBinding.assignSpinner.setText("");
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Employee");
                builder.setMultiChoiceItems(m_months, checkedMOnths, coloursDialogListener);
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedmonthForString.clear();
                        for (int i = 0; i < m_months.length; i++) {
                            for (int j = 0; j < selectedmonth.size(); j++) {
                                if (m_months[i].equals(selectedmonth.get(j))) {
                                    selectedmonthForString.add(m_months[i]);
                                    onChangeSelectedMonth(selectedmonthForString);
                                }
                            }
                        }

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        fragmentsuggestionBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> employeeId = new ArrayList<>();
                FinalEmployeeIdStr = "";
                if (fragmentsuggestionBinding.assignSpinner.getText().toString().contains(",")) {
                    String[] split = fragmentsuggestionBinding.assignSpinner.getText().toString().split("\\,");
                    for (int i = 0; i < split.length; i++) {
                        for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                            if (finalArrayStandardsList.get(j).getEmployeeName().equalsIgnoreCase(split[i])) {
                                employeeId.add(String.valueOf(finalArrayStandardsList.get(j).getEmployeeID()));
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                        if (finalArrayStandardsList.get(j).getEmployeeName().equalsIgnoreCase(fragmentsuggestionBinding.assignSpinner.getText().toString())) {
                            employeeId.add(String.valueOf(finalArrayStandardsList.get(j).getEmployeeID()));
                        }
                    }
                }
                StringBuilder stringBuilder1 = new StringBuilder();
                for (CharSequence empId : employeeId) {
                    Log.i(TAG, "size = " + employeeId.size());
                    if (employeeId.size() <= 1) {
                        stringBuilder1.append(empId);
                    } else {
                        stringBuilder1.append(empId + ",");
                    }
                }
                if (employeeId.size() <= 1) {
                    FinalEmployeeIdStr = stringBuilder1.toString().trim();
                } else {
                    FinalEmployeeIdStr = stringBuilder1.toString().trim().substring(0, stringBuilder1.length() - 1);
                }

                Log.d(" FinalEmployeeIdStr", FinalEmployeeIdStr);
                if (!FinaltypeStr.equalsIgnoreCase("Please Select")) {
                    if (!FinalEmployeeIdStr.equalsIgnoreCase("")) {
                        callInsertSuggestionPermission();
                    } else {
                        Utils.ping(mContext, "Please select assign to");
                    }
                } else {
                    Utils.ping(mContext, "Please select type");
                }
            }
        });
    }

    protected void onChangeSelectedMonth(ArrayList<CharSequence> selectedmonth) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence month : selectedmonth) {
            Log.i(TAG, "size = " + selectedmonth.size());
            if (selectedmonth.size() <= 1) {
                stringBuilder.append(month);
            } else {
                stringBuilder.append(month + ",");
            }
        }
        if (selectedmonth.size() <= 1) {
            fragmentsuggestionBinding.assignSpinner.setText(stringBuilder.toString().trim());
        } else {
            fragmentsuggestionBinding.assignSpinner.setText(stringBuilder.toString().trim().substring(0, stringBuilder.length() - 1));
        }

    }

    // CALL Employee API HERE
    private void callEmployeeApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getEmployeeForSuggestionPermission(getEmployeeDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel employeeModel, Response response) {
                Utils.dismissDialog();
                if (employeeModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (employeeModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (employeeModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (employeeModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = employeeModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        MonthID_Arr = new ArrayList<String>();
                        m_months = new String[finalArrayStandardsList.size()];
                        for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                            m_months[i] = finalArrayStandardsList.get(i).getEmployeeName();
                            Log.d("months", m_months[i].toString());
                            MonthID_Arr.add(String.valueOf(finalArrayStandardsList.get(i).getEmployeeID()));
                        }

                        // fillAssignSpinner();
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

    private Map<String, String> getEmployeeDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL SuggestionPermission API HERE
    private void callSuggestionPermissionApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getSuggestionPermission(getSuggestionPermissionDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel suggestionPermissionModel, Response response) {
                Utils.dismissDialog();
                if (suggestionPermissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (suggestionPermissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (suggestionPermissionModel.getSuccess().equalsIgnoreCase("false")) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentsuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentsuggestionBinding.lvHeader.setVisibility(View.GONE);
                    fragmentsuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (suggestionPermissionModel.getSuccess().equalsIgnoreCase("True")) {
                    if (suggestionPermissionModel.getFinalArray().size() > 0) {
                        fragmentsuggestionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentsuggestionBinding.lvHeader.setVisibility(View.VISIBLE);
                        fragmentsuggestionBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        suggestionPermissionAdapter = new SuggestionPermissionAdapter(mContext, suggestionPermissionModel, new onDeleteButton() {
                            @Override
                            public void deleteSentMessage() {
                                FinalDeleteIdStr = String.valueOf(suggestionPermissionAdapter.getId());

                                FinalDeleteIdStr = FinalDeleteIdStr.substring(1, FinalDeleteIdStr.length() - 1);
                                Log.d("FinalDeleteId", FinalDeleteIdStr);
                                if (!FinalDeleteIdStr.equalsIgnoreCase("")) {
                                    callDeleteSuggestionPermission();
                                }
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentsuggestionBinding.suggestionList.setLayoutManager(mLayoutManager);
                        fragmentsuggestionBinding.suggestionList.setItemAnimator(new DefaultItemAnimator());
                        fragmentsuggestionBinding.suggestionList.setAdapter(suggestionPermissionAdapter);
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

    private Map<String, String> getSuggestionPermissionDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    // CALL InsertSuggestionPermission
    public void callInsertSuggestionPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertSuggestionPermission(getInsertSuggestionPermission(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    callSuggestionPermissionApi();
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

    private Map<String, String> getInsertSuggestionPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("Type", FinaltypeStr);
        map.put("StaffIDs", FinalEmployeeIdStr);
        return map;
    }


    // CALL DeleteSuggestionPermission
    public void callDeleteSuggestionPermission() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deleteSuggestionPermission(getDeleteSuggestionPermission(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    callSuggestionPermissionApi();
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

    private Map<String, String> getDeleteSuggestionPermission() {
        Map<String, String> map = new HashMap<>();
        map.put("AssignPermissionID", FinalDeleteIdStr);

        return map;
    }


    //use for fill assign spinner
    public void fillAssignSpinner() {

    }
//    //use for fill assign spinner
//    public void fillAssignSpinner() {
//        ArrayList<String> firstValue = new ArrayList<>();
//        firstValue.add("--Select--");
//
//        ArrayList<String> employeename = new ArrayList<>();
//        for (int z = 0; z < firstValue.size(); z++) {
//            employeename.add(firstValue.get(z));
//            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
//                employeename.add(finalArrayStandardsList.get(i).getEmployeeName());
//            }
//        }
//        ArrayList<Integer> firstValueId = new ArrayList<>();
//        firstValueId.add(0);
//        ArrayList<Integer> employeeId = new ArrayList<>();
//        for (int m = 0; m < firstValueId.size(); m++) {
//            employeeId.add(firstValueId.get(m));
//            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
//                employeeId.add(finalArrayStandardsList.get(j).getEmployeeID());
//            }
//        }
//        String[] spinneremployeeIdArray = new String[employeeId.size()];
//
//        spinnerEmployeeMap = new HashMap<Integer, String>();
//        for (int i = 0; i < employeeId.size(); i++) {
//            spinnerEmployeeMap.put(i, String.valueOf(employeeId.get(i)));
//            spinneremployeeIdArray[i] = employeename.get(i).trim();
//        }
//
//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentsuggestionBinding.assignSpinner);
//
//            popupWindow.setHeight(spinneremployeeIdArray.length > 4 ? 500 : spinneremployeeIdArray.length * 100);
////            popupWindow1.setHeght(200);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }
//
//
//        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinneremployeeIdArray);
//        fragmentsuggestionBinding.assignSpinner.setAdapter(adapterstandard);
//
//        FinalEmployeeIdStr = spinnerEmployeeMap.get(0);
//    }

    //use for fill Type spinner
    public void fillType() {
        ArrayList<String> selectedArray = new ArrayList<>();
        selectedArray.add("Please Select");
        selectedArray.add("Academic");
        selectedArray.add("Admin");
        selectedArray.add("Other");


        //Collections.sort(selectedArray);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + selectedArray);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentsuggestionBinding.typeSpinner);

            popupWindow.setHeight(selectedArray.size() > 1 ? 200 : selectedArray.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterSpinYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, selectedArray);
        fragmentsuggestionBinding.typeSpinner.setAdapter(adapterSpinYear);

        fragmentsuggestionBinding.typeSpinner.setSelection(0);
    }


}

