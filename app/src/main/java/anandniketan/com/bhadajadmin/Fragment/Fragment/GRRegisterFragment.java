package anandniketan.com.bhadajadmin.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.GRRegisterAdapter;
import anandniketan.com.bhadajadmin.Interface.onViewClick;
import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayGetTermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.FragmentGrregisterBinding;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GRRegisterFragment extends Fragment {

    private FragmentGrregisterBinding fragmentGrregisterBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    List<FinalArrayGetTermModel> finalArrayGetTermModels;
    HashMap<Integer, String> spinnerTermMap;
    List<FinalArrayStandard> finalArrayStandardsList;
    HashMap<Integer, String> spinnerStandardMap;
    HashMap<Integer, String> spinnerSectionMap;
    HashMap<Integer, String> spinnerStatusMap;
    String FinalStandardIdStr, FinalClassIdStr, StandardName, FinalTermIdStr, FinalStandardStr, FinalSectionStr, FinalStatusStr, FinalStatusIdStr;
    GRRegisterAdapter grRegisterAdapter;

    public GRRegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentGrregisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_grregister, container, false);

        rootView = fragmentGrregisterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setListners();
        callTermApi();
        callStandardApi();

        return rootView;
    }


    public void setListners() {
        fragmentGrregisterBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        fragmentGrregisterBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        fragmentGrregisterBinding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentGrregisterBinding.termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(fragmentGrregisterBinding.termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermIdStr = getid.toString();
                Log.d("FinalTermIdStr", FinalTermIdStr);
                AppConfiguration.TermName=name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentGrregisterBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = fragmentGrregisterBinding.gradeSpinner.getSelectedItem().toString();
                String getid = spinnerStandardMap.get(fragmentGrregisterBinding.gradeSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalStandardIdStr = getid.toString();
                Log.d("FinalStandardIdStr", FinalStandardIdStr);
                StandardName = name;
                if (name.equalsIgnoreCase("All")){
                    FinalStandardStr = "0";
                }else{
                    FinalStandardStr = name;
                }
                Log.d("StandardName", FinalStandardStr);
                fillSection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fragmentGrregisterBinding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedsectionstr = fragmentGrregisterBinding.sectionSpinner.getSelectedItem().toString();
                String getid = spinnerSectionMap.get(fragmentGrregisterBinding.sectionSpinner.getSelectedItemPosition());

                Log.d("value", selectedsectionstr + " " + getid);
                FinalClassIdStr = getid.toString();
                if (selectedsectionstr.equalsIgnoreCase("All")){
                    FinalSectionStr = "0";
                }else{
                    FinalSectionStr = selectedsectionstr;
                }

                Log.d("FinalClassIdStr", FinalSectionStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fragmentGrregisterBinding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedstatusstr = fragmentGrregisterBinding.statusSpinner.getSelectedItem().toString();
                String getid = spinnerStatusMap.get(fragmentGrregisterBinding.statusSpinner.getSelectedItemPosition());
                Log.d("value", selectedstatusstr + "" + getid);
                FinalStatusStr = selectedstatusstr;
                FinalStatusIdStr = getid;
                Log.d("FinalStatusStr", FinalStatusStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentGrregisterBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGRRegisterApi();
            }
        });
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                Utils.dismissDialog();
                if (termModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();
                        fillStatus();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL Standard API HERE
    private void callStandardApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getStandardDetail(getStandardDetail(), new retrofit.Callback<GetStandardModel>() {
            @Override
            public void success(GetStandardModel standardModel, Response response) {
                Utils.dismissDialog();
                if (standardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (standardModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayStandardsList = standardModel.getFinalArray();
                    if (finalArrayStandardsList != null) {
                        fillGradeSpinner();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getStandardDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    // CALL GRRegisterData API HERE
    private void callGRRegisterApi() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }
        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getNewRegister(getGRRegisterDetail(), new retrofit.Callback<StudentAttendanceModel>() {
            @Override
            public void success(StudentAttendanceModel studentFullDetailModel, Response response) {
//                Utils.dismissDialog();
                if (studentFullDetailModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("False")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    Utils.dismissDialog();
                    if (studentFullDetailModel.getFinalArray().size() == 0) {
                        fragmentGrregisterBinding.studentGrregisterList.setVisibility(View.GONE);
                        fragmentGrregisterBinding.recyclerLinear.setVisibility(View.GONE);
                        fragmentGrregisterBinding.listHeader.setVisibility(View.GONE);
                        fragmentGrregisterBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (studentFullDetailModel.getSuccess().equalsIgnoreCase("True")) {
                    if (studentFullDetailModel.getFinalArray().size() > 0) {
                        fragmentGrregisterBinding.txtNoRecords.setVisibility(View.GONE);
                        fragmentGrregisterBinding.studentGrregisterList.setVisibility(View.VISIBLE);
                        fragmentGrregisterBinding.recyclerLinear.setVisibility(View.VISIBLE);
                        fragmentGrregisterBinding.listHeader.setVisibility(View.VISIBLE);
                        grRegisterAdapter = new GRRegisterAdapter(mContext, studentFullDetailModel, new onViewClick() {
                            @Override
                            public void getViewClick() {
                                fragment = new GRNoStudentDetailFragment();
                                Bundle args=new Bundle();
                                args.putString("flag","0");
                                fragment.setArguments(args);
                                fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                        .replace(R.id.frame_container, fragment).commit();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentGrregisterBinding.studentGrregisterList.setLayoutManager(mLayoutManager);
                        fragmentGrregisterBinding.studentGrregisterList.setItemAnimator(new DefaultItemAnimator());
                        fragmentGrregisterBinding.studentGrregisterList.setAdapter(grRegisterAdapter);
                        Utils.dismissDialog();
                    } else {
                        fragmentGrregisterBinding.txtNoRecords.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getGRRegisterDetail() {
        Map<String, String> map = new HashMap<>();

        AppConfiguration.FinalTermIdStr = FinalTermIdStr;
        AppConfiguration.FinalStandardIdStr = FinalStandardStr;
        AppConfiguration.FinalClassIdStr = FinalSectionStr;
        AppConfiguration.FinalStatusStr = FinalStatusStr;

        map.put("Year", FinalTermIdStr);
        map.put("Grade", FinalStandardStr);
        map.put("Section", FinalSectionStr);

        return map;
    }

    public void fillTermSpinner() {

        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }



        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentGrregisterBinding.termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        fragmentGrregisterBinding.termSpinner.setAdapter(adapterTerm);
        FinalTermIdStr = spinnerTermMap.get(0);
    }

    public void fillGradeSpinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("All");

        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < finalArrayStandardsList.size(); i++) {
                standardname.add(finalArrayStandardsList.get(i).getStandard());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < finalArrayStandardsList.size(); j++) {
                standardId.add(finalArrayStandardsList.get(j).getStandardID());
            }
        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        spinnerStandardMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            spinnerStandardMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentGrregisterBinding.gradeSpinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 4 ? 500 : spinnerstandardIdArray.length * 100);
//            popupWindow1.setHeght(200);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        fragmentGrregisterBinding.gradeSpinner.setAdapter(adapterstandard);

        FinalStandardIdStr = spinnerStandardMap.get(0);
    }

    public void fillSection() {
        ArrayList<String> sectionname = new ArrayList<>();
        ArrayList<Integer> sectionId = new ArrayList<>();
        ArrayList<String> firstSectionValue = new ArrayList<String>();
        firstSectionValue.add("All");
        ArrayList<Integer> firstSectionId = new ArrayList<>();
        firstSectionId.add(0);

        if (StandardName.equalsIgnoreCase("All")) {
            for (int j = 0; j < firstSectionValue.size(); j++) {
                sectionname.add(firstSectionValue.get(j));
            }
            for (int i = 0; i < firstSectionId.size(); i++) {
                sectionId.add(firstSectionId.get(i));
            }

        }
        for (int z = 0; z < finalArrayStandardsList.size(); z++) {
            if (StandardName.equalsIgnoreCase(finalArrayStandardsList.get(z).getStandard())) {
                for (int j = 0; j < firstSectionValue.size(); j++) {
                    sectionname.add(firstSectionValue.get(j));
                    for (int i = 0; i < finalArrayStandardsList.get(z).getSectionDetail().size(); i++) {
                        sectionname.add(finalArrayStandardsList.get(z).getSectionDetail().get(i).getSection());
                    }
                }
                for (int j = 0; j < firstSectionId.size(); j++) {
                    sectionId.add(firstSectionId.get(j));
                    for (int m = 0; m < finalArrayStandardsList.get(z).getSectionDetail().size(); m++) {
                        sectionId.add(finalArrayStandardsList.get(z).getSectionDetail().get(m).getSectionID());
                    }
                }
            }
        }

        String[] spinnersectionIdArray = new String[sectionId.size()];

        spinnerSectionMap = new HashMap<Integer, String>();
        for (int i = 0; i < sectionId.size(); i++) {
            spinnerSectionMap.put(i, String.valueOf(sectionId.get(i)));
            spinnersectionIdArray[i] = sectionname.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentGrregisterBinding.sectionSpinner);

            popupWindow.setHeight(spinnersectionIdArray.length > 4 ? 500 : spinnersectionIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstandard = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersectionIdArray);
        fragmentGrregisterBinding.sectionSpinner.setAdapter(adapterstandard);

        FinalSectionStr = spinnerSectionMap.get(0);
        callGRRegisterApi();
    }

    public void fillStatus() {

        ArrayList<Integer> statusdetailId = new ArrayList<>();
//        statusdetailId.add(0);
        statusdetailId.add(1);
        statusdetailId.add(2);
        statusdetailId.add(3);


        ArrayList<String> statusdetail = new ArrayList<>();
//        statusdetail.add("--Select--");
        statusdetail.add("Current Student");
        statusdetail.add("Left School");
        statusdetail.add("PassOut");


        String[] spinnerstatusdetailIdArray = new String[statusdetailId.size()];

        spinnerStatusMap = new HashMap<Integer, String>();
        for (int i = 0; i < statusdetailId.size(); i++) {
            spinnerStatusMap.put(i, String.valueOf(statusdetailId.get(i)));
            spinnerstatusdetailIdArray[i] = statusdetail.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(fragmentGrregisterBinding.statusSpinner);

            popupWindow.setHeight(spinnerstatusdetailIdArray.length > 4 ? 500 : spinnerstatusdetailIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstatusdetailIdArray);
        fragmentGrregisterBinding.statusSpinner.setAdapter(adapterTerm);

        FinalStatusStr = spinnerStatusMap.get(0);
    }
}
