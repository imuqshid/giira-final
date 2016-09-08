package gira.cdap.com.giira;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;

import java.util.ArrayList;
import java.util.HashMap;

import gira.cdap.com.giira.Task.AccommodationAdapter;
import gira.cdap.com.giira.Task.AddReviewTask;
import gira.cdap.com.giira.Task.DownloadImageTask;
import gira.cdap.com.giira.Task.GetRatingTask;
import gira.cdap.com.giira.Task.MyRecyclerAdapter;
import gira.cdap.com.giira.Task.ReviewRecyclerAdapter;
import gira.cdap.com.giira.Task.TaskGetAccommodation;
import gira.cdap.com.giira.Task.TaskGetTravelling;
import gira.cdap.com.giira.Task.TaskgetReviews;
import gira.cdap.com.giira.Task.Taskgetplace;
import gira.cdap.com.giira.Task.getfinalratingTask;
import gira.cdap.com.giira.Task.serverURL;
import gira.cdap.com.giira.activity.LoginActivity;
import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;

public class one_place extends AppCompatActivity {

    TabHost tabHost;
    String name,address,discription,placeid,placerating,username;

//    ProfileFragment getusername;

    String thumb,thumbuser;

    String placename;

    TextView txtplaname,txtadd,txtdesc;

    TextView textViewcom,textViewAcc,textViewTravel,textView_AB;

    ImageView imageView,imageView2,imageView3,imageView4,userimageview;

    EditText etcomment;
    String comment;

    String comm;
    ArrayList accomadationlist,reviewlist;
    String travelling;

    RatingView ratingView;

    AccommodationAdapter accommodationAdapter;
    ListView lv;

    private RecyclerView mRecyclerView,mRecyclerView1;
    private ProgressBar progressBar;

    String ratingsingle;

    private SQLiteHandler db;
    private SessionManager session;


//    String imageurl = "http://192.168.56.1/giira/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_place);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String userimage=user.get("thumb");
//
//        String url = new String(serverURL.local_host_url+"giira/"+thumb);
//        new DownloadImageTask(userimageview).execute(url);

        thumbuser=userimage;
        username=name;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);

        actionBar.setCustomView(cView);
//        TextView textView= (TextView) findViewById(R.id.ABtext);
//        textView.setText("Place");

        placename=getIntent().getExtras().getString("name");
        textView_AB = (TextView) findViewById(R.id.ABtext);
