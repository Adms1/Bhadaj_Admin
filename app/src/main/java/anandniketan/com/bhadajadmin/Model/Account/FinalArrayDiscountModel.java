package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayDiscountModel {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("TotalFees")
    @Expose
    private String totalFees;
    @SerializedName("WaveOffAmt")
    @Expose
    private String waveOffAmt;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("PayableAmt")
    @Expose
    private String payableAmt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGRNO() {
        return gRNO;
    }

    public void setGRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public String getWaveOffAmt() {
        return waveOffAmt;
    }

    public void setWaveOffAmt(String waveOffAmt) {
        this.waveOffAmt = waveOffAmt;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayableAmt() {
        return payableAmt;
    }

    public void setPayableAmt(String payableAmt) {
        this.payableAmt = payableAmt;
    }

}
