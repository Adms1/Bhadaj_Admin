package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.MISStudenttAdapter;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentMisstudentListBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MISStudentListFragment extends Fragment {

    private String title = "", requestType = "", date = "", termID = "", deptId = "";
    private String countdata = "", requestTitle = "";
    private FragmentMisstudentListBinding fragmentMisDataBinding;
    private View rootView;
    private List<MISStudentModel.StudentDatum> misStudentDataList;
    private MISStudenttAdapter misStudentAdapter;

    private ProgressBar progressBar;
    private List<MISStudentModel.StandardDatum> misStudentStandardDataList;

    private Handler handler;
    private View innerTitleView, innerListHeaderView;
    private ExpandableListView rvMisdataList1;
    private Context mContext;
    private TextView mTvInnerTitle, mTvInnerAttendanceStatus, mTvinnerGrno, mTvStudent, mTvDept, mTvCode, mTvName, mTvPhone, mTvGRNO, mTvClassTeacher, mTVGrade, mTvSection, mTvLeaveDay, mTvReason, mTvAbsentFrom;

    public MISStudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMisDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_misstudent_list, container, false);

        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;

        rootView = fragmentMisDataBinding.getRoot();
        progressBar = rootView.findViewById(R.id.loader);
        rvMisdataList1 = rootView.findViewById(R.id.rv_misdata_list1);
        mContext = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        title = bundle.getString("title");
        requestType = bundle.getString("requestType");
        termID = bundle.getString("TermID");
        date = bundle.getString("Date");

        try {
            countdata = bundle.getString("countdata");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            requestTitle = bundle.getString("requestTitle");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        if (title != null && !TextUtils.isEmpty(title)) {
            fragmentMisDataBinding.textView3.setText(title);
        }


        try {
            deptId = bundle.getString("deptID");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        if (requestType.equalsIgnoreCase("Total")) {
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText("Total Students");
//
//        } else if (requestType.equalsIgnoreCase("Present")) {
//
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText(requestType);
//
//            mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
//            mTvInnerAttendanceStatus.setVisibility(View.GONE);
//
//        } else if (requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave")) {
//
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText(requestType);
//
//            mTvGRNO = (TextView) innerListHeaderView.findViewById(R.id.grno_txt);
//            mTvGRNO.setVisibility(View.VISIBLE);
//            mTvGRNO.setText("Phone No.");
//            mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
////
//            mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
//            mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
//            mTvInnerAttendanceStatus.setText("SMS");
//
//            mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//
//        } else if (requestType.equalsIgnoreCase("ANT")) {
//
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText("Attendance Not Taken");
//
////                    mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
////                    mTvInnerAttendanceStatus.setText("Class Teacher");
////                    mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));
//
//            mTvGRNO = (TextView) innerListHeaderView.findViewById(R.id.grno_txt);
//            mTvGRNO.setVisibility(View.VISIBLE);
//            mTvGRNO.setText("Phone No.");
//            mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//
//            mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
//            mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
//            mTvInnerAttendanceStatus.setText("SMS");
//            mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//
//            mTvClassTeacher = (TextView) innerListHeaderView.findViewById(R.id.teacher_name_txt);
//            mTvClassTeacher.setVisibility(View.VISIBLE);
//
//            mTvStudent = (TextView) innerListHeaderView.findViewById(R.id.student_txt);
//            mTvStudent.setText("Total Students");
////mark_syllabus_item_header
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.layout_mis_student_detail_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText(requestType);
//
//            mTvGRNO = (TextView) innerListHeaderView.findViewById(R.id.grno_txt);
//            mTvGRNO.setVisibility(View.VISIBLE);
//            mTvGRNO.setText("Phone No.");
//            mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
////
//            mTvInnerAttendanceStatus = (TextView) innerListHeaderView.findViewById(R.id.status_txt);
//            mTvInnerAttendanceStatus.setVisibility(View.VISIBLE);
//            mTvInnerAttendanceStatus.setText("SMS");
//
//            mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//
//
//        } else if (requestType.equalsIgnoreCase("ConsistentAbsent")) {
//
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_absent3day_student_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//            innerTitleView.setVisibility(View.VISIBLE);
//
//            mTvInnerTitle = (TextView) innerListHeaderView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText(requestType);
//
//
//            mTVGrade = (TextView) innerListHeaderView.findViewById(R.id.grade_txt);
//            mTVGrade.setVisibility(View.VISIBLE);
//            mTVGrade.setText("Phone No.");
//            mTVGrade.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
//
//            mTvGRNO = (TextView) innerListHeaderView.findViewById(R.id.grno_txt);
//            mTvGRNO.setVisibility(View.VISIBLE);
//            mTvGRNO.setText("SMS");
//            mTvGRNO.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
//
//            mTvSection = (TextView) innerListHeaderView.findViewById(R.id.section_txt);
//            mTvSection.setVisibility(View.VISIBLE);
//            mTvSection.setText("Class");
//            mTvInnerAttendanceStatus.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f));
//
//        } else if (requestType.equalsIgnoreCase("Attendance less then 70%")) {
//
//            fragmentMisDataBinding.layoutStub.getViewStub().setLayoutResource(R.layout.list_item_mis_absent3day_student_header);
//            innerListHeaderView = fragmentMisDataBinding.layoutStub.getViewStub().inflate();
//
//            fragmentMisDataBinding.layoutStub1.getViewStub().setLayoutResource(R.layout.list_item_mis_data_header);
//            innerTitleView = fragmentMisDataBinding.layoutStub1.getViewStub().inflate();
//
//            mTvInnerTitle = (TextView) innerTitleView.findViewById(R.id.title_txt);
//            mTvInnerTitle.setText(requestType);
//
//            mTvAbsentFrom = (TextView) innerListHeaderView.findViewById(R.id.absentfrom_txt);
//            mTvAbsentFrom.setText("Absent(in %)");
//            mTvAbsentFrom.setVisibility(View.VISIBLE);
////
//            mTVGrade = (TextView) innerListHeaderView.findViewById(R.id.grade_txt);
//            mTVGrade.setText("Phone No.");
//            mTVGrade.setVisibility(View.VISIBLE);
////
//            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f);
//            params1.gravity = Gravity.CENTER;
//            mTVGrade.setLayoutParams(params1);
////
//            mTvGRNO = (TextView) innerListHeaderView.findViewById(R.id.grno_txt);
//            mTvGRNO.setVisibility(View.VISIBLE);
//            mTvGRNO.setText("SMS");
//            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f);
//            params2.gravity = Gravity.CENTER;
//            mTvGRNO.setLayoutParams(params2);
////
////
//            mTvSection = (TextView) innerListHeaderView.findViewById(R.id.section_txt);
//            mTvSection.setVisibility(View.VISIBLE);
//            mTvSection.setText("Class");
//            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f);
//            params3.gravity = Gravity.CENTER;
//            mTvSection.setLayoutParams(params3);
//
//
//        }


        progressBar.setVisibility(View.VISIBLE);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callStudentMISDataApi();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 2000);

        fragmentMisDataBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppConfiguration.ReverseTermDetailId = "";
//                fragment = new MISFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();

                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        fragmentMisDataBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppConfiguration.position = 66;
        AppConfiguration.firsttimeback = true;
    }

    private Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("Date", date);
        map.put("TermID", termID);
        map.put("RequestType", requestType);
        return map;
    }

    private void callStudentMISDataApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //Utils.showDialog(getActivity());
        ApiHandler.getApiService().getMISStudentdata(getParams(), new retrofit.Callback<MISStudentModel>() {
            @Override
            public void success(MISStudentModel staffSMSDataModel, Response response) {
                //Utils.dismissDialog();

                if (staffSMSDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
////                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    return;
                }
                if (staffSMSDataModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
////                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }
                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                    fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                    fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }

                if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                    progressBar.setVisibility(View.GONE);
//
                    misStudentStandardDataList = staffSMSDataModel.getFinalArray().get(0).getStandardData();
                    misStudentDataList = staffSMSDataModel.getFinalArray().get(0).getStudentData();
//                        misStudentANTDataList = staffSMSDataModel.getFinalArray().get(0).getANT();
//                        misStudentFinalDataList = staffSMSDataModel.getFinalArray();
//
                    fragmentMisDataBinding.lvHeader2.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.lvHeader.setVisibility(View.VISIBLE);
//                        fragmentMisDataBinding.recyclerLinear.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.recyclerLinear1.setVisibility(View.VISIBLE);
                    fragmentMisDataBinding.txtNoRecords.setVisibility(View.GONE);

                    List<MISStudentModel.FinalArray> mainArr = staffSMSDataModel.getFinalArray();
                    List<MISStudentModel.StandardDatum> newArr = staffSMSDataModel.getFinalArray().get(0).getStandardData();
                    List<MISStudentModel.StudentDatum> newArr1 = staffSMSDataModel.getFinalArray().get(0).getStudentData();


                    ArrayList<String> header = new ArrayList<>();
                    HashMap<String, ArrayList<MISStudentModel.StudentDatum>> child = new HashMap<>();

                    for (int i = 0; i < newArr.size(); i++) {

                        header.add(newArr.get(i).getStandard() + "|" + newArr.get(i).getTotalStudent());

                        ArrayList<MISStudentModel.StudentDatum> child_child = new ArrayList();
                        for (int j = 0; j < newArr1.size(); j++) {

                            if (newArr.get(i).getStandard().equalsIgnoreCase(newArr1.get(j).getGrade())) {

                                child_child.add(staffSMSDataModel.getFinalArray().get(0).getStudentData().get(j));
                            }

                            child.put(header.get(i), child_child);
                        }
                    }

                    if (!requestType.equalsIgnoreCase("ANT")) {
//
                        int totalCount = 0;

                        for (int count = 0; count < misStudentStandardDataList.size(); count++) {

                            totalCount += Integer.parseInt(misStudentStandardDataList.get(count).getTotalStudent());
                        }
//
                        if (requestType.equalsIgnoreCase("Present") || requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave") || requestType.equalsIgnoreCase("ConsistentAbsent") || requestType.equalsIgnoreCase("Attendance less then 70%")) {
                            fragmentMisDataBinding.tvTxt.setText(requestType + ": " + totalCount);
                        } else if (requestType.equalsIgnoreCase("Total")) {
                            fragmentMisDataBinding.tvTxt.setText("Total Student: " + totalCount);
                        }

                        if (requestType.equalsIgnoreCase("ConsistentAbsent")) {
                            misStudentAdapter = new MISStudenttAdapter(mContext, header, child, 1, "ConsistentAbsent");
                            rvMisdataList1.setAdapter(misStudentAdapter);

                        } else if (requestType.equalsIgnoreCase("Attendance less then 70%")) {
                            misStudentAdapter = new MISStudenttAdapter(mContext, header, child, 1, "Attendance less then 70%");
                            rvMisdataList1.setAdapter(misStudentAdapter);
                        }
//
                        else {
                            misStudentAdapter = new MISStudenttAdapter(mContext, header, child, 0, requestType);
                            rvMisdataList1.setAdapter(misStudentAdapter);
                        }
//
                    } else if (requestType.equalsIgnoreCase("ANT")) {
//
                        int totalCount = 0;

//                            for (int count = 0; count < misStudentANTDataList.size(); count++) {
//
//                                totalCount += misStudentANTDataList.get(count).getTotalStudent();
//                            }

                        for (int count = 0; count < misStudentStandardDataList.size(); count++) {

                            totalCount += Integer.parseInt(misStudentStandardDataList.get(count).getTotalStudent());
                        }

                        fragmentMisDataBinding.tvTxt.setText("Attendance Not Taken: " + totalCount);
                        misStudentAdapter = new MISStudenttAdapter(mContext, header, child, 0, "ANT");
                        rvMisdataList1.setAdapter(misStudentAdapter);
                    }
//
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                progressBar.setVisibility(View.GONE);
                fragmentMisDataBinding.lvHeader2.setVisibility(View.GONE);
//                fragmentMisDataBinding.lvHeader.setVisibility(View.GONE);
//                fragmentMisDataBinding.recyclerLinear.setVisibility(View.GONE);
                fragmentMisDataBinding.recyclerLinear1.setVisibility(View.GONE);
                fragmentMisDataBinding.txtNoRecords.setVisibility(View.VISIBLE);
                fragmentMisDataBinding.txtNoRecords.setText(error.getMessage());
                //Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

}
