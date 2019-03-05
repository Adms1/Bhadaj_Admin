package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableSuggestion;
import anandniketan.com.bhadajadmin.Model.Student.SuggestionDataModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import anandniketan.com.bhadajadmin.databinding.FragmentSuggestionBinding;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static String dateFinal = "", FinalStatusIdStr;
    private FragmentSuggestionBinding fragmentSuggestionBinding;
    private TextView tvHeader;
    private Button btnBack, btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private DatePickerDialog datePickerDialog;
    private int whichDateClicked = 1;
    private int Year, Month, Day;
    private Calendar calendar;
    private View rootView;
    private Context mContext;
    private ArrayList<SuggestionDataModel.FinalArray> finalArraySuggestionFinal;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<SuggestionDataModel.FinalArray>> listDataChild;
    private ExpandableSuggestion expandableSuggestion;

    public SuggestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSuggestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestion, container, false);

        rootView = fragmentSuggestionBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText("Suggestion");

        setListener();
        fillStatusSpinner();
    }

    private void setListener() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

//        hour = calendar.get(Calendar.HOUR_OF_DAY);
//        minute = calendar.get(Calendar.MINUTE);
//        second = calendar.get(Calendar.SECOND);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentSuggestionBinding.sugFromdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 1;
                datePickerDialog = DatePickerDialog.newInstance(SuggestionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });
        fragmentSuggestionBinding.sugTodateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichDateClicked = 2;
                datePickerDialog = DatePickerDialog.newInstance(SuggestionFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        fragmentSuggestionBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callParentSuggestionApi();
            }
        });
    }

    private void callParentSuggestionApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<SuggestionDataModel> call = apiService.getSuggestion();

        call.enqueue(new Callback<SuggestionDataModel>() {

//        ApiHandler.getApiService().getAllStaffLeaveRequest(getDetail(), new retrofit.Callback<SuggestionDataModel>() {

            @Override
            public void onResponse(Call<SuggestionDataModel> call, retrofit2.Response<SuggestionDataModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));

                    fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {
                    finalArraySuggestionFinal = response.body().getFinalArray();
                    if (finalArraySuggestionFinal != null) {
                        fragmentSuggestionBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentSuggestionBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentSuggestionBinding.suggestionList.setVisibility(View.VISIBLE);

                        fillExpLV();
//                        expandableListCircular = new ExpandableSuggestion(getActivity(), listDataHeader, listDataChild, onAdapterItemButtonClick);
//                        fragmentSuggestionBinding.suggestionList.setAdapter(expandableListCircular);
                    } else {
                        fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                        fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SuggestionDataModel> call, Throwable t) {
                Utils.dismissDialog();
                t.printStackTrace();
                t.getMessage();
                fragmentSuggestionBinding.txtNoRecords.setText(t.getMessage());
                fragmentSuggestionBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentSuggestionBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentSuggestionBinding.suggestionList.setVisibility(View.GONE);

                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArraySuggestionFinal.size(); i++) {

            listDataHeader.add(finalArraySuggestionFinal.get(i).getSubject() + "|" + finalArraySuggestionFinal.get(i).getDate() + "|" + finalArraySuggestionFinal.get(i).getSuggestiondatetime());
            Log.d("header", "" + listDataHeader);
            ArrayList<SuggestionDataModel.FinalArray> row = new ArrayList<>();
            row.add(finalArraySuggestionFinal.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month + 1);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;

        if (whichDateClicked == 1) {
            fragmentSuggestionBinding.sugFromdateBtn.setText(dateFinal);

        } else if (whichDateClicked == 2) {
            fragmentSuggestionBinding.sugTodateBtn.setText(dateFinal);

        } else if (whichDateClicked == 3) {
            fragmentSuggestionBinding.sugFromdateBtn.setText(dateFinal);
        }
    }

    private void fillStatusSpinner() {

        ArrayList<String> status = new ArrayList<>();

        status.add("-Select-");
        status.add("Pending");
        status.add("Replying");

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, status);
        fragmentSuggestionBinding.assignSpinner.setAdapter(adapterTerm);

        FinalStatusIdStr = status.get(0);
    }

}
