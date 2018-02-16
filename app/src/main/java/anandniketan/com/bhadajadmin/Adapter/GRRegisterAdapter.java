package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentFullDetailModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;


/**
 * Created by admsandroid on 1/25/2018.
 */

public class GRRegisterAdapter extends RecyclerView.Adapter<GRRegisterAdapter.MyViewHolder> {
    private Context context;
    private StudentFullDetailModel filteredDataModel;
    private onViewClick onViewClick;

    public GRRegisterAdapter(Context mContext, StudentFullDetailModel studentFullDetailModel, onViewClick flag) {
        this.context = mContext;
        this.filteredDataModel = filteredDataModel;
        this.onViewClick = onViewClick;
    }


    @Override
    public GRRegisterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grregister_list, parent, false);
        return new GRRegisterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GRRegisterAdapter.MyViewHolder holder, int position) {
        final FinalArrayStudentFullDetailModel filter = filteredDataModel.getFinalArray().get(position);
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);
        holder.firstname_txt.setText(filter.getFirstName());
        holder.lastname_txt.setText(filter.getLastName());
        holder.grnno_txt.setText(String.valueOf(filter.getGRNO()));
        holder.grade_txt.setText(filter.getStandard());

        holder.view_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfiguration.CheckStudentId = filter.getStudentId().toString();
                Log.d("CheckStudentId",AppConfiguration.CheckStudentId);
                onViewClick.getViewClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataModel.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, firstname_txt, lastname_txt, grnno_txt, grade_txt, view_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt = (TextView) itemView.findViewById(R.id.index_txt);
            firstname_txt = (TextView) itemView.findViewById(R.id.firstname_txt);
            lastname_txt = (TextView) itemView.findViewById(R.id.lastname_txt);
            grnno_txt = (TextView) itemView.findViewById(R.id.grnno_txt);
            grade_txt = (TextView) itemView.findViewById(R.id.grade_txt);
            view_txt = (TextView) itemView.findViewById(R.id.view_txt);

        }
    }

}
