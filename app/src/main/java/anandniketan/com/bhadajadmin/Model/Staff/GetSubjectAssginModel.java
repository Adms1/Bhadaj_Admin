package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class GetSubjectAssginModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayAssignSubjectModel> finalArray = new ArrayList<FinalArrayAssignSubjectModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayAssignSubjectModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayAssignSubjectModel> finalArray) {
        this.finalArray = finalArray;
    }

}
