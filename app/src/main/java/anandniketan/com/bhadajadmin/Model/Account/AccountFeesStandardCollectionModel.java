package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class AccountFeesStandardCollectionModel {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StdTotalFees")
    @Expose
    private Double stdTotalFees;
    @SerializedName("StdStudent")
    @Expose
    private String stdStudent;
    @SerializedName("StdTotalRcv")
    @Expose
    private Double stdTotalRcv;
    @SerializedName("StdStudentRcv")
    @Expose
    private String stdStudentRcv;
    @SerializedName("StdTotalDues")
    @Expose
    private Double stdTotalDues;
    @SerializedName("StdStudentDues")
    @Expose
    private String stdStudentDues;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Double getStdTotalFees() {
        return stdTotalFees;
    }

    public void setStdTotalFees(Double stdTotalFees) {
        this.stdTotalFees = stdTotalFees;
    }

    public String getStdStudent() {
        return stdStudent;
    }

    public void setStdStudent(String stdStudent) {
        this.stdStudent = stdStudent;
    }

    public Double getStdTotalRcv() {
        return stdTotalRcv;
    }

    public void setStdTotalRcv(Double stdTotalRcv) {
        this.stdTotalRcv = stdTotalRcv;
    }

    public String getStdStudentRcv() {
        return stdStudentRcv;
    }

    public void setStdStudentRcv(String stdStudentRcv) {
        this.stdStudentRcv = stdStudentRcv;
    }

    public Double getStdTotalDues() {
        return stdTotalDues;
    }

    public void setStdTotalDues(Double stdTotalDues) {
        this.stdTotalDues = stdTotalDues;
    }

    public String getStdStudentDues() {
        return stdStudentDues;
    }

    public void setStdStudentDues(String stdStudentDues) {
        this.stdStudentDues = stdStudentDues;
    }
}
