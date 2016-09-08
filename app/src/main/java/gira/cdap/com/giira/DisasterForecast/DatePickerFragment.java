package gira.cdap.com.giira.DisasterForecast;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;



import java.util.Calendar;

import gira.cdap.com.giira.R;

/**
 * Created by siva on 9/5/2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {
    public  int s;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        EditText tv1= (EditText) getActivity().findViewById(R.id.textview1);
        tv1.setText( view.getYear()+"-"+(view.getMonth()+1)+"-"+view.getDayOfMonth());
        s=view.getMonth();

    }

}
