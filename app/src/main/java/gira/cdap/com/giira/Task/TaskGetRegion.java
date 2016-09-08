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

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.display_results;


public class TaskGetRegion extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;
ProgressDialog prgDialog;
    gira.cdap.com.giira.display_results display_results;

    ArrayList<region> regionArrayList;

    public TaskGetRegion(Context context,display_results one)
    {
        this.display_results=one;
        this.context = context;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(display_results);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
//        value.add(new BasicNameValuePair("place_id",null));


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/retriveregion",1,value);
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
        JSONArray regions;
//        JSONArray Serviceid;
//
//
//        List<String> servicesL = new ArrayList<String>();
//        String[] servicesA = null;
//        List<String> serviceidsL = new ArrayList<String>();
//        String[] serviceidsA = null;




        try {



//            StringBuilder stringBuilder=new StringBuilder("");
            regionArrayList = new ArrayList<region>();

            regions = json.getJSONArray("regions");

            for (int i = 0; i < regions.length(); i++) {

                JSONObject object=regions.getJSONObject(i);

                region region1=new region();

                region1.setRegionID(object.getString("id"));
                region1.setRegiontype(object.getString("regionType"));
                region1.setRegionimage(object.getString("region_image"));

                regionArrayList.add(region1);

            }

            display_results.setregion(regionArrayList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
