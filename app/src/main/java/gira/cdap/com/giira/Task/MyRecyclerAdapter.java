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

import gira.cdap.com.giira.One_Accomadation;
import gira.cdap.com.giira.R;

/**
 * Created by Perceptor on 8/1/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<Accommodations> feedItemList;
    private Context mContext;

    String AccID,AccName;

    public MyRecyclerAdapter(Context context, List<Accommodations> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_recycler, null);

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

                Accommodations feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getAccname(), Toast.LENGTH_SHORT).show();

                AccID=feedItem.getAccid().toString();
                AccName=feedItem.getAccname().toString();



                Intent intent;
                intent = new Intent(mContext,One_Accomadation.class);
                intent.putExtra("id", AccID);
                intent.putExtra("name", AccName);

                mContext.startActivity(intent);

            }
        };


        customViewHolder.title.setOnClickListener(clickListener);
        customViewHolder.thumbnail.setOnClickListener(clickListener);


        customViewHolder.title.setTag(customViewHolder);
        customViewHolder.thumbnail.setTag(customViewHolder);

        Accommodations feedItem = feedItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(feedItem.getAccimage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.thumbnail);

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(feedItem.getAccname()));

//        AccID=Html.fromHtml(feedItem.getAccid()).toString();



    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