//        System.out.print(categoryName);
        textView_AB.setText(placename);

        ImageButton imageButton = (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Details");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Details");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Reviews");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Reviews");
        host.addTab(spec);



        TabHost host2 = (TabHost)findViewById(R.id.tabHost2);
        host2.setup();

        //Tab 1
        TabHost.TabSpec spec2 = host.newTabSpec("Accommodation");
        spec2.setContent(R.id.tab1_1);
        spec2.setIndicator("Accommodation");
        host2.addTab(spec2);

        //Tab 2
        spec2 = host.newTabSpec("Travelling");
        spec2.setContent(R.id.tab1_2);
        spec2.setIndicator("Travelling");
        host2.addTab(spec2);


        initalize();



        placeid=getIntent().getExtras().getString("id");
        Taskgetplace tasasakgetplace=new Taskgetplace(getApplicationContext(),placeid,one_place.this);
        tasasakgetplace.execute();

        getfinalratingTask getfinalratingTask=new getfinalratingTask(getApplicationContext(),placeid,one_place.this);
        getfinalratingTask.execute();

        TaskgetReviews taskgetReviews=new TaskgetReviews(getApplicationContext(),placeid,one_place.this);
        taskgetReviews.execute();

        TaskGetAccommodation taskGetAccommodation=new TaskGetAccommodation(getApplicationContext(),placeid,one_place.this);
        taskGetAccommodation.execute();

        TaskGetTravelling taskGetTravelling=new TaskGetTravelling(getApplicationContext(),placeid,one_place.this);
        taskGetTravelling.execute();





    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(one_place.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void Addreview(View view)
    {
        comment=etcomment.getText().toString();
        GetRatingTask gTask=new GetRatingTask(getApplicationContext(),comment,this);
        gTask.execute();

//        placeid=getIntent().getExtras().getString("id");
//        placerating=getIntent().getExtras().getString("rating");
//        AddReviewTask addReviewTask=new AddReviewTask(getApplicationContext(),comment,placerating,placeid,one_place.this);
//        addReviewTask.execute();
//
//
//
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initalize()
    {
        txtplaname=(TextView)findViewById(R.id.txtplname);
        txtadd=(TextView)findViewById(R.id.txtaddress);
        txtdesc=(TextView)findViewById(R.id.txtdescription);

        etcomment=(EditText)findViewById(R.id.etcommentadd);
        ratingView=(RatingView) findViewById(R.id.star);

//        textViewcom=(TextView)findViewById(R.id.tvcomment);

        imageView=(ImageView)findViewById(R.id.ivthumb);

        imageView2=(ImageView)findViewById(R.id.belowimage2);
        imageView3=(ImageView)findViewById(R.id.belowimage3);
        imageView4=(ImageView)findViewById(R.id.belowimage4);
        userimageview=(ImageView)findViewById(R.id.userthumb);

//        lv  = (ListView)findViewById(R.id.acclist);


        textViewTravel=(TextView)findViewById(R.id.tvtravelling);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_reviews);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));



    }

    public void set(String name,String address,String descri,String thumb){
        this.name=name;
        this.address=address;
        this.discription=descri;
        this.thumb=thumb;
        txtplaname.setText(name);
        txtadd.setText(address);
        txtdesc.setText(descri);


        String url = new String(serverURL.local_host_url+"giira/"+thumb);
        String url2=new String(serverURL.local_host_url+"giira/images/"+name+"2.JPEG");
        String url3=new String(serverURL.local_host_url+"giira/images/"+name+"3.JPEG");
        String url4=new String(serverURL.local_host_url+"giira/images/"+name+"4.JPEG");

        new DownloadImageTask(imageView).execute(url);
        new DownloadImageTask(imageView2).execute(url2);
        new DownloadImageTask(imageView3).execute(url3);
        new DownloadImageTask(imageView4).execute(url4);
//        InputStream is=new URL(url).openStream();
//        Bitmap bmp = BitmapFactory.decodeStream(is);
//        imageView.setImageBitmap(bmp);

    }

    public void set2(String comm)
    {
        this.comm=comm;
        textViewcom.setText(comm);
    }

    public void set3(ArrayList accommodation)
    {
        this.accomadationlist=accommodation;

        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(one_place.this, accomadationlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        mRecyclerView.setAdapter(myRecyclerAdapter);




//            lv.setAdapter(accommodationAdapter);
//        setListViewHeightBasedOnChildren(lv);
    }

    public void set4(String travelling)
    {
        this.travelling=travelling;
        textViewTravel.setText(travelling);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }

    public void setRating(Double val) {
        Double rating=val;
//        ratingView.setRating(rating);
        ratingsingle=rating.toString();

        placeid=getIntent().getExtras().getString("id");
        AddReviewTask addReviewTask=new AddReviewTask(getApplicationContext(),comment,ratingsingle,placeid,username,thumbuser,one_place.this);
        addReviewTask.execute();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void setreviews(ArrayList review)
    {
        this.reviewlist=review;

        ReviewRecyclerAdapter myRecyclerAdapter = new ReviewRecyclerAdapter(one_place.this, reviewlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));

        mRecyclerView1.setAdapter(myRecyclerAdapter);

    }

    public void setRatings(float averageRating)
    {
        float rating=averageRating;
        ratingView.setRating(rating);
    }

    //    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(" ");
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.setExpanded(true);
//
//        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle(getString(R.string.app_name));
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
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
