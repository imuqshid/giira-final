package gira.cdap.com.giira.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.NotificationActivity;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.UserProfileActivity;


/**
 * Created by Perceptor on 6/13/2016.
 */

public class GetNotification extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String message,time,type,status;
    Integer id;

    JSONObject json;
    NotificationActivity notificationActivity ;

    Context context;


    public GetNotification(Context context, String message, String time,String type,String status, NotificationActivity notificationActivity)
    {
        this.context=context;
        this.message=message;
        this.time=time;
        this.type=type;
        this.status=status;
        this.notificationActivity=notificationActivity;

    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("message", message));
        value.add(new BasicNameValuePair("time", time));
        value.add(new BasicNameValuePair("type", type));
        value.add(new BasicNameValuePair("status", status));

        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                ""+serverURL.local_host_url+"giira/index.php/friends/checkstatus?user1="+id,1,
                value);
        parsing = new JSONParser();

        try {
            json = parsing.getJSONFromResponse(inputStream);
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String status;

        if(result.matches("true"))
        {
            try {
                status=json.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();

        }


    }
}
