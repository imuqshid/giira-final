package gira.cdap.com.giira.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.display_results;

/**
 * Created by Perceptor on 8/9/2016.
 */
public class TaskGetCategory extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    display_results display_results;

    ArrayList<category> categoryArrayList;

    ProgressDialog prgDialog;

    public TaskGetCategory(Context context,display_results one)
    {
        this.display_results=one;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(display_results);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
//        value.add(new BasicNameValuePair("place_id",null));


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/retrivecategory",1,value);
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
        JSONArray categories;
//        JSONArray Serviceid;
//
//
//        List<String> servicesL = new ArrayList<String>();
//        String[] servicesA = null;
//        List<String> serviceidsL = new ArrayList<String>();
//        String[] serviceidsA = null;




        try {



//            StringBuilder stringBuilder=new StringBuilder("");
            categoryArrayList = new ArrayList<category>();

            categories = json.getJSONArray("category");

            for (int i = 0; i < categories.length(); i++) {

                JSONObject object=categories.getJSONObject(i);

                category category1=new category();

                category1.setCategoryname(object.getString("categoryName"));
                category1.setCategoryID(object.getString("categoryID"));
                category1.setCategoryimage(object.getString("category_Images"));

                categoryArrayList.add(category1);

            }

            display_results.setcategory(categoryArrayList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
