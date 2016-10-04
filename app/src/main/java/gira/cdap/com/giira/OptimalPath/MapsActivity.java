package gira.cdap.com.giira.OptimalPath;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;





import com.google.android.gms.maps.OnMapReadyCallback;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;


import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gira.cdap.com.giira.DisasterFragment;
import gira.cdap.com.giira.OptimalPath.Disaster.DisasterPlace;
import gira.cdap.com.giira.OptimalPath.Disaster.UserPlace;
import gira.cdap.com.giira.OptimalPath.Modules.DirectionFinder;
import gira.cdap.com.giira.OptimalPath.Modules.DirectionFinderAlter;
import gira.cdap.com.giira.OptimalPath.Modules.DirectionFinderListener;
import gira.cdap.com.giira.OptimalPath.Modules.Route;
import gira.cdap.com.giira.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,DirectionFinderListener {

    private Button btnFindPath,btnFindAlterPath;
    private Button btnshow;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    String name = "";
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    public  List<DisasterPlace> location = new ArrayList<DisasterPlace>();
    public  List<UserPlace> locationnew = new ArrayList<UserPlace>();
    public String routepath="";
    public String startp="";
    public String endp="";
    public Context context;



    String JSON_STRING;
    String JSON_STRINGnew;



    private GoogleApiClient client;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new BackgroundTask().execute();
        new BackgroundTaskSecound().execute();

        // new BackgroundTaskSecound().execute();


        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        // etOrigin = (EditText) findViewById(R.id.etOrigin);
        //etDestination = (EditText) findViewById(R.id.etDestination);
        //btnFindAlterPath=(Button) findViewById(R.id.alternativepath);
       ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        DisasterFragment.class);
                startActivity(i);
                finish();
            }
        });




        /** btnFindPath.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        sendRequest();
        }
        });**/





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void Savepath(View view)
    {
        new Savauserpath(this).execute();
    }
    public void startjourney(View view)
    {

        sendRequest();


    }
    public class Savauserpath extends AsyncTask<String, Void, String> {

        private Context context;

        public Savauserpath(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            String name = "vjay";

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {
                data = "?name=" + URLEncoder.encode(name, "UTF-8");
                data += "&route=" + URLEncoder.encode(routepath, "UTF-8");


                link = "http://192.168.123.1/pushnotifications/storeroute.php" + data;

                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {
                        Toast.makeText(context,"Route saved successfully.", Toast.LENGTH_SHORT).show();
                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "your route already selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        String json_string;
        @Override
        protected void onPreExecute() {
            json_url="http://192.168.123.1/Disasterplace/GetData.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String res ) {
            // super.onPostExecute(aVoid);
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;


        //List<DisasterPlace> location = new ArrayList<DisasterPlace>();
        JSONObject place = new JSONObject(data);
        JSONArray results = place.getJSONArray("result");
        try {
            for (int i = 0; i < results.length(); i++) {
                JSONObject land = results.getJSONObject(i);
                DisasterPlace d=new DisasterPlace();
                d.LocPoints= new LatLng(land.getDouble("latitude"),land.getDouble("longitude"));
                d.PlaceName=land.getString("name_en");
                d.DisasterName=land.getString("disaster");
                location.add(d);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < location.size(); i++) {
                Latitude = location.get(i).LocPoints.latitude;
                Longitude =location.get(i).LocPoints.longitude;
                String lacationname = location.get(i).PlaceName;
                String disastername = location.get(i).DisasterName;
                String message= disastername+" at "+lacationname;
                MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(message);
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 8));
            }
        }
        catch (Exception e)
        {
            System.out.print("error occur at marking");
        }


    }
    class BackgroundTaskSecound extends AsyncTask<Void,Void,String>
    {
        String json_urlnew;
        String json_string;
        @Override
        protected void onPreExecute() {
            json_urlnew="http://192.168.123.1/pushnotifications/userpathPlace.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_urlnew);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRINGnew=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRINGnew+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String res ) {
            // super.onPostExecute(aVoid);
            try {
                parseJSonsecund(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void parseJSonsecund(String data) throws JSONException {
        if (data == null)
            return;


        //List<DisasterPlace> location = new ArrayList<DisasterPlace>();
        JSONObject place = new JSONObject(data);
        JSONArray results = place.getJSONArray("result");
        try {
            for (int i = 0; i < results.length(); i++) {
                JSONObject land = results.getJSONObject(i);
                UserPlace u=new UserPlace();
                u.startplace= land.getString("startPlace");
                u.endplace=land.getString("endPlace");

                locationnew.add(u);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < locationnew.size(); i++) {
                if(i==(locationnew.size()-1)) {
                    startp = locationnew.get(i).startplace;
                    endp = locationnew.get(i).endplace;
                    locationnew.clear();
                }


            }
        }
        catch (Exception e)
        {
            System.out.print("error occur to get results");
        }


    }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;


        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    private void sendRequest() {
        String origin = startp;
        String destination = endp;
        if (origin.isEmpty()) {
            Toast.makeText(this, " couldn't find origin place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "coudn't find destination place", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void sendRequestAlter() {
        String origin = startp;
        String destination = endp;
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinderAlter(this, origin, destination).executeAlter();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }


    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 8));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.RED).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
        outerloop:
        for (Route route : routes) {
            for (int i = 0; i < route.points.size(); i++) {

                for (DisasterPlace d : location) {

                    if (distance(route.points.get(i).latitude, route.points.get(i).longitude, d.LocPoints.latitude, d.LocPoints.longitude) < 10) {

                        new AlertDialog.Builder(this).setTitle("Disaster Warning").setMessage("your optimal path is affected by disaster,Get the alternative path in order to avoid the risk").setNegativeButton("Cancel", null).setPositiveButton("Get Alternativepath", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendRequestAlter();
                            }
                        }).setIcon(R.drawable.cancel).show();
                         break outerloop;

                    }


                }

            }

        }

        //routepath="2 1,2 2,1 2";

        for (Route route : routes) {
            routepath="";
            for (int i = 0; i <route.points.size(); i++) {
                routepath =routepath + route.points.get(i).latitude;
                routepath =routepath + " ";
                routepath =routepath +route.points.get(i).longitude;
                routepath =routepath + ",";

            }

        }
        if(routes.isEmpty())
        {
            Toast.makeText(getApplicationContext(),
                    "Incorrect origin and destination places", Toast.LENGTH_LONG)
                    .show();
        }
        else {
            routes.clear();
        }


    }
    public void onDirectionFinderSuccessAlter(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 8));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.RED).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
        outerloop:
        for (Route route : routes) {
            for (int i = 0; i < route.points.size(); i++) {

                for (DisasterPlace d : location) {

                    if (distance(route.points.get(i).latitude, route.points.get(i).longitude, d.LocPoints.latitude, d.LocPoints.longitude) < 10) {

                        new AlertDialog.Builder(this).setTitle("Disaster Warning").setMessage("Your Alternative path also affected by disaster So Cancel the journey plan").setNegativeButton("Cancel", null).setIcon(R.drawable.cancel).show();
                        routes.clear();
                        break outerloop;
                    }



                }

            }

        }

        //routepath="2 1,2 2,1 2";

        for (Route route : routes) {
            routepath="";
            for (int i = 0; i <route.points.size(); i++) {
                routepath =routepath + route.points.get(i).latitude;
                routepath =routepath + " ";
                routepath =routepath +route.points.get(i).longitude;
                routepath =routepath + ",";

            }

        }

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /**client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sivarasan.alternativepath/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);**/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       /** Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.sivarasan.alternativepath/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();**/
    }
}
