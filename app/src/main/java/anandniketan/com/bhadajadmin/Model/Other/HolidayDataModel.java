package anandniketan.com.bhadajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 2/12/2018.
 */

public class HolidayDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayHolidayDetial> finalArray = new ArrayList<FinalArrayHolidayDetial>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayHolidayDetial> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayHolidayDetial> finalArray) {
        this.finalArray = finalArray;
    }

}
