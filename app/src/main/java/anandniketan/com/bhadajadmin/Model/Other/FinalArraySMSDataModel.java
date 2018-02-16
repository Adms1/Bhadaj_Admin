package anandniketan.com.bhadajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class FinalArraySMSDataModel {
    @SerializedName("PK_EmployeeID")
    @Expose
    private Integer pKEmployeeID;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("Emp_MobileNo")
    @Expose
    private Object empMobileNo;
    @SerializedName("CheckboxStatus")
    @Expose
    private String check;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("StudentCount")
    @Expose
    private Integer studentCount;
    @SerializedName("TeacherCount")
    @Expose
    private Integer teacherCount;
    @SerializedName("AdminCount")
    @Expose
    private Integer adminCount;
    @SerializedName("Employee Code")
    @Expose
    private String employeeCode;
    @SerializedName("Employee Name")
    @Expose
    private String employeeName;
    @SerializedName("Login Details")
    @Expose
    private String loginDetails;

    public Integer getPKEmployeeID() {
        return pKEmployeeID;
    }

    public void setPKEmployeeID(Integer pKEmployeeID) {
        this.pKEmployeeID = pKEmployeeID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Object getEmpMobileNo() {
        return empMobileNo;
    }

    public void setEmpMobileNo(Object empMobileNo) {
        this.empMobileNo = empMobileNo;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(Integer teacherCount) {
        this.teacherCount = teacherCount;
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(String loginDetails) {
        this.loginDetails = loginDetails;
    }
}
