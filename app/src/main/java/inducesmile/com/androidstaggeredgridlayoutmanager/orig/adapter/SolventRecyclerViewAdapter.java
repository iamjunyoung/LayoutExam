package inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.ItemTouchHelperListener;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;


//class와 public class의 차이?
//class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders>
public class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<ViewHolder>
        implements AdapterContract.Model, AdapterContract.View, ItemTouchHelperListener {
    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_FIRST = 1;

    private List<ItemObjects> itemList;
    //아래처럼 바꾸는게 맞는가?
    //private List itemList;

    private Context context;
    private String TAG = "Solvent";

    public SolventRecyclerViewAdapter(Context context, List<ItemObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    // Added by JY
    // 왜 TodoListener 라는 형태로 만들었을까? (예제앱은)
    public SolventRecyclerViewAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }

    // 아래처럼 하지말고.. 왜냐면 아래처럼 쓰는 건 getItemViewType()을 통해 구분해서 view를 보여주는 방식과 좀 어긋나는 것 같기 때문이다.
    //item각각의 type, itemViewtype()으로 보여주는 방식을 결정하는 것인데
    //아래는 MainActivity에 있는 전역적인 변수로 구분하여 보여주는 방식을 결정하는 것이기 때문이다.
    //중요한 포인트는,
    //내가 하고있는 grid <-> list <-> staggered 변경시에 Adapter도 별개로 써야 하느냐 마느냐는 것이다.
    //현재 아래와 같이 구현한 이유는 view종류 변경시 Adapter를 동일한 하나를 사용하고 있기 때문에
    //SolventRecyclerViewAdapter.java 내부에서 아래와 같이 분기를 만들 수 밖에 없다...
    //그렇다면 각각의 adapter를 따로 만들면 어떨까?
    // + item에 type을 설명하는 변수를 추가하여 getItemViewType을 통해 itemType별로 view를 보여줄 수 있게 한다.

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        int resId = ViewHolderFactory.getItemLayoutId(viewType);
        View view = LayoutInflater.from(context).inflate(resId, parent, false);

        if (viewType == VIEW_TYPE_NORMAL) {
            Log.i("JYN", "hi ");
            return new SolventViewHolders(view);
        } else {
            Log.i("JYN", "hi FirstItemViewHolders ");
            return new FirstItemViewHolders(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_FIRST;
            else return VIEW_TYPE_NORMAL;
        //return MainActivity.recyclerViewLayoutType;
        //return super.getItemViewType(position);

        //아래와 같은 방법이 어떨까??
        //return getItem(position).getType();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(context, itemList.get(position));

        if (holder instanceof SolventViewHolders) {
            if (((SolventViewHolders) holder).countryPhoto == null) {
                Log.i("JYN", "hi countryPhoto ");
            }
            /*
            Glide.with(context)
                    .load(itemList.get(position).getPhoto())
                   .into(((SolventViewHolders) holder).countryPhoto);
                   */
        }
    }

    private Bitmap resize(int image) {
        //Bitmap b = ((BitmapDrawable)image).getBitmap();
        //Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false);
        //return new BitmapDrawable(context.getResources(), bitmapResized);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        //출처: http://aroundck.tistory.com/59 [돼지왕 왕돼지 놀이터]
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), image, options);
        return bm;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    /////////////////////////////
    //////// For MVP
    /////////////////////////////
    @Override
    public void add(ItemObjects itemObjects) {

    }

    @Override
    public ItemObjects remove(int position) {
        ItemObjects io = itemList.get(position);
        itemList.remove(position);
        return io;
    }

    @Override
    public ItemObjects getPhoto(int position) {
        return null;
    }

    @Override
    public void addItems(List<ItemObjects> list) {
        Log.i("JYN", "[SolventRecyclerViewAdapter][addItems]. list : " + list);
        itemList.addAll(list);
        //notifyDataSetChanged();
    }

    @Override
    public int getSize() {
        return 0;
    }
    //////////////////////////////////////////////////
    @Override
    public void refreshItemList() {
        Log.i("JYN", "[SolventRecyclerViewAdapter][refreshItemList]" );
        notifyDataSetChanged();
    }

    @Override
    public void refreshItemAdded(int position) {

    }

    @Override
    public void refreshItemRemoved(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public void refreshItemChanged(int position) {

    }
    //////////////////////////////////////

    void update(int itemId) {
        int position = -1;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemId == itemList.get(i).getId()) {
                position = i;
                break;
            }
        }
        notifyItemChanged(position);
    }


    void removeItem(ItemObjects itemObjects) {
        itemList.remove(itemObjects);
        //remove를 구현하려면 equals()를 오버라이드 해야 한다.
        notifyItemRemoved(itemList.indexOf(itemObjects));
    }

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }

/////////

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //Log.i(TAG, "onItemMove.", new Throwable());
        Log.i(TAG, "onItemMove. fromPosition : "+fromPosition+", toPosition : "+toPosition + "    this obj : " + this);
        Log.i(TAG, "onItemMove. fromItem : "+itemList.get(fromPosition).getName() + ", toItem : "+itemList.get(toPosition).getName());

        if(fromPosition < 0 || fromPosition >= itemList.size() || toPosition < 0 || toPosition >= itemList.size()){
            Log.i(TAG, "onItemMove. return false case    from : " + fromPosition + ",   toPosition : " + toPosition);
            return false;
        }

        ItemObjects fromItem = itemList.get(fromPosition);


        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        //출처: http://fullstatck.tistory.com/15 [풀스택 엔지니어]



        /*
        Collections.swap(itemList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        */

        /*
        for(int i = 0; i < itemList.size(); i++){
            Log.i(TAG, "onItemMove : "+itemList.get(i).getName());
        }*/
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        Log.i(TAG, "onItemRemove. pos : " + position + "    item : "+itemList.get(position).getName());
        //아래 두 line도 MVP고려해야되는가?
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemSwipe(int position) {
        Log.i("JYN", "onItemSwipe. item : "+itemList.get(position).getName());

    }
}

class ViewHolderFactory {
    public static int getItemLayoutId(int type) {
        if (type == SolventRecyclerViewAdapter.VIEW_TYPE_FIRST) return R.layout.item_staggered_first;
        else return R.layout.item_staggered;
    }
    /*
    public static ViewHolder getViewHolder(int type, View itemView) {
        if (type == VIEW_TYPE_FIRST) return new FirstItemViewHolders(itemView);
        else return new SolventViewHolders(itemView);
    }
    */
}
