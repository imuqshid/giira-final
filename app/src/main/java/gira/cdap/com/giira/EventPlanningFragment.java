package gira.cdap.com.giira;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;

import gira.cdap.com.giira.Task.GetEventDetailsTask;
import gira.cdap.com.giira.Task.GetEventTask;


public class EventPlanningFragment extends AppCompatActivity implements View.OnClickListener,OnDateSelectedListener {

    private ImageButton homeIcon;
    private TextView pageTitle;
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    String[] idAr=null;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private MaterialCalendarView calView;
    private ListView listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_event_planning);


        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        calView=(MaterialCalendarView)findViewById(R.id.calendarView);
        listEvents=(ListView)findViewById(R.id.listEvents);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        pageTitle = (TextView)findViewById(R.id.pageTitle);
        pageTitle.setText("Event");

        homeIcon = (ImageButton) findViewById(R.id.back);
        homeIcon.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        calView.setOnDateChangedListener(this);


        listEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();
                String ids=Long.toString(id);
                int idd=Integer.parseInt(ids);
                String ido=idAr[idd];

                GetEventDetailsTask gtask = new GetEventDetailsTask(getApplicationContext(),ido,EventPlanningFragment.this);
                gtask.execute();


            }
        });

    }



    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        GetEventTask gTask=new GetEventTask(getApplicationContext(),getSelectedDatesString(),this);
        gTask.execute();
    }

    private String getSelectedDatesString() {
        CalendarDay date = calView.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab2:

                Log.d("Clicked", "Fab 2");

                animateFAB();

                Intent i = new Intent(getApplicationContext(),
                        NewTeam.class);
                startActivity(i);
                finish();

                break;
            case R.id.fab1:

                Log.d("Clicked", "Fab 1");

                animateFAB();

                Intent ii = new Intent(getApplicationContext(),
                        NewEvent.class);
                startActivity(ii);
                finish();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    public void loadList(String[] fullArr,String[] idArr) {
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fullArr);
        listEvents.setAdapter(adapter);
        idAr=idArr;

    }


    public void loadPopup(String time, String name, String date, String description, String location, String team, String members) {

        final Dialog dialogCustom = new Dialog(this);

        dialogCustom.setContentView(R.layout.view_event);
        dialogCustom.setTitle("View Event Details");

        TextView namei = (TextView) dialogCustom.findViewById(R.id.name);
        TextView descriptioni=(TextView)dialogCustom.findViewById(R.id.description);
        TextView locationi=(TextView)dialogCustom.findViewById(R.id.location);
        TextView datei=(TextView)dialogCustom.findViewById(R.id.date);
        TextView timei=(TextView)dialogCustom.findViewById(R.id.time);
        TextView teami=(TextView)dialogCustom.findViewById(R.id.team);
        TextView membersi=(TextView)dialogCustom.findViewById(R.id.members);
        Button can=(Button)dialogCustom.findViewById(R.id.event_cancel);

        can.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialogCustom.dismiss();
            }
        });

        namei.setText(name);
        descriptioni.setText(description);
        locationi.setText(location);
        datei.setText(date);
        timei.setText(time);
        teami.setText(team);
        membersi.setText(members);

        dialogCustom.show();
    }
}
