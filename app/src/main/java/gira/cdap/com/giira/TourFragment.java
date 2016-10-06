package gira.cdap.com.giira;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import gira.cdap.com.giira.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourFragment extends Activity {

    private ImageButton homeIcon;
    TextView pageTitle;
    private static final int PLACE_PICKER_REQUEST = 1;


    Animation fade_in, fade_out;
    ViewFlipper viewFlipper1;

    private TextView btnLinkfea;
    public ImageButton btnLinknear;

    public ImageButton btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tour);

        pageTitle = (TextView) findViewById(R.id.pageTitle);
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
        btn1 = (ImageButton) findViewById(R.id.tour);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TourFragment.this, TourPlanning.class);
                startActivity(intent);

            }
        });
        btn2 = (ImageButton) findViewById(R.id.emer);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TourFragment.this, Emergancy.class);
                startActivity(intent);

            }
        });
        btnLinknear = (ImageButton) findViewById(R.id.image1);

        btnLinknear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();

                    Intent intent = intentBuilder.build(TourFragment.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        viewFlipper1 = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper2);
        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper1.setInAnimation(fade_in);
        viewFlipper1.setOutAnimation(fade_out);
//sets auto flipping
        viewFlipper1.setAutoStart(true);

        viewFlipper1.setFlipInterval(4000);
        viewFlipper1.startFlipping();

        btnLinkfea = (TextView) findViewById(R.id.btnLinkfea);

        // Link to Register Screen
        // Link to Register Screen
        btnLinkfea.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Featureplace.class);
                startActivity(i);
                finish();
            }
        });

    }
}
