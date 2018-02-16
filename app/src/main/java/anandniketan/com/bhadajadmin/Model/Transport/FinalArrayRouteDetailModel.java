package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class FinalArrayRouteDetailModel {
    @SerializedName("Route")
    @Expose
    private String route;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;
    @SerializedName("PickupPointDetail")
    @Expose
    private List<PickupPointDetailModel> pickupPointDetail = new ArrayList<PickupPointDetailModel>();

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public List<PickupPointDetailModel> getPickupPointDetail() {
        return pickupPointDetail;
    }

    public void setPickupPointDetail(List<PickupPointDetailModel> pickupPointDetail) {
        this.pickupPointDetail = pickupPointDetail;
    }
}
