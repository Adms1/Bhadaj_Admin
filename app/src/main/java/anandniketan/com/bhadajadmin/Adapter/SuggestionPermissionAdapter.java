package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Interface.onDeleteButton;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;

public class SuggestionPermissionAdapter extends RecyclerView.Adapter<SuggestionPermissionAdapter.MyViewHolder> {
    onDeleteButton onDeleteButton;
    ArrayList<String> deleteId;
    private Context context;
    private StudentAttendanceModel profilePermissionModel;

    public SuggestionPermissionAdapter(Context mContext, StudentAttendanceModel profilePermissionModel, onDeleteButton onDeleteButton) {
        this.context = mContext;
        this.profilePermissionModel = profilePermissionModel;
        this.onDeleteButton = onDeleteButton;
    }


    @Override
    public SuggestionPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_permission_list, parent, false);
        return new SuggestionPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuggestionPermissionAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        final StudentAttendanceFinalArray result = profilePermissionModel.getFinalArray().get(position);
        holder.index_txt.setText(sr);
        holder.type_txt.setText(result.getType());
        holder.assign_txt.setText(result.getEmployeeName());
        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteId = new ArrayList<>();
                deleteId.add(String.valueOf(result.getAssignPermissionID()));
                onDeleteButton.deleteSentMessage();
            }
        });

    }

    @Override
    public int getItemCount() {
        return profilePermissionModel.getFinalArray().size();
    }

    public ArrayList<String> getId() {
        return deleteId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type_txt, assign_txt, index_txt;
        ImageView delete_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            type_txt = (TextView) itemView.findViewById(R.id.type_txt);
            assign_txt = (TextView) itemView.findViewById(R.id.assign_txt);
            delete_img = (ImageView) itemView.findViewById(R.id.delete_img);
        }
    }
}
