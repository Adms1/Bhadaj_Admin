package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayFeesStructureModel {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("Term 1 Admission Fee")
    @Expose
    private String term1AdmissionFee;
    @SerializedName("Term 1 Caution Money")
    @Expose
    private String term1CautionMoney;
    @SerializedName("Term 1 Tuition Fee")
    @Expose
    private String term1TuitionFee;
    @SerializedName("Term 1 Imprest")
    @Expose
    private String term1Imprest;
    @SerializedName("Term 2 Tuition Fee")
    @Expose
    private String term2TuitionFee;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getTerm1AdmissionFee() {
        return term1AdmissionFee;
    }

    public void setTerm1AdmissionFee(String term1AdmissionFee) {
        this.term1AdmissionFee = term1AdmissionFee;
    }

    public String getTerm1CautionMoney() {
        return term1CautionMoney;
    }

    public void setTerm1CautionMoney(String term1CautionMoney) {
        this.term1CautionMoney = term1CautionMoney;
    }

    public String getTerm1TuitionFee() {
        return term1TuitionFee;
    }

    public void setTerm1TuitionFee(String term1TuitionFee) {
        this.term1TuitionFee = term1TuitionFee;
    }

    public String getTerm1Imprest() {
        return term1Imprest;
    }

    public void setTerm1Imprest(String term1Imprest) {
        this.term1Imprest = term1Imprest;
    }

    public String getTerm2TuitionFee() {
        return term2TuitionFee;
    }

    public void setTerm2TuitionFee(String term2TuitionFee) {
        this.term2TuitionFee = term2TuitionFee;
    }

}
