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
import gira.cdap.com.giira.one_place;

/**
 * Created by Perceptor on 8/9/2016.
 */
public class categoryResultsRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<category> feedItemList;
    private Context mContext;

    String placeID,placeName;

    public categoryResultsRecyclerAdapter(Context context, List<category> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_recycler_category_results, null);

        FeedListRowHolder viewHolder = new FeedListRowHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeedListRowHolder customViewHolder, int i) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedListRowHolder holder = (FeedListRowHolder) view.getTag();
                int position = holder.getPosition();

                category feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getCategoryname(), Toast.LENGTH_SHORT).show();

                placeID=feedItem.getCategoryID().toString();
                placeName=feedItem.getCategoryname().toString();




                Intent intent;
                intent = new Intent(mContext,one_place.class);
                intent.putExtra("id", placeID);
                intent.putExtra("name", placeName);

                mContext.startActivity(intent);

            }
        };

        customViewHolder.title.setOnClickListener(clickListener);
        customViewHolder.thumbnail.setOnClickListener(clickListener);

        customViewHolder.title.setTag(customViewHolder);
        customViewHolder.thumbnail.setTag(customViewHolder);

        category feedItem = feedItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(feedItem.getCategoryimage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.thumbnail);

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(feedItem.getCategoryname()));

//        AccID=Html.fromHtml(feedItem.getAccid()).toString();



    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

}
