package gira.cdap.com.giira;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.ResourceBundle;

import gira.cdap.com.giira.Task.AddFriendTask;
import gira.cdap.com.giira.Task.GetFriendStatus;
import gira.cdap.com.giira.Task.GetUsersByIdTask;
import gira.cdap.com.giira.Task.RespondFriendTask;
import gira.cdap.com.giira.activity.LoginActivity;
import gira.cdap.com.giira.helper.SQLiteHandler;
import gira.cdap.com.giira.helper.SessionManager;

public class UserProfileActivity extends AppCompatActivity {
    Button btn_friend;
    PopupMenu popupMenu;
    SessionManager session;
    HashMap<String,String> user;
    SQLiteHandler db;
    String uid;

    TextView name_tv,email_tv;
    String id,name,email;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        btn_friend=(Button)findViewById(R.id.btn_friend);
        popupMenu=new PopupMenu(UserProfileActivity.this,btn_friend);

        TextView name_tv= (TextView)findViewById(R.id.username1);
        TextView email_tv= (TextView)findViewById(R.id.useremail);

        ImageButton homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        session=new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // Fetching user details from sqlite

        id=getIntent().getExtras().getString("id");

        user=db.getUserDetails();
        uid=user.get("uid");

        //Toast.makeText(this, id+ ""+ uid, Toast.LENGTH_SHORT).show();
        GetFriendStatus sTask=new GetFriendStatus(getApplicationContext(),uid,id,UserProfileActivity.this);
        sTask.execute();

        initalize();


        GetUsersByIdTask getUsersByIdTask=new GetUsersByIdTask(getApplicationContext(),id,UserProfileActivity.this);
        getUsersByIdTask.execute();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String text=item.getTitle().toString();
               if(text.matches("Accept Request"))
               {
                   RespondFriendTask rTask=new RespondFriendTask(getApplicationContext(),uid,id,"1",UserProfileActivity.this);
                   rTask.execute();
               }
               else if(text.matches("Decline Request"))
               {
                   RespondFriendTask rTask=new RespondFriendTask(getApplicationContext(),uid,id,"2",UserProfileActivity.this);
                   rTask.execute();
               }
               else if(text.matches("Cancel Request"))
               {
                   RespondFriendTask rTask=new RespondFriendTask(getApplicationContext(),uid,id,"2",UserProfileActivity.this);
                   rTask.execute();
               }
               else if(text.matches("Unfriend"))
               {
                   RespondFriendTask rTask=new RespondFriendTask(getApplicationContext(),uid,id,"2",UserProfileActivity.this);
                   rTask.execute();
               }
                return true;
            }
        });

    }

    public void buttonClick(View v)
    {
        String text=btn_friend.getText().toString();

        if(text.matches("Friends"))
        {
            popupMenu.getMenu().clear();
            popupMenu.getMenu().add("Unfriend");
            popupMenu.show();
        }

        else if(text.matches("Add Friend"))
        {
            AddFriendTask rTask=new AddFriendTask(getApplicationContext(),uid,id,UserProfileActivity.this);
            rTask.execute();
        }

        else if(text.matches("Respond"))
        {
            popupMenu.getMenu().clear();
            popupMenu.getMenu().add("Accept Request");
            popupMenu.getMenu().add("Decline Request");
            popupMenu.show();
        }

        else if(text.matches("Request Sent"))
        {
            popupMenu.getMenu().clear();
            popupMenu.getMenu().add("Cancel Request");
            popupMenu.show();
        }

    }


    public void setButtonText(String status)
    {
        switch (status)
        {
            case "friends":
                btn_friend.setText("Friends");
                break;
            case "not friends":
                btn_friend.setText("Add Friend");
                break;
            case "respond":
                btn_friend.setText("Respond");
                break;
            case "requested":
                btn_friend.setText("Request Sent");
                break;
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void initalize() {
        name_tv = (TextView) findViewById(R.id.username1);
        email_tv = (TextView) findViewById(R.id.useremail);
    }


    public void set(String name, String email) {

        this.name=name;
        this.email=email;
        
        name_tv.setText(name);
        email_tv.setText(email);;
    }


    public void set(String name) {

    }
}
