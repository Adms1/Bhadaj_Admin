package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/19/2017.
 */

public class FinalArraySubjectModel {
    @SerializedName("Pk_SubjectID")
    @Expose
    private Integer pkSubjectID;
    @SerializedName("Subject")
    @Expose
    private String subject;

    public Integer getPkSubjectID() {
        return pkSubjectID;
    }

    public void setPkSubjectID(Integer pkSubjectID) {
        this.pkSubjectID = pkSubjectID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
