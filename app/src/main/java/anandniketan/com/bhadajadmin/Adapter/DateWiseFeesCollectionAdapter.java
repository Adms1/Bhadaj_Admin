package anandniketan.com.bhadajadmin.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.DateWiseFeesCollectionModel;
import anandniketan.com.bhadajadmin.Model.Student.AnnouncementModel;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentModel;
import anandniketan.com.bhadajadmin.R;

public class DateWiseFeesCollectionAdapter extends RecyclerView.Adapter<DateWiseFeesCollectionAdapter.MyViewHolder> {

    private Context context;
    private List<DateWiseFeesCollectionModel.FinalArray> announcmentModel;
    SpannableStringBuilder discriptionSpanned;
    String discriptionStr;

    public DateWiseFeesCollectionAdapter(Context mContext, List<DateWiseFeesCollectionModel.FinalArray> announcmentModel) {
        this.context = mContext;
        this.announcmentModel = announcmentModel;
    }


    @Override
    public DateWiseFeesCollectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group_student_discount_detail, parent, false);
        return new DateWiseFeesCollectionAdapter.MyViewHolder(itemView);
    }

        @Override
        public void onBindViewHolder(DateWiseFeesCollectionAdapter.MyViewHolder holder, int position) {

            final DateWiseFeesCollectionModel.FinalArray result = announcmentModel.get(position);

            holder.studentname_txt.setText(result.getName());
            holder.grnno_txt.setText(result.getGRNO());
            holder.section_txt.setText(result.getStandard());
            holder.totalPaid_txt.setText("₹"+String.valueOf(result.getAmount()));


        holder.view_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_datewise_collection_list_child_item);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                    TextView tvUserName = (TextView) dialog.findViewById(R.id.tv_name);

                    TextView tvOpeningBal = (TextView)dialog.findViewById(R.id.tv_opening_bal);
                    TextView totalFees  = (TextView)dialog.findViewById(R.id.tv_totalfees_bal);

                    tvOpeningBal.setText("₹"+result.getOpeningBalance());
                    totalFees.setText("₹"+result.getTotalAmt());

                    tvUserName.setText(result.getName()+"("+result.getGRNO()+")");

                    RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.rv_datewisechildlist);

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    List<DateWiseFeesCollectionModel.FeeReceiptDetail> dataList = result.getFeeReceiptDetail() ;

                    DatewiseFeesChildItemAdapter datewiseFeesChildItemAdapter  = new DatewiseFeesChildItemAdapter(context,dataList);
                    recyclerView.setAdapter(datewiseFeesChildItemAdapter);

                    Button btnClose = (Button) dialog.findViewById(R.id.close_btn);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return announcmentModel.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView studentname_txt,grnno_txt, section_txt,totalPaid_txt, view_txt;


            public MyViewHolder(View itemView) {
                super(itemView);
                studentname_txt = (TextView) itemView.findViewById(R.id.studentname_txt);
                grnno_txt = (TextView) itemView.findViewById(R.id.grnno_txt);
                section_txt = (TextView) itemView.findViewById(R.id.section_txt);
                totalPaid_txt = (TextView) itemView.findViewById(R.id.totalPaid_txt);
                view_txt = (TextView) itemView.findViewById(R.id.view_txt);


            }


            private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
            int trimStart = 0;
            int trimEnd = 0;
            String text = spannable.toString();

            while (text.length() > 0 && text.startsWith("\n")) {
                text = text.substring(1);
                trimStart += 1;
            }
            while (text.length() > 0 && text.endsWith("\n")) {
                text = text.substring(0, text.length() - 1);
                trimEnd += 1;
            }
            return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
        }

    }

}
