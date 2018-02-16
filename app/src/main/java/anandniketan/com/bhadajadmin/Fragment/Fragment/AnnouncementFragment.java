package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.AnnouncmentAdpater;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentAnnouncementBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

;


public class AnnouncementFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentAnnouncementBinding fragmentAnnouncementBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private static String dateFinal;
    private DatePickerDialog datePickerDialog;
    HashMap<Integer, String> spinnerOrderMap;
    AnnouncmentAdpater announcmentAdpater;


    String FinalDateStr, FinalSubjectStr, FinalDiscriptionStr, FinalOrderStr, FinalStatusStr = "1";

    public AnnouncementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAnnouncementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);

        rootView = fragmentAnnouncementBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        fillOrderSpinner();
        setListners();
        callAnnouncementDataApi();
        return rootView;
    }


    public void setListners() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        fragmentAnnouncementBinding.dateButton.setText(Utils.getTodaysDate());

        fragmentAnnouncementBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentAnnouncementBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentAnnouncementBinding.orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentAnnouncementBinding.orderSpinner.getSelectedItem().toString();
                String getid = spinnerOrderMap.get(fragmentAnnouncementBinding.orderSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalOrderStr = name.toString();
                Log.d("FinalOrderStr", FinalOrderStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentAnnouncementBinding.statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.done_chk:
                            FinalStatusStr = fragmentAnnouncementBinding.doneChk.getTag().toString();
                            break;
                        case R.id.pendding_chk:
                            FinalStatusStr = fragmentAnnouncementBinding.penddingChk.getTag().toString();
                            break;
                    }
                }
            }
        });
        fragmentAnnouncementBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFinalAllId();
                if (!FinalSubjectStr.equalsIgnoreCase("") && !FinalDiscriptionStr.equalsIgnoreCase("") && !FinalOrderStr.equalsIgnoreCase("--Select--")) {
                    callInsertAnnouncement();
                } else {
                    Utils.ping(mContext, "Blank Filed not Allowed");
                }
            }
        });
        fragmentAnnouncementBinding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(AnnouncementFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    // CALL AnnouncementData API HERE
    private void callAnnouncementDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAnnouncementData(getAnnouncementDataDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel announcmentModel, Response response) {
//                Utils.dismissDialog();
                if (announcmentModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (announcmentModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (announcmentModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.dismissDialog();
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentAnnouncementBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentAnnouncementBinding.lvExpHeader.setVisibility(View.GONE);
                    fragmentAnnouncementBinding.recyclerLinear.setVisibility(View.GONE);
                    return;
                }
                if (announcmentModel.getSuccess().equalsIgnoreCase("True")) {

                    if (announcmentModel.getFinalArray().size() > 0) {

                        fragmentAnnouncementBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentAnnouncementBinding.lvExpHeader.setVisibility(View.VISIBLE);
                        fragmentAnnouncementBinding.recyclerLinear.setVisibility(View.VISIBLE);

                        announcmentAdpater = new AnnouncmentAdpater(mContext, announcmentModel);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentAnnouncementBinding.announcmentList.setLayoutManager(mLayoutManager);
                        fragmentAnnouncementBinding.announcmentList.setItemAnimator(new DefaultItemAnimator());
                        fragmentAnnouncementBinding.announcmentList.setAdapter(announcmentAdpater);
                        Utils.dismissDialog();
                    } else {
                        fragmentAnnouncementBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentAnnouncementBinding.lvExpHeader.setVisibility(View.GONE);
                        fragmentAnnouncementBinding.recyclerLinear.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getAnnouncementDataDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL InsertAnnouncement
    public void callInsertAnnouncement() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().InsertAnnouncement(getInsertAnnouncement(), new retrofit.Callback<InsertMenuPermissionModel>() {
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
                    callAnnouncementDataApi();
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

    private Map<String, String> getInsertAnnouncement() {
        Map<String, String> map = new HashMap<>();
        map.put("Dt", FinalDateStr);
        map.put("Subject", FinalSubjectStr);
        map.put("Description", FinalDiscriptionStr);
        map.put("order", FinalOrderStr);
        map.put("Status", FinalStatusStr);
        map.put("Pk_CircularID", "0");
        return map;
    }

    //Use for DatePicker Dialog
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Selected Date : " + Day + "/" + Month + "/" + Year;
        String datestr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        mDay = dayOfMonth;
        mMonth = monthOfYear + 1;
        mYear = year;
        String d, m, y;
        d = Integer.toString(mDay);
        m = Integer.toString(mMonth);
        y = Integer.toString(mYear);

        if (mDay < 10) {
            d = "0" + d;
        }
        if (mMonth < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        fragmentAnnouncementBinding.dateButton.setText(dateFinal);
    }

    //Use for Fill Order Spinner
    public void fillOrderSpinner() {
        ArrayList<Integer> orderIdArray = new ArrayList<Integer>();
        orderIdArray.add(0);
        orderIdArray.add(1);
        orderIdArray.add(2);
        orderIdArray.add(3);
        orderIdArray.add(4);
        orderIdArray.add(5);
        orderIdArray.add(6);
        orderIdArray.add(7);
        orderIdArray.add(8);
        orderIdArray.add(9);
        orderIdArray.add(10);


        ArrayList<String> orderdetail = new ArrayList<>();
        orderdetail.add("--Select--");
        orderdetail.add("1");
        orderdetail.add("2");
        orderdetail.add("3");
        orderdetail.add("4");
        orderdetail.add("5");
        orderdetail.add("6");
        orderdetail.add("7");
        orderdetail.add("8");
        orderdetail.add("9");
        orderdetail.add("10");


        String[] spinnerorderIdArray = new String[orderIdArray.size()];

        spinnerOrderMap = new HashMap<Integer, String>();
        for (int i = 0; i < orderIdArray.size(); i++) {
            spinnerOrderMap.put(i, String.valueOf(orderIdArray.get(i)));
            spinnerorderIdArray[i] = orderdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentAnnouncementBinding.orderSpinner);

            popupWindow.setHeight(spinnerorderIdArray.length > 4 ? 500 : spinnerorderIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerorderIdArray);
        fragmentAnnouncementBinding.orderSpinner.setAdapter(adapterTerm);

        FinalOrderStr = spinnerOrderMap.get(0);
    }

    //Use for Get the Selected Value
    public void getFinalAllId() {
        FinalDateStr = fragmentAnnouncementBinding.dateButton.getText().toString();
        FinalSubjectStr = fragmentAnnouncementBinding.subjectEdt.getText().toString();
        FinalDiscriptionStr = fragmentAnnouncementBinding.discriptionEdt.getText().toString();
    }
}

