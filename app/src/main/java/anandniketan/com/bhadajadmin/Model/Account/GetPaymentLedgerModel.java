package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class GetPaymentLedgerModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayPaymentLedgerModel> finalArray = new ArrayList<FinalArrayPaymentLedgerModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayPaymentLedgerModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayPaymentLedgerModel> finalArray) {
        this.finalArray = finalArray;
    }

}
