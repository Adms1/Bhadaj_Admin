package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class FinalArrayStudentModel {
    @SerializedName("TotalStudent")
    @Expose
    private String totalStudent;
    @SerializedName("StudentPresent")
    @Expose
    private String studentPresent;
    @SerializedName("StudentLeave")
    @Expose
    private String studentLeave;
    @SerializedName("StudentAbsent")
    @Expose
    private String studentAbsent;
    @SerializedName("StandardWiseAttendance")
    @Expose
    private List<StandardWiseAttendanceModel> standardWiseAttendance = null;

    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getStudentPresent() {
        return studentPresent;
    }

    public void setStudentPresent(String studentPresent) {
        this.studentPresent = studentPresent;
    }

    public String getStudentLeave() {
        return studentLeave;
    }

    public void setStudentLeave(String studentLeave) {
        this.studentLeave = studentLeave;
    }

    public String getStudentAbsent() {
        return studentAbsent;
    }

    public void setStudentAbsent(String studentAbsent) {
        this.studentAbsent = studentAbsent;
    }

    public List<StandardWiseAttendanceModel> getStandardWiseAttendance() {
        return standardWiseAttendance;
    }

    public void setStandardWiseAttendance(List<StandardWiseAttendanceModel> standardWiseAttendance) {
        this.standardWiseAttendance = standardWiseAttendance;
    }

}
