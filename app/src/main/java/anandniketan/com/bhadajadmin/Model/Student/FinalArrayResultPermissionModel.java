package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/24/2018.
 */

public class FinalArrayResultPermissionModel {
    @SerializedName("PK_StatusID")
    @Expose
    private Integer pKStatusID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Pk_CategoryId")
    @Expose
    private Integer pkCategoryId;

    public Integer getPKStatusID() {
        return pKStatusID;
    }

    public void setPKStatusID(Integer pKStatusID) {
        this.pKStatusID = pKStatusID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getpKStatusID() {
        return pKStatusID;
    }

    public void setpKStatusID(Integer pKStatusID) {
        this.pKStatusID = pKStatusID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPkCategoryId() {
        return pkCategoryId;
    }

    public void setPkCategoryId(Integer pkCategoryId) {
        this.pkCategoryId = pkCategoryId;
    }

}
