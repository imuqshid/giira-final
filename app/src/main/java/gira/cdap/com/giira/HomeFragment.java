package gira.cdap.com.giira;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;

import gira.cdap.com.giira.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Activity {

    private ImageButton homeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);


    }

}
