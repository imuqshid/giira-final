package gira.cdap.com.giira;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import gira.cdap.com.giira.Task.DownloadImageTask;
import gira.cdap.com.giira.Task.getAccommodation;
import gira.cdap.com.giira.Task.serverURL;

public class One_Accomadation extends AppCompatActivity {

String accid,name,address,description,thumb,website,phoneNo,Email;

    ImageView IVaccimage,IVaccwebsite;
    TextView TVAccname,TVAccDesc,TVAccAddress,TVAccWebsite,TVAccPhone,TVAccEmail;

    TextView textView_AB;

    String AccommdationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one__accomadation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);

        actionBar.setCustomView(cView);
//        TextView textView= (TextView) findViewById(R.id.ABtext);
//        textView.setText("Search Places");

        AccommdationName=getIntent().getExtras().getString("name");
        textView_AB = (TextView) findViewById(R.id.ABtext);
//        System.out.print(categoryName);
        textView_AB.setText(AccommdationName);

        ImageButton imageButton = (ImageButton)findViewById(R.id.action_bar_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initalize();

        accid=getIntent().getExtras().getString("id");
        getAccommodation getAccommodation=new getAccommodation(getApplicationContext(),accid,One_Accomadation.this);
        getAccommodation.execute();





    }

    public void initalize()
    {
        IVaccimage=(ImageView)findViewById(R.id.ivaccimage);
        TVAccname=(TextView)findViewById(R.id.tvaccname);
        TVAccDesc=(TextView)findViewById(R.id.tvaccdescription);
        TVAccAddress=(TextView)findViewById(R.id.tvaccaddress);
//        TVAccWebsite=(TextView)findViewById(R.id.tvaccwebsite);
//        TVAccPhone=(TextView)findViewById(R.id.tvaccphoneno);
//        TVAccEmail=(TextView)findViewById(R.id.tvaccemail);

    }

    public void setAccommodation(String name,String address,String descri, final String website,String phoneNO,String email,String thumb){
        this.name=name;
        this.address=address;
        this.description=descri;
        this.thumb= thumb;
        this.website=website;
        this.phoneNo=phoneNO;
        this.Email=email;

        TVAccname.setText(name);
        TVAccAddress.setText(address);
        TVAccDesc.setText(descri);
//        TVAccWebsite.setText(website);
//        TVAccPhone.setText(phoneNO);
//        TVAccEmail.setText(email);


//        String url = new String(thumb);



        new DownloadImageTask(IVaccimage).execute(serverURL.local_host_url+thumb);
//        InputStream is=new URL(url).openStream();
//        Bitmap bmp = BitmapFactory.decodeStream(is);
//        imageView.setImageBitmap(bmp);

//        IVaccwebsite.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setData(Uri.parse(website));
//                startActivity(intent);
//            }
//        });

    }

}
