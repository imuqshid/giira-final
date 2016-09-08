package gira.cdap.com.giira;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import gira.cdap.com.giira.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourFragment extends Activity {

    private ImageButton homeIcon;
    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tour);

        pageTitle = (TextView)findViewById(R.id.pageTitle);
        pageTitle.setText("Tour Planning");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}
