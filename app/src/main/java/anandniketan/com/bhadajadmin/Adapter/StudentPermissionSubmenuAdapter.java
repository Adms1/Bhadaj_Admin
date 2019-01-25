package anandniketan.com.bhadajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;


/**
 * Created by admsandroid on 1/24/2018.
 */

public class StudentPermissionSubmenuAdapter extends BaseAdapter {
    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Report%20Card.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Online%20Payment.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Marks_Syllabus.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Suggestion.png",
    };
    public String[] mThumbNames = {"ReportCard", "Online Payment", "Mark/Syllabus","Suggestion"};
    private Context mContext;
    private String reportstatus, onlinepaystatus, markstatus, suggestionstatus;

    // Constructor
    public StudentPermissionSubmenuAdapter(Context c, String reportstatus, String onlinepaystatus, String markstatus, String suggestionstatus) {
        mContext = c;
        this.reportstatus = reportstatus;
        this.onlinepaystatus = onlinepaystatus;
        this.markstatus = markstatus;
        this.suggestionstatus = suggestionstatus;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.sub_menu_grid_cell, null);

        imgGridOptions = convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = convertView.findViewById(R.id.txtGridOptionsName);

        String url = mThumbIds[position];
//        Log.d("url", url);

        Glide.with(mContext)
                .load(url)
                .fitCenter()
                .into(imgGridOptions);

//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}

