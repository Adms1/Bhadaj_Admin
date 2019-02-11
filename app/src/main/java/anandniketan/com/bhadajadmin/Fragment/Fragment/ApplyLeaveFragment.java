package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Model.HR.LeaveDayModel;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApplyLeaveFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView tvHeader, tvNoRecords;
    private Button btnBack, btnMenu, btnSend, btnSDate, btnEDate;
    private RecyclerView rvList;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    private LinearLayout llHeader;
    private Calendar calendar;
    private int whichclicked;
    private List<LeaveDayModel.FinalArray> finalArrayGetLeaveDays;
    private List<FinalArrayStaffModel> finalArrayGetHead;
    private HashMap<Integer, String> spinnerDaymap, spinnerHeadmap;
    private Spinner spLeaveDays, spHead;
    private String FinalDay, FinalHead;

    public ApplyLeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_leave, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        btnSDate = view.findViewById(R.id.applyleave_btnsDate);
        btnEDate = view.findViewById(R.id.applyleave_btneDate);
        btnSend = view.findViewById(R.id.applyleave_btnSend);
        llHeader = view.findViewById(R.id.list_header);
        rvList = view.findViewById(R.id.applyleave_list);
        tvNoRecords = view.findViewById(R.id.txtNoRecords);
        spLeaveDays = view.findViewById(R.id.applyleave_spLeaveDays);
        spHead = view.findViewById(R.id.applyleave_spHead);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tvHeader.setText(R.string.applyleave);

        setListners();
        callLeaveDaysApi();
        callHeadApi();

    }

    private void setListners() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        btnSDate.setText(Utils.getTodaysDate());
        btnEDate.setText(Utils.getTodaysDate());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApplyLeaveListApi();
            }
        });

        btnSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichclicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(ApplyLeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichclicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(ApplyLeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date To DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                AppConfiguration.position = 55;
                fragment = new StaffFragment();
                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });
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

        String dateFinal = d + "/" + m + "/" + y;

        if (whichclicked == 1) {
            btnSDate.setText(dateFinal);
        } else {
            btnEDate.setText(dateFinal);
        }

    }

    // CALL Term API HERE
    private void callLeaveDaysApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getleaveDays(getLeaveDayParams(), new retrofit.Callback<LeaveDayModel>() {
            @Override
            public void success(LeaveDayModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetLeaveDays = termModel.getFinalArray();
                    if (finalArrayGetLeaveDays != null) {
                        fillDialogDaysSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getLeaveDayParams() {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", PrefUtils.getInstance(getActivity()).getStringValue("StaffID", ""));
        return map;
    }

    public void fillDialogDaysSpinner() {
        ArrayList<String> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
            TermId.add(String.valueOf(finalArrayGetLeaveDays.get(i).getValue()));
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetLeaveDays.size(); j++) {
            Term.add(String.valueOf(finalArrayGetLeaveDays.get(j).getDays()));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerDaymap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerDaymap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spLeaveDays);

            popupWindow.setHeight(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, spinnertermIdArray);
        spLeaveDays.setAdapter(adapterTerm);

        FinalDay = spinnerDaymap.get(0);
        spLeaveDays.setSelection(0);

//        for (int i = 0; i < finalArrayGetLeaveDays.size(); i++) {
//            if (dataItem != null) {
//                if (dataItem.getLeaveDays().equalsIgnoreCase(finalArrayGetLeaveDays.get(i).getValue())) {
//                    spLeaveDays.setSelection(i);
//                    String[] dates = dataItem.getLeaveDates().split("-");
//                    dialogModifyLeaveBinding.fromdateBtn.setText(dates[0]);
//                    dialogModifyLeaveBinding.todateBtn.setText(dates[1]);
//                    updateDateFromDialog = dataItem.getLeaveDays();
//                    break;
//                } else {
//                    dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//                    updateDateFromDialog = "0";
//
//                }
//            } else {
//                dialogModifyLeaveBinding.leavedaySpinner.setSelection(0);
//                updateDateFromDialog = "0";
//
//            }
//        }

    }

    // CALL Term API HERE
    private void callHeadApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getHead(getHeadParams(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetHead = termModel.getFinalArray();
                    if (finalArrayGetHead != null) {
                        fillDialogHeadSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(getActivity(), getString(R.string.something_wrong));
            }
        });
    }

    private Map<String, String> getHeadParams() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public void fillDialogHeadSpinner() {
        ArrayList<String> TermId = new ArrayList<>();
        for (int i = 0; i < finalArrayGetHead.size(); i++) {
            TermId.add(String.valueOf(finalArrayGetHead.get(i).getEmpId()));
        }
        ArrayList<String> Term = new ArrayList<>();
        for (int j = 0; j < finalArrayGetHead.size(); j++) {
            Term.add(String.valueOf(finalArrayGetHead.get(j).getEmpName()));
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerHeadmap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerHeadmap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spHead);

            popupWindow.setHeight(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, spinnertermIdArray);
        spHead.setAdapter(adapterTerm);

        FinalHead = spinnerHeadmap.get(0);
        spHead.setSelection(0);
    }

    private void callApplyLeaveListApi() {

    }

}
