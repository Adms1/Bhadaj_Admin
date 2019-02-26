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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    private BarChart chart;
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
        chart = view.findViewById(R.id.chart_topperchart);
        noRecords = view.findViewById(R.id.chart_tv_no_records);

        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDescription(null);
        chart.setNoDataText("No data to display");
        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);
        chart.setExtraOffsets(15, 10, 0, 10);

        setListener();
        fillSchoolResultTermSpinner();
    }

    private void setListener() {

        Legend l = chart.getLegend();
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
                        chart.setVisibility(View.GONE);
//                        llHeader.setVisibility(View.GONE);
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {

                        chart.setVisibility(View.VISIBLE);
                        noRecords.setVisibility(View.GONE);

                        if (response.body().getFinalarray() != null) {

//                            float barWidth;
//                            float barSpace;
//                            float groupSpace;
//
//                            barWidth = 5000f;
//                            barSpace = 0f;
//                            groupSpace = 4000f;


//                            float groupSpace = 0.08f;
//                            float barSpace = 0.03f; // x4 DataSet
//                            float barWidth = 0.2f; /

                            float groupSpace = 0.2f;
                            float barSpace = 0f; // x4 DataSet
                            float barWidth = 0.5f; // x4 DataSet

                            int groupCount = response.body().getFinalarray().get(1).getData().size();

                            ArrayList xVals = new ArrayList();

                            for (int i = 0; i < response.body().getFinalarray().get(1).getData().size(); i++) {
                                xVals.add(response.body().getFinalarray().get(1).getData().get(i).getStandard() + "-" + response.body().getFinalarray().get(1).getData().get(i).getClassName());
                            }

                            ArrayList<MISClassWiseResultModel.FinalArray> values1 = new ArrayList<>();
                            values1.addAll(response.body().getFinalarray().get(1).getData());

                            ArrayList<MISClassWiseResultModel.FinalArray> values2 = new ArrayList<>();
                            values2.addAll(response.body().getFinalarray().get(2).getData());

                            ArrayList<BarEntry> yVals1 = new ArrayList<>();
                            ArrayList<BarEntry> yVals2 = new ArrayList<>();

                            // fill the lists
                            for (int i = 0; i < values1.size(); i++) {
                                yVals1.add(new BarEntry((i + 1), Float.valueOf(values1.get(i).getMarkGained().toString())));
                                yVals2.add(new BarEntry((i + 1), Float.valueOf(values2.get(i).getMarkGained().toString())));


                            }

                            BarDataSet set1, set2;

                            set1 = new BarDataSet(yVals1, response.body().getFinalarray().get(1).getTerm());
                            set1.setColor(Color.rgb(192, 80, 77));
                            set2 = new BarDataSet(yVals2, response.body().getFinalarray().get(2).getTerm());
                            set2.setColor(Color.rgb(79, 129, 189));

                            BarData data = new BarData(set1, set2);
                            data.setValueFormatter(new LargeValueFormatter());


                            chart.setData(data);
                            chart.animateY(2500);

                            chart.getBarData().setBarWidth(barWidth);
                            chart.getXAxis().setAxisMinimum(0);
                            chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);

                            //chart.getXAxis().setAxisMaximum(chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                            chart.groupBars(0, groupSpace, barSpace);
                            chart.getData().setHighlightEnabled(false);


                            //X-axis
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setGranularity(1f);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setCenterAxisLabels(true);
                            xAxis.setDrawGridLines(false);
                            xAxis.setAxisMaximum(response.body().getFinalarray().get(1).getData().size());
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

                            xAxis.setLabelCount(25, false);

//Y-axis
                            chart.getAxisLeft().setEnabled(false);
                            YAxis leftAxis = chart.getAxisRight();
                            leftAxis.setValueFormatter(new LargeValueFormatter());
                            leftAxis.setDrawGridLines(true);
                            leftAxis.setSpaceTop(35f);
                            leftAxis.setAxisMinimum(0f);
                            leftAxis.setGranularity(200f);
                            leftAxis.setAxisMaximum(900);


                        }
                    }
                } else {
                    Utils.dismissDialog();
                    chart.setVisibility(View.GONE);
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

//    private void callTopperListChartApi() {
//
//        if (!Utils.checkNetwork(getActivity())) {
//            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
//            return;
//        }
//
//        Utils.showDialog(getActivity());
//
//        WebServices apiService = ApiClient.getClient().create(WebServices.class);
//
//        Call<TopperChartModel> call = apiService.getTopperChart(FinalSchoolResultTermID);
//        call.enqueue(new Callback<TopperChartModel>() {
//
//            @Override
//            public void onResponse(Call<TopperChartModel> call, retrofit2.Response<TopperChartModel> response) {
//                Utils.dismissDialog();
//                if (response.body() != null) {
//
//                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
//                        noRecords.setVisibility(View.VISIBLE);
//                        chart.setVisibility(View.GONE);
////                        llHeader.setVisibility(View.GONE);
//                        return;
//                    }
//
//                    if (response.body().getSuccess().equalsIgnoreCase("True")) {
//
//                        chart.setVisibility(View.VISIBLE);
//                        noRecords.setVisibility(View.GONE);
//
//                        if (response.body().getFinalarray() != null) {
//
//
//                            ArrayList xVals = new ArrayList();
//
//                            for (int i = 0; i < response.body().getFinalarray().get(1).getData().size(); i++) {
//                                xVals.add(response.body().getFinalarray().get(1).getData().get(i).getStandard());
//                            }
//
//                            XAxis xAxis = chart.getXAxis();
////                            xAxis.setTypeface(tfLight);
//                            xAxis.setGranularity(1f);
//                            xAxis.setCenterAxisLabels(true);
//                            xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//                            YAxis leftAxis = chart.getAxisLeft();
////                            leftAxis.setTypeface(tfLight);
//                            leftAxis.setValueFormatter(new LargeValueFormatter());
//                            leftAxis.setDrawGridLines(false);
//                            leftAxis.setSpaceTop(35f);
//                            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//                            chart.getAxisRight().setEnabled(false);
//
//                            float groupSpace = 0.08f;
//                            float barSpace = 0.03f; // x4 DataSet
//                            float barWidth = 0.2f; // x4 DataSet
//                            // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
//
//                            int groupCount = 50 + 1;
//                            int startYear = 1980;
//                            int endYear = startYear + groupCount;
//
//                            ArrayList<BarEntry> values1 = new ArrayList<>();
//                            ArrayList<BarEntry> values2 = new ArrayList<>();
//                            ArrayList<BarEntry> values3 = new ArrayList<>();
//                            ArrayList<BarEntry> values4 = new ArrayList<>();
//
//                            float randomMultiplier = 50 * 100000f;
//
//                            for (int i = startYear; i < endYear; i++) {
//                                values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                                values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                                values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                                values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                            }
//
//                            BarDataSet set1, set2, set3, set4;
//
//                            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
//
//                                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
//                                set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
//                                set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
//                                set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
//                                set1.setValues(values1);
//                                set2.setValues(values2);
//                                set3.setValues(values3);
//                                set4.setValues(values4);
//                                chart.getData().notifyDataChanged();
//                                chart.notifyDataSetChanged();
//
//                            } else {
//                                // create 4 DataSets
//                                set1 = new BarDataSet(values1, "Company A");
//                                set1.setColor(Color.rgb(104, 241, 175));
//                                set2 = new BarDataSet(values2, "Company B");
//                                set2.setColor(Color.rgb(164, 228, 251));
//                                set3 = new BarDataSet(values3, "Company C");
//                                set3.setColor(Color.rgb(242, 247, 158));
//                                set4 = new BarDataSet(values4, "Company D");
//                                set4.setColor(Color.rgb(255, 102, 0));
//
//                                BarData data = new BarData(set1, set2, set3, set4);
//                                data.setValueFormatter(new LargeValueFormatter());
////                                data.setValueTypeface(tfLight);
//
//                                chart.setData(data);
//                            }
//
//                            // specify the width each bar should have
//                            chart.getBarData().setBarWidth(barWidth);
//
//                            // restrict the x-axis range
//                            chart.getXAxis().setAxisMinimum(200);
//
//                            // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//                            chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//                            chart.groupBars(startYear, groupSpace, barSpace);
//                            chart.invalidate();
//
//                        }
//                    }
//                } else {
//                    Utils.dismissDialog();
//                    chart.setVisibility(View.GONE);
////                    llHeader.setVisibility(View.GONE);
//                    noRecords.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<TopperChartModel> call, @NonNull Throwable t) {
//                // Log error here since request failed
//                Utils.dismissDialog();
//                Log.e("gallery", t.toString());
//            }
//        });
//    }


    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }

}
