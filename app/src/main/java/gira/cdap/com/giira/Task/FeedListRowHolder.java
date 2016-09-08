package gira.cdap.com.giira.Task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;

import gira.cdap.com.giira.R;

/**
 * Created by Perceptor on 8/1/2016.
 */
public class FeedListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;

    protected ImageView userimage;
    protected TextView title,comment,date;
    protected CheckBox checkbox;
    protected RatingView ratingView;

    public FeedListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.userimage = (ImageView) view.findViewById(R.id.userthumb);
        this.checkbox=(CheckBox) view.findViewById(R.id.checkbox);
        this.title = (TextView) view.findViewById(R.id.title);
        this.ratingView = (RatingView) view.findViewById(R.id.star);
        this.comment = (TextView) view.findViewById(R.id.comment_review);
        this.date = (TextView) view.findViewById(R.id.commentDate);
    }

}