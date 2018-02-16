package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class FinalArrayStudentFilteredData {
    @SerializedName("FatherName")
    @Expose
    private String fatherName;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Grade_Section")
    @Expose
    private String gradeSection;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("StudentID")
    @Expose
    private Integer studentID;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGradeSection() {
        return gradeSection;
    }

    public void setGradeSection(String gradeSection) {
        this.gradeSection = gradeSection;
    }

    public String getGRNO() {
        return gRNO;
    }

    public void setGRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

}
