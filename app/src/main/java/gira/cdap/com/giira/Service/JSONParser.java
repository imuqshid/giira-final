package gira.cdap.com.giira.Service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author methan
 *
 */
public class JSONParser { // to communicate with local server

    InputStream is = null;
    static JSONArray jarray = null;
    static String json = "";


    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromResponse(InputStream is) throws IOException {
        JSONObject jObj = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.d("memoryTest", "bc");

            Log.d("Response", sb.toString());
            jObj = new JSONObject(sb.toString());

            Log.d("memoryTest", "gc");

        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;

    }

//	public JSONArray getJSONFromUrl(String url) {
//
//		StringBuilder builder = new StringBuilder();
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
//        HttpResponse response=null;
//		try {
//			response = client.execute(httpGet);
//			StatusLine statusLine = response.getStatusLine();
//			int statusCode = statusLine.getStatusCode();
//			if (statusCode == 200) {
//				HttpEntity entity = response.getEntity();
//				InputStream content = entity.getContent();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//				String line;
//				while ((line = reader.readLine()) != null) {
//					builder.append(line);
//				}
//			} else {
//				Log.e("==&gt;", "Failed to download file");
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// Parse String to JSON object
//		try {
//			jarray = new JSONArray( builder.toString());
//		} catch (JSONException e) {
//			Log.e("JSON Parser", "Error parsing data " + e.toString());
//		}
//
//		// return JSON Object
//		return jarray;
//
//	}

}
