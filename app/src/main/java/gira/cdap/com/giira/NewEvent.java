package gira.cdap.com.giira;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import gira.cdap.com.giira.Task.AddEventTask;
import gira.cdap.com.giira.Task.GetUsersByNameTask;
import gira.cdap.com.giira.Task.serverURL;

/**
 * Created by Muqshid on 7/30/2016.
 */
public class NewEvent extends ActionBarActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    private TextView pageTitle;
    private ImageButton homeIcon;
    EditText name,description;
    MultiAutoCompleteTextView members;
    AutoCompleteTextView team,location;
    String list_userid,list_username;
    String [] nameA=null;
    String [] idA=null;
    String [] unidA=null;

    static final int DIALOG_ID_DATE = 0;
    static final int DIALOG_ID_TIME = 1;
    EditText date, time;
    Bitmap bitmap;
    ImageButton bmp;


    int year_x, month_x, day_x;
    int hour_x, minute_x;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int Loadimage = 1;
    private int PICK_IMAGE_REQUEST = 1;
    String picturePath;

    protected TimePickerDialog.OnTimeSetListener kTimePickerListner =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour_x = hourOfDay;
                    minute_x = minute;
                    String hour_s=Integer.toString(hour_x);
                    String minute_s=Integer.toString(minute_x);
                    if(hour_s.length()==1)
                    {
                        hour_s="0"+hour_s;
                    }
                    if(minute_s.length()==1)
                    {
                        minute_s="0"+minute_s;
                    }
                    Toast.makeText(NewEvent.this, hour_s + "-" + minute_s, Toast.LENGTH_LONG).show();
                    time.setText(hour_s + ":" + minute_s);
                }
            };

    private DatePickerDialog.OnDateSetListener dpickerListner =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    year_x = year;
                    month_x = monthOfYear + 1;
                    day_x = dayOfMonth;
                    String month_s=Integer.toString(month_x);
                    String day_s=Integer.toString(day_x);
                    if(month_s.length()==1)
                    {
                        month_s="0"+month_s;
                    }
                    if(day_s.length()==1)
                    {
                        day_s="0"+day_s;
                    }
                    Toast.makeText(NewEvent.this, year_x + "-" + month_s + "-" + day_s, Toast.LENGTH_LONG).show();

                    date.setText(year_x + "-" +month_s + "-" + day_s);

                }
            };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        GetUsersByNameTask gTask=new GetUsersByNameTask(getApplicationContext(),"0",this);
        gTask.execute();



        //---------------------

        AutoCompleteTextView location=(AutoCompleteTextView)findViewById(R.id.location_tf);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.text,
                LOCATIONS);

        location.setAdapter(adapter2);

        //-------------------------

        AutoCompleteTextView team=(AutoCompleteTextView)findViewById(R.id.team_tf);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.text,
                TEAM);

        team.setAdapter(adapter1);

        //------------------------



        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DATE);

        showDialogOnTextClick();
        showTimePickerDialogOnTextClick();
        bmp=(ImageButton)findViewById(R.id.plcimage);

        pageTitle = (TextView) findViewById(R.id.pageTitle);
        pageTitle.setText("Create Event");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        EventPlanningFragment.class);
                startActivity(i);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        getElements();
    }


    static final String[] USERS = new String[] { "Cristiano Ronaldo","David Beckham", "Sabhan","Chrishana","Zlatan Ibrahimovic","Rooney", "Shiva"
    };

    static final String[] TEAM = new String[] { "Team A","Team B", "Team C","Team D","Team E","Team F", "Team G"
    };

    static final String[] LOCATIONS = new String[] { "Sigiriya","Temple of the tooth", "Hortain Plains National Park","Yala National park","Adam's park","Kelaniya Raja Maha Viharaya", "Sinharaja Forest"
    };


    private void getElements() {

        name= (EditText) findViewById(R.id.name);
        description= (EditText) findViewById(R.id.description);
        location= (AutoCompleteTextView) findViewById(R.id.location_tf);
        team= (AutoCompleteTextView) findViewById(R.id.team_tf);
        members=(MultiAutoCompleteTextView)findViewById(R.id.members_tf);

    }

    public void showDialogOnTextClick() {
        date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID_DATE);
                    }
                }
        );
    }

    public void showTimePickerDialogOnTextClick() {
        time = (EditText) findViewById(R.id.time);
        time.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID_TIME);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID_TIME)
            return new TimePickerDialog(this, kTimePickerListner, hour_x, minute_x, false);
        else if (id == DIALOG_ID_DATE)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        return null;
    }

    public void upload(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picturePath=null;
        bitmap=null;

        try {
            Uri selectedImage=null;
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE ||requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                Log.d("Main Activity", "Gallery");
                selectedImage = data.getData();
            }

            if(selectedImage==null)
            {
                Log.d("Main Activity","Back");
                return;
            }

            Log.d("Main Activity", "Out");
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.d("Main Activity", picturePath);
            cursor.close();
          //  Toast.makeText(getApplicationContext(), "successfully added", Toast.LENGTH_SHORT).show();
            bitmap= BitmapFactory.decodeFile(picturePath);
            bmp.setImageBitmap(bitmap);
            bitmap= (Bitmap) data.getExtras().get("data");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("Main Activity","Exception");

        }
    }

    public void addEvent(View v){
        String namef=name.getText().toString();
        String descriptionf=description.getText().toString();
        String locationf=location.getText().toString();
        String teamf=team.getText().toString();
        String datef=date.getText().toString();
        String timef=time.getText().toString();
        String memberf=members.getText().toString();

        if(bitmap !=null)
        {
            String imagepathf=bitmap.toString();
            AddEventTask addEventTask=new AddEventTask(getApplicationContext(),namef,descriptionf,locationf,datef,timef,teamf,memberf,imagepathf,this);
            addEventTask.execute();
        }

        else

        {
            AddEventTask addEventTask=new AddEventTask(getApplicationContext(),namef,descriptionf,locationf,datef,timef,teamf,memberf,this);
            addEventTask.execute();
        }

    }

    public void fillArrays(String[] userA, String[] nameA, String[] unidA) {
        this.nameA=nameA;
        this.idA=userA;
        this.unidA=unidA;

        MultiAutoCompleteTextView members=(MultiAutoCompleteTextView)findViewById(R.id.members_tf);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.text,
                nameA);
        members.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        members.setAdapter(adapter);
    }
}
