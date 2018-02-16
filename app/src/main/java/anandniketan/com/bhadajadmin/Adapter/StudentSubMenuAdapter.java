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
 * Created by admsandroid on 11/17/2017.
 */

public class StudentSubMenuAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Search%20Student.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "View%20Inquiry.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Student%20Transport.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Permission.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Attendence.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Left_Detail.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "GR%20Register.png",
            AppConfiguration.BASEURL_IMAGES + "Student/" + "Student%20Marks.png",
    };

    public String[] mThumbNames = {"Search Student", "View Inquiry", "Student Transport",
            "Permission", "Attendance", "Left/Detail", "GR Register", "Student Marks"};

    // Constructor
    public StudentSubMenuAdapter(Context c) {
        mContext = c;
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

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = (TextView) convertView.findViewById(R.id.txtGridOptionsName);
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

