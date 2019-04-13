package com.example.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add_notes:
                    button.setText(R.string.notes_message);
                    textView.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.remainder:
                    textView2.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    button.setText(R.string.remainder_message);

                    return true;
            }
            return false;
        }
    };
    Button button;
    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView appname = findViewById(R.id.appname);
        appname.setText("Notes");

        button = findViewById(R.id.button);
        textView = findViewById(R.id.Notes_message);
        textView2 = findViewById(R.id.remainder_message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.add_notes);
    }

}
