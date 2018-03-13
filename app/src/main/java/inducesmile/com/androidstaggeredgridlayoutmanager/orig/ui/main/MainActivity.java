package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.ItemLayoutManger;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.ItemTouchHelperCallback;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.SolventRecyclerViewAdapter;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.GridRecyclerViewAdapter;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.ListRecyclerViewAdapter;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.common.BaseActivity;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.BucketItemObject;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

import static inducesmile.com.androidstaggeredgridlayoutmanager.orig.R.id.viewType;


public class MainActivity extends BaseActivity implements MainMvpView, ItemListener {

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    Toolbar toolbar;

    private View emptyView;

    RecyclerView recyclerView;
    private SolventRecyclerViewAdapter rcAdapter;
    // adapter 추가
    private ListRecyclerViewAdapter lrAdapter;
    private GridRecyclerViewAdapter grAdapter;

    //Bucket용 adapter추가
    private BucketItemRecyclerViewAdapter bucketAdapter;

    public static int LAYOUT_TYPE_LIST = 0;
    public static int LAYOUT_TYPE_GRID = 1;
    public static int LAYOUT_TYPE_STAGGERED = 2;
    public static int LAYOUT_TYPE_IMAGE_LIST = 3;
    public static int LAYOUT_TYPE_IMAGE_GRID_2 = 4;
    public static int LAYOUT_TYPE_IMAGE_GRID_3 = 5;

    public static int recyclerViewLayoutType = LAYOUT_TYPE_STAGGERED; // default

    String viewItemType = "test";

    // For MVP
    public static MainMvpPresenter<MainActivity> presenter;
    //임시로 static으로 해 둔다. 이후 Adapter쪽 수정 시 다시 되돌릴 예정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_grid);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        emptyView = findViewById(R.id.tv_activity_main_empty);


        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        gaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        gaggeredGridLayoutManager.invalidateSpanAssignments();
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //List<ItemObjects> gaggeredList = getListItemData();
        rcAdapter = new SolventRecyclerViewAdapter(this);
        recyclerView.setAdapter(rcAdapter);

        //
        bucketAdapter = new BucketItemRecyclerViewAdapter(this);

