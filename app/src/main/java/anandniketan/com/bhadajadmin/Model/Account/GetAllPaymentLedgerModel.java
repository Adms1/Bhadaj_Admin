package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class GetAllPaymentLedgerModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayAllPaymentLedgerModel> finalArray = new ArrayList<FinalArrayAllPaymentLedgerModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayAllPaymentLedgerModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayAllPaymentLedgerModel> finalArray) {
        this.finalArray = finalArray;
    }

}
