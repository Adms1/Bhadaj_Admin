package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class VehicleRouteDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayVehicleRouteModel> finalArray = new ArrayList<FinalArrayVehicleRouteModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayVehicleRouteModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayVehicleRouteModel> finalArray) {
        this.finalArray = finalArray;
    }

}
