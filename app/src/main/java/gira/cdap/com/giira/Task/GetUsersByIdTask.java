package gira.cdap.com.giira.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
import gira.cdap.com.giira.UserProfileActivity;

/**
 * @author perceptor
 */
public  class GetUsersByIdTask extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    JSONObject json;

    String userid;
    String name,email;
    UserProfileActivity userProfileActivity;

    Context context;


ProgressDialog prgDialog;

    public GetUsersByIdTask(Context context, String userid, UserProfileActivity userProfileActivity)
    {
        this.userid=userid;
        this.context = context;
        this.userProfileActivity=userProfileActivity;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(userProfileActivity);
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
        value.add(new BasicNameValuePair("id",userid));


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/friends/getuserdetailsbyid?id="+userid,1,value);
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
        JSONArray user;
        JSONArray Serviceid;


        List<String> servicesL = new ArrayList<String>();
        String[] servicesA = null;
        List<String> serviceidsL = new ArrayList<String>();
        String[] serviceidsA = null;

        try {

            user = json.getJSONArray("user");

            for (int i = 0; i < user.length(); i++) {

                 name=user.getJSONObject(i).getString("name");
                 email=user.getJSONObject(i).getString("email");

            }
            userProfileActivity.set(name,email);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}