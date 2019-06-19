package com.example.notes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    List<Integer>id;
    List<String>matterList;
    List<String>dateList;
    Cursor result;
    DatabaseHandler databaseHandler;
    Button TakeNotes;
    Button NewRemainder;
    ListView listView;
    View Noteslayout;
    View Remainderlayout;
    Toolbar toolbar;
    int ID_TO_BE_DELETED = -1;
    View currentselectedview;
    BottomNavigationView navigation;
    Menu menu_toolbar;
    MyCustomAdapter customAdapter;
    View previous_view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.add_notes:
                    workwithOptionToolbar(true);
                    //menu_toolbar.setGroupVisible(R.id.options_menu_deletebutton,true);
                    Noteslayout.setVisibility(View.VISIBLE);
                    Remainderlayout.setVisibility(View.INVISIBLE);
                    refreshListView();
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long idno) {
                                ID_TO_BE_DELETED = id.get(position);
                                currentselectedview = view;
                                highlightCurrentRow(currentselectedview);
                                return false;
                            }
                    });


                    TakeNotes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),NoteTaking.class));
                        }
                    });
                    return true;
                case R.id.remainder:

                    workwithOptionToolbar(false);
                    Noteslayout.setVisibility(View.INVISIBLE);
                    Remainderlayout.setVisibility(View.VISIBLE);
                    NewRemainder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),NewRemainderActivity.class));
                        }
                    });
                    return true;
            }
            return false;
        }
    };

    public  void refreshListView(){
        result = databaseHandler.getallData();
        if(result.getCount() == 0){
            Log.i("MAIN_ACTIVITY","NO DATA");
        }
        else {
            id = new ArrayList<Integer>();
            matterList = new ArrayList<String>();
            dateList = new ArrayList<String>();
            while (result.moveToNext()) {

                id.add(Integer.parseInt(result.getString(0)));
                matterList.add(result.getString(1));
                dateList.add(result.getString(2));
            }

            customAdapter = new MyCustomAdapter();
            listView.setAdapter(customAdapter);
        }
    }

    private void workwithOptionToolbar(boolean b) {
        if(menu_toolbar != null) {
            Log.i("MAIN_ACTIVITY", "Not Null");
            if (b == true) {
                menu_toolbar.setGroupVisible(R.id.options_menu_deletebutton, b);
                menu_toolbar.setGroupVisible(R.id.options_menu_editbutton, b);
                menu_toolbar.setGroupVisible(R.id.options_menu_deletebutton2, false);
            } else {
                menu_toolbar.setGroupVisible(R.id.options_menu_deletebutton, false);
                menu_toolbar.setGroupVisible(R.id.options_menu_editbutton, false);
                menu_toolbar.setGroupVisible(R.id.options_menu_deletebutton2, true);
            }
        }
        else {
            Log.i("MAIN_ACTIVITY", "Null");
            return;

        }

    }

    private void highlightCurrentRow(View currentselectedview) {
        if(previous_view == null) {
            currentselectedview.setBackgroundColor(Color.GRAY);
            previous_view = currentselectedview;
        }
        else{
            previous_view.setBackgroundColor(Color.parseColor("#00000000"));
            currentselectedview.setBackgroundColor(Color.GRAY);
            previous_view = currentselectedview;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView appname = findViewById(R.id.appname);
        appname.setText("Notes and Remainder");

        databaseHandler = new DatabaseHandler(getApplicationContext());
        Noteslayout = findViewById(R.id.notesItems);
        Remainderlayout = findViewById(R.id.remainderItems);
        TakeNotes = findViewById(R.id.button);
        listView = findViewById(R.id.RecyclerView);
        NewRemainder = findViewById(R.id.new_remainder);
//        timePicker = findViewById(R.id.TimePicker);
//        StartTimer = findViewById(R.id.timerStart);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.add_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_options_menu,menu);
        menu_toolbar = menu;
        workwithOptionToolbar(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Delete) {
            //Delete the item
            Integer res;
            Log.i("MAIN_ACTIVITY_2: ",String.valueOf(ID_TO_BE_DELETED));
            if(ID_TO_BE_DELETED!=-1) {
                Log.i("MAIN_ACTIVITY_2: ",String.valueOf(ID_TO_BE_DELETED));
                res = databaseHandler.deleteData(String.valueOf(ID_TO_BE_DELETED));
                if (res != 0) {
                    refreshListView();
                    Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
                    ID_TO_BE_DELETED = -1;
                } else {
                    refreshListView();
                    Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please select a data first!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.Edit){
            //Edit the item
            Long res;
            Log.i("MAIN_ACTIVITY_3: ",String.valueOf(ID_TO_BE_DELETED));
            if(ID_TO_BE_DELETED==-1){
                Toast.makeText(this, "Please select the data to edit!", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(getApplicationContext(),EditNotes.class);
                intent.putExtra("ID",ID_TO_BE_DELETED);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class MyCustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return id.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.recyler_view,null);
            TextView date = convertView.findViewById(R.id.datetime);
            TextView matter = convertView.findViewById(R.id.matter);

            date.setText(dateList.get(position));
            date.setTextColor(Color.parseColor("#FFF0E7"));
            matter.setText(matterList.get(position));
            matter.setTextColor(Color.parseColor("#FFF0E7"));
            return convertView;
        }
    }
}
