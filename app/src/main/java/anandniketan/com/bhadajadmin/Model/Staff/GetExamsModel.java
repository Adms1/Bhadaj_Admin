package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/28/2017.
 */

public class GetExamsModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayExamsModel> finalArray = new ArrayList<FinalArrayExamsModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayExamsModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayExamsModel> finalArray) {
        this.finalArray = finalArray;
    }

}
