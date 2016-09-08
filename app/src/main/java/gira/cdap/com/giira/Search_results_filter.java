package gira.cdap.com.giira;

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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import gira.cdap.com.giira.Task.TaskGetCategoryResluts;
import gira.cdap.com.giira.Task.TaskGetRegionResults;
import gira.cdap.com.giira.Task.categoryResultsRecyclerAdapter;
import gira.cdap.com.giira.Task.regionRecyclerResultsAdapter;

public class Search_results_filter extends AppCompatActivity {

    ArrayList categoryplacesList,regionlist;

    private RecyclerView mRecyclerView,mRecyclerViewRegion;

    String categoryID,categoryName,regionID,regionName;

    TextView textView_AB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_filter);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar_region, null);

        actionBar.setCustomView(cView);

        categoryName=getIntent().getExtras().getString("categoryName");
        textView_AB = (TextView) findViewById(R.id.ABtext);
//        System.out.print(categoryName);
//        textView_AB.setText(categoryName);

        regionName=getIntent().getExtras().getString("regionType");
//        textView_AB = (TextView) findViewById(R.id.ABtext);
//        System.out.print(categoryName);

        if(categoryName != null){
            textView_AB.setText(categoryName);

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            categoryID=getIntent().getExtras().getString("category");
            TaskGetCategoryResluts taskGetCategoryResluts=new TaskGetCategoryResluts(getApplicationContext(),categoryID,Search_results_filter.this);
            taskGetCategoryResluts.execute();

        }
        else if( regionName !=null){
            textView_AB.setText(regionName);

            mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            regionID=getIntent().getExtras().getString("id");
            TaskGetRegionResults taskGetRegionResults=new TaskGetRegionResults(getApplicationContext(),regionID,Search_results_filter.this);
            taskGetRegionResults.execute();
        }



        ImageButton imageButton = (ImageButton) findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void initalize()
    {
//        mRecyclerViewRegion= (RecyclerView) findViewById(R.id.recycler_view1);
//        mRecyclerViewRegion.setLayoutManager(new LinearLayoutManager(this));

    }


    public void SetCategoryResults(ArrayList categoryplaces)
    {
        this.categoryplacesList=categoryplaces;

        categoryResultsRecyclerAdapter myRecyclerAdapter = new categoryResultsRecyclerAdapter(Search_results_filter.this, categoryplacesList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

       mRecyclerView.setAdapter(myRecyclerAdapter);
    }

    public void SetRegionResults(ArrayList regionplaces)
    {
        this.regionlist=regionplaces;

        regionRecyclerResultsAdapter myRecyclerAdapter = new regionRecyclerResultsAdapter(Search_results_filter.this, regionlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        mRecyclerView.setAdapter(myRecyclerAdapter);
    }

//    public void SetActionbarText(String categoryname){
//
//                actionbarname = categoryname;
//        textView_AB.setText(actionbarname);
////        String text=textView_AB.toString();
////                textView.setText(text);
//    }

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
