package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 11/20/2017.
 */

public class StandardwiseStudentAttendaceAdapter extends RecyclerView.Adapter<StandardwiseStudentAttendaceAdapter.ViewHolder> {
    private Context context;
    private StudentAttendanceModel studentAttendanceModel;

    public StandardwiseStudentAttendaceAdapter(Context mContext, StudentAttendanceModel studentAttendanceModel) {
        this.context = mContext;
        this.studentAttendanceModel = studentAttendanceModel;
    }




    @Override
    public StandardwiseStudentAttendaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standardwise_attendace_list, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(StandardwiseStudentAttendaceAdapter.ViewHolder holder, int position) {
        StandardWiseAttendanceModel detail = studentAttendanceModel.getFinalArray().get(0).getStandardWiseAttendance().get(position);
        holder.standard_txt.setText(detail.getStandard());
        holder.status_txt.setText(detail.getStatus());
    }

    @Override
    public int getItemCount() {
        return studentAttendanceModel.getFinalArray().get(0).getStandardWiseAttendance().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView standard_txt, status_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            standard_txt = (TextView) itemView.findViewById(R.id.standard_txt);
            status_txt = (TextView) itemView.findViewById(R.id.status_txt);
        }
    }

}