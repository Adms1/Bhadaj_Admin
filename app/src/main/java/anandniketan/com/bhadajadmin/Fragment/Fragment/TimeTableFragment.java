package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterTimeTable;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Staff.Datum;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentTimeTableBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

// Antra 28/01/2019
// Add term spinner and Grade Spinner

public class TimeTableFragment extends Fragment {

    List<String> listDataHeader;
    HashMap<String, ArrayList<Datum>> listDataChild;
    ExpandableListAdapterTimeTable expandableListAdapterTimeTable;
    private FragmentTimeTableBinding fragmentTimeTableBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private List<FinalArrayStaffModel> finalArrayTimeTableList;
    private String viewstatus, FinalTermIdStr, finalStandardIdStr, standardName, updatestatus, deletestatus;
    private LinearLayout ll;
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;
    private List<FinalArrayStandard> finalArrayStandardsList;
    private String[] spinnerstandardIdArray;
    private HashMap<Integer, String> spinnerStandardMap;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public TimeTableFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTimeTableBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_table, container, false);

        rootView = fragmentTimeTableBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        ll = rootView.findViewById(R.id.timetable_ll);

        Bundle bundle = this.getArguments();
        viewstatus = bundle.getString("viewstatus");
        updatestatus = bundle.getString("updatestatus");
        deletestatus = bundle.getString("deletestatus");

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.timetable);

        fragmentTimeTableBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewstatus.equalsIgnoreCase("true")) {

                    callTimeTableApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }

            }
        });

        fragmentTimeTableBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentTimeTableBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentTimeTableBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);

                if (viewstatus.equalsIgnoreCase("true")) {
                    callStandardApi();
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentTimeTableBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentTimeTableBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentTimeTableBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                finalStandardIdStr = getid.toString();
                Log.d("FinalStandardIdStr", finalStandardIdStr);
                standardName = name;
                Log.d("StandardName", standardName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setListners();
        callTermApi();

    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        fragmentTimeTableBinding.lvExpTimeTable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    fragmentTimeTableBinding.lvExpTimeTable.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
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
        ApiHandler.getApiService().getStandardSectionCombine(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
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
                        fillGradeSpinner();
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

    // CALL TimeTable API HERE
    private void callTimeTableApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTimeTable(getTimeTableDetail(), new retrofit.Callback<StaffAttendaceModel>() {
            @Override
            public void success(StaffAttendaceModel timeTableModel, Response response) {
                Utils.dismissDialog();
                if (timeTableModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (timeTableModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (timeTableModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentTimeTableBinding.txtNoRecordsTimetable.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.GONE);
                    return;
                }
                if (timeTableModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayTimeTableList = timeTableModel.getFinalArray();
                    if (finalArrayTimeTableList != null) {
                        fragmentTimeTableBinding.txtNoRecordsTimetable.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterTimeTable = new ExpandableListAdapterTimeTable(getActivity(), listDataHeader, listDataChild);
                        fragmentTimeTableBinding.lvExpTimeTable.setAdapter(expandableListAdapterTimeTable);
                    } else {
                        fragmentTimeTableBinding.txtNoRecordsTimetable.setVisibility(View.VISIBLE);
                        ll.setVisibility(View.GONE);
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

    private Map<String, String> getTimeTableDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("TermID", FinalTermIdStr);
        map.put("ClassID", finalStandardIdStr);
        map.put("Type", "GradeWise");
        map.put("StaffID", "0");
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        for (int i = 0; i < finalArrayTimeTableList.size(); i++) {
            listDataHeader.add(finalArrayTimeTableList.get(i).getDay());
            Log.d("header", "" + listDataHeader);
            ArrayList<Datum> row = new ArrayList<>();

            for (int j = 0; j < finalArrayTimeTableList.get(i).getData().size(); j++) {
                row.add(finalArrayTimeTableList.get(i).getData().get(j));
                Log.d("row", "" + row);
            }
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    //Use for fill the Term Spinner
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

        spinnerTermMap = new HashMap<>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTimeTableBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentTimeTableBinding.termSpinner.setAdapter(adapterTerm);
        fragmentTimeTableBinding.termSpinner.setSelection(1);
        FinalTermIdStr = spinnerTermMap.get(0);
        callStandardApi();
    }

    //Use for fill the Standard Spinner
    public void fillGradeSpinner() {
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> StandardId = new ArrayList<Integer>();

        for (int m = 0; m < firstValueId.size(); m++) {
            StandardId.add(firstValueId.get(m));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                StandardId.add(finalArrayStandardsList.get(i).getClassID());
            }
        }

        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");
        ArrayList<String> Standard = new ArrayList<String>();
        for (int z = 0; z < firstValue.size(); z++) {
            Standard.add(firstValue.get(z));

            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                Standard.add(finalArrayStandardsList.get(j).getStandardClass());
            }
        }

        spinnerstandardIdArray = new String[StandardId.size()];

        spinnerStandardMap = new HashMap<>();
        for (int i = 0; i < StandardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(StandardId.get(i)));
            spinnerstandardIdArray[i] = Standard.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentTimeTableBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentTimeTableBinding.gradeSpinner.setAdapter(adapterTerm);
        fragmentTimeTableBinding.gradeSpinner.setSelection(1);
    }

}

