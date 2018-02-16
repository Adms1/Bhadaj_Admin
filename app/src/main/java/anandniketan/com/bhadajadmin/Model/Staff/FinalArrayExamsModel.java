package anandniketan.com.bhadajadmin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/28/2017.
 */

public class FinalArrayExamsModel {
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("Grade")
    @Expose
    private String grade;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("TestDate")
    @Expose
    private String testDate;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

}
