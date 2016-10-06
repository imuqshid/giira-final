package gira.cdap.com.giira.DisasterForecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import gira.cdap.com.giira.DisasterFragment;
import gira.cdap.com.giira.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.Spinner;

import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import java.io.IOException;

public class DisasterForecastActivity extends AppCompatActivity implements OnItemSelectedListener{
    String JSON_STRING;
    List<Double> Probability =  new ArrayList<Double>();
    public Spinner spinner;
    public String spinVal;
    public EditText Startdate;
    public EditText Enddate;
    public String StartdateS;
    public String EnddateS;
    List<Probability> Prob = new ArrayList<Probability>();
    String text;
    String Startmonth;
    public  String Smonth;
    public  String Emonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_forecast);
        spinner=(Spinner) findViewById(R.id.spinner);

        DatePickerFragment d=new DatePickerFragment();
        DatePickerFragmentEnd e=new DatePickerFragmentEnd();
        int Startmonthi=d.s;
        int Endmonthi=e.e;
        Smonth=String.valueOf(Startmonthi);
        Emonth=String.valueOf(Endmonthi);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void DisasterForecast(View view)
    {
        try {
            EditText Startdatenew = (EditText) findViewById(R.id.textview1);
            EditText Enddatenew = (EditText) findViewById(R.id.textview2);

            String a = Startdatenew.getText().toString();
            String b = Enddatenew.getText().toString();
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            String[] s = a.split("-");
            String[] r = b.split("-");
            final int l = Integer.valueOf(s[0]);
            final int m = Integer.valueOf(s[1]);
            final int n = Integer.valueOf(s[2]);
            final int p = Integer.valueOf(r[0]);
            final int q = Integer.valueOf(r[1]);
            final int r2 = Integer.valueOf(r[2]);
            if(day>n||day>r2)
            {
                if((month+1)>=m ||(month+1)>=q )
                {
                    if(year >= l || year >=p) {
                        Toast.makeText(getApplicationContext(),
                                "Please select dates after current day", Toast.LENGTH_LONG)
                                .show();
                    }
                }

            }
            else if((month+1)>=m ||(month+1)>q ) {
                if (year >= l || year >= p) {
                    Toast.makeText(getApplicationContext(),
                            "Please select dates after current day", Toast.LENGTH_LONG)
                            .show();
                }
            }
            else if (l > p) {


                Toast.makeText(getApplicationContext(),
                        "End date must come after start date", Toast.LENGTH_LONG)
                        .show();
            }
            else if (m > q) {
                Toast.makeText(getApplicationContext(),
                        "End date must come after start date", Toast.LENGTH_LONG)
                        .show();

            }
            else if (n > r2) {
                if(m >=q) {
                    Toast.makeText(getApplicationContext(),
                            "End date must come after start date", Toast.LENGTH_LONG)
                            .show();

                }
            } else {
                new BackgroundTask().execute();



            }
        }
        catch(Exception e)
        {

            Toast.makeText(getApplicationContext(),
                    "You should select start and end date to get disaster forecast", Toast.LENGTH_LONG)
                    .show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showDatePickerDialogEnd(View v) {
        DialogFragment newFragment = new DatePickerFragmentEnd();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        //String text= ((Spinner) findViewById((R.id.spinner))).getSelectedItem().toString();
        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        String text = mySpinner.getSelectedItem().toString();
        EditText Startdatenew= (EditText)findViewById(R.id.textview1);
        EditText Enddatenew= (EditText)findViewById(R.id.textview2);
        String a=Startdatenew.getText().toString();
        String b=Enddatenew.getText().toString();
        String [] c=a.split("-");
        String [] d=b.split("-");


        String json_url;
        String District=text;


        @Override
        protected void onPreExecute() {
            json_url="http://192.168.123.1/DistrictNames/PredictData.php?"+"DistricName="+District+"&startmonth="+c[1]+"&endmonth="+d[1];
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String res ) {
            // super.onPostExecute(aVoid);
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;


        //List<DisasterPlace> location = new ArrayList<DisasterPlace>();
        JSONObject place = new JSONObject(data);
        JSONArray results = place.getJSONArray("result");
        try {
            for (int i = 0; i < results.length(); i++) {
                JSONObject land = results.getJSONObject(i);
                Probability p=new Probability();

                p.Pone=land.getDouble("Probability_Final1");
                p.Ptow=land.getDouble("Probability_Final2");
                Prob.add(p);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try {
            for (Probability p : Prob) {

                if(p.Pone >= 6.5 || p.Ptow >=6.5 )
                {
                    new AlertDialog.Builder(this).setTitle("Disaster Forecast").setMessage("Your destination district has high disaster risk,please change your trip plan").setNegativeButton("ok",null).show();
                    break;
                }
                else{
                    new AlertDialog.Builder(this).setTitle("Disaster Forecast").setMessage("you can carry on trip").setNegativeButton("ok",null).show();
                    break;
                }


            }
            if(Prob.isEmpty())
            {
                new AlertDialog.Builder(this).setTitle("Disaster Forecast").setMessage("you can carry on trip").setNegativeButton("ok", null).show();
            }
            else
            {
                Prob.clear();
            }
        }
        catch (Exception e)
        {
            System.out.print("error occur at marking");
        }

    }
}
