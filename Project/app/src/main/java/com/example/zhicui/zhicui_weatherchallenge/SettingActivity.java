package com.example.zhicui.zhicui_weatherchallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private Button clear_btn;
    private ArrayList<City> cityArrayList = new ArrayList<>();
    private RadioGroup rg;
    private RadioButton c_rb;
    private RadioButton f_rb;
    private String pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        clear_btn = findViewById(R.id.clear_btn_setting);
        f_rb = findViewById(R.id.f_radioButton);
        c_rb = findViewById(R.id.c_radioButton2);

        rg = findViewById(R.id.rg_setting);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.apply();

        pref = preferences.getString(MethodsKeys.PREFERENCE_UNIT,"f");

        //tell user current status
        if(pref.equals("f")){
            f_rb.toggle();
        }else {
            c_rb.toggle();
        }

        //radio group listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.f_radioButton){
                    editor.putString(MethodsKeys.PREFERENCE_UNIT,"f");
                    editor.apply();
                }
                if (checkedId == R.id.c_radioButton2){
                    editor.putString(MethodsKeys.PREFERENCE_UNIT,"c");
                    editor.apply();
                }
            }
        });

        //clear function
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSerializable();
                if(cityArrayList.size()==0){
                    Toast.makeText(SettingActivity.this, "There is no saved city need to delete.", Toast.LENGTH_SHORT).show();
                }else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
                    alert.setTitle("Clear list");
                    alert.setMessage("You sure you want to clear all of the saved cities?");
                    alert.setPositiveButton("CANCEL", null);
                    alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SaveSerializable(new ArrayList<City>());
                            Toast.makeText(SettingActivity.this, "Successful clear.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.show();
                }
            }
        });
    }


    //Save city
    private void SaveSerializable(ArrayList<City> _newsArrayList){

        try {
            FileOutputStream fos = openFileOutput(MethodsKeys.FILENAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_newsArrayList);
            oos.close();
            fos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    //Get List of City
    private void GetSerializable(){
        cityArrayList.clear();
        try {

            FileInputStream fis = openFileInput(MethodsKeys.FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityArrayList = (ArrayList<City>) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //back home and refresh activity
    private void gotoHome(){
        Intent gotoHome = new Intent(this, MainActivity.class);
        gotoHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoHome);

    }


    @Override
    public void onBackPressed() {
        gotoHome();
    }
}
