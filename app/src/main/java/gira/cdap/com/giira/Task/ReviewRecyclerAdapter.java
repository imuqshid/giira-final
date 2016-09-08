package gira.cdap.com.giira.Task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import gira.cdap.com.giira.R;


public class ReviewRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<reviews> feedItemList;
    private Context mContext;

//    String AccID;

    public ReviewRecyclerAdapter(Context context, List<reviews> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_recycler_review, null);

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

                reviews feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getName(), Toast.LENGTH_SHORT).show();

//                AccID=feedItem.getAccid().toString();



//                Intent intent;
//                intent = new Intent(mContext,One_Accomadation.class);
//                intent.putExtra("id", AccID);
//
//                mContext.startActivity(intent);

            }
        };


//        customViewHolder.title.setOnClickListener(clickListener);
//        customViewHolder.thumbnail.setOnClickListener(clickListener);


        customViewHolder.title.setTag(customViewHolder);
        customViewHolder.userimage.setTag(customViewHolder);

        reviews feedItem = feedItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(feedItem.getUserimage())
                .error(R.drawable.defaultuser)
                .placeholder(R.drawable.defaultuser)
                .into(customViewHolder.userimage);

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(feedItem.getName()));
        customViewHolder.comment.setText(Html.fromHtml(feedItem.getComment()));
        customViewHolder.date.setText(Html.fromHtml(feedItem.getDate()));
        customViewHolder.ratingView.setRating((float) feedItem.getRating());


//        AccID=Html.fromHtml(feedItem.getAccid()).toString();



    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
