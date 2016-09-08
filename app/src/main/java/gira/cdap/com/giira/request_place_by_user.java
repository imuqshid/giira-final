package gira.cdap.com.giira;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import gira.cdap.com.giira.Task.InsertPlaceTask;

public class request_place_by_user extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    EditText etplace,etdisc,etaddress,etpalcekey;
    String placename,address,type,discription;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int Loadimage = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "http://maps.google.com/maps/api";
    private static final String TYPE_AUTOCOMPLETE = "/geocode";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyDhFGUWlyd0KsjPQ59ATr-yL0bQKujHmeg";

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private File fileUri; // file url to store image/video

    private ImageView imgPreview;
    private VideoView videoPreview;
    private Button btnCapturePicture, btnRecordVideo;
    Bitmap bmp1;
    Bitmap bmp2;
    Bitmap bmp3;
    Bitmap bmp4;

//    String picturePath;

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;
    ImageButton palceimages;
    ImageButton palceimage1;
    ImageButton palceimage2;
    ImageButton palceimage3;
    ImageButton palceimage4;

    String firstpath,secondpath,thirdpath,fourthpath;

    private Bitmap bitmap;
    AutoCompleteTextView autoCompleteTextView;

    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_place_by_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);

        actionBar.setCustomView(cView);
        TextView textView= (TextView) findViewById(R.id.ABtext);
        textView.setText("Add New Place");

        ImageButton imageButton = (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initalize();


    }

    public void initalize()
    {
        etplace=(EditText)findViewById(R.id.etplace);
//        etaddress=(EditText)findViewById(R.id.etaddress);
        etdisc=(EditText)findViewById(R.id.etdisc);
        etpalcekey=(EditText)findViewById(R.id.ettype);
        palceimage1=(ImageButton)findViewById(R.id.plcimage1);
        palceimage2=(ImageButton)findViewById(R.id.plcimage2);
        palceimage3=(ImageButton)findViewById(R.id.plcimage3);
        palceimage4=(ImageButton)findViewById(R.id.plcimage4);
        palceimages=(ImageButton)findViewById(R.id.plcimage);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteaddress);
        autoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.listitemall, R.id.textView1));
//        autoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.listitemall));
        autoCompleteTextView.setOnItemClickListener(this);

//        textView = (TextView) findViewById(R.id.text_view);
    }

    //insert places onclick function
    public void request(View view)
    {
        InsertPlaceTask insertPlaceTask;
        placename=etplace.getText().toString();
//        address=etaddress.getText().toString();
        address=autoCompleteTextView.getText().toString();
        type=etpalcekey.getText().toString();
        discription=etdisc.getText().toString();


        insertPlaceTask = new InsertPlaceTask(getApplicationContext(), placename, discription, address, type, firstpath, secondpath, thirdpath, fourthpath, request_place_by_user.this);
        insertPlaceTask.execute();

    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(request_place_by_user.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result=Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    captureImage();

                } else if (items[item].equals("Choose from Library")) {
                    selectgalery();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public  void selectgalery()
    {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        Intent intent = new Intent(request_place_by_user.this, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 4);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0, l = images.size(); i < l; i++) {
                stringBuffer.append(images.get(i).path + "\n");
//                picturePath=(images.get(i).path+"\n");

            }
//            textView.setText(stringBuffer.toString());
//            textView.setVisibility(View.VISIBLE);
//            palceimages.setVisibility(View.GONE);
            StringTokenizer tokens = new StringTokenizer(stringBuffer.toString(), "\n");
            firstpath = tokens.nextToken();
//            String secondpath = tokens.nextToken();
            secondpath = tokens.hasMoreTokens() ? tokens.nextToken() : null;
            thirdpath = tokens.hasMoreTokens() ? tokens.nextToken() : null;
            fourthpath = tokens.hasMoreTokens() ? tokens.nextToken() : null;
            bmp1= BitmapFactory.decodeFile(firstpath);
            bmp2= BitmapFactory.decodeFile(secondpath);
            bmp3= BitmapFactory.decodeFile(thirdpath);
            bmp4= BitmapFactory.decodeFile(fourthpath);

            palceimage1.setImageBitmap(bmp1);
            palceimage2.setImageBitmap(bmp2);
            palceimage3.setImageBitmap(bmp3);
            palceimage4.setImageBitmap(bmp4);
            palceimages.setVisibility(View.INVISIBLE);
            palceimage1.setVisibility(View.VISIBLE);
            palceimage2.setVisibility(View.VISIBLE);
            palceimage3.setVisibility(View.VISIBLE);
            palceimage4.setVisibility(View.VISIBLE);


//            bmp= (Bitmap) data.getExtras().get("data");
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        picturePath=null;
//        bmp=null;
//
//
//        try {
//            Uri selectedImage=null;
//            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE ||requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
//                Log.d("Main Activity", "Gallery");
//                selectedImage = data.getData();
//            }
//
//            if(selectedImage==null)
//            {
//                Log.d("Main Activity","Back");
//                return;
//            }
//
//            Log.d("Main Activity", "Out");
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            picturePath = cursor.getString(columnIndex);
//            Log.d("Main Activity", picturePath);
//            cursor.close();
//
////scale the image by factor 2
//
//
//            bmp= BitmapFactory.decodeFile(picturePath);
//
//            palceimages.setImageBitmap(bmp);
//            bmp= (Bitmap) data.getExtras().get("data");
//
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Log.d("Main Activity","Exception");
//
//        }
//    }

    public void uploadmultiimage(View view)
    {
        selectImage();
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFile(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }




    private void previewCapturedImage() {
        try {
            // hide video preview
            videoPreview.setVisibility(View.GONE);

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating file uri to store image/video
     */

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }



    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        ArrayList resultgemorart=null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);

            sb.append("?address=" + URLEncoder.encode(input, "utf8")+"&sensor=false&components=country%3alk");

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");


            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("formatted_address"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
//                resultgemorart.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
            }
        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        String str = (String) adapterView.getItemAtPosition(position);

        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

    }



    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int resource,
                                               int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();


                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}
