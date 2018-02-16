package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class AccountFeesStructureModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayFeesStructureModel> finalArray = new ArrayList<FinalArrayFeesStructureModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayFeesStructureModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayFeesStructureModel> finalArray) {
        this.finalArray = finalArray;
    }

}
