package com.example.notes;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNotes extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    TextView matter;
    int ID_TO_BE_EDITED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView appname = findViewById(R.id.appname);
        appname.setText("Add Note");

        databaseHandler = new DatabaseHandler(this);
        matter = findViewById(R.id.Matter);
        // Using the id from the intent add the data to the textview matter
        Intent intent = getIntent();
        ID_TO_BE_EDITED = intent.getIntExtra("ID",-1);

        Cursor res = databaseHandler.getOneData(String.valueOf(ID_TO_BE_EDITED));
        if(res.moveToFirst()) {
            matter.setText(res.getString(1));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Save){
            //make database entry
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            String currDate = df.format(dateobj);
            databaseHandler.updateData(String.valueOf(ID_TO_BE_EDITED),matter.getText().toString(),currDate,"NOTES");
            Toast.makeText(this, "Item Saved", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
