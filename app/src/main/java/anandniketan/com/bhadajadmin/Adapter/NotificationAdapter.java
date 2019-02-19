package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Model.Notification.NotificationModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;

// Antra 19/02/2019

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {

    private Context context;
    private ArrayList<NotificationModel.FinalArray> finalArrays;

    public NotificationAdapter(Context context, ArrayList<NotificationModel.FinalArray> finalArrays) {
        this.context = context;
        this.finalArrays = finalArrays;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item_list, viewGroup, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {

        viewholder.title.setText(finalArrays.get(i).getName());
        viewholder.type.setText("(" + finalArrays.get(i).getType() + ")");
        viewholder.discription.setText(finalArrays.get(i).getDepartment());

//        if(image == finalArrays.get(i).getNotificationtype()) {
        if (finalArrays.get(i).getNotificationtype().equalsIgnoreCase("StudentLeave") || finalArrays.get(i).getNotificationtype()
                .equalsIgnoreCase("StaffLeave")) {
            Picasso.get().load(AppConfiguration.BASEURL_ICONS + "leave.png")
                    .resize(100, 100).into(viewholder.notitype);
        } else {
            Picasso.get().load(AppConfiguration.BASEURL_ICONS + finalArrays.get(i).getNotificationtype() + ".png")
                    .resize(100, 100).into(viewholder.notitype);
        }
//        }
//        else {
//            if (finalArrays.get(i).getNotificationtype().equalsIgnoreCase("StudentLeave") || finalArrays.get(i).getNotificationtype()
//                    .equalsIgnoreCase("StaffLeave")) {
//                Picasso.get().load(AppConfiguration.BASEURL_ICONS + "leave.png")
//                        .resize(100, 100).into(viewholder.notitype);
//            } else {
//                Picasso.get().load(AppConfiguration.BASEURL_ICONS + finalArrays.get(i).getNotificationtype() + ".png")
//                        .resize(100, 100).into(viewholder.notitype);
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return finalArrays.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView title, discription, type;
        ImageView user, notitype;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noti_name);
            discription = itemView.findViewById(R.id.noti_department);
            user = itemView.findViewById(R.id.noti_user);
            type = itemView.findViewById(R.id.noti_type);
            notitype = itemView.findViewById(R.id.noti_notitype);

        }
    }

}
