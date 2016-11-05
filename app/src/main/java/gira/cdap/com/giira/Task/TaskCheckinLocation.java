package gira.cdap.com.giira.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.CheckinActivity;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.Toprated;


public class TaskCheckinLocation extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;
    ProgressDialog prgDialog;
    //gira.cdap.com.giira.display_results display_results;
    CheckinActivity checkinActivity;

    ArrayList<places> locationlist;

    public TaskCheckinLocation(Context context, CheckinActivity one)
    {
        this.checkinActivity=one;
        this.context = context;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(checkinActivity);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/retrivecheckinPlace",1,value);
        parsing = new JSONParser();
        try {
            json = parsing.getJSONFromResponse(is);
            result = String.valueOf(json.getBoolean("response"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        prgDialog.hide();
        JSONArray places;
        try {

            locationlist = new ArrayList<places>();

            places = json.getJSONArray("places");

            for (int i = 0; i < 7; i++) {

                JSONObject object=places.getJSONObject(i);

                places places1=new places();

                places1.setPlaceID(object.getString("placeID"));
                //places.setRegiontype(object.getString("regionType"));
                places1.setPlaceimage(object.getString("placeimage"));
                //places1.getPlaceAddress();

                locationlist.add(places1);

            }

            checkinActivity.setLocation(locationlist);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
