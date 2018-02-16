package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class ParentsNameModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayParentsNameModel> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayParentsNameModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayParentsNameModel> finalArray) {
        this.finalArray = finalArray;
    }

}
