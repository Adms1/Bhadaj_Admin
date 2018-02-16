package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class FinalArrayInsertClassTeachersModel {
    @SerializedName("Pk_ClsTeacherID")
    @Expose
    private Integer pkClsTeacherID;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getPkClsTeacherID() {
        return pkClsTeacherID;
    }

    public void setPkClsTeacherID(Integer pkClsTeacherID) {
        this.pkClsTeacherID = pkClsTeacherID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
