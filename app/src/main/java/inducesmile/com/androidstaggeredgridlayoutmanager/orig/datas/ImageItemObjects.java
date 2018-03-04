package inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas;

public class ImageItemObjects {

    //Image 파일 PrimaryKey : MediaStore.Images.Media._ID
    //Image 파일 이름 String : MediaStore.Images.Media.TITLE
    //Image 파일 위치 String : MediaStore.Images.Media.DATA //full path
    //Image 파일 사이즈 String : MediaStore.Images.Media.SIZE
    //Image 파일 날짜 String : MediaStore.Images.Media.DATE_ADDED //저장시 데이터 날짜 ... String -> Long(parseLong) -> SimpleDateFormat
    //Image 파일 타입 String : ??

    /*
    BUCKET_DISPLAY_NAME 에는 디렉토리명이 있으며,
    DISPLAY_NAME 에는 파일명이 있고,
    DATA 에는 전체경로가 들어가 있습니다.
    */
    //https://www.androidpub.com/181622

    /*
    MediaStore.Images.Media._ID,
    MediaStore.Images.Media.TITLE,
    MediaStore.Images.Media.DATA,
    MediaStore.Images.Media.SIZE,
    MediaStore.Images.Media.DATE_ADDED,
    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
    MediaStore.Images.Media.BUCKET_ID,
    MediaStore.Images.Media.CONTENT_TYPE,
    MediaStore.Images.Media.DATE_MODIFIED,
    MediaStore.Images.Media.DATE_TAKEN,
    MediaStore.Images.Media.DESCRIPTION,
    MediaStore.Images.Media.MIME_TYPE,
    MediaStore.Images.Media.MINI_THUMB_MAGIC};
    */

    //PrimaryKey
    private String id;
    private String title;
    private String bucket;
    private String size;
    private String type;
    private String date_added;
    private String date_taken;
    private String date_modified;

    public ImageItemObjects(String id, String title, String bucket, String size, String type, String date_added, String date_taken, String date_modified) {
        this.id = id;
        this.title = title;
        this.bucket = bucket;
        this.size = size;
        this.type = type;
        this.date_added = date_added;
        this.date_taken = date_taken;
        this.date_modified = date_modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageItemObjects that = (ImageItemObjects) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.id);
    }
}
