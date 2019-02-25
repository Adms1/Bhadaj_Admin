package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajadmin.Model.MIS.MISClassWiseResultModel;
import anandniketan.com.bhadajadmin.Model.MIS.TopperChartModel;
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
public class ChartFragment extends Fragment {

    private Spinner spTermDetail;
    private BarChart lineChart;
    private HashMap<Integer, String> spinnerSchoolResultMap;
    private String FinalSchoolResultTermID = "1";
    private TextView noRecords;
    private BarDataSet set1, set2;

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spTermDetail = view.findViewById(R.id.chart_spTermdetail);
        lineChart = view.findViewById(R.id.chart_topperchart);
        noRecords = view.findViewById(R.id.chart_tv_no_records);

        lineChart.getDescription().setEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDescription(null);
        lineChart.setNoDataText("No data to display");
        lineChart.setDrawBarShadow(false);

        lineChart.setDrawGridBackground(false);

        setListener();
        fillSchoolResultTermSpinner();
    }

    private void setListener() {

        Legend l = lineChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
//        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        spTermDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spTermDetail.getSelectedItem().toString();
                String getid = spinnerSchoolResultMap.get(spTermDetail.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalSchoolResultTermID = getid;
                AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;

                Log.d("FinalTermIdStr", FinalSchoolResultTermID);

                try {
//                    if (isAdded()) {
                    callTopperListChartApi();
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fillSchoolResultTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<Integer>();

        TermId.add(1);
        TermId.add(2);

//        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
//            TermId.add(finalArrayGetTermModels.get(i).getTermId());
//        }
        ArrayList<String> Term = new ArrayList<String>();
//        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
//            Term.add(finalArrayGetTermModels.get(j).getTerm());
//        }

        Term.add("Term 1");
        Term.add("Term 2");

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerSchoolResultMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerSchoolResultMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }


//        try {
//            Field popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            // Get private mPopup member variable and try cast to ListPopupWindow
//            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentMisBinding.resultOfSchoolTermSpinner);
//
//            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
//        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
//            // silently fail...
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, spinnertermIdArray) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                //((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER_HORIZONTAL);

                return v;

            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.LEFT);

                return v;

            }

        };


        spTermDetail.setAdapter(adapter);
        FinalSchoolResultTermID = spinnerSchoolResultMap.get(0);
        AppConfiguration.schoolResultTermID = FinalSchoolResultTermID;
        spTermDetail.setSelection(0, false);

    }

    private void callTopperListChartApi() {

        if (!Utils.checkNetwork(getActivity())) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<TopperChartModel> call = apiService.getTopperChart(FinalSchoolResultTermID);
        call.enqueue(new Callback<TopperChartModel>() {

            @Override
            public void onResponse(Call<TopperChartModel> call, retrofit2.Response<TopperChartModel> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        noRecords.setVisibility(View.VISIBLE);
                        lineChart.setVisibility(View.GONE);
//                        llHeader.setVisibility(View.GONE);
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {

                        lineChart.setVisibility(View.VISIBLE);
                        noRecords.setVisibility(View.GONE);
//                        llHeader.setVisibility(View.VISIBLE);


                        final ArrayList<String> yaxisheader = new ArrayList<>();
                        for (int i = 0; i < response.body().getFinalarray().get(1).getData().size(); i++) {
                            yaxisheader.add(response.body().getFinalarray().get(1).getData().get(i).getStandard() + "-" + response.body().getFinalarray().get(1).getData().get(i).getClassName());
                        }

                        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setTypeface(tfLight);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(200f);
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(900);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setGranularityEnabled(true);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return String.valueOf((int) value);
//            }
//        });

                        YAxis leftAxis = lineChart.getAxisLeft();
                        leftAxis.setGranularity(1f);
                        leftAxis.setGranularityEnabled(true);
//        leftAxis.setTypeface(tfLight);
//                        for (int i = 0; i <yaxisheader.size() ; i++) {
//                            final int finalI = i;
                        leftAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {

//                                    String header = "";
//
//                                    header = yaxisheader.get(finalI);
                                return yaxisheader.get(Integer.parseInt(String.valueOf(value - 1)));
                            }
                        });
//                        }
                        leftAxis.setDrawGridLines(false);
                        leftAxis.setSpaceTop(35f);
//                        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

                        lineChart.getAxisRight().setEnabled(false);

                        if (response.body().getFinalarray() != null) {

                            float groupSpace = 0.08f;
                            float barSpace = 0f; // x4 DataSet
                            float barWidth = 0.2f; // x4 DataSet

//                            int groupCount = seekBarX.getProgress() + 1;
//                            int startYear = 1980;
//                            int endYear = startYear + groupCount;

                            ArrayList<MISClassWiseResultModel.FinalArray> values1 = response.body().getFinalarray().get(1).getData();
                            ArrayList<MISClassWiseResultModel.FinalArray> values2 = response.body().getFinalarray().get(2).getData();

                            ArrayList<BarEntry> entriesGroup1 = new ArrayList<>();
                            ArrayList<BarEntry> entriesGroup2 = new ArrayList<>();

                            // fill the lists
                            for (int i = 0; i < 18; i++) {
                                entriesGroup1.add(new BarEntry(i, Float.valueOf(values1.get(i).getMarkGained().toString())));
                                entriesGroup2.add(new BarEntry(i, Float.valueOf(values2.get(i).getMarkGained().toString())));

                            }

//                            for (int j = 0; j < values2.size(); j++) {
//                            }

//                                ArrayList<String> xAxis = new ArrayList<>();
//                                xAxis.add("JAN");
//                                xAxis.add("FEB");
//                                xAxis.add("MAR");
//                                xAxis.add("APR");
//                                xAxis.add("MAY");
//                                xAxis.add("JUN");


                            BarDataSet set1, set2;

                            set1 = new BarDataSet(entriesGroup1, response.body().getFinalarray().get(1).getTerm());
                            set1.setColor(Color.rgb(192, 80, 77));
                            set2 = new BarDataSet(entriesGroup2, response.body().getFinalarray().get(2).getTerm());
                            set2.setColor(Color.rgb(79, 129, 189));

                            ArrayList<String> arrayList = new ArrayList<>();

                            BarData data = new BarData(set1, set2);
                            data.setValueFormatter(new LargeValueFormatter());
//                            data.setValueTypeface(tfLight);

                            lineChart.setData(data);
//                            lineChart.invalidate();

                            // specify the width each bar should have
                            lineChart.getBarData().setBarWidth(barWidth);
                            lineChart.getXAxis().setAxisMaximum(900);
                            lineChart.getXAxis().setAxisMinimum(0);
                            lineChart.getXAxis().setXOffset(200f);
                            lineChart.groupBars(0, groupSpace, barSpace);
                            lineChart.invalidate();
                        }
                    }
                } else {
                    Utils.dismissDialog();
                    lineChart.setVisibility(View.GONE);
//                    llHeader.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopperChartModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("gallery", t.toString());
            }
        });
    }

}
