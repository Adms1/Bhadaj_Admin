package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.TransportAdapter;
import anandniketan.com.bhadajadmin.Model.MIS.TransportMainModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

//created by Antra 11/01/2019

public class MISStudentTransportFragment extends Fragment {

    private RecyclerView rvList;
    private TransportAdapter transportAdapter;
    private ArrayList<TransportMainModel.StandardDatum> transportMainArray;
    private ProgressBar progressBar;
    private TextView tvNorecord, tvHeader;
    private RelativeLayout llHeader;
    private String termid = "";
    private Button btnBack, btnMenu;

    public MISStudentTransportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_misstudent_transport, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        termid = bundle.getString("TermID");

        rvList = view.findViewById(R.id.transport_rv_misdata_list1);
        progressBar = view.findViewById(R.id.transport_loader);
        tvNorecord = view.findViewById(R.id.transport_txtNoRecords);
        llHeader = view.findViewById(R.id.transport_header);
        btnBack = view.findViewById(R.id.transport_btnBack);
        btnMenu = view.findViewById(R.id.transport_btnmenu);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

        callTransportDetail();
    }

    private void callTransportDetail() {
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TransportMainModel> call = apiService.getTransportDetail(AppConfiguration.BASEURL + "GetMISTransport?RequestType=" + "&TermID=" + termid);
        call.enqueue(new Callback<TransportMainModel>() {

            @Override
            public void onResponse(Call<TransportMainModel> call, retrofit2.Response<TransportMainModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    Utils.ping(getActivity(), getString(R.string.something_wrong));
                    return;
                }

                if (response.body().getSuccess().equalsIgnoreCase("false")) {
                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    Utils.ping(getActivity(), getString(R.string.false_msg));
                    return;
                }
                if (response.body().getSuccess().equalsIgnoreCase("True")) {

                    progressBar.setVisibility(View.GONE);
                    rvList.setVisibility(View.VISIBLE);
                    llHeader.setVisibility(View.VISIBLE);

                    if (response.body().getFinalArray().size() > 0) {

                        transportMainArray = response.body().getFinalArray().get(0).getStandarddata();

                        if (transportMainArray != null) {
                            llHeader.setVisibility(View.VISIBLE);
                            tvNorecord.setVisibility(View.GONE);
                            transportAdapter = new TransportAdapter(getActivity(), transportMainArray, termid);
                            rvList.setAdapter(transportAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TransportMainModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("headfee", t.toString());
            }
        });
    }

}