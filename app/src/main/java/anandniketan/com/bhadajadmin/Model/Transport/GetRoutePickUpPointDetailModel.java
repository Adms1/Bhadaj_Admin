package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class GetRoutePickUpPointDetailModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayRouteDetailModel> finalArray = new ArrayList<FinalArrayRouteDetailModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayRouteDetailModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayRouteDetailModel> finalArray) {
        this.finalArray = finalArray;
    }

}
