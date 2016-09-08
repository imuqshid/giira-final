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

import gira.cdap.com.giira.NewEvent;
import gira.cdap.com.giira.ProfileFragment;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;


/**
 * Created by Perceptor on 6/13/2016.
 */

public class UploadProfileImageTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String imagepath;
    ProfileFragment profileFragment;
    int imgchck=0;

    Context context;


    public UploadProfileImageTask(Context context, String imagepath, ProfileFragment profileFragment)
    {
        this.context=context;
        this.imagepath=imagepath;
        this.profileFragment=profileFragment;
        this.imgchck=1;
    }

    public UploadProfileImageTask(Context context, ProfileFragment profileFragment)
    {
        this.context=context;
        this.profileFragment=profileFragment;
        this.imgchck=0;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        if(imgchck==1)
        {
            value.add(new BasicNameValuePair("image_name", imagepath));
        }

        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/profilepic/uploadimage",2,
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
            if(profileFragment!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            if(profileFragment!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
