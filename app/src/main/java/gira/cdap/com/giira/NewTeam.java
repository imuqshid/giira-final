package gira.cdap.com.giira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import gira.cdap.com.giira.Task.AddTeamTask;

/**
 * Created by Muqshid on 7/30/2016.
 */
public class NewTeam extends AppCompatActivity implements View.OnClickListener {

    private TextView pageTitle;
    private static final int ADD_MEMBER = 9000;
    EditText tname,description,members;
    int count =1;

    ImageButton homeIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_team);

        MultiAutoCompleteTextView multiAutoCompleteTextView=(MultiAutoCompleteTextView)findViewById(R.id.members_tf);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.text,
                USERS);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        multiAutoCompleteTextView.setAdapter(adapter);


        pageTitle = (TextView)findViewById(R.id.pageTitle);
        pageTitle.setText("New Team");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        EventPlanningFragment.class);
                startActivity(i);
                finish();


            }
        });

        getElements();

        /*LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout);
        TextView tv = new TextView(this);
        tv.setVisibility(View.VISIBLE);
        tv.setText("Add member");
        tv.setTextSize(15);
        tv.setPaddingRelative(25,0,25,0);
        tv.setTextColor(Color.BLUE);
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        tv.setId(ADD_MEMBER);
        tv.setOnClickListener(this);

        ll.addView(tv);*/

    }

    static final String[] USERS = new String[] { "Cristiano Ronaldo","David Beckham", "Sabhan","Chrishana","Zlatan Ibrahimovic","Rooney", "Shiva"

    };


    @Override
    public void onClick(View v) {

    }

    private void getElements() {

        tname= (EditText) findViewById(R.id.name);
        description= (EditText) findViewById(R.id.description);
        members = (MultiAutoCompleteTextView)findViewById(R.id.members_tf);
    }

    public void addTeam(View v){
        String namef=tname.getText().toString();
        String descriptionf=description.getText().toString();
        String membersf=members.getText().toString();

        AddTeamTask addTeamTask = new AddTeamTask(getApplicationContext(), namef, descriptionf, membersf, this);
        addTeamTask.execute();


    }
}
