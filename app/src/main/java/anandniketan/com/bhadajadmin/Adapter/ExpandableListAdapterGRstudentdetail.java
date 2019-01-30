package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceFinalArray;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.databinding.GrStudentListDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudentListItemCommunicationDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudentListItemFatherDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudentListItemMotherDetailBinding;
import anandniketan.com.bhadajadmin.databinding.StudetnListItemTransportDetailBinding;


/**
 * Created by admsandroid on 1/29/2018.
 */

public class ExpandableListAdapterGRstudentdetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<StudentAttendanceFinalArray>> _listDataChild;
    ImageLoader imageLoader;


    public ExpandableListAdapterGRstudentdetail(Context context, List<String> listDataHeader, HashMap<String,
            ArrayList<StudentAttendanceFinalArray>> listDataChild) {
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
        final ArrayList<StudentAttendanceFinalArray> childData = getChild(groupPosition, 0);
        GrStudentListDetailBinding binding;
        if (convertView == null) {
            Log.d("groupposition", "" + groupPosition);
        }
        String headerTitle = (String) getGroup(groupPosition);
        if (headerTitle.equalsIgnoreCase("Student Details")) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.gr_student_list_detail, parent, false);
            convertView = binding.getRoot();


            binding.tagTxt.setText(childData.get(childPosition).getTag());
            binding.firstnameTxt.setText(childData.get(childPosition).getFirstName());
            binding.lastnameTxt.setText(childData.get(childPosition).getLastName());
            binding.dobTxt.setText(childData.get(childPosition).getDateOfBirth());
            binding.birthplaceTxt.setText(childData.get(childPosition).getBirthPlace());
            binding.ageTxt.setText(childData.get(childPosition).getAge());
            binding.genderTxt.setText(childData.get(childPosition).getGender());
            binding.studentTypeTxt.setText(AppConfiguration.TermName);
            binding.gradeTxt.setText(childData.get(childPosition).getStandard());
            binding.sectionTxt.setText(childData.get(childPosition).get_class());
            binding.lastschoolTxt.setText(childData.get(childPosition).getLastSchool());
            binding.bloodgroupTxt.setText(childData.get(childPosition).getBloodGroup());
            binding.religionTxt.setText(childData.get(childPosition).getReligion());
            binding.grnoTxt.setText(childData.get(childPosition).getGRNO());
            binding.nationalityTxt.setText(childData.get(childPosition).getNationality());
            binding.admissionTakenTxt.setText(childData.get(childPosition).getAdmissionTaken());

            if (childData.get(childPosition).getStatus().equalsIgnoreCase("1")) {
                binding.statusTxt.setTextColor(Color.parseColor("#FF6BAE18"));
                binding.statusTxt.setText("Active");

            } else {
                binding.statusTxt.setTextColor(Color.parseColor("#ff0000"));
                binding.statusTxt.setText("InActive");
            }
        } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
            StudetnListItemTransportDetailBinding transportDetailBinding;
            transportDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.studetn_list_item_transport_detail, parent, false);
            convertView = transportDetailBinding.getRoot();

            transportDetailBinding.kmsTxt.setText(childData.get(childPosition).getRouteName());
            transportDetailBinding.pickupTxt.setText(childData.get(childPosition).getPickupBus());
            transportDetailBinding.pickuppointTxt.setText(childData.get(childPosition).getPickupPoint());
            transportDetailBinding.pickuptimeTxt.setText(childData.get(childPosition).getPickupPointTime());
            transportDetailBinding.dropbusTxt.setText(childData.get(childPosition).getDropBus());
            transportDetailBinding.droppointTxt.setText(childData.get(childPosition).getDropPoint());
            transportDetailBinding.droptimeTxt.setText(childData.get(childPosition).getDropPointTime());

        } else if (headerTitle.equalsIgnoreCase("Father Details")) {
            StudentListItemFatherDetailBinding fatherDetailBinding;
            fatherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_father_detail, parent, false);
            convertView = fatherDetailBinding.getRoot();
            fatherDetailBinding.fatherfirstnameTxt.setText(childData.get(childPosition).getFatherFirstName());
            fatherDetailBinding.fatherlastnameTxt.setText(childData.get(childPosition).getFatherLastName());
            fatherDetailBinding.fphoneTxt.setText(childData.get(childPosition).getFatherPhoneNo());
            fatherDetailBinding.fmobileTxt.setText(childData.get(childPosition).getFatherMobileNo());
            fatherDetailBinding.fEmailTxt.setText(childData.get(childPosition).getFatherEmailAddress());
            fatherDetailBinding.fQualificationTxt.setText(childData.get(childPosition).getFatherQualification());
            fatherDetailBinding.foccupationTxt.setText(childData.get(childPosition).getFatherOccupation());
            fatherDetailBinding.forganisationTxt.setText(childData.get(childPosition).getFatherOrganization());
            fatherDetailBinding.fdesignationTxt.setText(childData.get(childPosition).getFatherDesignation());
            fatherDetailBinding.officeTxt.setText(childData.get(childPosition).getFatherofficeaddress());

        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
            StudentListItemMotherDetailBinding motherDetailBinding;
            motherDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_mother_detail, parent, false);
            convertView = motherDetailBinding.getRoot();

            motherDetailBinding.motherfirstnameTxt.setText(childData.get(childPosition).getMotherFirstName());
            motherDetailBinding.motherlastnameTxt.setText(childData.get(childPosition).getMotherLastName());
            motherDetailBinding.mphoneTxt.setText(childData.get(childPosition).getMotherPhoneNo());
            motherDetailBinding.mmobileTxt.setText(childData.get(childPosition).getMotherMobileNo());
            motherDetailBinding.memailTxt.setText(childData.get(childPosition).getMotherEmailAddress());
            motherDetailBinding.mqualificationTxt.setText(childData.get(childPosition).getMotherQualification());
            motherDetailBinding.moccupationTxt.setText(childData.get(childPosition).getMotherOccupation());
            motherDetailBinding.morganisationTxt.setText(childData.get(childPosition).getMotherOrganization());
            motherDetailBinding.mdesignationTxt.setText(childData.get(childPosition).getMotherDesignation());
            motherDetailBinding.officeTxt.setText(childData.get(childPosition).getMotherofficeaddress());

        } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
            StudentListItemCommunicationDetailBinding communicationDetailBinding;
            communicationDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_list_item_communication_detail, parent, false);
            convertView = communicationDetailBinding.getRoot();

            communicationDetailBinding.smsTxt.setText(childData.get(childPosition).getSMSCommunicationNo());
            communicationDetailBinding.cityTxt.setText(childData.get(childPosition).getCity());
            communicationDetailBinding.addressTxt.setText(childData.get(childPosition).getAddress());
            communicationDetailBinding.zipTxt.setText(childData.get(childPosition).getZipCode());
            communicationDetailBinding.smsEmailTxt.setText(childData.get(childPosition).getsMSCommunicationemail());
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<StudentAttendanceFinalArray> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_student_full_detail, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        LinearLayout linear_group = convertView.findViewById(R.id.linear_group);
        if (headerTitle.equalsIgnoreCase("Student Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#3597D3"));
        } else if (headerTitle.equalsIgnoreCase("Transport Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF6BAE18"));
        } else if (headerTitle.equalsIgnoreCase("Father Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FFE6B12E"));
        } else if (headerTitle.equalsIgnoreCase("Mother Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF48ADDE"));
        } else if (headerTitle.equalsIgnoreCase("Communication Details")) {
            linear_group.setBackgroundColor(Color.parseColor("#FF607D8B"));
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

