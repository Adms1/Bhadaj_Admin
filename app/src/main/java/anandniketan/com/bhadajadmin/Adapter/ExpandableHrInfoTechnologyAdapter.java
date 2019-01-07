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

import anandniketan.com.bhadajadmin.Model.HR.DailyHrAdminModel;
import anandniketan.com.bhadajadmin.Model.HR.DailyInfoTechnology;
import anandniketan.com.bhadajadmin.R;

public class ExpandableHrInfoTechnologyAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<DailyInfoTechnology.FinalArray>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public ExpandableHrInfoTechnologyAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<DailyInfoTechnology.FinalArray>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
    }

    @Override
    public List<DailyInfoTechnology.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<DailyInfoTechnology.FinalArray> childData = getChild(groupPosition, 0);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_daily_hr_it,null);
        }

        TextView txt_laptop,txt_tablet,txt_printing,txt_it,txt_other;
        txt_laptop = (TextView)convertView.findViewById(R.id.txt_laptop);
        txt_tablet = (TextView)convertView.findViewById(R.id.txt_tablet);
        txt_printing = (TextView)convertView.findViewById(R.id.txt_printing);
        txt_it = (TextView)convertView.findViewById(R.id.txt_it);
        txt_other = (TextView)convertView.findViewById(R.id.txt_other);


        txt_laptop.setText(String.valueOf(childData.get(childPosition).getLaptopIssued()));
        txt_tablet.setText(String.valueOf(childData.get(childPosition).getTabletIssued()));
        txt_printing.setText(String.valueOf(childData.get(childPosition).getPrinting()));
        txt_it.setText(String.valueOf(childData.get(childPosition).getIT()));
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
            convertView = infalInflater.inflate(R.layout.list_item_header_daily_hr_admin, null);
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
