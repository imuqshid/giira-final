package gira.cdap.com.giira.Task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gira.cdap.com.giira.Service.JSONParser;
import gira.cdap.com.giira.Service.ServiceHandler;
import gira.cdap.com.giira.request_place_by_user;

/**
 * Created by Perceptor on 6/8/2016.
 */
public class InsertPlaceTask extends AsyncTask<String,Void,String> {

    ServiceHandler serviceHandler ;
    InputStream inputStream;
    JSONParser parsing;
    String name,description,address,tag,path,path2,path3,path4,encodedImage,encodedImage2,encodedImage3,encodedImage4;
    request_place_by_user  requestPlaceByUser;
    Bitmap bit,bit2,bit3,bit4;

    Context context;


    public InsertPlaceTask(Context context, String name, String description, String address,String tag,String path,String path2,String path3,String path4,request_place_by_user requestPlaceByUser)
    {
        this.context=context;
        this.name=name;
        this.description=description;
        this.address=address;
        this.tag=tag;
        this.path=path;
        this.path2=path2;
        this.path3=path3;
        this.path4=path4;
        this.requestPlaceByUser=requestPlaceByUser;
    }
    @Override
    protected String doInBackground(String... params) {
        String result = null;

        if(path==null) {
            encodedImage=null;

        }
        else
        {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bit = BitmapFactory.decodeFile(path,
                    options);
            Bitmap.createScaledBitmap(bit, (int) (bit.getWidth() * 2), (int) (bit.getHeight() * 0.2), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byte_arr = stream.toByteArray();


//            image.compress(compressFormat, quality, byteArrayOS);
//            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
            // Encode Image to String
            encodedImage = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        }
        if(path2==null) {
            encodedImage2=null;

        }
        else
        {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bit2 = BitmapFactory.decodeFile(path2,
                    options);
            Bitmap.createScaledBitmap(bit2, (int) (bit2.getWidth() * 2), (int) (bit2.getHeight() * 0.2), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bit2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byte_arr = stream.toByteArray();


//            image.compress(compressFormat, quality, byteArrayOS);
//            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
            // Encode Image to String
            encodedImage2 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        }
        if(path3==null) {
            encodedImage3=null;

        }
        else
        {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bit3 = BitmapFactory.decodeFile(path3,
                    options);
            Bitmap.createScaledBitmap(bit3, (int) (bit3.getWidth() * 2), (int) (bit3.getHeight() * 0.2), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bit3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byte_arr = stream.toByteArray();


//            image.compress(compressFormat, quality, byteArrayOS);
//            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
            // Encode Image to String
            encodedImage3 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        }
        if(path4==null) {
            encodedImage4=null;

        }
        else
        {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bit4 = BitmapFactory.decodeFile(path4,
                    options);
            Bitmap.createScaledBitmap(bit4, (int) (bit4.getWidth() * 2), (int) (bit4.getHeight() * 0.2), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bit4.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byte_arr = stream.toByteArray();


//            image.compress(compressFormat, quality, byteArrayOS);
//            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
            // Encode Image to String
            encodedImage4 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        }


        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair("name", name));
        value.add(new BasicNameValuePair("description", description));
        value.add(new BasicNameValuePair("address", address));
        value.add(new BasicNameValuePair("tag", tag));
        value.add(new BasicNameValuePair("encoded_string", encodedImage));
        value.add(new BasicNameValuePair("encoded_string2",encodedImage2));
        value.add(new BasicNameValuePair("encoded_string3",encodedImage3));
        value.add(new BasicNameValuePair("encoded_string4",encodedImage4));


        android.util.Log.d("Task","InsertPlaceTask");
        serviceHandler = new ServiceHandler();
        inputStream = serviceHandler.makeServiceCall(
                serverURL.local_host_url+"giira/index.php/places/giiraplaces",2,
                value);
        parsing = new JSONParser();

        try {
            JSONObject json = parsing.getJSONFromResponse(inputStream);
            result = String.valueOf(json.getBoolean("response"));
//            System.out.println(result);

//            if (json.getString("response").matches("true")) {
//                result = "Successful Added";
//            } else {
//                result = "Failed";
//            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result.matches("true"))

        {
            if(requestPlaceByUser!=null)
            {
                Toast.makeText(context, "successfully added", Toast.LENGTH_SHORT).show();
                //insertPlace.showLogin();
            }

//			if(availableSeats!=null)
//			{
//				availableSeats.setPassengerUserNameU(uname);
//				availableSeats.seatTaskUpdateCall();
//			}
        }
        else
        {
//			if(availableSeats!=null)
//			{
//				Toast.makeText(availableSeats.getApplicationContext(), "Booking failed", Toast.LENGTH_SHORT).show();
//			}
            if(requestPlaceByUser!=null)
            {
                Toast.makeText(context, "Insertion Failed", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
