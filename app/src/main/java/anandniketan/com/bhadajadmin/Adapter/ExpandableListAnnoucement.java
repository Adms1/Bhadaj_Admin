package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Fragment.Fragment.AnnouncementFragment;
import anandniketan.com.bhadajadmin.Interface.OnUpdateRecord;
import anandniketan.com.bhadajadmin.Interface.onDeleteWithId;
import anandniketan.com.bhadajadmin.Model.Student.AnnouncementModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.DialogUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;

public class ExpandableListAnnoucement extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<AnnouncementModel.FinalArray>> listChildData;
    private String standard = "",standardIDS = "";
    private int annousID;
    private onDeleteWithId onDeleteWithIdref;
    private OnUpdateRecord onUpdateRecordRef;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public ExpandableListAnnoucement(Context context, List<String> listDataHeader, HashMap<String, ArrayList<AnnouncementModel.FinalArray>> listDataChild,onDeleteWithId onDeleteWithIdref,OnUpdateRecord onUpdateRecordRef) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.onDeleteWithIdref = onDeleteWithIdref;
        this.onUpdateRecordRef = onUpdateRecordRef;
    }

    @Override
    public List<AnnouncementModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<AnnouncementModel.FinalArray> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_annoucement, null);
        }


        TextView txtGradelabel,txtGrade,txtPdfLink,txtAnnsLabel;
        LinearLayout Ll_AnnsView,LL_PDFView;
        txtGrade = (TextView) convertView.findViewById(R.id.txt_grade);
        txtPdfLink = (TextView)convertView.findViewById(R.id.txt_pdf_link);
        txtAnnsLabel = (TextView)convertView.findViewById(R.id.txt_annoucement);
        LL_PDFView = (LinearLayout)convertView.findViewById(R.id.pdf_view);
        Ll_AnnsView = (LinearLayout)convertView.findViewById(R.id.announcement_view);
        Button btnDelete  = (Button)convertView.findViewById(R.id.btn_delete);
        Button btnEdit = (Button)convertView.findViewById(R.id.btn_edit);


        if(!TextUtils.isEmpty(childData.get(childPosition).getAnnoucementPDF()) || !childData.get(childPosition).getAnnoucementPDF().equalsIgnoreCase("")){
            LL_PDFView.setVisibility(View.VISIBLE);
            Ll_AnnsView.setVisibility(View.GONE);
        }else{
            LL_PDFView.setVisibility(View.GONE);
            Ll_AnnsView.setVisibility(View.VISIBLE);

        }
        standardIDS = childData.get(childPosition).getStandardID();
        standard = childData.get(childPosition).getStandard();
        annousID = childData.get(childPosition).getPKAnnouncmentID();


        txtGrade.setText(childData.get(childPosition).getStandard());
        txtAnnsLabel.setText(childData.get(childPosition).getAnnDesc());

        txtPdfLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(childData.get(childPosition).getAnnoucementPDF())){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setDataAndType(Uri.parse(AppConfiguration.LIVE_BASE_URL+childData.get(childPosition).getAnnoucementPDF()), "application/pdf");
                    Intent chooser = Intent.createChooser(browserIntent,"Open Pdf");
                    _context.startActivity(chooser);
                }else{
                    Utils.ping(_context,"No PDF File Found");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.createConfirmDialog(_context,R.string.app_name,R.string.delete_confirm_msg,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onDeleteWithIdref.deleteRecordWithId(String.valueOf(childData.get(childPosition).getPKAnnouncmentID()));
                    }

                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
               }
            });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
                onUpdateRecordRef.onUpdateRecord(childData);
                Bundle bundleData = new Bundle();
                bundleData.putParcelableArrayList("data",(ArrayList<? extends Parcelable>) childData);
                fragment = new AnnouncementFragment();
                fragmentManager = ((FragmentActivity)_context).getSupportFragmentManager();
                fragment.setArguments(bundleData);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();


            }
        });

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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_header_annoucement, null);
        }
        TextView txt_subject,txt_date;
        ImageView iv_status,iv_indicator;

        txt_subject = (TextView) convertView.findViewById(R.id.subject_txt);
        txt_date = (TextView) convertView.findViewById(R.id.createdate_txt);
        iv_status = (ImageView)convertView.findViewById(R.id.iv_status);
        iv_indicator = (ImageView)convertView.findViewById(R.id.iv_indicator);

        txt_subject.setText(headerTitle1);
        txt_date.setText(headerTitle2);


        if (headerTitle3.equalsIgnoreCase("No")) {
            iv_status.setColorFilter(ContextCompat.getColor(_context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);

        } else {
            iv_status.setColorFilter(ContextCompat.getColor(_context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);

        }

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
