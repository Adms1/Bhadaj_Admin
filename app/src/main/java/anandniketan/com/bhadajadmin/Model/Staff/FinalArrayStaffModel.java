package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class FinalArrayStaffModel {
    @SerializedName("TotalStaff")
    @Expose
    private String totalStaff;
    @SerializedName("StaffPresent")
    @Expose
    private String staffPresent;
    @SerializedName("StaffLeave")
    @Expose
    private String staffLeave;
    @SerializedName("StaffAbsent")
    @Expose
    private String staffAbsent;
    @SerializedName("ConsistenceAbsent")
    @Expose
    private List<ConsistenceAbsentStaffModel> consistenceAbsent = null;

    public String getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(String totalStaff) {
        this.totalStaff = totalStaff;
    }

    public String getStaffPresent() {
        return staffPresent;
    }

    public void setStaffPresent(String staffPresent) {
        this.staffPresent = staffPresent;
    }

    public String getStaffLeave() {
        return staffLeave;
    }

    public void setStaffLeave(String staffLeave) {
        this.staffLeave = staffLeave;
    }

    public String getStaffAbsent() {
        return staffAbsent;
    }

    public void setStaffAbsent(String staffAbsent) {
        this.staffAbsent = staffAbsent;
    }

    public List<ConsistenceAbsentStaffModel> getConsistenceAbsent() {
        return consistenceAbsent;
    }

    public void setConsistenceAbsent(List<ConsistenceAbsentStaffModel> consistenceAbsent) {
        this.consistenceAbsent = consistenceAbsent;
    }

}
