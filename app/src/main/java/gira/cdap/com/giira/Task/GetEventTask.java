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

public class GetEventTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String date;
    JSONObject json;
    EventPlanningFragment eventPlanningFragment;

    Context context;


    public GetEventTask(Context context,String date, EventPlanningFragment eventPlanningFragment)
    {
        this.context=context;
        this.date=date;
        this.eventPlanningFragment=eventPlanningFragment;

    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("date", date));
        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                ""+serverURL.local_host_url+"giira/index.php/event/getevents?",1,
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
        String[] timeArr,nameArr,fullArr,idArr;

        if(result.matches("true"))
        {

            if(eventPlanningFragment!=null)
            {
                try {
                    jarr=json.getJSONArray("event");
                    nameArr=new String[jarr.length()];
                    timeArr=new String[jarr.length()];
                    fullArr=new String[jarr.length()];
                    idArr=new String[jarr.length()];
                    for(int i=0;i<jarr.length();i++)
                    {
                        timeArr[i]= (String) jarr.getJSONObject(i).get("time");
                        nameArr[i]= (String) jarr.getJSONObject(i).get("name");
                        idArr[i]= (String) jarr.getJSONObject(i).get("event_id");
                        fullArr[i]=timeArr[i]+"     "+nameArr[i];
                    }

                    eventPlanningFragment.loadList(fullArr,idArr);
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
