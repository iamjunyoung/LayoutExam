package inducesmile.com.androidstaggeredgridlayoutmanager.orig.singletons;

import java.util.ArrayList;
import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

/**
 * Created by junyoung on 2017. 10. 6..
 */

public class ImageItemManager {

    //DB라 생각하자.
    private static List<ItemObjects> listViewItems;
    private static int maxId = -1;

    public static ItemObjects load(int id) {
        for (ItemObjects i : listViewItems) {
            if (id == i.getId()) return i;
        }
        return null;
    }

    public static void createImageItems() {
        listViewItems = new ArrayList<>();
        //listViewItems.add(new ItemObjects(0, "Alkane", R.drawable.one));
        listViewItems.add(new ItemObjects(0, "+", R.drawable.one));
        listViewItems.add(new ItemObjects(1, "Ethane", R.drawable.two));
        listViewItems.add(new ItemObjects(2, "Alkyne", R.drawable.three));
        listViewItems.add(new ItemObjects(3, "Benzene", R.drawable.four));
        listViewItems.add(new ItemObjects(4, "Amide", R.drawable.one));
        listViewItems.add(new ItemObjects(5, "Amino Acid", R.drawable.two));
        listViewItems.add(new ItemObjects(6, "Phenol", R.drawable.three));
        listViewItems.add(new ItemObjects(7, "Carbonxylic", R.drawable.four));
        listViewItems.add(new ItemObjects(8, "Nitril", R.drawable.one));
        listViewItems.add(new ItemObjects(9, "Ether", R.drawable.two));
        listViewItems.add(new ItemObjects(10, "Ester", R.drawable.three));
        listViewItems.add(new ItemObjects(11, "Alcohol", R.drawable.four));

        listViewItems.add(new ItemObjects(12, "delete", R.drawable.ic_delete_black_24dp));
        listViewItems.add(new ItemObjects(13, "finger", R.drawable.ic_fingerprint_black_24dp));
        listViewItems.add(new ItemObjects(14, "grade", R.drawable.ic_grade_black_24dp));
        listViewItems.add(new ItemObjects(15, "edit", R.drawable.ic_mode_edit_black_24dp));
        listViewItems.add(new ItemObjects(16, "share", R.drawable.ic_share_black_24dp));
        listViewItems.add(new ItemObjects(17, "black", R.drawable.ic_label_black_24dp));

        listViewItems.add(new ItemObjects(18, "초아", R.drawable.choa));
        listViewItems.add(new ItemObjects(19, "제니", R.drawable.jenni));
        listViewItems.add(new ItemObjects(20, "제니2", R.drawable.jenni2));
        listViewItems.add(new ItemObjects(21, "제니3", R.drawable.jenni3));
        listViewItems.add(new ItemObjects(22, "제니4", R.drawable.jenni4));
        listViewItems.add(new ItemObjects(23, "수지", R.drawable.suji));
        listViewItems.add(new ItemObjects(24, "수지2", R.drawable.suji2));

        listViewItems.add(new ItemObjects(25, "미나", R.drawable.mina));
        listViewItems.add(new ItemObjects(26, "미나2", R.drawable.mina2));
        listViewItems.add(new ItemObjects(27, "미나3", R.drawable.mina3));
        listViewItems.add(new ItemObjects(28, "미나4", R.drawable.mina4));

        listViewItems.add(new ItemObjects(29, "1", R.drawable.one));
        listViewItems.add(new ItemObjects(30, "2", R.drawable.two));
        listViewItems.add(new ItemObjects(31, "3", R.drawable.three));
        listViewItems.add(new ItemObjects(32, "4", R.drawable.four));
        listViewItems.add(new ItemObjects(33, "5", R.drawable.one));
        listViewItems.add(new ItemObjects(34, "6", R.drawable.two));
        listViewItems.add(new ItemObjects(35, "7", R.drawable.three));
        listViewItems.add(new ItemObjects(36, "8", R.drawable.four));
        listViewItems.add(new ItemObjects(37, "9", R.drawable.one));
        listViewItems.add(new ItemObjects(38, "10", R.drawable.two));
        listViewItems.add(new ItemObjects(39, "11", R.drawable.three));
        listViewItems.add(new ItemObjects(40, "12", R.drawable.four));

        maxId = 40;
    }

    public static List<ItemObjects> search(String text) {
        List<ItemObjects> findedItems = new ArrayList<>();
        for (ItemObjects i : listViewItems) {
            if (i.getName() != null && i.getName().contains(text)) {
                findedItems.add(i);
            }
        }
        return findedItems;
    }

    public static ItemObjects createItem(String title) {
        ItemObjects createdItem = new ItemObjects(++maxId, title, R.drawable.suji2);
        listViewItems.add(createdItem);
        return createdItem;
    }

    public static List<ItemObjects> getItemData(){
        return listViewItems;
    }
}
