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

public class StaffSubMenuAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Class%20Teacher.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Assign%20Subject.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Time%20Table.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Add%20Exam_Syllabus.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "Home%20Work%20Not%20Done.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "My%20Leave.png",
            AppConfiguration.BASEURL_IMAGES + "Staff/" + "View%20Marks.png",
    };

    public String[] mThumbNames = {"Class Teacher", "Assign Subject","Time Table",
            "Exam","Home Work Not Done","My Leave","View Marks" };

    // Constructor
    public StaffSubMenuAdapter(Context c) {
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


