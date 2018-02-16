package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class FinalArrayAssignSubjectModel {
    @SerializedName("Pk_AssignID")
    @Expose
    private Integer pkAssignID;
    @SerializedName("TeacherName")
    @Expose
    private String teacherName;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Chapter_Name")
    @Expose
    private String chapterName;
    @SerializedName("Objective")
    @Expose
    private String objective;
    @SerializedName("Key_Point")
    @Expose
    private String keyPoint;
    @SerializedName("Assessment_Question")
    @Expose
    private String assessmentQuestion;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("ChapterNo")
    @Expose
    private Integer chapterNo;
    @SerializedName("ID")
    @Expose
    private Integer iD;

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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public String getAssessmentQuestion() {
        return assessmentQuestion;
    }

    public void setAssessmentQuestion(String assessmentQuestion) {
        this.assessmentQuestion = assessmentQuestion;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }
}
