package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class StudentFullDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentFullDetailModel> finalArray = new ArrayList<FinalArrayStudentFullDetailModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentFullDetailModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentFullDetailModel> finalArray) {
        this.finalArray = finalArray;
    }

}