        presenter.setImageAdapterModel(rcAdapter);
        presenter.setImageAdapterView(rcAdapter);
        presenter.createSamples();
        presenter.loadItemList();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback(this, rcAdapter));
        helper.attachToRecyclerView(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater inflater = getMenuInflater();

        int color;
        int scrollFlg;
        int menuRes;

        Toolbar collapsingToolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();

        /*
        if(!longTouched) {
        */
            color = Color.parseColor("#0000FF");
            scrollFlg = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS|AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP; // list other flags here by |
            menuRes = R.menu.menu_main;
        /*
        } else {
            color = Color.parseColor("#FF00FF");
            scrollFlg = 0;
            menuRes = R.menu.menu_long_touched;
        }
        */

        params.setScrollFlags(scrollFlg);
        collapsingToolbar.setLayoutParams(params);

        toolbar.setBackgroundColor(color);
        inflater.inflate(menuRes, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "action settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_test) {
            Toast.makeText(this, "action test", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.changeItems) {
            if (viewItemType == "test") {
                viewItemType = "images";

            } else {
                viewItemType = "test";
            }
            Toast.makeText(this, "change items", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.viewTypeForImage) {
            int iconInToolbar = 0;
            String viewType = "";
            RecyclerView.LayoutManager layoutManager = null;

            /////
            recyclerView.setAdapter(bucketAdapter);
            presenter.loadBucketItemList(this);
            // TODO) loadBucketItemList 하는 부분을 앱 실행시 thread로 빼서 수행하도록 수정.
            //       해당 bucket의 최상단 이미지 thumbnail을 어떻게 가져오는게 좋을까??
            //////

            //아래는 UI를 바꿔주기 위해 LayoutManager를 변경해주고 icon을 바꿔주는 로직
            //아직 ViewTypeForImage 중 아무것도 적용되어 있지 않다면
            // default로 LAYOUT_TYPE_IMAGE_LIST 로 set
            if (recyclerViewLayoutType == LAYOUT_TYPE_IMAGE_GRID_3) {
                recyclerViewLayoutType = LAYOUT_TYPE_IMAGE_LIST;
                layoutManager = new ItemLayoutManger(this);
                iconInToolbar = R.drawable.icon_list_32;
                viewType = "... to List for Image";
            } else if (recyclerViewLayoutType == LAYOUT_TYPE_IMAGE_LIST) {
                // List -> Grid x2
                recyclerViewLayoutType = LAYOUT_TYPE_IMAGE_GRID_2;
                layoutManager = new GridLayoutManager(this, 2);
                iconInToolbar = R.drawable.icon_grid_2x2_32;
                viewType = "From List for Image to Grid(x2) for Image";
            } else if (recyclerViewLayoutType == LAYOUT_TYPE_IMAGE_GRID_2) {
                // Grid x2 -> Grid x3
                recyclerViewLayoutType = LAYOUT_TYPE_IMAGE_GRID_3;
                layoutManager = new GridLayoutManager(this, 3);
                iconInToolbar = R.drawable.icon_grid_3x3_32;
                viewType = "From Grid(x2) for Image to Grid(x3) for Image";
            } else { //Image view type이 아니었다면,
                // default로 list로 해줘
                //if (recyclerViewLayoutType == LAYOUT_TYPE_STAGGERED || recyclerViewLayoutType == LAYOUT_TYPE_LIST || recyclerViewLayoutType == LAYOUT_TYPE_GRID
                recyclerViewLayoutType = LAYOUT_TYPE_IMAGE_LIST;
                layoutManager = new ItemLayoutManger(this);
                iconInToolbar = R.drawable.icon_list_32;
                viewType = "[else]... to List for Image";
            }
            item.setIcon(iconInToolbar);
            rcAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(layoutManager);
            Toast.makeText(this, "ViewTypeForImage : " + viewType, Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == viewType) {
            int iconInToolbar = 0;
            String viewType = "";
            RecyclerView.LayoutManager layoutManager = null;

            if (recyclerViewLayoutType == LAYOUT_TYPE_STAGGERED) {
                recyclerViewLayoutType = LAYOUT_TYPE_LIST;

                //List adpater필요
                lrAdapter = new ListRecyclerViewAdapter(this);
                recyclerView.setAdapter(lrAdapter);

                layoutManager = new ItemLayoutManger(this);
                iconInToolbar = R.drawable.ic_list_24dp;
                viewType = "From Staggered to List";
            } else if (recyclerViewLayoutType == LAYOUT_TYPE_LIST) {
                // List -> Grid
                recyclerViewLayoutType = LAYOUT_TYPE_GRID;

                //Grid adpater필요
                grAdapter = new GridRecyclerViewAdapter(this);
                //Presenter에게 Adapter의 종류가 바뀌었음을 알려줘야 하지 않을까?
                //즉. Presenter.java쪽에 switchAdapter() 등의 메서드가 추가되어야 하지 않을까??
                recyclerView.setAdapter(grAdapter);

                layoutManager = new GridLayoutManager(this, 2);
                iconInToolbar = R.drawable.ic_grid_24dp;
                viewType = "From List to Grid";
            } else if (recyclerViewLayoutType == LAYOUT_TYPE_GRID) {
                // Grid -> Staggered
                recyclerViewLayoutType = LAYOUT_TYPE_STAGGERED;

                //Solvent adpater필요
                rcAdapter = new SolventRecyclerViewAdapter(this);
                recyclerView.setAdapter(rcAdapter);

                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                layoutManager = staggeredGridLayoutManager;
                iconInToolbar = R.drawable.ic_staggered_24dp;
                viewType = "From Grid to Staggered";
            } else {
                recyclerViewLayoutType = LAYOUT_TYPE_STAGGERED;

                //Solvent adpater필요
                rcAdapter = new SolventRecyclerViewAdapter(this);
                recyclerView.setAdapter(rcAdapter);

                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                layoutManager = staggeredGridLayoutManager;
                iconInToolbar = R.drawable.ic_staggered_24dp;
                viewType = "[else] ... to Staggered";
            }
            item.setIcon(iconInToolbar);

            presenter.createSamples();
            presenter.loadItemList();

            //rcAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(layoutManager);
            Toast.makeText(this, "ViewType : " + viewType, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void inject() {

    }

    //Q. initPresenter()는 누가 호출해주냐?
    // BaseActivity의 onCreate() 에서 호출해준다.
    @Override
    public void initPresenter(BaseActivity baseActivity) {
        // 1. Activity/Fragment/View에서 필요한 Presenter을 직접 생성
        // 2. setView을 전달한다
        presenter = new MainMvpPresenterImpl<>(); //realm 작업을 해줘야 할듯..구찮다
        presenter.attachView(this);
        //attachView()가 setView()인듯.

    }

    @Override
    public void onUpdateItemList(List<ItemObjects> itemList) {
        String viewType = null;
        if (recyclerViewLayoutType == LAYOUT_TYPE_STAGGERED) {
            rcAdapter.clear();
            rcAdapter.addItems(itemList);
            viewType = "From Staggered to List";
        } else if (recyclerViewLayoutType == LAYOUT_TYPE_LIST) {
            lrAdapter.clear();
            lrAdapter.addItems(itemList);
            viewType = "From List to Grid";
        } else if (recyclerViewLayoutType == LAYOUT_TYPE_GRID) {
            grAdapter.clear();
            grAdapter.addItems(itemList);
            viewType = "From Grid to Staggered";
        } else {
            rcAdapter.clear();
            rcAdapter.addItems(itemList);
            viewType = "[else] ... to Staggered";
        }

        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onCreatedItem(ItemObjects itemObjects) {
        rcAdapter.add(itemObjects);
    }

    @Override
    public void showEmtpyView() {
        rcAdapter.clear();
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessCreateSamples() {

    }

    @Override
    public void onUpdateBucketItemList(List<BucketItemObject> bItemList) {
        bucketAdapter.clear();;
        bucketAdapter.addItems(bItemList);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmtpyBucketView() {
        bucketAdapter.clear();
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTouched(int id) {

    }

    @Override
    public void onLongTouched(int id) {

    }
}
