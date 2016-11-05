package gira.cdap.com.giira.Task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import gira.cdap.com.giira.R;
import gira.cdap.com.giira.Search_results_filter;


public class checkinPlaceRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<places> feedItemList;
    private Context mContext;

    String placeID,placeName,placeAddress;

//    Search_results_filter actionbar;

    public checkinPlaceRecyclerAdapter(Context context, List<places> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, null);

        FeedListRowHolder viewHolder = new FeedListRowHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FeedListRowHolder customViewHolder, int i) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedListRowHolder holder = (FeedListRowHolder) view.getTag();
                int position = holder.getPosition();

                places feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getPlaceID(), Toast.LENGTH_SHORT).show();

                placeID=feedItem.getPlaceID().toString();
                placeName=feedItem.getName().toString();

                Intent intent;
                intent = new Intent(mContext,Search_results_filter.class);

                intent.putExtra("id", placeID);
                intent.putExtra("regionType", placeName);

                mContext.startActivity(intent);

//                actionbar = new Search_results_filter();
//                actionbar.SetActionbarText(categoryname);


            }
        };



        customViewHolder.title.setOnClickListener(clickListener);
        customViewHolder.thumbnail.setOnClickListener(clickListener);
        // customViewHolder.checkbox.setOnClickListener(checkboxListener);

        customViewHolder.title.setTag(customViewHolder);
        customViewHolder.thumbnail.setTag(customViewHolder);
        //  customViewHolder.checkbox.setTag(R.drawable.redshape);

        places feedItem = feedItemList.get(i);

        Picasso.with(mContext).load(feedItem.getPlaceimage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.thumbnail);


        customViewHolder.title.setText(Html.fromHtml(feedItem.getPlaceID()));

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}