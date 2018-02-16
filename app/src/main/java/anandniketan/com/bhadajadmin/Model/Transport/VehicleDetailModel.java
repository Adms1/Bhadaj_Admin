package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class VehicleDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayVehicleDetail> finalArray = new ArrayList<FinalArrayVehicleDetail>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayVehicleDetail> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayVehicleDetail> finalArray) {
        this.finalArray = finalArray;
    }

}
