package anandniketan.com.bhadajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class GetBulkSMSDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayBulkSMSModel> finalArray = new ArrayList<FinalArrayBulkSMSModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayBulkSMSModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayBulkSMSModel> finalArray) {
        this.finalArray = finalArray;
    }

}
