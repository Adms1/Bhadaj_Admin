package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class StudentTransportDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentTransportModel> finalArray = new ArrayList<FinalArrayStudentTransportModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentTransportModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentTransportModel> finalArray) {
        this.finalArray = finalArray;
    }

}
