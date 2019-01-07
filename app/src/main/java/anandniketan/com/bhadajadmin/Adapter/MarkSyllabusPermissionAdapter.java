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

import anandniketan.com.bhadajadmin.Interface.OnEditRecordWithPosition;
import anandniketan.com.bhadajadmin.Model.Student.MarkSyllabusModel;
import anandniketan.com.bhadajadmin.R;

public class MarkSyllabusPermissionAdapter extends RecyclerView.Adapter<MarkSyllabusPermissionAdapter.MyViewHolder> {
    private Context context;
    private MarkSyllabusModel profilePermissionModel;
    private List<MarkSyllabusModel.FinalArray> mDataList = new ArrayList<MarkSyllabusModel.FinalArray>();
    private OnEditRecordWithPosition listner;
    private ArrayList<String> rowvalue = new ArrayList<String>();


    public MarkSyllabusPermissionAdapter(Context mContext, MarkSyllabusModel profilePermissionModel, OnEditRecordWithPosition listner) {
        this.context= mContext;
        this.profilePermissionModel = profilePermissionModel;
        this.mDataList = profilePermissionModel.getFinalArray();
        this.listner = listner;
    }


    @Override
    public MarkSyllabusPermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mark_syllabus, parent, false);
        return new MarkSyllabusPermissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MarkSyllabusPermissionAdapter.MyViewHolder holder, final int position) {
        String sr = String.valueOf(position + 1);
        final MarkSyllabusModel.FinalArray result = mDataList.get(position);

        rowvalue.add(result.getTerm() + "|" + result.getType() + "|" + result.getStandard() + "|" +result.getTestName()+ "|" +result.getStatus() +"|" +result.getPermissionID());

        holder.term_txt.setText(result.getTerm());
        holder.type_txt.setText(String.valueOf(result.getType()));
        holder.grade_txt.setText(result.getStandard());
        holder.testname_txt.setText(result.getTestName());
        holder.result_status_txt.setText(result.getStatus());

        holder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.getEditpermission(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView term_txt, type_txt,grade_txt,testname_txt, result_status_txt;
        private ImageView mIvEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            term_txt = (TextView) itemView.findViewById(R.id.term_txt);
            type_txt = (TextView) itemView.findViewById(R.id.type_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            testname_txt = (TextView) itemView.findViewById(R.id.testname_txt);
            result_status_txt = (TextView) itemView.findViewById(R.id.result_status_txt);
            mIvEdit = (ImageView) itemView.findViewById(R.id.iv_edt);
        }
    }

    public ArrayList<String> getRowValue() {
        return rowvalue;
    }

    public List<MarkSyllabusModel.FinalArray> getDatas() {
        return mDataList;
    }
}
