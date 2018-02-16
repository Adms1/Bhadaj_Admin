package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/19/2017.
 */

public class FinalArrayTeachersModel {
    @SerializedName("Teacher")
    @Expose
    private String teacher;
    @SerializedName("Pk_EmployeeID")
    @Expose
    private Integer pkEmployeeID;
    @SerializedName("Emp_Code")
    @Expose
    private String empCode;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getPkEmployeeID() {
        return pkEmployeeID;
    }

    public void setPkEmployeeID(Integer pkEmployeeID) {
        this.pkEmployeeID = pkEmployeeID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

}
