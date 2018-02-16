package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class InsertClassTeachersModel {
    @SerializedName("Success")
    @Expose
    public String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayInsertClassTeachersModel> finalArray = new ArrayList<FinalArrayInsertClassTeachersModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayInsertClassTeachersModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayInsertClassTeachersModel> finalArray) {
        this.finalArray = finalArray;
    }

}
