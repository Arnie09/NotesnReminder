package com.example.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add_notes:

                    Noteslayout.setVisibility(View.VISIBLE);
                    Remainderlayout.setVisibility(View.INVISIBLE);
                    Cursor result = databaseHandler.getallData();
                    if(result.getCount() == 0){
                        Log.i("MAIN_ACTIVITY","NO DATA");
                    }
                    else{
                        StringBuffer buff = new StringBuffer();
                        while(result.moveToNext()){
                            buff.append("Serial: "+result.getString(0)+"\n");
                            buff.append(result.getString(1)+"\n");
                            buff.append("Date: "+result.getString(2)+"\n\n");
                        }
                        Log.i("MAIN_ACTIVITY",buff.toString());
                    }
                    TakeNotes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),NoteTaking.class));
                        }
                    });
                    return true;
                case R.id.remainder:
                    Noteslayout.setVisibility(View.INVISIBLE);
                    Remainderlayout.setVisibility(View.VISIBLE);
                    StartTimer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "Timer Started", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
            }
            return false;
        }
    };
    DatabaseHandler databaseHandler;
    Button TakeNotes;
    Button StartTimer;
    RecyclerView recyclerView;
    TimePicker timePicker;
    View Noteslayout;
    View Remainderlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView appname = findViewById(R.id.appname);
        appname.setText("Notes and Remainder");

        databaseHandler = new DatabaseHandler(getApplicationContext());
        Noteslayout = findViewById(R.id.notesItems);
        Remainderlayout = findViewById(R.id.remainderItems);
        TakeNotes = findViewById(R.id.button);
        recyclerView = findViewById(R.id.RecyclerView);
        timePicker = findViewById(R.id.TimePicker);
        StartTimer = findViewById(R.id.timerStart);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.add_notes);
    }

}
