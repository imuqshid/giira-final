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
 * @author perceptor
 */
public  class Taskgetplace extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    one_place one;
    String placeid;
    String name,address,description,imagethumb;


ProgressDialog prgDialog;

    public Taskgetplace(Context context, String placeid, one_place one)
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
        // TODO Auto-generated method stub
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("id",null));


//        Log.d("Task", "placeTaskImport");
        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/getplacedetails?id="+placeid,1,value);
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
        JSONArray place;
        JSONArray Serviceid;


        List<String> servicesL = new ArrayList<String>();
        String[] servicesA = null;
        List<String> serviceidsL = new ArrayList<String>();
        String[] serviceidsA = null;




        try {

            place = json.getJSONArray("places");

            for (int i = 0; i < place.length(); i++) {

                 name=place.getJSONObject(i).getString("name");
                address=place.getJSONObject(i).getString("address");
                description=place.getJSONObject(i).getString("description");

                imagethumb=place.getJSONObject(i).getString("thumb");



//                String name = Service.getString(i);
//                servicesL.add(name);
            }
            one.set(name,address,description,imagethumb);
//            servicesA = servicesL.toArray(new String[servicesL.size()]);
//
//            Serviceid = json.getJSONArray("vendoridlist");
//
//            for (int i = 0; i < Serviceid.length(); i++) {
//                String name = Serviceid.getString(i);
//                serviceidsL.add(name);
//            }
//            serviceidsA = serviceidsL.toArray(new String[serviceidsL.size()]);
//            serviceform.populatevendorlist(servicesA,serviceidsA);


//
//            if(!(customerrequest== null)) {
//
//
//                customerrequest.setservice(servicesA, serviceidsA);
//                customerrequest.makeimageview();
//            }
//            else
//            {
//                serviceform.setdetails(serviceidsA,servicesA);
//                serviceform.addItemsOnSpinnertype();
//
//            }

            // cancel(true);
//                  Toast.makeText(this.context, "finished", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        catch (IOException e) {
//            e.printStackTrace();
//        }


    }




}