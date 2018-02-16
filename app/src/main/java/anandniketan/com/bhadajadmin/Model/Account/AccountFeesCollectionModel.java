package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class AccountFeesCollectionModel {
    @SerializedName("TermID")
    @Expose
    private Integer termID;
    @SerializedName("TotalAmt")
    @Expose
    private Double totalAmt;
    @SerializedName("TotalAmtStudent")
    @Expose
    private String totalAmtStudent;
    @SerializedName("TotalRcv")
    @Expose
    private Double totalRcv;
    @SerializedName("TotalRcvStudent")
    @Expose
    private String totalRcvStudent;
    @SerializedName("TotalDue")
    @Expose
    private Double totalDue;
    @SerializedName("TotalDueStudent")
    @Expose
    private String totalDueStudent;
    @SerializedName("TotalDuePer")
    @Expose
    private String totalDuePer;
    @SerializedName("DueStudentPer")
    @Expose
    private String dueStudentPer;

    public Integer getTermID() {
        return termID;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalAmtStudent() {
        return totalAmtStudent;
    }

    public void setTotalAmtStudent(String totalAmtStudent) {
        this.totalAmtStudent = totalAmtStudent;
    }

    public Double getTotalRcv() {
        return totalRcv;
    }

    public void setTotalRcv(Double totalRcv) {
        this.totalRcv = totalRcv;
    }

    public String getTotalRcvStudent() {
        return totalRcvStudent;
    }

    public void setTotalRcvStudent(String totalRcvStudent) {
        this.totalRcvStudent = totalRcvStudent;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public String getTotalDueStudent() {
        return totalDueStudent;
    }

    public void setTotalDueStudent(String totalDueStudent) {
        this.totalDueStudent = totalDueStudent;
    }

    public String getTotalDuePer() {
        return totalDuePer;
    }

    public void setTotalDuePer(String totalDuePer) {
        this.totalDuePer = totalDuePer;
    }

    public String getDueStudentPer() {
        return dueStudentPer;
    }

    public void setDueStudentPer(String dueStudentPer) {
        this.dueStudentPer = dueStudentPer;
    }

}
