package gira.cdap.com.giira.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.one_place;

/**
 * Created by Perceptor on 6/14/2016.
 */
public class TaskGetAccommodation extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    one_place one;
    String placeid;
    String name,address,website;

    ArrayList<Accommodations> accommodationsArrayList;

    ProgressDialog prgDialog;

//    AccommodationAdapter accommodationAdapter;





    public TaskGetAccommodation(Context context, String placeid, one_place one)
    {
        this.one=one;
        this.context = context;
        this.placeid=placeid;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(one);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("place_id",null));


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/getaccomodation?place_id="+placeid,1,value);
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
        JSONArray accomodation;
//        JSONArray Serviceid;
//
//
//        List<String> servicesL = new ArrayList<String>();
//        String[] servicesA = null;
//        List<String> serviceidsL = new ArrayList<String>();
//        String[] serviceidsA = null;




        try {



//            StringBuilder stringBuilder=new StringBuilder("");
            accommodationsArrayList = new ArrayList<Accommodations>();

            accomodation = json.getJSONArray("accomodation");

            for (int i = 0; i < accomodation.length(); i++) {

                JSONObject object=accomodation.getJSONObject(i);

                Accommodations accommodations=new Accommodations();

                accommodations.setAccname(object.getString("name"));
                accommodations.setAccid(object.getString("id"));
                accommodations.setAccimage(object.getString("image"));

                accommodationsArrayList.add(accommodations);




            }

            one.set3(accommodationsArrayList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
