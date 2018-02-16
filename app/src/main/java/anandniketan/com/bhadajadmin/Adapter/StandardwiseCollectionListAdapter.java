package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStandardCollectionModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 11/28/2017.
 */

public class StandardwiseCollectionListAdapter extends RecyclerView.Adapter<StandardwiseCollectionListAdapter.ViewHolder> {
    private Context context;
    private List<AccountFeesStandardCollectionModel> studentCollectionModel;

    public StandardwiseCollectionListAdapter(Context mContext, List<AccountFeesStandardCollectionModel> studentCollectionModel) {
        this.context = mContext;
        this.studentCollectionModel = studentCollectionModel;
    }


    @Override
    public StandardwiseCollectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.standardwise_collection_list, parent, false);
        return new StandardwiseCollectionListAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(StandardwiseCollectionListAdapter.ViewHolder holder, int position) {
        String amount1 = "", amount2 = "", amount3 = "";
        Double longval1 = null, longval2 = null, longval3 = null;
        DecimalFormat formatter = new DecimalFormat("#,##,##,##");
        String formattedString1, formattedString2, formattedString3;

        amount1 = String.valueOf(studentCollectionModel.get(position).getStdTotalFees());
        amount2 = String.valueOf(studentCollectionModel.get(position).getStdTotalRcv());
        amount3 = String.valueOf(studentCollectionModel.get(position).getStdTotalDues());

        longval1 = Double.parseDouble(amount1);
        longval2 = Double.parseDouble(amount2);
        longval3 = Double.parseDouble(amount3);

        formattedString1 = String.format("%,.0f", longval1);
        formattedString2 = String.format("%,.0f", longval2);
        formattedString3 = String.format("%,.0f", longval3);

        holder.std_txt.setText(studentCollectionModel.get(position).getStandard());
        holder.total_txt.setText("₹" + " " +formattedString1);
        holder.received_txt.setText("₹" + " " +formattedString2);
        holder.dues_txt.setText("₹" + " " +formattedString3);
        holder.total_per_txt.setText("(" + studentCollectionModel.get(position).getStdStudent() + ")");
        holder.received_per_txt.setText("(" + studentCollectionModel.get(position).getStdStudentRcv() + ")");
        holder.dues_per_txt.setText("(" + studentCollectionModel.get(position).getStdStudentDues() + ")");

    }

    @Override
    public int getItemCount() {
        return studentCollectionModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView std_txt, total_txt, received_txt, dues_txt, total_per_txt, received_per_txt, dues_per_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            std_txt = (TextView) itemView.findViewById(R.id.std_txt);
            total_txt = (TextView) itemView.findViewById(R.id.total_txt);
            received_txt = (TextView) itemView.findViewById(R.id.received_txt);
            dues_txt = (TextView) itemView.findViewById(R.id.dues_txt);
            total_per_txt = (TextView) itemView.findViewById(R.id.total_per_txt);
            received_per_txt = (TextView) itemView.findViewById(R.id.received_per_txt);
            dues_per_txt = (TextView) itemView.findViewById(R.id.dues_per_txt);
        }
    }

}