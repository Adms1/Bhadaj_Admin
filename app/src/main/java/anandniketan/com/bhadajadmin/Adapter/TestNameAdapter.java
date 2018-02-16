package anandniketan.com.bhadajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Student.FinalArrayStudentNameModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 1/30/2018.
 */

public class TestNameAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinalArrayStudentNameModel> testnameList;
    private ArrayList<FinalArrayStandard> arrayList = new ArrayList<>();

    // Constructor
    public TestNameAdapter(Context c, List<FinalArrayStudentNameModel> testnameList) {
        mContext = c;
        this.testnameList = testnameList;
    }

    private class ViewHolder {
        CheckBox check_standard;
    }

    @Override
    public int getCount() {
        return testnameList.size();
    }

    @Override
    public Object getItem(int position) {
        return testnameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_test_name_checkbox, null);
            viewHolder.check_standard = (CheckBox) convertView.findViewById(R.id.check_standard);
            final FinalArrayStudentNameModel standarObj = testnameList.get(position);
            try {
                viewHolder.check_standard.setText(standarObj.getTestName());
                viewHolder.check_standard.setTag(standarObj.getTestID());
                if (standarObj.getCheckedStatus().equalsIgnoreCase("1")) {
                    viewHolder.check_standard.setChecked(true);
                } else {
                    viewHolder.check_standard.setChecked(false);
                }

                viewHolder.check_standard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            standarObj.setCheckedStatus("1");
                            testnameList.get(position).setCheckedStatus("1");
                        } else {
                            standarObj.setCheckedStatus("0");
                            testnameList.get(position).setCheckedStatus("0");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public List<FinalArrayStudentNameModel> getDatas() {
        return testnameList;
    }

}
