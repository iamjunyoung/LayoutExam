package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.AdapterContract;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.ViewHolder;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.BaseMvpPresenter;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.BaseMvpView;

/**
 * Created by junyoung on 2017. 10. 2..
 */

public interface MainMvpPresenter <MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    // View -->
    // Activity(View)가 보여질 때 item들을 load해야 하며
    // 이때 Presenter의 loadItemList()를 호출한다.
    void loadItemList();

    void addItem(String title);

    void createSamples();

    // Activity(View)에서 호출할 메서드 들이라고 보면 될듯?
    //// Added for bucket item.
    void loadBucketItemList(Context context);

    void loadImagesItemListInBucket(Context context, String findDirName);

    void switchAdapter(RecyclerView.Adapter<ViewHolder> recyclerViewAdapter);

    void setImageAdapterModel(AdapterContract.Model adapterModel);

    void setImageAdapterView(AdapterContract.View adapterView);
}
