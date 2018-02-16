package anandniketan.com.bhadajadmin.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterInquiryData;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentNameModel;
import anandniketan.com.bhadajadmin.Model.Student.InquiryStausDetail;
import anandniketan.com.bhadajadmin.Model.Student.StudentNameModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStudentViewInquiryBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StudentViewInquiryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentStudentViewInquiryBinding fragmentStudentViewInquiryBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    int Year, Month, Day;
    Calendar calendar;
    private static String dateFinal;
    private static boolean isFromDate = false;
    private DatePickerDialog datePickerDialog;
    HashMap<Integer, String> spinnerOrderMap;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStudentNameModel> finalArrayinquiryCountList;
    private int lastExpandedPosition = -1;
    String FinalStartDateStr, FinalEndDateStr, FinalStatusStr, FinalStatusIdStr, FinalTermIdStr, FinalTermStr, FinalOnlineStatusStr = "All";
    ExpandableListAdapterInquiryData expandableListAdapterInquiryData;
    List<String> listDataHeader;
    HashMap<String,List<InquiryStausDetail>> listDataChild;

    public StudentViewInquiryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentViewInquiryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_view_inquiry, container, false);

        rootView = fragmentStudentViewInquiryBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        return rootView;
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentStudentViewInquiryBinding.startdateButton.setText(Utils.getTodaysDate());
        fragmentStudentViewInquiryBinding.enddateButton.setText(Utils.getTodaysDate());

        fragmentStudentViewInquiryBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentViewInquiryBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentStudentViewInquiryBinding.lvExpviewinquiry.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override

            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentStudentViewInquiryBinding.lvExpviewinquiry.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
        fragmentStudentViewInquiryBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentStudentViewInquiryBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                callInquiryCountApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentStudentViewInquiryBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentStudentViewInquiryBinding.statusSpinner.getSelectedItem().toString();
                String getid = spinnerOrderMap.get(fragmentStudentViewInquiryBinding.statusSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStatusIdStr = getid.toString();
                Log.d("FinalStatusIdStr", FinalStatusIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentStudentViewInquiryBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.all_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.allChk.getTag().toString();
                            break;
                        case R.id.online_inquiry_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.onlineInquiryChk.getTag().toString();
                            break;
                        case R.id.offline_inquiry_chk:
                            FinalOnlineStatusStr = fragmentStudentViewInquiryBinding.offlineInquiryChk.getTag().toString();
                            break;
                    }
                }
            }
        });
        fragmentStudentViewInquiryBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInquiryDataApi();
            }
        });
        fragmentStudentViewInquiryBinding.startdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(StudentViewInquiryFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentStudentViewInquiryBinding.enddateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(StudentViewInquiryFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
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
                        fillStatusSpinner();
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

    // CALL InquiryCount API HERE
    private void callInquiryCountApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getInquiryCount(getInquiryCountDetail(), new retrofit.Callback<StudentNameModel>() {
            @Override
            public void success(StudentNameModel inquiryCountModel, Response response) {
                Utils.dismissDialog();
                if (inquiryCountModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryCountModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryCountModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    return;
                }
                if (inquiryCountModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayinquiryCountList = inquiryCountModel.getFinalArray();
                    if (finalArrayinquiryCountList != null) {
                        fillInquiryCount();

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

    private Map<String, String> getInquiryCountDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermId", FinalTermIdStr);
        return map;
    }


    // CALL InquiryData API HERE
    private void callInquiryDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getInquiryData(getInquiryDetail(), new retrofit.Callback<StudentNameModel>() {
            @Override
            public void success(StudentNameModel inquiryDataModel, Response response) {
                if (inquiryDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.GONE);
                    return;
                }
                if (inquiryDataModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayinquiryCountList = inquiryDataModel.getFinalArray();
                    if (finalArrayinquiryCountList != null) {
                        fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterInquiryData = new ExpandableListAdapterInquiryData(getActivity(), listDataHeader, listDataChild);
                        fragmentStudentViewInquiryBinding.lvExpviewinquiry.setAdapter(expandableListAdapterInquiryData);
                        Utils.dismissDialog();
                    }else{
                        fragmentStudentViewInquiryBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentStudentViewInquiryBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentStudentViewInquiryBinding.listHeader.setVisibility(View.GONE);
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

    private Map<String, String> getInquiryDetail() {
        getFinalAllId();
        Map<String, String> map = new HashMap<>();
        map.put("stdt", FinalStartDateStr);
        map.put("enddt", FinalEndDateStr);
        map.put("onlineStatus", FinalOnlineStatusStr);
        map.put("status", FinalStatusIdStr);

        return map;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        if (isFromDate) {
            fragmentStudentViewInquiryBinding.startdateButton.setText(dateFinal);
        } else {
            fragmentStudentViewInquiryBinding.enddateButton.setText(dateFinal);
        }
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
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentStudentViewInquiryBinding.termSpinner.setAdapter(adapterTerm);

        FinalTermIdStr = spinnerTermMap.get(0);
        callInquiryCountApi();
    }

    public void fillStatusSpinner() {
        ArrayList<String> statusIdArray = new ArrayList<String>();
        statusIdArray.add("All");
        statusIdArray.add("Generated");
        statusIdArray.add("Admission Form Issued");
        statusIdArray.add("Interaction Call");
        statusIdArray.add("Interaction Call");
        statusIdArray.add("Interview Done");
        statusIdArray.add("Generate Circular");
        statusIdArray.add("Fees Paid");


        ArrayList<String> statusdetail = new ArrayList<>();
        statusdetail.add("All");
        statusdetail.add("Inquiry Generated");
        statusdetail.add("Generated Admission Form");
        statusdetail.add("Received Admission Form");
        statusdetail.add("Interaction Call");
        statusdetail.add("Come for Interview");
        statusdetail.add("Generate Circular");
        statusdetail.add("Fees Paid");


        String[] spinnerstatusIdArray = new String[statusIdArray.size()];

        spinnerOrderMap = new HashMap<Integer, String>();
        for (int i = 0; i < statusIdArray.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(statusIdArray.get(i)));
            spinnerstatusIdArray[i] = statusdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentStudentViewInquiryBinding.statusSpinner);

            popupWindow.setHeight(spinnerstatusIdArray.length > 4 ? 500 : spinnerstatusIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstatusIdArray);
        fragmentStudentViewInquiryBinding.statusSpinner.setAdapter(adapterTerm);

        FinalStatusIdStr = spinnerOrderMap.get(0);
        callInquiryDataApi();
    }

    public void fillInquiryCount() {
        for (int i = 0; i < finalArrayinquiryCountList.size(); i++) {
            if (finalArrayinquiryCountList.get(i).getName().equalsIgnoreCase("Total")) {
                fragmentStudentViewInquiryBinding.totalInquiryCount.setText(finalArrayinquiryCountList.get(i).getTotal());
            } else if (finalArrayinquiryCountList.get(i).getName().equalsIgnoreCase("Confirmed")) {
                fragmentStudentViewInquiryBinding.totalConfirmCount.setText(finalArrayinquiryCountList.get(i).getTotal());
            }
        }
    }

    public void getFinalAllId() {
        FinalStartDateStr = fragmentStudentViewInquiryBinding.startdateButton.getText().toString();
        FinalEndDateStr = fragmentStudentViewInquiryBinding.enddateButton.getText().toString();
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<InquiryStausDetail>>();

        for (int i = 0; i < finalArrayinquiryCountList.size(); i++) {
            listDataHeader.add(finalArrayinquiryCountList.get(i).getName() + "|" +
                    finalArrayinquiryCountList.get(i).getGrade() + "|" +
                    finalArrayinquiryCountList.get(i).getGender() + "|" +
                    finalArrayinquiryCountList.get(i).getCurrentStatus());
            Log.d("header", "" + listDataHeader);
            ArrayList<InquiryStausDetail> row = new ArrayList<InquiryStausDetail>();

            for (int j = 0; j < finalArrayinquiryCountList.get(i).getStausDetail().size(); j++) {
                row.add(finalArrayinquiryCountList.get(i).getStausDetail().get(j));
                Log.d("row", "" + row);

            }
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

}

