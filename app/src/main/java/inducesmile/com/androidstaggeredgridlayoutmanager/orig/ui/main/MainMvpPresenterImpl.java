package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.AdapterContract;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.ViewHolder;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.BaseMvpView;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.RxPresenter;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.data.BucketData;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.BucketItemObject;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.singletons.ItemManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import static inducesmile.com.androidstaggeredgridlayoutmanager.orig.data.BucketData.TAG;

/**
 * Created by junyoung on 2017. 10. 2..
 */

public class MainMvpPresenterImpl <MvpView extends BaseMvpView> extends RxPresenter
        implements MainMvpPresenter<MvpView> {

    private MainMvpView view; //view를 갖고있다.

    private AdapterContract.Model adapterModel;
    private AdapterContract.View adapterView;

    //Adpapter의 종류가 바뀌어야 할 때,
    //MainActivity쪽에서 MainMvpPresenter쪽으로 dataModel이 바뀌었음을 메서드?등을 통해 알려주어야 할까? (switchAdapter()가 MainActivity로부터 호출된다든지...)
    //3가지 adpater가 변경되면서 set될 수가 있는 상황인데 이를 어떻게 처리하는 것이 좋을까?

    //Presenter는 SolventDataModel을 갖고있어야 겠다 (adpaterDatapModel)

    private PublishSubject<String> searchTextChangeSubject = PublishSubject.create();

    //MainMvpPresenterImpl(final Realm realm) {
    MainMvpPresenterImpl() {
        //this.realm = realm;

        add(searchTextChangeSubject
                .throttleLast(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String text) throws Exception {
                                //RealmResults<Todo> todoList = TodoManager.search(realm, text);
                                List<ItemObjects> searchResultList = ItemManager.search(text);
                                if (searchResultList.isEmpty()) {
                                    view.showEmtpyView();
                                } else {
                                    view.onUpdateItemList(searchResultList);
                                }
                            }
                        }));
    }

    // Q. 이 attachView()는 누가 어디서 호출하는가?
    // --> A. MainActivity의 initPresenter()내부에서 호출한다.
    @Override
    public void attachView(MvpView view) {
        this.view = (MainMvpView) view;
        //MainMvpView 는 interface이다.


        //또한 아래와 같이 MainActivity는 MainMvpView를 구현하였다
        //MainActivity -> MainMvpView -> BaseMvpView
        //                    MvpView -> BaseMvpView

        //Presenter는 View를 조작할 때 인자로 넘어온 this를 이용한다.
        // 이렇게 액티비티가 아닌 인터페이스를 전달하는 이유는 자료형을 교환하기 위해서다.
        // 만약 테스트를 할 시 해당 인터페이스를 테스트용 구현으로 교환이 가능하다

        //하지만 MVP 설계에서는 ~Presenter.selectLanguage() 메서드 호출을 통해 프레젠터에게 뷰 이벤트 발생을 알리고 이에 필요한 작업을 프레젠터에게 위임한다.
        // 즉, 이전 포스트에 있던 loadRepositories() 작업을 뷰(Activity)에서 직접 하는 게 아니라 프레젠터가 하게 되고 프레젠터가 직접 모델에 접근해서 RecyclerView의 목록을 가져온다.
        // 또한 loadRepositories 메서드에서는 프로그래스 바 보이기/감추기, 스택바 보이기 같은 UI를 변경하는 메서드들이 있었다.
        // 이런 사소한 UI 변경 작업도 뷰가 하지 않고 프레젠터에 위임한 것이다.
        //http://programmingfbf7290.tistory.com/
    }

    
    @Override
    public void destroy() {

    }

    @Override
    public void loadItemList() {
        List<ItemObjects> itemList = ItemManager.getItemData();   // 1. Model
        if (null != itemList && itemList.isEmpty()) {
            Log.i("JYN", "[MainMvpPresenterImpl][loadItemList] showEmtpyView : " + itemList);

            view.showEmtpyView();
        } else {
            // 2. AdapterModel
            Log.i("JYN", "[MainMvpPresenterImpl][loadItemList] addItems : " + itemList);
            adapterModel.addItems(itemList);
            // 3. AdapterView
            adapterView.refreshItemList();
            // 4. Veiw
            view.onUpdateItemList(itemList); // --> 이후 onUpdateItemList 에서는 rcAdapter.addItems(itemList);를 해주게 됨.

        }
    }

    @Override
    public void addItem(String title) {
        ItemObjects itemObjects = ItemManager.createItem(title);
        adapterModel.add(itemObjects);  //이런식으로 adapter의 dataModel에게 addItem을 해주고
        view.onCreatedItem(itemObjects); // view 에도 마찬가지로 addItem 동작을 해준다.
    }

    //https://github.com/ZeroBrain/Android-MVP-AdapterRoleSample/blob/master/app/src/main/java/com/nobrain/samples/adapterrolesample/main/presenter/MainPresenterImpl.java
    //위 예제 코드에서 처럼
    //Presenter에서 무언가 동작을 하면
    // 1) adatper Data Model 쪽으로 메서드 호출
    // 2) view쪽으로 메서드 호출
    //    ->view(MainActivity)에서는 adpater view 쪽으로 메서드 호출
    /*
    @Override
    public void removePhoto(int position) {
        photoDataModel.remove(position);
        view.refresh();
    }
    */
    
    @Override
    public void createSamples() {
        ItemManager.createSampleItems();
    }

    @Override
    public void loadBucketItemList(Context context) {
        List<BucketItemObject> bItemList = BucketData.getInstance().loadBucketItemList(context);
        if (null != bItemList && bItemList.isEmpty()) {
            view.showEmtpyBucketView(); // --> showEmtpyBucketView() 만들어야 되...
        } else {
            view.onUpdateBucketItemList(bItemList); // --> 이후 onUpdateBucketItemList 에서는 adapter.addItems(itemList);를 해주게 됨.
        }
    }

    @Override
    public void loadImagesItemListInBucket(Context context, String findDirName) {
        List<BucketItemObject> bItemList = BucketData.getInstance().findAllImagesInDir(context, findDirName);
        if (null != bItemList && bItemList.isEmpty()) {
            view.showEmtpyBucketView(); // --> showEmtpyBucketView() 만들어야 되...
        } else {
            view.onUpdateBucketItemList(bItemList); // --> 이후 onUpdateBucketItemList 에서는 adapter.addItems(itemList);를 해주게 됨.
        }
    }

    @Override
    public void switchAdapter(RecyclerView.Adapter<ViewHolder> recyclerViewAdapter) {
        //사용자가 view종류를 바꿀 때마다,
        //MainActivity쪽에서는 이 switchAdapter()를 호출하여
        //Presenter쪽으로 Adpater의 종류가 바뀌어야 함을 알려주고
        //Presenter쪽에서는 관련된 동작을 수행해 주고, DataModel의 종류또한 Adapter와 맞게 바꿔준다.
    }

    @Override
    public void setImageAdapterModel(AdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setImageAdapterView(AdapterContract.View adapterView) {
        this.adapterView = adapterView;
    }

    //Q. 근데 왜 MainMvpPresenter와 MainMvpPresenterImpl을 나눴을까?
}
