package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class FinalArrayTransportChargesModel {
    @SerializedName("Km")
    @Expose
    private String km;
    @SerializedName("Term 1")
    @Expose
    private Double term1;
    @SerializedName("Term 2")
    @Expose
    private Double term2;

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public Double getTerm1() {
        return term1;
    }

    public void setTerm1(Double term1) {
        this.term1 = term1;
    }

    public Double getTerm2() {
        return term2;
    }

    public void setTerm2(Double term2) {
        this.term2 = term2;
    }

}
