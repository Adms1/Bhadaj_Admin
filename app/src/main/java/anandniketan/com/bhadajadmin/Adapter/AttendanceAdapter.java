package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceDetail;
import anandniketan.com.bhadajadmin.Model.Student.StudentNameModel;
import anandniketan.com.bhadajadmin.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admsandroid on 1/31/2018.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private Context context;
    private StudentNameModel attendanceModel;
    ImageLoader imageLoader;

    public AttendanceAdapter(Context mContext, StudentNameModel attendanceModel) {
        this.context = mContext;
        this.attendanceModel = attendanceModel;
    }


    @Override
    public AttendanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, parent, false);
        return new AttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendanceAdapter.MyViewHolder holder, int position) {
        imageLoader = ImageLoader.getInstance();

        final StudentAttendanceDetail detail = attendanceModel.getFinalArray().get(0).getStudentDetail().get(position);
        try {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    context)
                    .threadPriority(Thread.MAX_PRIORITY)
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .denyCacheImageMultipleSizesInMemory()
                    .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                    .build();
            imageLoader.init(config.createDefault(context));
            imageLoader.displayImage(detail.getStudentImage(), holder.profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.student_name_txt.setText(detail.getStudentName());
        holder.attendance_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.present_chk:
                            detail.setAttendenceStatus("1");
                            break;

                        case R.id.absent_chk:
                            detail.setAttendenceStatus("0");
                            break;

                        case R.id.leave_chk:
                            detail.setAttendenceStatus("-1");
                            break;

                        case R.id.onduty_chk:
                            detail.setAttendenceStatus("3");
                    }

                }
            }
        });
        switch (Integer.parseInt(detail.getAttendenceStatus())) {
            case 0:
                holder.absent_chk.setChecked(true);
                break;
            case 1:
                holder.present_chk.setChecked(true);
                break;
            case -1:
                holder.leave_chk.setChecked(true);
                break;
            case -2:
                holder.present_chk.setChecked(true);
                break;
            case 3:
                holder.onduty_chk.setChecked(true);
                holder.onduty_chk.setClickable(false);
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return attendanceModel.getFinalArray().get(0).getStudentDetail().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_name_txt;
        CircleImageView profile_image;
        RadioGroup attendance_group;
        RadioButton present_chk, absent_chk, leave_chk, onduty_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            student_name_txt = (TextView) itemView.findViewById(R.id.student_name_txt);
            attendance_group = (RadioGroup) itemView.findViewById(R.id.attendance_group);
            present_chk = (RadioButton) itemView.findViewById(R.id.present_chk);
            absent_chk = (RadioButton) itemView.findViewById(R.id.absent_chk);
            leave_chk = (RadioButton) itemView.findViewById(R.id.leave_chk);
            onduty_chk = (RadioButton) itemView.findViewById(R.id.onduty_chk);

        }
    }

}

