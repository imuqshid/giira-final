package gira.cdap.com.giira;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import gira.cdap.com.giira.Task.TaskCheckinLocation;
import gira.cdap.com.giira.Task.TaskGetTop;
import gira.cdap.com.giira.Task.checkinPlaceRecyclerAdapter;
import gira.cdap.com.giira.Task.regionRecyclerAdapter;

/**
 * Created by Muqshid on 8/20/2016.
 */
public class CheckinActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double clatitude,clongitude;

    String placeid,placename;

    ArrayList categorylist,locationlist;

    private RecyclerView mRecyclerView,mRecyclerViewRegion;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin_layout);


        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_checkinList);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);




        TextView pageTitle = (TextView) findViewById(R.id.pageTitle);
        pageTitle.setText("Pick a Place");

        ImageButton homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();

            }
        });


        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);

        initalize();


        TaskCheckinLocation taskCheckinLocation=new TaskCheckinLocation(getApplicationContext(),CheckinActivity.this);
        taskCheckinLocation.execute();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void initalize()
    {


        mRecyclerViewRegion= (RecyclerView) findViewById(R.id.recycler_checkinList);
        mRecyclerViewRegion.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Gpstracker gps=new Gpstracker(CheckinActivity.this);

        if(gps.canGetLocation())
        {
            clatitude=gps.getLatitude();
            clongitude=gps.getLongitude();
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(clatitude, clongitude)).zoom(0).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void setLocation(ArrayList location)
    {
        this.locationlist=location
        ;

        checkinPlaceRecyclerAdapter myRecyclerAdapter = new checkinPlaceRecyclerAdapter(CheckinActivity.this, locationlist);
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

