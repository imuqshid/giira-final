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

import gira.cdap.com.giira.NewTeam;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;


/**
 * Created by Muqshid on 6/13/2016.
 */

public class AddTeamTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String tname,description,members;
    NewTeam newTeam;

    Context context;


    public AddTeamTask(Context context, String tname, String description, String members, NewTeam newTeam)
    {
        this.context=context;
        this.tname=tname;
        this.description=description;
        this.members=members;

        this.newTeam=newTeam;

    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();

        value.add(new BasicNameValuePair("tname", tname));
        value.add(new BasicNameValuePair("description", description));
        value.add(new BasicNameValuePair("member", members));
        android.util.Log.d("Task","AddTeamTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/team/addteam",2,
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

        if(result.matches("true")){
            Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
        }

        /*{
            if(newTeam!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            if(newTeam!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
            }

        }*/
        else{
            Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
