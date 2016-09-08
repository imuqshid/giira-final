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
public class TaskGetTravelling extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    one_place one;
    String placeid;
    String name,website,contactno;

    ProgressDialog prgDialog;



    public TaskGetTravelling(Context context, String placeid, one_place one)
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
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/gettravalling?place_id="+placeid,1,value);
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
        JSONArray travelling;
        JSONArray Serviceid;


        List<String> servicesL = new ArrayList<String>();
        String[] servicesA = null;
        List<String> serviceidsL = new ArrayList<String>();
        String[] serviceidsA = null;




        try {

            StringBuilder stringBuilder=new StringBuilder("");

            travelling = json.getJSONArray("travelling");

            for (int i = 0; i < travelling.length(); i++) {

                name=travelling.getJSONObject(i).getString("name");
                website=travelling.getJSONObject(i).getString("website");
                contactno=travelling.getJSONObject(i).getString("contact_num");

                stringBuilder.append("\n"+"\n"+name +"\n"+"\n"+contactno+"\n"+"\n"+website+"\n");

//                allcomments.append(comment+"\n");


            }

//            allcomm = allcomments.getText().toString();

            one.set4(stringBuilder.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
