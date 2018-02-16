package anandniketan.com.bhadajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 2/6/2018.
 */

public class DisplayStudentModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentForCreate> finalArraycreate = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentForCreate> getFinalArraycreate() {
        return finalArraycreate;
    }

    public void setFinalArraycreate(List<FinalArrayStudentForCreate> finalArraycreate) {
        this.finalArraycreate = finalArraycreate;
    }
}
