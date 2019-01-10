package anandniketan.com.bhadajadmin.Adapter;

//Created by Antra 7/1/2019

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;
import anandniketan.com.bhadajadmin.Model.MIS.MISHeadwiseFee;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.SpacesItemDecoration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

public class HeadwiseFeeAdapter extends RecyclerView.Adapter<HeadwiseFeeAdapter.MyViewHolder> {

    private static int lastCheckedPos = 0;
    private static CheckBox lastChecked = null;
    Context context;
    //    SparseBooleanArray sparseBooleanArray;
    private ArrayList<MISHeadwiseFee.FinalArray> finalArrayGetFeeModels;
    private Fragment fragment = null;
    private Bundle bundle;
    private FragmentManager fragmentManager = null;
    private String termid;
    private HeaderwiseStudentDetailAdapter misFinanceReportAdapter;
    private ArrayList<HeadwiseStudent.Finalarray> financedataValues;

    public HeadwiseFeeAdapter(Context context, ArrayList<MISHeadwiseFee.FinalArray> finalArrayGetFeeModels, String termid) {

        this.context = context;
        this.finalArrayGetFeeModels = finalArrayGetFeeModels;
        this.termid = termid;

//        sparseBooleanArray=new SparseBooleanArray();

    }

    @Override
    public HeadwiseFeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.headwise_list_item, parent, false);
        return new HeadwiseFeeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HeadwiseFeeAdapter.MyViewHolder holder, final int position) {

//        holder.ivview.setChecked(finalArrayGetFeeModels.get(position).isSelected());
        holder.ivview.setTag(new Integer(position));

        if (finalArrayGetFeeModels.get(position).isSelected()) {

            holder.ll.setVisibility(View.VISIBLE);
            holder.ivview.setImageResource(R.drawable.arrow_1_42_down);

            if (!Utils.checkNetwork(context)) {
                Utils.showCustomDialog(context.getResources().getString(R.string.internet_error), context.getResources().getString(R.string.internet_connection_error), (Activity) context);
                return;
            }

            WebServices apiService = ApiClient.getClient().create(WebServices.class);
            Call<HeadwiseStudent> call = apiService.getHeadWiseFeesCollectionStudent(AppConfiguration.BASEURL + "GetHeadDetailByStudentID?StudentID=" + finalArrayGetFeeModels.get(position).getStudentID() + "&Term=" + termid);

            call.enqueue(new Callback<HeadwiseStudent>() {

                @Override
                public void onResponse(Call<HeadwiseStudent> call, retrofit2.Response<HeadwiseStudent> response) {
//                progressBar.setVisibility(View.GONE);
                    HeadwiseStudent staffSMSDataModel = response.body();
                    if (staffSMSDataModel == null) {
                        Utils.ping(context, context.getString(R.string.something_wrong));
                        return;
                    }
                    if (staffSMSDataModel.getSuccess() == null) {
                        Utils.ping(context, context.getString(R.string.something_wrong));
                        return;
                    }
                    if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                        Utils.ping(context, context.getString(R.string.false_msg));

                        return;
                    }
                    if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                        try {

                            financedataValues = staffSMSDataModel.getFinalArray();

                            misFinanceReportAdapter = new HeaderwiseStudentDetailAdapter(context, financedataValues);
                            holder.rvList.setLayoutManager(new GridLayoutManager(context, financedataValues.size(), OrientationHelper.HORIZONTAL, false));
                            holder.rvList.addItemDecoration(new SpacesItemDecoration(0));
                            holder.rvList.setAdapter(misFinanceReportAdapter);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<HeadwiseStudent> call, Throwable t) {
                    t.getMessage();
                }
            });
//
        } else {
            holder.ll.setVisibility(View.GONE);
            holder.ivview.setImageResource(R.drawable.arrow_1_42);

        }
        holder.classtxt.setText(finalArrayGetFeeModels.get(position).getStandard() + "-" + finalArrayGetFeeModels.get(position).getClassName());
        holder.stndrdtxt.setText(finalArrayGetFeeModels.get(position).getStudentName());
        holder.phonetxt.setText(finalArrayGetFeeModels.get(position).getMobileNo());

        holder.rvList.setNestedScrollingEnabled(false);

//        setClickListner(holder, position);

        holder.ivview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyDataSetChanged();
                if (finalArrayGetFeeModels.get(position).isSelected()) {
                    finalArrayGetFeeModels.get(position).setSelected(false);
                } else {

                    for (int i = 0; i < finalArrayGetFeeModels.size(); i++) {
                        finalArrayGetFeeModels.get(i).setSelected(false);
                    }

                    finalArrayGetFeeModels.get(position).setSelected(true);

                }

                notifyDataSetChanged();
//                act.fillSubCategories(position);

//                lastCheckedPosition=position;
//                fragment = new HeadCollectionStudentFragment();
//                bundle = new Bundle();
//                bundle.putString("StudentID", finalArrayGetFeeModels.get(position).getStudentID());
//                bundle.putString("TermID", termid);
////                        bundle.putString("countdata",holder.total_txt.getText().toString());
////                        bundle.putString("Date",AppConfiguration.staffDate);
////                        bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
//                fragment.setArguments(bundle);
//                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
//                        .add(R.id.frame_container, fragment).addToBackStack(null).commit();
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 65;
            }


        });
    }

    @Override
    public int getItemCount() {
        return finalArrayGetFeeModels.size();
    }

    private void callFinanceMISDataApi(String studentId, String termId) {


    }

    private void setClickListner(final HeadwiseFeeAdapter.MyViewHolder holder, final int position) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView classtxt, stndrdtxt, phonetxt;
        ImageView ivview;
        LinearLayout ll;
        RecyclerView rvList;

        private boolean mIsViewExpanded;

        public MyViewHolder(View itemView) {
            super(itemView);
//            head_txt = (TextView) itemView.findViewById(R.id.head_txt);
            classtxt = itemView.findViewById(R.id.head_item_class_txt);
            stndrdtxt = itemView.findViewById(R.id.head_item_stndrd_txt);
            phonetxt = itemView.findViewById(R.id.head_item_phone_txt);
            ivview = itemView.findViewById(R.id.head_item_view_txt);
            rvList = itemView.findViewById(R.id.headwisestudent_rv_finance_list);
            ll = itemView.findViewById(R.id.headwisestudent_linear_finance_recyler_grid);

        }

    }

}
