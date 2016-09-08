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


/**
 * Created by Perceptor on 6/13/2016.
 */

public class GetEventDetailsTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String event_id;
    JSONObject json;
    EventPlanningFragment eventPlanningFragment;

    Context context;


    public GetEventDetailsTask(Context context, String event_id, EventPlanningFragment eventPlanningFragment)
    {
        this.context=context;
        this.event_id=event_id;
        this.eventPlanningFragment=eventPlanningFragment;

    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("event_id", event_id));
        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                ""+serverURL.local_host_url+"giira/index.php/event/geteventdetails?",1,
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
        JSONArray jarr;
        String time,name,description,location,date,images,team,members;

        if(result.matches("true"))
        {

            if(eventPlanningFragment!=null)
            {
                try {
                    jarr=json.getJSONArray("event");
                    for(int i=0;i<jarr.length();i++)
                    {
                        time= (String) jarr.getJSONObject(i).get("time");
                        name= (String) jarr.getJSONObject(i).get("name");
                        description= (String) jarr.getJSONObject(i).get("description");
                        location= (String) jarr.getJSONObject(i).get("location");
                        date= (String) jarr.getJSONObject(i).get("date");
//                        images= (String) jarr.getJSONObject(i).get("images");
                        team= (String) jarr.getJSONObject(i).get("team");
                        members= (String) jarr.getJSONObject(i).get("members");
                        eventPlanningFragment.loadPopup(time,name,date,description,location,team,members);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
        else
        {
            if(eventPlanningFragment!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
