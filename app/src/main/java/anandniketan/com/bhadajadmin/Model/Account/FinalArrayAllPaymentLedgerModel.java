package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/27/2017.
 */

public class FinalArrayAllPaymentLedgerModel {
    @SerializedName("PayDate")
    @Expose
    private String payDate;
    @SerializedName("Paid")
    @Expose
    private String paid;
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("TermDetail")
    @Expose
    private String termDetail;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("PayMode")
    @Expose
    private String payMode;
    @SerializedName("PaidFee")
    @Expose
    private String paidFee;
    @SerializedName("ReceiptNo")
    @Expose
    private String receiptNo;
    @SerializedName("AdmissionFee")
    @Expose
    private String admissionFee;
    @SerializedName("CautionFee")
    @Expose
    private String cautionFee;
    @SerializedName("PreviousFees")
    @Expose
    private String previousFees;
    @SerializedName("TutionFee")
    @Expose
    private String tutionFee;
    @SerializedName("ImprestFee")
    @Expose
    private String imprestFee;
    @SerializedName("LateFees")
    @Expose
    private String lateFees;
    @SerializedName("TransportFee")
    @Expose
    private String transportFee;
    @SerializedName("DiscountFee")
    @Expose
    private String discountFee;
    @SerializedName("PayPaidFees")
    @Expose
    private String payPaidFees;
    @SerializedName("CurrentOutstandingFees")
    @Expose
    private String currentOutstandingFees;
    @SerializedName("ChequeNo")
    @Expose
    private String chequeNo;
    @SerializedName("ChequeStatus")
    @Expose
    private String chequeStatus;

    @SerializedName("Data")
    @Expose
    private List<DatumPaymentLedgerModel> data = new ArrayList<DatumPaymentLedgerModel>();

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermDetail() {
        return termDetail;
    }

    public void setTermDetail(String termDetail) {
        this.termDetail = termDetail;
    }

    public String getgRNO() {
        return gRNO;
    }

    public void setgRNO(String gRNO) {
        this.gRNO = gRNO;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(String paidFee) {
        this.paidFee = paidFee;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getCautionFee() {
        return cautionFee;
    }

    public void setCautionFee(String cautionFee) {
        this.cautionFee = cautionFee;
    }

    public String getPreviousFees() {
        return previousFees;
    }

    public void setPreviousFees(String previousFees) {
        this.previousFees = previousFees;
    }

    public String getTutionFee() {
        return tutionFee;
    }

    public void setTutionFee(String tutionFee) {
        this.tutionFee = tutionFee;
    }

    public String getImprestFee() {
        return imprestFee;
    }

    public void setImprestFee(String imprestFee) {
        this.imprestFee = imprestFee;
    }

    public String getLateFees() {
        return lateFees;
    }

    public void setLateFees(String lateFees) {
        this.lateFees = lateFees;
    }

    public String getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(String transportFee) {
        this.transportFee = transportFee;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getPayPaidFees() {
        return payPaidFees;
    }

    public void setPayPaidFees(String payPaidFees) {
        this.payPaidFees = payPaidFees;
    }

    public String getCurrentOutstandingFees() {
        return currentOutstandingFees;
    }

    public void setCurrentOutstandingFees(String currentOutstandingFees) {
        this.currentOutstandingFees = currentOutstandingFees;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(String chequeStatus) {
        this.chequeStatus = chequeStatus;
    }

    public List<DatumPaymentLedgerModel> getData() {
        return data;
    }

    public void setData(List<DatumPaymentLedgerModel> data) {
        this.data = data;
    }

}
