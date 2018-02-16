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
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStudentModel> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<FinalArrayStudentModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStudentModel> finalArray) {
        this.finalArray = finalArray;
    }

}
