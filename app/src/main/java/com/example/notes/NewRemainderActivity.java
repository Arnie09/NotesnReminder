package com.example.notes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewRemainderActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    TextView Text;
    DateFormat df;
    TimePicker pickerTime;
    DatePicker pickerDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remainder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView appname = findViewById(R.id.appname);
        appname.setText("Add New Remainder");

        Text = findViewById(R.id.remainder_text);
        pickerTime = findViewById(R.id.time_);
        pickerDate = findViewById(R.id.datetime);

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setMinute(now.get(Calendar.MINUTE));

        databaseHandler = new DatabaseHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Save) {

            String remainder_text = Text.getText().toString();
            //make database entry and calender entry
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            String currDate = df.format(dateobj);
            long id_ = databaseHandler.insertData(remainder_text,currDate,"REMINDERS");
            Log.i("ID",String.valueOf(id_));

            //making alarm manager entry first
            Calendar current = Calendar.getInstance();

            Calendar cal = Calendar.getInstance();
            cal.set(pickerDate.getYear(),
                    pickerDate.getMonth(),
                    pickerDate.getDayOfMonth(),
                    pickerTime.getHour(),
                    pickerTime.getMinute(),
                    00);

            if (cal.compareTo(current) <= 0) {
                //The set Date/Time already passed
                Toast.makeText(getApplicationContext(),
                        "Invalid Date/Time",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Reminder Added", Toast.LENGTH_SHORT).show();
                setAlarm(cal,remainder_text,id_);
            }
        }
            return super.onOptionsItemSelected(item);
    }
    private void setAlarm(Calendar targetCal,String remainder_text,long id){

        Intent intent = new Intent(NewRemainderActivity.this, AlarmReceiver.class);
        intent.putExtra("Message",remainder_text);
        intent.putExtra("ID",String.valueOf(id));
        int RQS_1 = (int)id;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NewRemainderActivity.this, RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        Log.i("new_reminder: ","set");
    }

}
