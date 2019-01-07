package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.LeaveModel;
import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveBalanceListAdapter extends RecyclerView.Adapter<LeaveBalanceListAdapter.MyViewHolder> {
    private Context context;
    private List<LeaveModel.FinalArray> data;
    ImageLoader imageLoader;

    public LeaveBalanceListAdapter(Context mContext,List<LeaveModel.FinalArray> data) {
        this.context = mContext;
        this.data = data;
    }


    @Override
    public LeaveBalanceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_leave_balance_list_item, parent, false);
        return new LeaveBalanceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaveBalanceListAdapter.MyViewHolder holder, int position) {
        imageLoader = ImageLoader.getInstance();

        LeaveModel.FinalArray dataItem = data.get(position);

        try {
            holder.emp_name_txt.setText(dataItem.getEmployeeName()+ "("+dataItem.getEmployeeID()+")");
            holder.txt_totalPL.setText(String.valueOf(dataItem.getTotalPL()));
            holder.txt_donePL.setText(String.valueOf(dataItem.getPLUSed()));
            holder.txt_doneCL.setText(String.valueOf(dataItem.getCLUsed()));
            holder.txt_totalCL.setText(String.valueOf(dataItem.getTotalCL()));
            holder.txt_doneLeave.setText(String.valueOf(dataItem.getUsed()));
            holder.txt_totalLeave.setText(String.valueOf(dataItem.getTotal()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView emp_name_txt,txt_totalPL,txt_donePL,txt_totalCL,txt_doneCL,txt_doneLeave,txt_totalLeave;

        public MyViewHolder(View itemView) {
            super(itemView);
            emp_name_txt = (TextView) itemView.findViewById(R.id.empname_txt);
            txt_totalPL = (TextView) itemView.findViewById(R.id.totalpl_txt);
            txt_donePL = (TextView) itemView.findViewById(R.id.donepl_txt);
            txt_totalCL = (TextView) itemView.findViewById(R.id.totalcl_txt);
            txt_doneCL = (TextView) itemView.findViewById(R.id.donecl_txt);
            txt_doneLeave = (TextView) itemView.findViewById(R.id.donetotalleave_txt);
            txt_totalLeave = (TextView) itemView.findViewById(R.id.totalleave_txt);

        }
    }
}
