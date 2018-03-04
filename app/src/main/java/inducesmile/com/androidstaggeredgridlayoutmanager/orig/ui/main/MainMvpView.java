package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.BucketItemObject;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.BaseMvpView;

/**
 * Created by junyoung on 2017. 10. 1..
 */

interface MainMvpView extends BaseMvpView {
    // MainActivity 가 implements할 method들
    // 이 method들은 MainMvpPresenterImpl 이 호출한다.

    void onUpdateItemList(List<ItemObjects> itemList);

    void onCreatedItem(ItemObjects itemObjects);

    void showEmtpyView();

    void onSuccessCreateSamples();

    ///////////////////////// For Bucket /////////////////////////
    void onUpdateBucketItemList(List<BucketItemObject> bItemList);

    void showEmtpyBucketView();


}
