package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/25/2018.
 */

public class GetStudentProfilePermissionModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayProfilePermissionModel> finalArray = new ArrayList<FinalArrayProfilePermissionModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayProfilePermissionModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayProfilePermissionModel> finalArray) {
        this.finalArray = finalArray;
    }

}
