package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayAccountFeesModel {
    @SerializedName("Collection")
    @Expose
    private List<AccountFeesCollectionModel> collection = new ArrayList<AccountFeesCollectionModel>();
    @SerializedName("StandardCollection")
    @Expose
    private List<AccountFeesStandardCollectionModel> standardCollection = new ArrayList<AccountFeesStandardCollectionModel>();

    public List<AccountFeesCollectionModel> getCollection() {
        return collection;
    }

    public void setCollection(List<AccountFeesCollectionModel> collection) {
        this.collection = collection;
    }

    public List<AccountFeesStandardCollectionModel> getStandardCollection() {
        return standardCollection;
    }

    public void setStandardCollection(List<AccountFeesStandardCollectionModel> standardCollection) {
        this.standardCollection = standardCollection;
    }

}
