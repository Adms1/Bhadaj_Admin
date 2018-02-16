package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/19/2017.
 */

public class GetTeachersModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayTeachersModel> finalArray = new ArrayList<FinalArrayTeachersModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayTeachersModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayTeachersModel> finalArray) {
        this.finalArray = finalArray;
    }

}
