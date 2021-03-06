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

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Model.IconHeaderModel;
import anandniketan.com.bhadajadmin.R;

public class DailyReportSubMenuAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<IconHeaderModel> newArr;

    // Constructor
    public DailyReportSubMenuAdapter(Context c, ArrayList<IconHeaderModel> newArr) {
        mContext = c;
        this.newArr = newArr;
    }

    @Override
    public int getCount() {
        return newArr.size();
    }

    @Override
    public Object getItem(int position) {
        return newArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        View line1 = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.sub_menu_grid_cell, null);

        imgGridOptions = convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = convertView.findViewById(R.id.txtGridOptionsName);
        line1 = convertView.findViewById(R.id.view_line1);

        String url = newArr.get(position).getUrl();
//        Log.d("url", url);
        Glide.with(mContext)
                .load(url)
                .into(imgGridOptions);

//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(newArr.get(position).getName());

        if (position % 2 == 0) {
            line1.setVisibility(View.VISIBLE);
        } else {
            line1.setVisibility(View.GONE);
        }


        return convertView;
    }

}



