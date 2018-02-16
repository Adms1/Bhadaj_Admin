package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class DailyFeeCollectionModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayDailyCollection> finalArray = new ArrayList<FinalArrayDailyCollection>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayDailyCollection> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayDailyCollection> finalArray) {
        this.finalArray = finalArray;
    }

}
