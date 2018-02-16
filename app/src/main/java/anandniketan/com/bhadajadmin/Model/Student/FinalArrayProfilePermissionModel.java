package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/25/2018.
 */

public class FinalArrayProfilePermissionModel {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
