package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Interface.getEditpermission;
import anandniketan.com.bhadajadmin.Interface.onDeleteButton;
import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 12/20/2017.
 */

public class ClassTeacherDetailListAdapter extends RecyclerView.Adapter<ClassTeacherDetailListAdapter.MyViewHolder> {
    List<FinalArrayStaffModel> classTeacherDetail;
    onDeleteButton onDeleteButton;
    getEditpermission getEditpermission;
    ArrayList<String> deleteId;
    ArrayList<String> editId;
    private Context context;

    public ClassTeacherDetailListAdapter(Context mContext, List<FinalArrayStaffModel> classTeacherDetail, onDeleteButton onDeleteButton, getEditpermission getEditpermission) {
        this.context = mContext;
        this.classTeacherDetail = classTeacherDetail;
        this.onDeleteButton = onDeleteButton;
        this.getEditpermission = getEditpermission;

    }


    @Override
    public ClassTeacherDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_teacher_detail_list_item, parent, false);

        return new ClassTeacherDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClassTeacherDetailListAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);

        String[] splitValue = classTeacherDetail.get(position).getStandard().split("\\-");
//        holder.index_txt.setText(sr);
        holder.grade_txt.setText(splitValue[0]);
        holder.teachername_txt.setText(classTeacherDetail.get(position).getName());
        holder.section_txt.setText(splitValue[1]);

        holder.edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editId = new ArrayList<>();
                editId.add(String.valueOf(classTeacherDetail.get(position).getPkClsTeacherID()) + "|" +
                        classTeacherDetail.get(position).getName() + "|" + holder.grade_txt.getText().toString() + "|" +
                        holder.section_txt.getText().toString());
                getEditpermission.getEditpermission();
            }
        });
        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteId = new ArrayList<>();
                deleteId.add(String.valueOf(classTeacherDetail.get(position).getPkClsTeacherID()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return classTeacherDetail.size();
    }

    public ArrayList<String> getId() {
        return deleteId;
    }

    public ArrayList<String> getEditId() {
        return editId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, grade_txt, teachername_txt, section_txt;
        ImageView edit_img, delete_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            //  index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            teachername_txt = (TextView) itemView.findViewById(R.id.classteachername_txt);
            section_txt = (TextView) itemView.findViewById(R.id.section_txt);

            edit_img = (ImageView) itemView.findViewById(R.id.edit_img);
            delete_img = (ImageView) itemView.findViewById(R.id.delete_img);
        }
    }

}

