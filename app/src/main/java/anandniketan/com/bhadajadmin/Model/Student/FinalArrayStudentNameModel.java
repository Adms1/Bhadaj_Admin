package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/21/2017.
 */

public class FinalArrayStudentNameModel {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("Test ID")
    @Expose
    private Integer testID;
    @SerializedName("Test Name")
    @Expose
    private String testName;
    @SerializedName("CheckedStatus")
    @Expose
    private String checkedStatus;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("Cir_Date")
    @Expose
    private String cirDate;
    @SerializedName("Cir_Subject")
    @Expose
    private String cirSubject;
    @SerializedName("Cir_Description")
    @Expose
    private String cirDescription;
    @SerializedName("Cir_Order")
    @Expose
    private Integer cirOrder;
    @SerializedName("Cir_Status")
    @Expose
    private String cirStatus;
    @SerializedName("PK_CircularID")
    @Expose
    private Integer pKCircularID;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Grade")
    @Expose
    private String grade;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Current Status")
    @Expose
    private String currentStatus;
    @SerializedName("Staus Detail")
    @Expose
    private List<InquiryStausDetail> stausDetail = new ArrayList<InquiryStausDetail>();
    @SerializedName("StandardID")
    @Expose
    private Integer standardID;
    @SerializedName("ClassID")
    @Expose
    private Integer classID;
    @SerializedName("TotalPresent")
    @Expose
    private Integer totalPresent;
    @SerializedName("TotalAbsent")
    @Expose
    private Integer totalAbsent;
    @SerializedName("TotalLeave")
    @Expose
    private Integer totalLeave;
    @SerializedName("TotalOnDuty")
    @Expose
    private Integer totalOnDuty;
    @SerializedName("StudentDetail")
    @Expose
    private List<StudentAttendanceDetail> studentDetail = new ArrayList<StudentAttendanceDetail>();
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("classname")
    @Expose
    private String classname;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("StudentData")
    @Expose
    private List<StudentAttendanceDetail> studentData = new ArrayList<StudentAttendanceDetail>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGRNO() {
        return gRNO;
    }

    public void setGRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getgRNO() {
        return gRNO;
    }

    public void setgRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    public String getuRL() {
        return uRL;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public String getCirDate() {
        return cirDate;
    }

    public void setCirDate(String cirDate) {
        this.cirDate = cirDate;
    }

    public String getCirSubject() {
        return cirSubject;
    }

    public void setCirSubject(String cirSubject) {
        this.cirSubject = cirSubject;
    }

    public String getCirDescription() {
        return cirDescription;
    }

    public void setCirDescription(String cirDescription) {
        this.cirDescription = cirDescription;
    }

    public Integer getCirOrder() {
        return cirOrder;
    }

    public void setCirOrder(Integer cirOrder) {
        this.cirOrder = cirOrder;
    }

    public String getCirStatus() {
        return cirStatus;
    }

    public void setCirStatus(String cirStatus) {
        this.cirStatus = cirStatus;
    }

    public Integer getpKCircularID() {
        return pKCircularID;
    }

    public void setpKCircularID(Integer pKCircularID) {
        this.pKCircularID = pKCircularID;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<InquiryStausDetail> getStausDetail() {
        return stausDetail;
    }

    public void setStausDetail(List<InquiryStausDetail> stausDetail) {
        this.stausDetail = stausDetail;
    }

    public Integer getStandardID() {
        return standardID;
    }

    public void setStandardID(Integer standardID) {
        this.standardID = standardID;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public Integer getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(Integer totalPresent) {
        this.totalPresent = totalPresent;
    }

    public Integer getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(Integer totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public Integer getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(Integer totalLeave) {
        this.totalLeave = totalLeave;
    }

    public Integer getTotalOnDuty() {
        return totalOnDuty;
    }

    public void setTotalOnDuty(Integer totalOnDuty) {
        this.totalOnDuty = totalOnDuty;
    }

    public List<StudentAttendanceDetail> getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(List<StudentAttendanceDetail> studentDetail) {
        this.studentDetail = studentDetail;
    }
    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public List<StudentAttendanceDetail> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentAttendanceDetail> studentData) {
        this.studentData = studentData;
    }
}
