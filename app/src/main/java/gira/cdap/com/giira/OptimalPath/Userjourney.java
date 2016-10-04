package gira.cdap.com.giira.OptimalPath;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import gira.cdap.com.giira.DisasterFragment;
import gira.cdap.com.giira.R;

public class Userjourney extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userjourney);

        ImageButton back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        DisasterFragment.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void SavePath(View view)
    {
        EditText start=(EditText) findViewById(R.id.textview1);
        EditText end=(EditText) findViewById(R.id.textview2);
        String startnew=start.getText().toString().trim();
        String Endnew=end.getText().toString().trim();
        if (!startnew.isEmpty() && !Endnew.isEmpty()) {
            // login user
            new SavePath(this).execute();;
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }

    }
    public class SavePath extends AsyncTask<String, Void, String> {

        private Context context;
        EditText start=(EditText) findViewById(R.id.textview1);
        EditText end=(EditText) findViewById(R.id.textview2);
        String startnew=start.getText().toString();
        String Endnew=end.getText().toString();

        public SavePath(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {
                data = "?startPlace=" + URLEncoder.encode(startnew, "UTF-8");
                data += "&Endplace=" + URLEncoder.encode(Endnew, "UTF-8");

                startnew="";
                Endnew="";
                link = "http://192.168.123.1/pushnotifications/savepath.php" + data;

                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {
                        Toast.makeText(context,"Your journey information saved successfully.", Toast.LENGTH_SHORT).show();
                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Your journey information already saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
