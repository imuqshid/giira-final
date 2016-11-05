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
import java.util.Iterator;
import java.util.List;

import gira.cdap.com.giira.NewEvent;
import gira.cdap.com.giira.NewTeam;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.UserProfileActivity;

/**
 * @author perceptor
 */
public  class GetUsersByNameTask extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    JSONObject json;

    String userid;
    String name,id,unid;
    NewEvent event;
    NewTeam team;
    Context context;


ProgressDialog prgDialog;

    public GetUsersByNameTask(Context context, String userid,NewEvent event)
    {
        this.userid=userid;
        this.context = context;
        this.event=event;
    }

    public GetUsersByNameTask(Context context, String userid,NewTeam team)
    {
        this.userid=userid;
        this.context = context;
        this.team=team;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        if(team==null) {
            prgDialog = new ProgressDialog(event);
        }
        else{
            prgDialog =new ProgressDialog(team);
        }
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
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/friends/retrivefriends",1,value);
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




        try {

            user = json.getJSONArray("user");
            String[] userA = new String[user.length()];
            String[] nameA = new String[user.length()];
            String[] unidA = new String[user.length()];

            for (int i=0;i<user.length();i++)
            {
                nameA[i]=user.getJSONObject(i).getString("name");
                userA[i]=user.getJSONObject(i).getString("id");
                unidA[i]=user.getJSONObject(i).getString("unique_id");

            }

            if (team==null) {
                event.fillArrays(userA, nameA, unidA);
            }
            else{
                team.fillArrays(userA,nameA,unidA);
            }


//
//            for(use; ) {
//                String item = i.next();
//                System.out.println(item);
//            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}