package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class FinalArrayInsertAssignSubjectModel {
    @SerializedName("Pk_AssignID")
    @Expose
    private Integer pkAssignID;
    @SerializedName("TeacherName")
    @Expose
    private String teacherName;
    @SerializedName("Subject")
    @Expose
    private String subject;

    public Integer getPkAssignID() {
        return pkAssignID;
    }

    public void setPkAssignID(Integer pkAssignID) {
        this.pkAssignID = pkAssignID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
