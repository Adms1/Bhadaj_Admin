package anandniketan.com.bhadajadmin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class FinalArrayStudentTransportModel {
    @SerializedName("Term")
    @Expose
    private String term;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("GRNO")
    @Expose
    private String gRNO;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("RouteName")
    @Expose
    private String routeName;
    @SerializedName("PickupPointName")
    @Expose
    private String pickupPointName;
    @SerializedName("KM")
    @Expose
    private String kM;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPickupPointName() {
        return pickupPointName;
    }

    public void setPickupPointName(String pickupPointName) {
        this.pickupPointName = pickupPointName;
    }

    public String getKM() {
        return kM;
    }

    public void setKM(String kM) {
        this.kM = kM;
    }

}
