package gira.cdap.com.giira.Task;

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

import gira.cdap.com.giira.EventPlanningFragment;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.UserProfileActivity;


/**
 * Created by Perceptor on 6/13/2016.
 */

public class GetFriendStatus extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String user1,user2;
    JSONObject json;
    UserProfileActivity userProfileActivity;

    Context context;


    public GetFriendStatus(Context context, String user1, String user2, UserProfileActivity userProfileActivity)
    {
        this.context=context;
        this.user1=user1;
        this.user2=user2;
        this.userProfileActivity=userProfileActivity;

    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("user1", user1));
        value.add(new BasicNameValuePair("user2", user2));
        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                ""+serverURL.local_host_url+"giira/index.php/friends/checkstatus?",1,
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
                userProfileActivity.setButtonText(status);
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
