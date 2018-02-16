package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class ConsistenceAbsentStaffModel {
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("Days")
    @Expose
    private Integer days;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

}
