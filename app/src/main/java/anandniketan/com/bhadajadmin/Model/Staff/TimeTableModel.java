package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class TimeTableModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayTimeTable> finalArray = new ArrayList<FinalArrayTimeTable>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayTimeTable> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayTimeTable> finalArray) {
        this.finalArray = finalArray;
    }

}
