package inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter;

import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

/**
 * Created by junyoung on 2018. 3. 13..
 */

public interface AdapterContract{
    interface Model {
        void add(ItemObjects itemObjects);
        ItemObjects remove(int position);
        ItemObjects getPhoto(int position);
        void addItems(List<ItemObjects> list);
        int getSize();
    }

    interface View {
        void refreshItemList();
        void refreshItemAdded(int position);
        void refreshItemRemoved(int postion);
        void refreshItemChanged(int position);
    }


}
