package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.ItemTouchHelperListener;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

import static inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main.SolventRecyclerViewAdapter.VIEW_TYPE_FIRST;


//class와 public class의 차이?
class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders>
        implements ItemTouchHelperListener {
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
        return ViewHolderFactory.getViewHolder(viewType, view);

        /*
        //SolventViewHolders rcv;
        if (MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_STAGGERED) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered, null);
        } else if (MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_LIST) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        } else if (MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_GRID) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, null);
        }

        else if (MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_IMAGE_LIST) { // list
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_for_image, null);
        } else if (MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_IMAGE_GRID_2) { // griad x2
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_for_image, null);
            //layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_for_image, null);
        } else { //(MainActivity.recyclerViewLayoutType == MainActivity.LAYOUT_TYPE_IMAGE_GRID_3) { // grid x3
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_for_image, null);
            //layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_for_image, null);
        }*/

        //return new SolventViewHolders(layoutView);
    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.recyclerViewLayoutType;
        //return super.getItemViewType(position);

        //아래와 같은 방법이 어떨까??
        //return getItem(position).getType();
    }


    @Override
    public void onBindViewHolder(final SolventViewHolders holder, int position) {
        //Log.i(TAG, "onBindViewHolder. position : " + position + "    title : " + itemList.get(position).getName());


        holder.countryName.setText(itemList.get(position).getName());


        // Replace this to Glide
        //holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        //holder.countryPhoto.setImageBitmap(resize(itemList.get(position).getPhoto()));

        /*
        if (itemList.get(position).getHeight() != -1) {
            ViewGroup.LayoutParams layoutParams = holder.countryPhoto.getLayoutParams();
            layoutParams.height = itemList.get(position).getHeight();
            holder.countryPhoto.setLayoutParams(layoutParams);
        }*/

        Glide.with(context)
                .load(itemList.get(position).getPhoto())
                .into(holder.countryPhoto);


        /*
        if (itemList.get(position).getHeight() == -1) {
            itemList.get(position).setHeight(holder.countryPhoto.getHeight());
        }*/


        Log.i(TAG, "onBindViewHolder pos : " + position + "    title : " + itemList.get(position).getName()
                + "    height : " + holder.countryPhoto.getHeight() + "    tag : " + holder.countryPhoto.getTag());

        //출처: http://itpangpang.xyz/243 [ITPangPang]

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //int holderPosition = ((SolventViewHolders) v.getTag()).getAdapterPosition();
                int holderPosition = holder.getAdapterPosition();
                Log.i("JYN", "Category : " + itemList.get(holderPosition).getName() + " 's itemView is long clicked");
                Toast.makeText(context, "Long clicked item = " + itemList.get(holderPosition).getName(), Toast.LENGTH_SHORT).show();


                //우명인 예제 코드와 같이
                //onLongClieded, onClicked 이벤트 발생시에
                //이 부분에서 ItemListener (인터페이스)의 api를 호출하도록 하자
                //결과적으로 MainActivity와의 comm이 이 방식으로 이루어 진다.
                //괜찮은 방법 같다.

                /*
                // JYN for multi select
                if (!((MainActivity) context).getLongTouched()) { //long touch상태가 아닌 경우(default)
                    Log.i("JYN", "set isMultiSelect to true");
                    ((MainActivity) context).setLongTouched(true);
                    //longTouchSelectedItems.add(visibleItems.get(holderPosition).getTitle());
                } else {                                          //long touch상태인 경우
                    //Log.i("JYN", "set isMultiSelect to false");
                    //((MainActivity)mContext).setLongTouched(false);
                    //longTouchSelectedItems.remove(visibleItems.get(holderPosition).getTitle());
                }
                */

                //longTouchSelectedItems.add(visibleItems.get(holderPosition).getTitle());
                //notifyItemChanged(holderPosition);

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int holderPosition = ((SolventViewHolders) v.getTag()).getAdapterPosition();
                int holderPosition = holder.getAdapterPosition();
                Toast.makeText(context, "Clicked item = " + itemList.get(holderPosition).getName(), Toast.LENGTH_SHORT).show();
            }
        });

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

    /////////
    // For MVP
    ////////

    //public void addItems(List<ItemObjects> list) {
    public void addItems(List list) {
        itemList.addAll(list);
        notifyDataSetChanged();
    }

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

    void addItem(ItemObjects itemObjects) {
        itemList.add(itemObjects);
        notifyItemInserted(itemList.size() - 1);
    }

    void removeItem(ItemObjects itemObjects) {
        itemList.remove(itemObjects);
        //remove를 구현하려면 equals()를 오버라이드 해야 한다.
        notifyItemRemoved(itemList.indexOf(itemObjects));
    }

    void clear() {
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

        /*
        if (fromPosition == 0 && ( toPosition == 1 | toPosition == 2 | toPosition ==3)) {
            temp = 0;
        }
        if (( fromPosition == 1 | fromPosition == 2 | fromPosition ==3) && temp == 0) {
            temp = -1;
            Log.i(TAG, "onItemMove. hihi case");
            return false;
        }
        */
        /*
        if (fromPosition == 0) {
            Log.i(TAG, "onItemMove. hihi case");
            toPosition = 1;
            ItemObjects fromItem = itemList.get(0);

            itemList.remove(fromPosition);
            itemList.add(toPosition, fromItem);

            notifyItemMoved(fromPosition, toPosition);
        }*/

        ItemObjects fromItem = itemList.get(fromPosition);

        /*
        itemList.remove(fromPosition);
        itemList.add(toPosition, fromItem);

        notifyItemMoved(fromPosition, toPosition);
        */


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

        itemList.remove(position);
        notifyItemRemoved(position);

        //과연 아래 방법 말고는 없는가..
        //notifyDataSetChanged();

    }

    @Override
    public void onItemSwipe(int position) {
        Log.i("JYN", "onItemSwipe. item : "+itemList.get(position).getName());

    }
}

class ViewHolderFactory {
    public static int getItemLayoutId(int type) {
        int resId;
        if (type == VIEW_TYPE_FIRST) { resId = R.layout.item_staggered_first; }
        else { resId = R.layout.item_staggered; }
        return resId;
    }
    public static ViewHolder getViewHolder(int type, View itemView) {
        if (type == VIEW_TYPE_FIRST) {
            return new FirstItemViewHolders(itemView);
        } else {
            return new SolventViewHolders(itemView);
        }
    }
}
