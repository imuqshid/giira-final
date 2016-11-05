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
import gira.cdap.com.giira.Task.GetUsersByNameTask;

/**
 * Created by Muqshid on 7/30/2016.
 */
public class NewTeam extends AppCompatActivity implements View.OnClickListener {

    private TextView pageTitle;
    private static final int ADD_MEMBER = 9000;
    EditText tname,description,members;
    int count =1;

    String [] nameA=null;
    String [] idA=null;
    String [] unidA=null;

    ImageButton homeIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_team);

        GetUsersByNameTask gTask=new GetUsersByNameTask(getApplicationContext(),"0",this);
        gTask.execute();


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


    public void fillArrays(String[] userA, String[] nameA, String[] unidA) {
        this.nameA=nameA;
        this.idA=userA;
        this.unidA=unidA;

        MultiAutoCompleteTextView multiAutoCompleteTextView=(MultiAutoCompleteTextView)findViewById(R.id.members_tf_t);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),R.layout.text,
                nameA);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        multiAutoCompleteTextView.setAdapter(adapter);
    }
}
