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
import java.util.List;

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.one_place;

/**
 * Created by Perceptor on 6/13/2016.
 */
public class GetRatingTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String comment;
    one_place one_place;

    Context context;
    JSONObject json;
    ProgressDialog prgDialog;


    public GetRatingTask(Context context, String comment,one_place one_place)
    {
        this.context=context;
        this.comment=comment;
        this.one_place=one_place;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(one_place);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("text", comment));
        android.util.Log.d("Task","AddReviewTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                "http://text-processing.com/api/sentiment/",2,
                value);
        parsing = new JSONParser();

        try {
            json = parsing.getJSONFromResponse(inputStream);
            result = json.getString("label");

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
        prgDialog.hide();
        JSONArray rating;

        if(result!=null)

        {
            try {

                Double val= json.getJSONObject("probability").getDouble("pos")*5;
                if(result.matches("pos"))
                {
                    val+=0.5;
                }
//                else if (result.matches("neg"))
//                {
//                    val-=0.8;
//                }

                one_place.setRating(val);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            if(one_place!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
