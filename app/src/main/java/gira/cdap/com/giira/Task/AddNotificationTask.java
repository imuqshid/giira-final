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

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.UserProfileActivity;


/**
 * Created by Perceptor on 6/13/2016.
 */

public class AddNotificationTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String message,status,type;
    UserProfileActivity userProfileActivity;

    Context context;


    public AddNotificationTask(Context context, String message,String status, String type, UserProfileActivity userProfileActivity)
    {
        this.context=context;
        this.message=message;
        this.status=status;
        this.type=type;
        this.userProfileActivity=userProfileActivity;

    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();

        value.add(new BasicNameValuePair("message", message));
        value.add(new BasicNameValuePair("status", status));
        value.add(new BasicNameValuePair("type", type));


        android.util.Log.d("Task","AddRequestTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/notification/addnotification",2,
                value);
        parsing = new JSONParser();

        try {
            JSONObject json = parsing.getJSONFromResponse(inputStream);
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

        if(result.matches("true"))

        {
                Toast.makeText(context, "successfully sent", Toast.LENGTH_SHORT).show();
                userProfileActivity.recreate();
        }
        else
        {

                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();


        }


    }
}
