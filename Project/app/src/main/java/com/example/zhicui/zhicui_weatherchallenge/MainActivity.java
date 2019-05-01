package com.example.zhicui.zhicui_weatherchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView location_lv;
    ArrayList<Location> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.add_menu,menu);
        return true;
    }


    //MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //ADD ACTION
        if(item.getItemId() == R.id.add_btn_menu){
            //TODO: goto map activity





        }
        return super.onOptionsItemSelected(item);
    }
}
