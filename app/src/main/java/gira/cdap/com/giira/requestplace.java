package gira.cdap.com.giira;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gira.cdap.com.giira.Task.InsertPlaceTask;


public class requestplace extends AppCompatActivity {

    EditText name1,desc1,address1,tag1,lati1,longi1,thumb1;
    Button insert;
    String name,desc,address,tag,lati,longi,thumb;
    InsertPlaceTask insertTask1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestplace);

        initialize();

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

    public void insert(View view)
    {
//Log.d("sabu", title);
        name=name1.getText().toString();
        desc=desc1.getText().toString();
        address=address1.getText().toString();
        tag=tag1.getText().toString();
        lati=lati1.getText().toString();
        longi=longi1.getText().toString();
        thumb=thumb1.getText().toString();
//

        Toast.makeText(this, "test", Toast.LENGTH_SHORT)
                .show();


    }

    public void initialize()
    {
        name1=(EditText)findViewById(R.id.editText);
        desc1=(EditText)findViewById(R.id.editText2);
        address1=(EditText)findViewById(R.id.editText3);
        tag1=(EditText)findViewById(R.id.editText4);
        lati1=(EditText)findViewById(R.id.editText5);
        longi1=(EditText)findViewById(R.id.editText6);
        thumb1=(EditText)findViewById(R.id.editText7);
        insert=(Button)findViewById(R.id.button);

    }

}
