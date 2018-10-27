package com.illuminati.www.medrec;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Mojito9542
 * @verson Created on 28-10-2018.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    int hour,min;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current time as the default values for the time picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //Create and return a new instance of TimePickerDialog
            return new TimePickerDialog(getActivity(),this, hour, min,
                    DateFormat.is24HourFormat(getActivity()));
        }

        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            long milliseconds = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hourOfDay) + TimeUnit.MINUTES.toSeconds(minute));
            long curr= TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hour) + TimeUnit.MINUTES.toSeconds(min));
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", new Date().getTime()-curr+milliseconds);
            intent.putExtra("endTime", new Date().getTime()-curr+milliseconds+60*60*1000);


            EditText e=(EditText)getActivity().findViewById(R.id.name);
            intent.putExtra("title", "Take "+ e.getText().toString().trim());
            startActivity(intent);

        }




    }

