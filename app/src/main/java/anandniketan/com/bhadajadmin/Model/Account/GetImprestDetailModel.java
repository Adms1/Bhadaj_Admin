package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class GetImprestDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayImprestDetailModel> finalArray = new ArrayList<FinalArrayImprestDetailModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayImprestDetailModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayImprestDetailModel> finalArray) {
        this.finalArray = finalArray;
    }
}
