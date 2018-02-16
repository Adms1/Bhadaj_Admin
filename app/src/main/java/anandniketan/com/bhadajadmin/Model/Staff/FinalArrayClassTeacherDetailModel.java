package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class FinalArrayClassTeacherDetailModel {
    @SerializedName("Pk_ClsTeacherID")
    @Expose
    private Integer pkClsTeacherID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("StandardId")
    @Expose
    private Integer standardId;
    @SerializedName("SubjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;
    @SerializedName("Employee")
    @Expose
    private String employee;


    public Integer getPkClsTeacherID() {
        return pkClsTeacherID;
    }

    public void setPkClsTeacherID(Integer pkClsTeacherID) {
        this.pkClsTeacherID = pkClsTeacherID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }
}
