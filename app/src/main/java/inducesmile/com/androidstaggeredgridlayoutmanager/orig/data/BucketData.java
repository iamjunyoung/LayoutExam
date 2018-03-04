package inducesmile.com.androidstaggeredgridlayoutmanager.orig.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.BucketItemObject;

import static android.os.Build.ID;

/**
 * Created by junyoung on 2018. 2. 24..
 */

public class BucketData {
    //singletond 으로 구현하자. getInstance()등
    private static BucketData bucketData;
    public  static String TAG = "BucketData";

    private BucketData() {

    }

    public static BucketData getInstance() {
        if (bucketData == null) {
            bucketData = new BucketData();
        }
        return bucketData;
    }

    public ArrayList<BucketItemObject> loadBucketItemList(Context context) {
        String[] projection = {
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
                };

        Cursor imageCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 이미지 컨텐트 테이블
                projection,
                null,       // 모든 개체 출력
                null,
                null);      // 정렬 안 함

        //ArrayList<Uri> result = new ArrayList<>(imageCursor.getCount());
        HashMap<String, String> result = new HashMap<>();
        int dataColumn_BUCKET_DISPLAY_NAME = imageCursor.getColumnIndex(projection[0]);
        int dataColumn_BUCKET_ID = imageCursor.getColumnIndex(projection[1]);

        String BUCKET_DISPLAY_NAME = null;
        String BUCKET_ID = null;

        if (imageCursor == null) {
            // Error 발생
            // 적절하게 handling 해주세요
        } else if (imageCursor.moveToFirst()) {

            int i = 0;
            do {
                BUCKET_DISPLAY_NAME = imageCursor.getString(dataColumn_BUCKET_DISPLAY_NAME);
                BUCKET_ID = imageCursor.getString(dataColumn_BUCKET_ID);

                Log.i(TAG, "BUCKET : " + BUCKET_DISPLAY_NAME + "  B_ID : " + BUCKET_ID + "  ID : " + ID);

                result.put(BUCKET_ID, BUCKET_DISPLAY_NAME);

            } while(imageCursor.moveToNext());
        } else {
            // imageCursor가 비었습니다.
        }
        imageCursor.close();

        ArrayList<BucketItemObject> items = new ArrayList<>();
        Iterator<String> iterator = result.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            BucketItemObject bItem = new BucketItemObject(key, result.get(key));
            items.add(bItem);
        }
        return items;
    }

    /// /List<Uri> fetchAllImages() {
    //HashMap<String, String> fetchAllImages(Context context, String findDirName) {
    public ArrayList<BucketItemObject> findAllImagesInDir(Context context, String findDirName) {
        // DATA는 이미지 파일의 스트림 데이터 경로를 나타냅니다.
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media._ID,
                //MediaStore.Images.Media.CONTENT_TYPE,
                MediaStore.Images.Media.DESCRIPTION,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.TITLE};

        Cursor imageCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 이미지 컨텐트 테이블
                projection, // DATA를 출력
                //null,       // 모든 개체 출력
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='" + findDirName + "'",
                null,
                null);      // 정렬 안 함

        //ArrayList<Uri> result = new ArrayList<>(imageCursor.getCount());
        HashMap<String, BucketItemObject> result = new HashMap<>();
        int dataColumn_DATA = imageCursor.getColumnIndex(projection[0]);
        int dataColumn_SIZE = imageCursor.getColumnIndex(projection[1]);
        int dataColumn_DATE_ADDED = imageCursor.getColumnIndex(projection[2]);
        int dataColumn_DATE_TAKEN = imageCursor.getColumnIndex(projection[3]);
        int dataColumn_DATE_MODIFIED = imageCursor.getColumnIndex(projection[4]);
        int dataColumn_BUCKET_DISPLAY_NAME = imageCursor.getColumnIndex(projection[5]);
        int dataColumn_BUCKET_ID = imageCursor.getColumnIndex(projection[6]);
        int dataColumn_ID = imageCursor.getColumnIndex(projection[7]);
        //int dataColumn_CONTENT_TYPE = imageCursor.getColumnIndex(projection[8]);
        int dataColumn_DESCRIPTION = imageCursor.getColumnIndex(projection[8]);
        int dataColumn_MIME_TYPE = imageCursor.getColumnIndex(projection[9]);
        int dataColumn_MINI_THUMB_MAGIC = imageCursor.getColumnIndex(projection[10]);
        int dataColumn_TITLE = imageCursor.getColumnIndex(projection[11]);

        if (imageCursor == null) {
            // Error 발생
            // 적절하게 handling 해주세요
        } else if (imageCursor.moveToFirst()) {
            int i = 0;
            //Cursor c = null;
            do {

                        //출처: http://androphil.tistory.com/369 [소림사의 홍반장!]

                        String filePath = imageCursor.getString(dataColumn_DATA);
                        Uri imageUri = Uri.parse(filePath);
                        //result.add(imageUri);
                /*
                        String fileSize = c.getString(dataColumn_SIZE);
                        String DATE_ADDED = c.getString(dataColumn_DATE_ADDED);
                        String DATE_TAKEN = c.getString(dataColumn_DATE_TAKEN);
                        String DATE_MODIFIED = c.getString(dataColumn_DATE_MODIFIED);
                */
                String BUCKET_ID = imageCursor.getString(dataColumn_BUCKET_ID);

                String BUCKET_DISPLAY_NAME = imageCursor.getString(dataColumn_BUCKET_DISPLAY_NAME);
                /*
                        String BUCKET_ID = c.getString(dataColumn_BUCKET_ID);
                        String ID = c.getString(dataColumn_ID);
                        //String CONTENT_TYPE = c.getString(dataColumn_CONTENT_TYPE);
                        String DESCRIPTION = c.getString(dataColumn_DESCRIPTION);
                        String MIME_TYPE = c.getString(dataColumn_MIME_TYPE);
                        String MINI_THUMB_MAGIC = c.getString(dataColumn_MINI_THUMB_MAGIC);
                */
                        String TITLE = imageCursor.getString(dataColumn_TITLE);

                        Log.i(TAG, "[" + ++i + "] Title : " + TITLE + "    Bucket : " + BUCKET_DISPLAY_NAME);
                        //if (filePath != null && filePath.contains("Screenshots")) {
                    /*
                    File f = new File(filePath);
                    Log.i(TAG, "[ " + ++i + " ]  " + imageUri + "  size : " + fileSize +
                            "  type : " + getMimeType(filePath) +
                            "  parent(k) : " + f.getParentFile().getName() + "  parent all(v) :" + f.getParent().toString());
                    Log.i(TAG, "ADDED : " + DATE_ADDED + "  TAKEN : " + DATE_TAKEN + "  MODIFY : " + DATE_MODIFIED
                            + "  BUCKET : " + BUCKET_DISPLAY_NAME + "  B_ID : " + BUCKET_ID + "  ID : " + ID
                            + "  DESC : " + DESCRIPTION + "  M_TYPE : " + MIME_TYPE
                            + "  MINI_THUMB : " + MINI_THUMB_MAGIC + "  TITLE : " + TITLE);
                    result.put(f.getParentFile().getName(), f.getParent().toString());
                    */
                result.put(BUCKET_ID, new BucketItemObject(BUCKET_ID, BUCKET_DISPLAY_NAME, imageUri));

            } while(imageCursor.moveToNext());
        } else {
            // imageCursor가 비었습니다.
        }
        imageCursor.close();

        ArrayList<BucketItemObject> items = new ArrayList<>(result.values());

        return items;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
        // https://code.i-harness.com/ko/q/83114d
        // http://susemi99.tistory.com/896
    }
}
