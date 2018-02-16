package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class StudentShowFilteredDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentFilteredData> finalArray = new ArrayList<FinalArrayStudentFilteredData>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStudentFilteredData> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentFilteredData> finalArray) {
        this.finalArray = finalArray;
    }

}
