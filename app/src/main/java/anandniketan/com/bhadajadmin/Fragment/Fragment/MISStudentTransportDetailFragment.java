package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */

//created by Antra 11/01/2019

public class MISStudentTransportDetailFragment extends Fragment {

    private Button btnMenu, btnBack;
    private TextView tvHeader;
    private String title;

    public MISStudentTransportDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.transport_detail_textView3);
        btnBack = view.findViewById(R.id.transport_detail_btnBack);
        btnMenu = view.findViewById(R.id.transport_detail_btnmenu);

        Bundle bundle = this.getArguments();
        title = bundle.getString("title");

        tvHeader.setText(title);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
//                fragment = new MISFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
            }
        });

    }
}
