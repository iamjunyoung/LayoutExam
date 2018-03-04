package inducesmile.com.androidstaggeredgridlayoutmanager.orig.ui.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;

public class BucketItemViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CardView cardView;
    public TextView bucketName;
    public ImageView countryPhoto;

    public BucketItemViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ///
        cardView = (CardView)itemView.findViewById(R.id.card_view);
        ///
        bucketName = (TextView) itemView.findViewById(R.id.country_name); //bucket_name
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo); //bucket_photo??
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }


}
