package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class GetDiscountDetailsModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayDiscountModel> finalArray = new ArrayList<FinalArrayDiscountModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayDiscountModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayDiscountModel> finalArray) {
        this.finalArray = finalArray;
    }

}
