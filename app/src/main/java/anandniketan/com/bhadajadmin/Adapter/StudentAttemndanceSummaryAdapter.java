package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.ConsistentAbsentStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.TotalAttendanceItemBinding;

public class StudentAttemndanceSummaryAdapter extends RecyclerView.Adapter<StudentAttemndanceSummaryAdapter.ViewHolder> {
    TotalAttendanceItemBinding totalAttendanceItemBinding;
    List<StudentAttendanceFinalArray> studentAttendanceFinalArrayList;
    List<StandardWiseAttendanceModel> standardWiseAttendanceModelList;
    List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList;
    StandardwiseStudentAttendaceAdapter standardwiseStudentAttendaceAdapter;
    ConsistentAbsentTeacherAdapter consistentAbsentTeacherAdapter;
    private Context context;

    public StudentAttemndanceSummaryAdapter(Context mContext, List<StudentAttendanceFinalArray> studentAttendanceFinalArrayList, List<StandardWiseAttendanceModel> standardWiseAttendanceModelList, List<ConsistentAbsentStudentModel> consistentAbsentStudentModelList) {
        this.context = mContext;
        this.studentAttendanceFinalArrayList = studentAttendanceFinalArrayList;
        this.standardWiseAttendanceModelList=standardWiseAttendanceModelList;
        this.consistentAbsentStudentModelList=consistentAbsentStudentModelList;
    }


    @Override
    public StudentAttemndanceSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        totalAttendanceItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.total_attendance_item, parent, false);
        view = totalAttendanceItemBinding.getRoot();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(StudentAttemndanceSummaryAdapter.ViewHolder holder, int position) {
        totalAttendanceItemBinding.absentstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentAbsent());
        totalAttendanceItemBinding.presentstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentPresent());
        totalAttendanceItemBinding.leavestudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getStudentLeave());
        totalAttendanceItemBinding.totalstudentCountTxt.setText(studentAttendanceFinalArrayList.get(position).getTotalStudent());

        standardwiseStudentAttendaceAdapter = new StandardwiseStudentAttendaceAdapter(context, standardWiseAttendanceModelList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
        totalAttendanceItemBinding.standardwiseRcv.setLayoutManager(mLayoutManager);
        totalAttendanceItemBinding.standardwiseRcv.setItemAnimator(new DefaultItemAnimator());
        totalAttendanceItemBinding.standardwiseRcv.setAdapter(standardwiseStudentAttendaceAdapter);

        consistentAbsentTeacherAdapter=new ConsistentAbsentTeacherAdapter(context,consistentAbsentStudentModelList);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
        totalAttendanceItemBinding.consistentAbsentRcv.setLayoutManager(mLayoutManager1);
        totalAttendanceItemBinding.consistentAbsentRcv.setItemAnimator(new DefaultItemAnimator());
        totalAttendanceItemBinding.consistentAbsentRcv.setAdapter(consistentAbsentTeacherAdapter);

    }

    @Override
    public int getItemCount() {
        return studentAttendanceFinalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}