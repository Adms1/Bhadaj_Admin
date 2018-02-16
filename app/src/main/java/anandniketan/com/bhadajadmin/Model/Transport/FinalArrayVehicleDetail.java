package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class FinalArrayVehicleDetail {
    @SerializedName("VehicleName")
    @Expose
    private String vehicleName;
    @SerializedName("VehicleId")
    @Expose
    private Integer vehicleId;
    @SerializedName("Registration No.")
    @Expose
    private String registrationNo;
    @SerializedName("YearMFG")
    @Expose
    private String yearMFG;
    @SerializedName("Passng Capacity")
    @Expose
    private Integer passngCapacity;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getYearMFG() {
        return yearMFG;
    }

    public void setYearMFG(String yearMFG) {
        this.yearMFG = yearMFG;
    }

    public Integer getPassngCapacity() {
        return passngCapacity;
    }

    public void setPassngCapacity(Integer passngCapacity) {
        this.passngCapacity = passngCapacity;
    }

}
