package anandniketan.com.bhadajadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveModel {


    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }


    public class Detail {

        @SerializedName("LeaveStartDate")
        @Expose
        private String leaveStartDate;
        @SerializedName("LeaveEndDate")
        @Expose
        private String leaveEndDate;
        @SerializedName("LeaveDays")
        @Expose
        private String leaveDays;
        @SerializedName("ApproveStartDate")
        @Expose
        private String approveStartDate;
        @SerializedName("ApproveEndDate")
        @Expose
        private String approveEndDate;
        @SerializedName("ApproveDays")
        @Expose
        private String approveDays;
        @SerializedName("ApproveStatus")
        @Expose
        private String approveStatus;
        @SerializedName("ApproveBy")
        @Expose
        private String approveBy;
        @SerializedName("PL")
        @Expose
        private String pL;
        @SerializedName("CL")
        @Expose
        private String cL;

        public String getLeaveStartDate() {
            return leaveStartDate;
        }

        public void setLeaveStartDate(String leaveStartDate) {
            this.leaveStartDate = leaveStartDate;
        }

        public String getLeaveEndDate() {
            return leaveEndDate;
        }

        public void setLeaveEndDate(String leaveEndDate) {
            this.leaveEndDate = leaveEndDate;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getApproveStartDate() {
            return approveStartDate;
        }

        public void setApproveStartDate(String approveStartDate) {
            this.approveStartDate = approveStartDate;
        }

        public String getApproveEndDate() {
            return approveEndDate;
        }

        public void setApproveEndDate(String approveEndDate) {
            this.approveEndDate = approveEndDate;
        }

        public String getApproveDays() {
            return approveDays;
        }

        public void setApproveDays(String approveDays) {
            this.approveDays = approveDays;
        }

        public String getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(String approveStatus) {
            this.approveStatus = approveStatus;
        }

        public String getApproveBy() {
            return approveBy;
        }

        public void setApproveBy(String approveBy) {
            this.approveBy = approveBy;
        }

        public String getPL() {
            return pL;
        }

        public void setPL(String pL) {
            this.pL = pL;
        }

        public String getCL() {
            return cL;
        }

        public void setCL(String cL) {
            this.cL = cL;
        }

    }





    public class FinalArray {

        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("EmployeeID")
        @Expose
        private Integer employeeID;
        @SerializedName("PLUSed")
        @Expose
        private Double pLUSed;
        @SerializedName("TotalPL")
        @Expose
        private Integer totalPL;
        @SerializedName("CLUsed")
        @Expose
        private Double cLUsed;
        @SerializedName("TotalCL")
        @Expose
        private Integer totalCL;
        @SerializedName("Used")
        @Expose
        private Double used;
        @SerializedName("Total")
        @Expose
        private Integer total;
        @SerializedName("PayStatus")
        @Expose
        private String payStatus;
        @SerializedName("Detail")
        @Expose
        private List<Detail> detail = null;

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Integer getEmployeeID() {
            return employeeID;
        }

        public void setEmployeeID(Integer employeeID) {
            this.employeeID = employeeID;
        }

        public Double getPLUSed() {
            return pLUSed;
        }

        public void setPLUSed(Double pLUSed) {
            this.pLUSed = pLUSed;
        }

        public Integer getTotalPL() {
            return totalPL;
        }

        public void setTotalPL(Integer totalPL) {
            this.totalPL = totalPL;
        }

        public Double getCLUsed() {
            return cLUsed;
        }

        public void setCLUsed(Double cLUsed) {
            this.cLUsed = cLUsed;
        }

        public Integer getTotalCL() {
            return totalCL;
        }

        public void setTotalCL(Integer totalCL) {
            this.totalCL = totalCL;
        }

        public Double getUsed() {
            return used;
        }

        public void setUsed(Double used) {
            this.used = used;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public List<Detail> getDetail() {
            return detail;
        }

        public void setDetail(List<Detail> detail) {
            this.detail = detail;
        }

    }

}
