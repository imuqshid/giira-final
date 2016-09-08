package gira.cdap.com.giira.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.Search_results_filter;
import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;

/**
 * Created by Perceptor on 8/9/2016.
 */
public class TaskGetCategoryResluts extends AsyncTask<String, Void, String> {

    ServiceHandler serviceHandler;
    InputStream is;
    JSONParser parsing;

    Context context;
    JSONObject json;

    Search_results_filter search_results_filter;
    String categoryID;
    String name,address,website;

    ProgressDialog prgDialog;

    ArrayList<category> categoryArrayresultsList;

//    AccommodationAdapter accommodationAdapter;





    public TaskGetCategoryResluts(Context context, String categoryID, Search_results_filter one)
    {
        this.search_results_filter=one;
        this.context = context;
        this.categoryID=categoryID;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        prgDialog = new ProgressDialog(search_results_filter);
        prgDialog.setMessage("Please Wait, Fetching Details..");
        prgDialog.setIndeterminate(false);
        prgDialog.setCancelable(false);
        prgDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("category",null));


        serviceHandler = new ServiceHandler();
        is = serviceHandler.makeServiceCall(serverURL.local_host_url+"giira/index.php/places/getcategoryplaces?category="+categoryID,1,value);
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
        JSONArray categoryplaces;
//        JSONArray Serviceid;
//
//
//        List<String> servicesL = new ArrayList<String>();
//        String[] servicesA = null;
//        List<String> serviceidsL = new ArrayList<String>();
//        String[] serviceidsA = null;




        try {



//            StringBuilder stringBuilder=new StringBuilder("");
            categoryArrayresultsList = new ArrayList<category>();

            categoryplaces = json.getJSONArray("category");

            String url="giira/";
            String image;

            for (int i = 0; i < categoryplaces.length(); i++) {

                JSONObject object=categoryplaces.getJSONObject(i);

                category category2=new category();

                category2.setCategoryname(object.getString("name"));
                category2.setCategoryID(object.getString("id"));
                image=object.getString("thumb");

                category2.setCategoryimage(url+image);



                categoryArrayresultsList.add(category2);




            }

            search_results_filter.SetCategoryResults(categoryArrayresultsList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
