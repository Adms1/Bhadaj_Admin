package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.ListGroupStudentInquiryDataDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemHeaderBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemInquiryDataBinding;


/**
 * Created by admsandroid on 1/30/2018.
 */

public class ExpandableListAdapterInquiryData extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<StandardWiseAttendanceModel>> listChildData;
    private HashMap<String, String> listfooterDate;


    public ExpandableListAdapterInquiryData(Context context, List<String> listDataHeader, HashMap<String, List<StandardWiseAttendanceModel>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
    }

    @Override
    public StandardWiseAttendanceModel getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemHeaderBinding headerBinding;
        ListItemInquiryDataBinding rowBinding;
//        LayoutInflater infalInflater = (LayoutInflater) this._context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (childPosition > 0 && childPosition < getChildrenCount(groupPosition)) {

            StandardWiseAttendanceModel currentchild = getChild(groupPosition, childPosition - 1);
            rowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.list_item_inquiry_data, parent, false);
            convertView = rowBinding.getRoot();

//            TextView status_txt, date_txt;
//            status_txt = (TextView) convertView.findViewById(R.id.status_txt);
//            date_txt = (TextView) convertView.findViewById(R.id.date_txt);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = null;
            try {
                d = sdf.parse(currentchild.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formatedate = output.format(d);
            rowBinding.statusTxt.setText(currentchild.getStatus());
            rowBinding.dateTxt.setText(formatedate);

        } else if (childPosition == 0) {
            headerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.list_item_header, parent, false);
            convertView = headerBinding.getRoot();
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size() + 1;
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ListGroupStudentInquiryDataDetailBinding groupBinding;
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {

        }
        groupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_student_inquiry_data_detail, parent, false);
        convertView = groupBinding.getRoot();
        String str = String.valueOf(groupPosition + 1);

        groupBinding.indexTxt.setText(str);
        groupBinding.studentnameTxt.setText(headerTitle1);
        groupBinding.StandardTxt.setText(headerTitle2);
        groupBinding.genderTxt.setText(headerTitle3);
        groupBinding.statusTxt.setText(headerTitle4);

        if (isExpanded) {
            groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupBinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
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

