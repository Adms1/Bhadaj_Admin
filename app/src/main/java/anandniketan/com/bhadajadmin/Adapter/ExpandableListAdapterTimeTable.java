package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Staff.Datum;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.ListGroupTimetableBinding;


/**
 * Created by admsandroid on 11/27/2017.
 */

public class ExpandableListAdapterTimeTable extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<Datum>> _listDataChild;


    public ExpandableListAdapterTimeTable(Context context, List<String> listDataHeader,
                                          HashMap<String, ArrayList<Datum>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
//        ListItemTimeTableBinding itembinding;


        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (childPosition > 0) {
            Datum detail = getChild(groupPosition, childPosition-1);
            convertView = infalInflater.inflate(R.layout.list_item_time_table, null);
            TextView txtLecture, txtSubject, txtclass;

            txtLecture = convertView.findViewById(R.id.txtLecture);
            txtSubject = convertView.findViewById(R.id.txtSubject);
            txtclass = convertView.findViewById(R.id.txtClass);

            txtLecture.setText(String.valueOf(detail.getLecture()));

            if (!detail.getSubject().equalsIgnoreCase("")) {
                txtSubject.setText(detail.getSubject());
            } else {
                txtSubject.setText("-");
            }
            if (!detail.getTeacherName().equalsIgnoreCase("")) {
                txtclass.setText(detail.getTeacherName());
            } else {
                txtclass.setText("-");
            }

        } else {
            convertView = infalInflater.inflate(R.layout.time_table_header, null);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size()+1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Datum getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
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
        ListGroupTimetableBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_timetable, parent, false);
        convertView = groupbinding.getRoot();


        groupbinding.lblListHeader.setText(headerTitle);

        if (isExpanded) {
            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupbinding.lblListHeader.setTextColor(_context.getResources().getColor(R.color.orange));
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





