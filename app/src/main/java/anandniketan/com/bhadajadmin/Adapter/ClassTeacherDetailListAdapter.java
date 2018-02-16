package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayClassTeacherDetailModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 12/20/2017.
 */

public class ClassTeacherDetailListAdapter extends RecyclerView.Adapter<ClassTeacherDetailListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArrayClassTeacherDetailModel> classTeacherDetail;

    public ClassTeacherDetailListAdapter(Context mContext, List<FinalArrayClassTeacherDetailModel> classTeacherDetail) {
        this.context = mContext;
        this.classTeacherDetail = classTeacherDetail;
    }


    @Override
    public ClassTeacherDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_teacher_detail_list_item, parent, false);

        return new ClassTeacherDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassTeacherDetailListAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);

        String[]splitValue=classTeacherDetail.get(position).getStandard().split("\\-");
        holder.index_txt.setText(sr);
        holder.grade_txt.setText(splitValue[0]);
        holder.teachername_txt.setText(classTeacherDetail.get(position).getName());
        holder.section_txt.setText(splitValue[1]);
    }

    @Override
    public int getItemCount() {
        return classTeacherDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, grade_txt, teachername_txt, section_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            teachername_txt = (TextView) itemView.findViewById(R.id.classteachername_txt);
            section_txt = (TextView) itemView.findViewById(R.id.section_txt);
        }
    }

}

