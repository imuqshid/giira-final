package gira.cdap.com.giira.Task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import gira.cdap.com.giira.R;

/**
 * Created by Perceptor on 7/31/2016.
 */
public class AccommodationAdapter extends ArrayAdapter<Accommodations> {
    ArrayList<Accommodations> accommodationList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public AccommodationAdapter(Context context, int resource, ArrayList<Accommodations> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        accommodationList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.ivImage = (ImageView) v.findViewById(R.id.ivaccimage);
            holder.tvName = (TextView) v.findViewById(R.id.tvaccname);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvaccdescription);
//            holder.tvAddress = (TextView) v.findViewById(R.id.tvaccaddress);
//            holder.tvWebsite = (TextView) v.findViewById(R.id.tvaccwebsite);
//            holder.tvPhoneNo = (TextView) v.findViewById(R.id.tvaccphoneno);
//            holder.tvEmail = (TextView) v.findViewById(R.id.tvaccemail);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.ivImage.setImageResource(R.drawable.placethumb);
        new DownloadImageTask(holder.ivImage).execute(accommodationList.get(position).getAccimage());
        holder.tvName.setText(accommodationList.get(position).getAccname());
        holder.tvDescription.setText(accommodationList.get(position).getAccdescription());
        holder.tvAddress.setText(accommodationList.get(position).getAccaddress());
//        holder.tvWebsite.setText(accommodationList.get(position).getAccwebsite());
//        holder.tvPhoneNo.setText(accommodationList.get(position).getAccphoneNo());
//        holder.tvEmail.setText(accommodationList.get(position).getAccEmail());

        return v;

    }

    static class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvAddress;
        public TextView tvWebsite;
        public TextView tvPhoneNo;
        public TextView tvEmail;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

