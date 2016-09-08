package gira.cdap.com.giira;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import gira.cdap.com.giira.Task.TaskGetCategory;
import gira.cdap.com.giira.Task.TaskGetRegion;
import gira.cdap.com.giira.Task.categoryRecycleAdapter;
import gira.cdap.com.giira.Task.regionRecyclerAdapter;
import gira.cdap.com.giira.Task.serverURL;

public class display_results extends AppCompatActivity implements AdapterView.OnItemClickListener {
    AutoCompleteTextView autoCompleteTextView;
    String placeid,placename;

    ArrayList categorylist,regionlist;

    private RecyclerView mRecyclerView,mRecyclerViewRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);

        actionBar.setCustomView(cView);
        TextView textView= (TextView) findViewById(R.id.ABtext);
        textView.setText("Search Places");

        ImageButton imageButton = (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        initalize();

        TaskGetCategory taskGetCategory=new TaskGetCategory(getApplicationContext(),display_results.this);
        taskGetCategory.execute();

        TaskGetRegion taskGetRegion=new TaskGetRegion(getApplicationContext(),display_results.this);
        taskGetRegion.execute();
    }



    public void initalize()
    {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompletesearch);
        autoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this,   R.layout.listitemall, R.id.textView1));
        autoCompleteTextView.setOnItemClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewRegion= (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerViewRegion.setLayoutManager(new LinearLayoutManager(this));
    }
    public ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        ArrayList resultgemorart=null;




        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            StringBuilder sb = new StringBuilder(serverURL.local_host_url+"giira/index.php/places/retriveplaces?place="+input);
//            sb.append("?=place" +input);



            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("place");

            String taglist;
            String[] tagarraylist;
            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("name"));
//                taglist=predsJsonArray.getJSONObject(i).getString("tag");
//                tagarraylist = (taglist.split(","));
//                System.out.println(tagarraylist);

                resultList.add(predsJsonArray.getJSONObject(i).getString("name"));
                resultList.add(predsJsonArray.getJSONObject(i).getString("tag"));
//                resultList.add(tagarraylist);
                placeid=predsJsonArray.getJSONObject(i).getString("id");
                placename=predsJsonArray.getJSONObject(i).getString("name");
//                Toast.makeText(this, placeid, Toast.LENGTH_SHORT).show();


//                resultgemorart.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
            }
        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        resultList.add("Add new Place");

        return resultList;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        String str = (String) adapterView.getItemAtPosition(position);
        if(str.equalsIgnoreCase("Add new Place"))
        {
            Intent intent=new Intent(this,request_place_by_user.class);

            startActivity(intent);
        }
        else
        {
            Intent intent=new Intent(this,one_place.class);
            intent.putExtra("id", placeid);
            intent.putExtra("name", placename);

            startActivity(intent);

        }

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }



    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

//        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, int textView1) {
//            super(context,textView1);
//        }

        public GooglePlacesAutocompleteAdapter(Context context, int resource,
                                   int textViewResourceId) {

            super(context, resource, textViewResourceId);
        }
        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();


                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


    public void setcategory(ArrayList category)
    {
        this.categorylist=category;

        categoryRecycleAdapter myRecyclerAdapter = new categoryRecycleAdapter(display_results.this, categorylist);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        mRecyclerView.setAdapter(myRecyclerAdapter);

    }

    public void setregion(ArrayList region)
    {
        this.regionlist=region;

        regionRecyclerAdapter myRecyclerAdapter = new regionRecyclerAdapter(display_results.this, regionlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerViewRegion.setLayoutManager(mLayoutManager);
        mRecyclerViewRegion.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewRegion.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerViewRegion.setAdapter(myRecyclerAdapter);

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




}
