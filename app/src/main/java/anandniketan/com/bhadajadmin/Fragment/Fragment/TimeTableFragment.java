package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListAdapterTimeTable;
import anandniketan.com.bhadajadmin.Model.Staff.Datum;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentTimeTableBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TimeTableFragment extends Fragment {

    private FragmentTimeTableBinding fragmentTimeTableBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private int lastExpandedPosition = -1;
    private List<FinalArrayStaffModel> finalArrayTimeTableList;
    List<String> listDataHeader;
    HashMap<String, ArrayList<Datum>> listDataChild;
    ExpandableListAdapterTimeTable expandableListAdapterTimeTable;

    public TimeTableFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentTimeTableBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_table, container, false);

        rootView = fragmentTimeTableBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTimeTableApi();

        return rootView;
    }


    public void setListners() {
        fragmentTimeTableBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentTimeTableBinding.btnBack.setOnClickListener(new View.OnClickListener() {
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
                    fragmentTimeTableBinding.lvExpTimeTable.setVisibility(View.GONE);
                    return;
                }
                if (timeTableModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayTimeTableList = timeTableModel.getFinalArray();
                    if (finalArrayTimeTableList != null) {
                        fragmentTimeTableBinding.txtNoRecordsTimetable.setVisibility(View.GONE);
                        fragmentTimeTableBinding.lvExpTimeTable.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListAdapterTimeTable = new ExpandableListAdapterTimeTable(getActivity(), listDataHeader, listDataChild);
                        fragmentTimeTableBinding.lvExpTimeTable.setAdapter(expandableListAdapterTimeTable);
                    } else {
                        fragmentTimeTableBinding.txtNoRecordsTimetable.setVisibility(View.VISIBLE);
                        fragmentTimeTableBinding.lvExpTimeTable.setVisibility(View.GONE);
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
        map.put("StaffID", "64");
        return map;
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<Datum>>();

        for (int i = 0; i < finalArrayTimeTableList.size(); i++) {
            listDataHeader.add(finalArrayTimeTableList.get(i).getDay());
            Log.d("header", "" + listDataHeader);
            ArrayList<Datum> row = new ArrayList<Datum>();

            for (int j = 0; j < finalArrayTimeTableList.get(i).getData().size(); j++) {
                row.add(finalArrayTimeTableList.get(i).getData().get(j));
                Log.d("row", "" + row);
            }
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

}

