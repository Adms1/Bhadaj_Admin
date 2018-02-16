package anandniketan.com.bhadajadmin.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 2/12/2018.
 */

public class FinalArrayHolidayDetial {
    @SerializedName("StartDT")
    @Expose
    private String startDT;
    @SerializedName("EndDT")
    @Expose
    private String endDT;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("HolidayName")
    @Expose
    private String holidayName;
    @SerializedName("PK_HolidayID")
    @Expose
    private Integer pKHolidayID;
    @SerializedName("PK_CategoryID")
    @Expose
    private Integer pKCategoryID;

    public String getStartDT() {
        return startDT;
    }

    public void setStartDT(String startDT) {
        this.startDT = startDT;
    }

    public String getEndDT() {
        return endDT;
    }

    public void setEndDT(String endDT) {
        this.endDT = endDT;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Integer getPKHolidayID() {
        return pKHolidayID;
    }

    public void setPKHolidayID(Integer pKHolidayID) {
        this.pKHolidayID = pKHolidayID;
    }

    public Integer getPKCategoryID() {
        return pKCategoryID;
    }

    public void setPKCategoryID(Integer pKCategoryID) {
        this.pKCategoryID = pKCategoryID;
    }

}
