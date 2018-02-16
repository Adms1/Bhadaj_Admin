package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayImprestDetailModel {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("TotalImprest")
    @Expose
    private Double totalImprest;
    @SerializedName("DeductImprest")
    @Expose
    private String deductImprest;
    @SerializedName("RemainImprest")
    @Expose
    private String remainImprest;

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

    public Double getTotalImprest() {
        return totalImprest;
    }

    public void setTotalImprest(Double totalImprest) {
        this.totalImprest = totalImprest;
    }

    public String getDeductImprest() {
        return deductImprest;
    }

    public void setDeductImprest(String deductImprest) {
        this.deductImprest = deductImprest;
    }

    public String getRemainImprest() {
        return remainImprest;
    }

    public void setRemainImprest(String remainImprest) {
        this.remainImprest = remainImprest;
    }

}
