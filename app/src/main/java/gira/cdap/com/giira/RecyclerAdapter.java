package gira.cdap.com.giira;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles = {"Sigiriya",
            "Temple of the Tooth",
            "Horton Plains National Park",
            "Yala National Park",
            "Udawalawe National Park",
            "Royal Botanical Gardens",
            "Adam's Peak",
            "Minneriya National Park"};

    private String[] details = {"Sigiriya,Matale District,Central Province, Sri Lanka.",
            "20000, Sri Dalada Veediya, Kandy 20000", "Central province, Sri Lanka",
            "B499,Yala National Park Information Centre,Hambantota district", "7th Mile Post,Sevanagala,Monaragala,Uva Province",
            "A1, Peradeniya 20400", "Sri Pada,Sri Lanka",
            "National park,Polonnaruwa District"};

    /*private int[] images = { R.drawable.android_image_1,
            R.drawable.android_image_2,
            R.drawable.android_image_3,
            R.drawable.android_image_4,
            R.drawable.android_image_5,
            R.drawable.android_image_6,
            R.drawable.android_image_7,
            R.drawable.android_image_8 };*/

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.thumbnail);
            itemTitle = (TextView)itemView.findViewById(R.id.title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Succesfully Checkin!" + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();


                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
//        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
