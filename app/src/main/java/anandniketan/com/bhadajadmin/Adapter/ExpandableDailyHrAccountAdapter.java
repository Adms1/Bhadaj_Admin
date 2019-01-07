package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.HR.DailyAccountModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyTransportationModel;
import anandniketan.com.bhadajadmin.R;

public class ExpandableDailyHrAccountAdapter extends BaseExpandableListAdapter{


    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<DailyAccountModel.FinalArray>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public ExpandableDailyHrAccountAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<DailyAccountModel.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
    }

        @Override
        public List<DailyAccountModel.FinalArray> getChild(int groupPosition, int childPosititon) {
            return this.listChildData.get(this._listDataHeader.get(groupPosition));
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
    }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final List<DailyAccountModel.FinalArray> childData = getChild(groupPosition, 0);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_child_daily_account, null);
            }

            TextView txt_bb,txt_petty_cash,txt_expenses,txt_fees_rec,txt_deposited,txt_other;

            txt_bb = (TextView)convertView.findViewById(R.id.txt_bb);
            txt_petty_cash = (TextView)convertView.findViewById(R.id.txt_petty_cash);
            txt_expenses = (TextView)convertView.findViewById(R.id.txt_expenses);
            txt_fees_rec = (TextView)convertView.findViewById(R.id.txt_fees_rec);
            txt_deposited = (TextView)convertView.findViewById(R.id.txt_deposited);
            txt_other = (TextView)convertView.findViewById(R.id.txt_other);

            txt_bb.setText(String.valueOf(childData.get(childPosition).getBankBalance()));
            txt_petty_cash.setText(String.valueOf(childData.get(childPosition).getPettyCash()));
            txt_expenses.setText(String.valueOf(childData.get(childPosition).getExpenses()));
            txt_fees_rec.setText(String.valueOf(childData.get(childPosition).getFeesRecieved()));
            txt_deposited.setText(String.valueOf(childData.get(childPosition).getDepositedInBank()));
            txt_other.setText(String.valueOf(childData.get(childPosition).getOther()));

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

            String headerTitle1 = headerTitle[0];
            String headerTitle2 = headerTitle[1];

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_header_daily_account, null);
            }
            TextView txt_index, txt_date,txt_createby;
            ImageView iv_indicator;

            txt_index = (TextView) convertView.findViewById(R.id.index_txt);
            txt_date = (TextView) convertView.findViewById(R.id.date_txt);
            txt_createby = (TextView) convertView.findViewById(R.id.createby_txt);
            iv_indicator = (ImageView)convertView.findViewById(R.id.iv_indicator);

            String index  = String.valueOf(groupPosition + 1);

            txt_index.setText(index);
            txt_date.setText(headerTitle1);
            txt_createby.setText(headerTitle2);


            if (isExpanded) {
                iv_indicator.setImageResource(R.drawable.arrow_1_42_down);
            } else {
                iv_indicator.setImageResource(R.drawable.arrow_1_42);
            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }



    }




