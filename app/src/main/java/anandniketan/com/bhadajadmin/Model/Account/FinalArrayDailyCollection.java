package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayDailyCollection {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("TotalFees")
    @Expose
    private String totalFees;
    @SerializedName("ReceivedFees")
    @Expose
    private String receivedFees;
    @SerializedName("PendingFees")
    @Expose
    private String pendingFees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public String getReceivedFees() {
        return receivedFees;
    }

    public void setReceivedFees(String receivedFees) {
        this.receivedFees = receivedFees;
    }

    public String getPendingFees() {
        return pendingFees;
    }

    public void setPendingFees(String pendingFees) {
        this.pendingFees = pendingFees;
    }

}
