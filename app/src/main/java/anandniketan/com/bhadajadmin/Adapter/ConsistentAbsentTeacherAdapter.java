package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Model.Staff.Datum;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 11/21/2017.
 */

public class ConsistentAbsentTeacherAdapter extends RecyclerView.Adapter<ConsistentAbsentTeacherAdapter.ViewHolder> {
    private Context context;
    private StaffAttendaceModel staffAttendaceModel;


    public ConsistentAbsentTeacherAdapter(Context mContext, StaffAttendaceModel staffAttendaceModel) {
        this.context = mContext;
        this.staffAttendaceModel = staffAttendaceModel;
    }


    @Override
    public ConsistentAbsentTeacherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.consistent_absent_teacher_list, parent, false);
        return new ConsistentAbsentTeacherAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ConsistentAbsentTeacherAdapter.ViewHolder holder, int position) {
        Datum details = staffAttendaceModel.getFinalArray().get(0).getConsistenceAbsent().get(position);

        holder.employee_txt.setText(String.valueOf(details.getEmpName().trim()));
        holder.days_txt.setText(String.valueOf(details.getDays()));
    }

    @Override
    public int getItemCount() {
        return staffAttendaceModel.getFinalArray().get(0).getConsistenceAbsent().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView employee_txt, days_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            employee_txt = (TextView) itemView.findViewById(R.id.employeename_txt);
            days_txt = (TextView) itemView.findViewById(R.id.daysvalue_txt);
        }
    }

}