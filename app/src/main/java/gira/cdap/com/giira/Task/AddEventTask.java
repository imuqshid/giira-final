package gira.cdap.com.giira.Task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.EventPlanningFragment;
import gira.cdap.com.giira.NewEvent;
import gira.cdap.com.giira.R;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;


/**
 * Created by Muqshid on 6/13/2016.
 */

public class AddEventTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String name,description,location,date,time,team,members,imagepath;
    NewEvent newEvent;
    int imgchck=0;

    Context context;


    public AddEventTask(Context context, String name,String description,String location,String date,String time,String team,String members,String imagepath, NewEvent newEvent)
    {
        this.context=context;
        this.name=name;
        this.description=description;
        this.location=location;
        this.date=date;
        this.time=time;
        this.team=team;
        this.members=members;
        this.imagepath=imagepath;
        this.newEvent=newEvent;
        this.imgchck=1;
    }

    public AddEventTask(Context context, String name,String description,String location,String date,String time,String team,String members, NewEvent newEvent)
    {
        this.context=context;
        this.name=name;
        this.description=description;
        this.location=location;
        this.date=date;
        this.time=time;
        this.team=team;
        this.members=members;
        this.newEvent=newEvent;
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
        value.add(new BasicNameValuePair("name", name));
        value.add(new BasicNameValuePair("description", description));
        value.add(new BasicNameValuePair("location", location));
        value.add(new BasicNameValuePair("date", date));
        value.add(new BasicNameValuePair("time", time));
        value.add(new BasicNameValuePair("team", team));
        value.add(new BasicNameValuePair("members",members));
        android.util.Log.d("Task","AddEventTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/event/addevent",2,
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
            if(newEvent!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();

            }

        }
        else
        {
            if(newEvent!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();

            }

        }


    }


}
