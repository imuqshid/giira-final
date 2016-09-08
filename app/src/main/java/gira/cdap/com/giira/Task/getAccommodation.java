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

import gira.cdap.com.giira.One_Accomadation;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;

/**
 * Created by Perceptor on 8/5/2016.
 */
public class getAccommodation extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    One_Accomadation one;
    String AccID;
    String name,description,address,website,phoneno,email,image;
    ProgressDialog prgDialog;




    public getAccommodation(Context context, String accid, One_Accomadation one)
    {
        this.one=one;
        this.context = context;
        this.AccID=accid;
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


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/getAccommodationdetails?id="+AccID,1,value);
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
        JSONArray AccomodationsArray;




        try {

            AccomodationsArray = json.getJSONArray("accomodation");

            for (int i = 0; i < AccomodationsArray.length(); i++) {

                name=AccomodationsArray.getJSONObject(i).getString("name");
                address=AccomodationsArray.getJSONObject(i).getString("address");
                description=AccomodationsArray.getJSONObject(i).getString("Description");
                website= AccomodationsArray.getJSONObject(i).getString("website");
                phoneno=AccomodationsArray.getJSONObject(i).getString("phoneNo");
                email=AccomodationsArray.getJSONObject(i).getString("Email");
                image=AccomodationsArray.getJSONObject(i).getString("image");



//                String name = Service.getString(i);
//                servicesL.add(name);
            }
            one.setAccommodation(name,address,description,website,phoneno,email,image);
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
