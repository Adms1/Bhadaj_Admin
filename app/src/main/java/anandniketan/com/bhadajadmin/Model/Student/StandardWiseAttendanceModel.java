package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class StandardWiseAttendanceModel {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Status")
    @Expose
    private String status;

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

}
