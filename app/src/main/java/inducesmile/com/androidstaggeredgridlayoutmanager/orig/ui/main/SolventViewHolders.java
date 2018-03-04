package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

//public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
public class SolventViewHolders extends ViewHolder implements View.OnClickListener{
    public CardView cardView;
    public TextView countryName;
    public ImageView countryPhoto;

    public SolventViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ///
        cardView = (CardView)itemView.findViewById(R.id.card_view);
        ///
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
    }

    @Override
    void bind(ItemObjects item) {
        setNormalItem();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }


}
