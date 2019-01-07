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
import anandniketan.com.bhadajadmin.Adapter.ExpandableStaffInquiryProfileAdapter;
import anandniketan.com.bhadajadmin.Adapter.ExpandableStudentInquiryProfileAdapter;
import anandniketan.com.bhadajadmin.Model.HR.SearchStaffModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentStaffProfileBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StaffInquiryProfileFragment extends Fragment {


    private FragmentStaffProfileBinding fragmentStudentInquiryProfileBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<SearchStaffModel.FinalArray> staffFullDetailArray;
    List<String> listDataHeader;
    HashMap<String, List<SearchStaffModel.FinalArray>> listDataChild;
    ExpandableStaffInquiryProfileAdapter  listAdapterStudentFullDetail;
    private int lastExpandedPosition = -1;

    public StaffInquiryProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentInquiryProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_profile, container, false);

        rootView = fragmentStudentInquiryProfileBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 61;


        try {
           Bundle bundle = getArguments();
            staffFullDetailArray = bundle.getParcelableArrayList("dataList");

            if (staffFullDetailArray != null) {
                ArrayList<String> arraystu = new ArrayList<String>();
                arraystu.add("Personal Details");
                //   arraystu.add("Transport Details");

                arraystu.add("Office Details");
                // arraystu.add("Communication Details");
                Log.d("array", "" + arraystu);

                listDataHeader = new ArrayList<>();
                listDataChild = new HashMap<>();

                for (int i = 0; i < arraystu.size(); i++) {
                    Log.d("arraystu", "" + arraystu);
                    listDataHeader.add(arraystu.get(i));
                    Log.d("header", "" + listDataHeader);
                    ArrayList<SearchStaffModel.FinalArray> row = new ArrayList<SearchStaffModel.FinalArray>();
                    for (int j = 0; j < staffFullDetailArray.size(); j++) {
                        row.add(staffFullDetailArray.get(j));
                    }
                    Log.d("row", "" + row);
                    listDataChild.put(listDataHeader.get(i), staffFullDetailArray);
                    Log.d("child", "" + listDataChild);
                }
                listAdapterStudentFullDetail = new ExpandableStaffInquiryProfileAdapter(getActivity(),listDataHeader,listDataChild);
                fragmentStudentInquiryProfileBinding.lvExpStudentDetail.setAdapter(listAdapterStudentFullDetail);
                Utils.dismissDialog();
            }
        }catch (Exception ex){

            ex.printStackTrace();
        }
        //callStaffApi();
        return rootView;
    }

    public void setListners() {
        fragmentStudentInquiryProfileBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentStudentInquiryProfileBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 51;
                fragment = new SearchStaffFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
                getActivity().onBackPressed();
            }
        });
        fragmentStudentInquiryProfileBinding.lvExpStudentDetail.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    fragmentStudentInquiryProfileBinding.lvExpStudentDetail.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });


    }

    // CALL Stuednt Full Detail API HERE

}
