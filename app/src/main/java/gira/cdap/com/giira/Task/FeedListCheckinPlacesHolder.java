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
public class FeedListCheckinPlacesHolder extends RecyclerView.ViewHolder {


    protected ImageView userimage;
    protected TextView name,address;

    public FeedListCheckinPlacesHolder(View view) {
        super(view);

        this.userimage = (ImageView) view.findViewById(R.id.userthumb);

        this.name = (TextView) view.findViewById(R.id.title);
        this.address = (TextView) view.findViewById(R.id.address);

    }

}