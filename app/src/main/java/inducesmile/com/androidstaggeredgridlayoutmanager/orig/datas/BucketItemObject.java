package inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas;

import android.net.Uri;

/**
 * Created by junyoung on 2018. 2. 24..
 */

public class BucketItemObject {
    private String bucketId;
    private String bucketDisplayName;
    private Uri image;
    //private String count;


    public BucketItemObject(String bucketId, String bucketDisplayName) {
        this.bucketId = bucketId;
        this.bucketDisplayName = bucketDisplayName;
    }

    public BucketItemObject(String bucketId, String bucketDisplayName, Uri image) {
        this.bucketId = bucketId;
        this.bucketDisplayName = bucketDisplayName;
        this.image = image;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
