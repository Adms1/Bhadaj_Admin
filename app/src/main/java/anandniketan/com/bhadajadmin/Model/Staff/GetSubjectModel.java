package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/19/2017.
 */

public class GetSubjectModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArraySubjectModel> finalArray = new ArrayList<FinalArraySubjectModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArraySubjectModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArraySubjectModel> finalArray) {
        this.finalArray = finalArray;
    }

}
