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

public class AttendenceReportSubMenuAdapter extends BaseAdapter {
    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/"+"In%20Out%20Summary.png",
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/"+"Employee%20In%20Out%20Details.png",
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/"+"Employee%20Present%20Details.png",
            //AppConfiguration.BASEURL_IMAGES + "Attendence Report/" + "",

    };
    public String[] mThumbNames = {"In Out Summary","Employee In Out Details","Employee Present Details"};

    private Context mContext;

    // Constructor
    public AttendenceReportSubMenuAdapter(Context c) {
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


