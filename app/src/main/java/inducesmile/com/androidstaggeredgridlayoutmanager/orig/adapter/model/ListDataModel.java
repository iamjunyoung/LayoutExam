package inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter.model;

import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

/**
 * Created by user on 2018-03-08.
 */

public interface ListDataModel extends DataModel{
    void add(ItemObjects itemObjects);
    ItemObjects remove(int position);
    ItemObjects getPhoto(int position);
    void addItems(List<ItemObjects> list);
    int getSize();
}
