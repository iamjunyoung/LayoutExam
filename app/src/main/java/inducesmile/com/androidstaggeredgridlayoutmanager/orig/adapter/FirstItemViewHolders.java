package inducesmile.com.androidstaggeredgridlayoutmanager.orig.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import inducesmile.com.androidstaggeredgridlayoutmanager.orig.R;
import inducesmile.com.androidstaggeredgridlayoutmanager.orig.datas.ItemObjects;

public class FirstItemViewHolders extends ViewHolder implements View.OnClickListener{
//public class FirstItemViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CardView cardView;
    public TextView countryName;
    public ImageView countryPhoto;

    public FirstItemViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ///
        cardView = (CardView)itemView.findViewById(R.id.card_view);
        ///
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
    }

    @Override
    void bind(Context context, ItemObjects item) {
        setFirstItem(context, item);
    }

    private void setFirstItem(final Context context, final ItemObjects item) {
        countryName.setText(item.getName());

//        Glide.with(context)
//                .load(item.getPhoto())
//                .into(countryPhoto);
        Log.i("SolventViewHolders", "onBindViewHolder " + item.getName());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int holderPosition = ((SolventViewHolders) v.getTag()).getAdapterPosition();
                Log.i("JYN", "Category : " + item.getName() + "    pos : " + holderPosition + " 's itemView is long clicked");
                Toast.makeText(context, "Long clicked item = " + item.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked item = " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }


}
