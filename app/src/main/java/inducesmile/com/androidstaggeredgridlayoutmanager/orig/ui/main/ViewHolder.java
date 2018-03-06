package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

/**
 * Created by junyoung on 2018. 3. 3..
 */

public abstract class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) { super(itemView); }
    abstract void bind(Context context, ItemObjects item);
}
