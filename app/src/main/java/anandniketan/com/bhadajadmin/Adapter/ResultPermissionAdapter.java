package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 1/24/2018.
 */

public class ResultPermissionAdapter extends RecyclerView.Adapter<ResultPermissionAdapter.MyViewHolder> {
    private Context context;
    private StudentAttendanceModel resultPermissionModel;
    private ArrayList<String> rowvalue = new ArrayList<String>();
    getEditpermission listner;

    public ResultPermissionAdapter(Context mContext, StudentAttendanceModel resultPermissionModel, getEditpermission listner) {
        this.context = mContext;
        this.resultPermissionModel = resultPermissionModel;
        this.listner = listner;
    }


    @Override
    public ResultPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_permission_list, parent, false);
        return new ResultPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultPermissionAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        final StudentAttendanceFinalArray result = resultPermissionModel.getFinalArray().get(position);
        holder.index_txt.setText(sr);
        holder.academicyear_txt.setText(resultPermissionModel.getYear());
        holder.grade_txt.setText(String.valueOf(result.getStandard()));
        holder.resultstatus_txt.setText(result.getStatus());

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowvalue.add(resultPermissionModel.getYear() + "|" + result.getStandard() + "|" + result.getStatus());
                listner.getEditpermission();
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultPermissionModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, academicyear_txt, grade_txt, resultstatus_txt;
        ImageView edit_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            academicyear_txt = (TextView) itemView.findViewById(R.id.academicyear_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            resultstatus_txt = (TextView) itemView.findViewById(R.id.resultstatus_txt);
            edit_img = (ImageView) itemView.findViewById(R.id.edit_img);

        }
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }
}
