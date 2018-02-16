package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class FinalArrayVehicleRouteModel {
    @SerializedName("Vehicle")
    @Expose
    private String vehicle;
    @SerializedName("VehicleIDID")
    @Expose
    private Integer vehicleIDID;
    @SerializedName("RouteNm")
    @Expose
    private String routeNm;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleIDID() {
        return vehicleIDID;
    }

    public void setVehicleIDID(Integer vehicleIDID) {
        this.vehicleIDID = vehicleIDID;
    }

    public String getRouteNm() {
        return routeNm;
    }

    public void setRouteNm(String routeNm) {
        this.routeNm = routeNm;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }
}
