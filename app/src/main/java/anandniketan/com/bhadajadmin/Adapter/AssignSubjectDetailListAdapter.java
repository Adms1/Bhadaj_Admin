package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayAssignSubjectModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 12/20/2017.
 */

public class AssignSubjectDetailListAdapter extends RecyclerView.Adapter<AssignSubjectDetailListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArrayAssignSubjectModel> AssignSubjectModelList;


    public AssignSubjectDetailListAdapter(Context mContext, List<FinalArrayAssignSubjectModel> AssignSubjectModelList) {
        this.context = mContext;
        this.AssignSubjectModelList = AssignSubjectModelList;
    }


    @Override
    public AssignSubjectDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_subject_detail_list_item, parent, false);

        return new AssignSubjectDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssignSubjectDetailListAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);
        holder.teachername_txt.setText(AssignSubjectModelList.get(position).getTeacherName());
        holder.subject_txt.setText(AssignSubjectModelList.get(position).getSubject());
    }

    @Override
    public int getItemCount() {
        return AssignSubjectModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, teachername_txt, subject_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            teachername_txt = (TextView) itemView.findViewById(R.id.teachername_txt);
            subject_txt = (TextView) itemView.findViewById(R.id.subject_txt);
        }
    }

}

