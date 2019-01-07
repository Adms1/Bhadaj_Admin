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

public class DailyReportSubMenuAdapter extends BaseAdapter {
    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Transportation.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Account.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Admin.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "House%20Keeping.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Information%20Technology.png",
            AppConfiguration.BASEURL_IMAGES + "Daily%20Report/" + "Hr%20Head.png",

    };
    public String[] mThumbNames = {"Trasportation", "Account","Admin","House Keeping","Information Technology","HR Head"};
    private Context mContext;

    // Constructor
    public DailyReportSubMenuAdapter(Context c) {
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



