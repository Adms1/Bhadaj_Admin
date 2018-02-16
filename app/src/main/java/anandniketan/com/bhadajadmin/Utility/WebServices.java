package anandniketan.com.bhadajadmin.Utility;

import java.util.Map;

import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStatusModel;
import anandniketan.com.bhadajadmin.Model.Account.AccountFeesStructureModel;
import anandniketan.com.bhadajadmin.Model.Account.DailyFeeCollectionModel;
import anandniketan.com.bhadajadmin.Model.Account.GetAllPaymentLedgerModel;
import anandniketan.com.bhadajadmin.Model.Account.GetDiscountDetailsModel;
import anandniketan.com.bhadajadmin.Model.Account.GetImprestDetailModel;
import anandniketan.com.bhadajadmin.Model.Account.GetPaymentLedgerModel;
import anandniketan.com.bhadajadmin.Model.Account.GetStandardModel;
import anandniketan.com.bhadajadmin.Model.HR.GetPageListModel;
import anandniketan.com.bhadajadmin.Model.HR.InsertMenuPermissionModel;
import anandniketan.com.bhadajadmin.Model.Other.DisplayStudentModel;
import anandniketan.com.bhadajadmin.Model.Other.GetBulkSMSDataModel;
import anandniketan.com.bhadajadmin.Model.Other.GetStaffSMSDataModel;
import anandniketan.com.bhadajadmin.Model.Other.HolidayDataModel;
import anandniketan.com.bhadajadmin.Model.Staff.GetClassTeacherDetailModel;
import anandniketan.com.bhadajadmin.Model.Staff.GetExamsModel;
import anandniketan.com.bhadajadmin.Model.Staff.GetSubjectAssginModel;
import anandniketan.com.bhadajadmin.Model.Staff.GetSubjectModel;
import anandniketan.com.bhadajadmin.Model.Staff.GetTeachersModel;
import anandniketan.com.bhadajadmin.Model.Staff.InsertAssignSubjectModel;
import anandniketan.com.bhadajadmin.Model.Staff.InsertClassTeachersModel;
import anandniketan.com.bhadajadmin.Model.Staff.StaffAttendaceModel;
import anandniketan.com.bhadajadmin.Model.Staff.TimeTableModel;
import anandniketan.com.bhadajadmin.Model.Student.GetResultPermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.GetStudentProfilePermissionModel;
import anandniketan.com.bhadajadmin.Model.Student.ParentsNameModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentFullDetailModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentNameModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentShowFilteredDataModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentTransportDetailModel;
import anandniketan.com.bhadajadmin.Model.Transport.GetRoutePickUpPointDetailModel;
import anandniketan.com.bhadajadmin.Model.Transport.TermModel;
import anandniketan.com.bhadajadmin.Model.Transport.TransportChargesModel;
import anandniketan.com.bhadajadmin.Model.Transport.VehicleDetailModel;
import anandniketan.com.bhadajadmin.Model.Transport.VehicleRouteDetailModel;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {
    @FormUrlEncoded
    @POST("/Admin_StudentAttendence")

    public void getStudentAttendace(@FieldMap Map<String, String> map, Callback<StudentAttendanceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StaffAttendence")
    public void getStaffAttendace(@FieldMap Map<String, String> map, Callback<StaffAttendaceModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByParentName")
    public void getParentName(@FieldMap Map<String, String> map, Callback<ParentsNameModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentSearchByStuName")
    public void getStudentName(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentShowFilteredData")
    public void getStudentFilterData(@FieldMap Map<String, String> map, Callback<StudentShowFilteredDataModel> callback);

    @FormUrlEncoded
    @POST("/Admin_StudentFullDetail")
    public void getStudentFullDetail(@FieldMap Map<String, String> map, Callback<StudentFullDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTerm")
    public void getTerm(@FieldMap Map<String, String> map, Callback<TermModel> callback);

    @FormUrlEncoded
    @POST("/GetRoutePickUpPointDetail")
    public void getRouteDetail(@FieldMap Map<String, String> map, Callback<GetRoutePickUpPointDetailModel> callback);

    @FormUrlEncoded
    @POST("/StudentTransportDetail")
    public void getStudentTransportDetail(@FieldMap Map<String, String> map, Callback<StudentTransportDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTrasportCharges")
    public void getTransportChargesDetail(@FieldMap Map<String, String> map, Callback<TransportChargesModel> callback);

    @FormUrlEncoded
    @POST("/GetVehicleDetail")
    public void getVehicleDetail(@FieldMap Map<String, String> map, Callback<VehicleDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetVehicleToRouteDetail")
    public void getVehicleRouteDetail(@FieldMap Map<String, String> map, Callback<VehicleRouteDetailModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStatus")
    public void getAccountFeesStatusDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStatusModel> callback);

    @FormUrlEncoded
    @POST("/Admin_AccountFeesStructure")
    public void getAccountFeesStructureDetail(@FieldMap Map<String, String> map, Callback<AccountFeesStructureModel> callback);

    @FormUrlEncoded
    @POST("/GetStandardSection")
    public void getStandardDetail(@FieldMap Map<String, String> map, Callback<GetStandardModel> callback);

    @FormUrlEncoded
    @POST("/GetDiscountDetail")
    public void getDiscountDetail(@FieldMap Map<String, String> map, Callback<GetDiscountDetailsModel> callback);

    @FormUrlEncoded
    @POST("/GetImprestDetail")
    public void getImprestDetail(@FieldMap Map<String, String> map, Callback<GetImprestDetailModel> callback);

    @FormUrlEncoded
    @POST("/DailyFeeColleCtion")
    public void getDailyFeeColleCtionDetail(@FieldMap Map<String, String> map, Callback<DailyFeeCollectionModel> callback);

    @FormUrlEncoded
    @POST("/TeacherGetTimetable")
    public void getTimeTable(@FieldMap Map<String, String> map, Callback<TimeTableModel> callback);

    @FormUrlEncoded
    @POST("/GetPaymentLedger")
    public void getPaymentLedger(@FieldMap Map<String, String> map, Callback<GetPaymentLedgerModel> callback);

    @FormUrlEncoded
    @POST("/GetAllPaymentLedger")
    public void getAllPaymentLedger(@FieldMap Map<String, String> map, Callback<GetAllPaymentLedgerModel> callback);

    @FormUrlEncoded
    @POST("/GetExams")
    public void getExams(@FieldMap Map<String, String> map, Callback<GetExamsModel> callback);

    @FormUrlEncoded
    @POST("/GetTeachers")
    public void getTeachers(@FieldMap Map<String, String> map, Callback<GetTeachersModel> callback);

    @FormUrlEncoded
    @POST("/GetSubject")
    public void getSubject(@FieldMap Map<String, String> map, Callback<GetSubjectModel> callback);

    @FormUrlEncoded
    @POST("/GetSubjectAssgin")
    public void getSubjectAssgin(@FieldMap Map<String, String> map, Callback<GetSubjectAssginModel> callback);

    @FormUrlEncoded
    @POST("/InsertAssignSubject")
    public void InsertAssignSubject(@FieldMap Map<String, String> map, Callback<InsertAssignSubjectModel> callback);

    @FormUrlEncoded
    @POST("/GetClassTeacherDetail")
    public void getClassTeacherDetail(@FieldMap Map<String, String> map, Callback<GetClassTeacherDetailModel> callback);

    @FormUrlEncoded
    @POST("/InsertClassTeachers")
    public void InsertClassTeachers(@FieldMap Map<String, String> map, Callback<InsertClassTeachersModel> callback);

    @FormUrlEncoded
    @POST("/GetPageList")
    public void getPageList(@FieldMap Map<String, String> map, Callback<GetPageListModel> callback);

    @FormUrlEncoded
    @POST("/InsertMenuPermission")
    public void InsertMenuPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertSingleSMSData")
    public void InsertSingleSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetStaffSMSData")
    public void getStaffSMSData(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertStaffSMSData")
    public void InsertStaffSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetBulkSMSData")
    public void getBulkSMSData(@FieldMap Map<String, String> map, Callback<GetBulkSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertBulkSMSData")
    public void InsertBulkSMSData(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetAbsentToday")
    public void getAbsentToday(@FieldMap Map<String, String> map, Callback<GetBulkSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertAbsentTodaySMS")
    public void InsertAbsentTodaySMS(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetResultPermission")
    public void getResultPermission(@FieldMap Map<String, String> map, Callback<GetResultPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertResultPermission")
    public void InsertResultPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetStudentProfilePermission")
    public void getStudentProfilePermission(@FieldMap Map<String, String> map, Callback<GetStudentProfilePermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertProfilePermission")
    public void InsertProfilePermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetOnlinePaymentPermission")
    public void getOnlinePaymentPermission(@FieldMap Map<String, String> map, Callback<GetResultPermissionModel> callback);

    @FormUrlEncoded
    @POST("/InsertOnlinePaymentPermission")
    public void InsertOnlinePaymentPermission(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetGRRegister")
    public void getGRRegister(@FieldMap Map<String, String> map, Callback<StudentFullDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetLeftDetainStudent")
    public void getLeftDetainStudent(@FieldMap Map<String, String> map, Callback<StudentFullDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetTestName")
    public void getTestName(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/GetStudentMarks")
    public void getStudentMarks(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/GetAnnouncementData")
    public void getAnnouncementData(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/InsertAnnouncement")
    public void InsertAnnouncement(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryCount")
    public void getInquiryCount(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/GetInquiryData")
    public void getInquiryData(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/GetAttendence_Admin")
    public void getAttendence_Admin(@FieldMap Map<String, String> map, Callback<StudentNameModel> callback);

    @FormUrlEncoded
    @POST("/GetReceiptDetails")
    public void getReceiptDetails(@FieldMap Map<String, String> map, Callback<GetAllPaymentLedgerModel> callback);

    @FormUrlEncoded
    @POST("/PTMTeacherStudentGetDetail")
    public void getPTMTeacherStudentGetDetail(@FieldMap Map<String, String> map, Callback<GetBulkSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/PTMTeacherStudentInsertDetail")
    public void PTMTeacherStudentInsertDetail(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/PTMDeleteMeeting")
    public void PTMDeleteMeeting(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/TeacherGetClassSubjectWiseStudent")
    public void getTeacherGetClassSubjectWiseStudent(@FieldMap Map<String, String> map, Callback<DisplayStudentModel> callback);

    @FormUrlEncoded
    @POST("/GetMonthlyCount")
    public void getMonthlyCount(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetDateCountPerMonth")
    public void getDateCountPerMonth(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);

    @FormUrlEncoded
    @POST("/GetLoginDetailsDatewise")
    public void getLoginDetailsDatewise(@FieldMap Map<String, String> map, Callback<GetStaffSMSDataModel> callback);
    @FormUrlEncoded
    @POST("/GetHolidayCategory")
    public void getHolidayCategory(@FieldMap Map<String, String> map, Callback<GetResultPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetHoliday")
    public void getHoliday(@FieldMap Map<String, String> map, Callback<HolidayDataModel> callback);

    @FormUrlEncoded
    @POST("/InsertHoliday")
    public void InsertHoliday(@FieldMap Map<String, String> map, Callback<InsertMenuPermissionModel> callback);

    @FormUrlEncoded
    @POST("/GetEmployeeBySubject")
    public void getEmployeeBySubject(@FieldMap Map<String, String> map, Callback<GetClassTeacherDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetLessonPlanSubject")
    public void getLessonPlanSubject(@FieldMap Map<String, String> map, Callback<GetClassTeacherDetailModel> callback);

    @FormUrlEncoded
    @POST("/GetLessonPlan")
    public void getLessonPlan(@FieldMap Map<String, String> map, Callback<GetSubjectAssginModel> callback);
}
