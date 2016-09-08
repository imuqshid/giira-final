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

public class AddFriendTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String user_from,user_to;
    UserProfileActivity userProfileActivity;

    Context context;


    public AddFriendTask(Context context, String user_from,String user_to, UserProfileActivity userProfileActivity)
    {
        this.context=context;
        this.user_from=user_from;
        this.user_to=user_to;
        this.userProfileActivity=userProfileActivity;

    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();

        value.add(new BasicNameValuePair("user_from", user_from));
        value.add(new BasicNameValuePair("user_to", user_to));

        android.util.Log.d("Task","AddRequestTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/friends/sendrequest",2,
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
