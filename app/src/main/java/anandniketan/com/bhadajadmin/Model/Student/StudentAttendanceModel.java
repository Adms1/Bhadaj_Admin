package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class StudentAttendanceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentModel> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentModel> finalArray) {
        this.finalArray = finalArray;
    }

}
