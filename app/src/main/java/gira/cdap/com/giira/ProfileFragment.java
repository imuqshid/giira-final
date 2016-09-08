package gira.cdap.com.giira;


import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;

import gira.cdap.com.giira.Task.UploadProfileImageTask;
import gira.cdap.com.giira.activity.LoginActivity;
import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;
/*import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;*/

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends TabActivity {
    /*private SQLiteHandler db;
    private SessionManager session;*/

    private ImageButton homeIcon;
    private TextView pageTitle;
    private Button btnLogout;
    private TextView txtName;
    private TextView txtEmail;

//    public String username;

    Bitmap bitmap;
    ImageButton bmp;

    private SQLiteHandler db;
    private SessionManager session;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int Loadimage = 1;
    private int PICK_IMAGE_REQUEST = 1;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        TabHost tabHost = getTabHost();

        // Tab for Activity
        TabHost.TabSpec activity = tabHost.newTabSpec("Activity");
        // setting Title and Icon for the Tab
        activity.setIndicator("Activity", getResources().getDrawable(R.drawable.activity));
        Intent activityIntent = new Intent(this, ProfileActivityFrame.class);
        activity.setContent(activityIntent);

        // Tab for Friends
        TabHost.TabSpec friend = tabHost.newTabSpec("Friends");
        friend.setIndicator("Friends", getResources().getDrawable(R.drawable.friends));
        Intent friendsIntent = new Intent(this, FriendRequestFrame.class);
        friend.setContent(friendsIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(activity); // Adding activity tab
        tabHost.addTab(friend); // Adding friends tab


        txtName = (TextView) findViewById(R.id.username1);
        txtEmail = (TextView) findViewById(R.id.useremail);
        btnLogout = (Button) findViewById(R.id.button_logout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

//        username=name;
//        Intent intentusername=new Intent(this,one_place.class);
//        intentusername.putExtra("username", name);

//        startActivity(intentusername);

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        pageTitle = (TextView)findViewById(R.id.pageTitle);
        pageTitle.setText("Profile");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    public void upload(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

        if(bitmap !=null)
        {
            String imagepathf=bitmap.toString();
            UploadProfileImageTask uploadProfileImageTask=new UploadProfileImageTask(getApplicationContext(),imagepathf,this);
            uploadProfileImageTask.execute();
        }
        else
        {
            UploadProfileImageTask uploadProfileImageTask=new UploadProfileImageTask(getApplicationContext(),this);
            uploadProfileImageTask.execute();
        }
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ProfileFragment.this, LoginActivity.class);
        startActivity(intent);
        finish();
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



}
