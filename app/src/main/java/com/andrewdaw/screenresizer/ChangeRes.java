package com.andrewdaw.screenresizer;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.champ.andrewdaw.screenresizer.R;

import java.util.ArrayList;
//app now does cool stuff
public class ChangeRes extends AppCompatActivity {


    ConstraintLayout cl;
    static int ahdee, HOR_RES, VERT_RES;
    static String THIS_PHONES_SCREEN_RES;
    DisplayMetrics disp;
    private SeekBar seekBarHor, seekBarVert;
    private TextView textViewHor, textViewVert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_res);







        cl = (ConstraintLayout) findViewById(R.id.lay);

        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        updateDisplayRes();
        initializeVariables();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = settings.edit();
        if((settings.getString("ScreenRes", "n/a").compareTo("n/a")) == 0) {
            edit.putString("ScreenRes", THIS_PHONES_SCREEN_RES);
            edit.apply();
        }
        String default_screen_res = settings.getString("ScreenRes", "n/a");
        TextView reset = (TextView) findViewById(R.id.tvReset);
        reset.setText("Default screen resolution is " + default_screen_res);



        // Initialize the horizontal seek bar
        textViewHor.setText("Horizontal Resolution: " + HOR_RES );
        seekBarHor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            int newRes;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;

            }
            @Override
public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                newRes = (HOR_RES+((progress-5)*20));
                textViewHor.setText("New Horizontal Resolution: " + newRes);

            }
        });




        // Initialize the vertical seek bar
        textViewVert.setText("Vertical Resolution: " + VERT_RES);
        seekBarVert.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;
            int newRes;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                newRes = (VERT_RES+((progress-5)*40));
                textViewVert.setText("New Vertical Resolution: " + newRes);

            }
        });
    }

        // A private method to help us initialize our variables.
    private void initializeVariables() {
        seekBarHor = (SeekBar) findViewById(R.id.seekBarHorRes);
        textViewHor = (TextView) findViewById(R.id.tvHorRes);
        seekBarVert = (SeekBar) findViewById(R.id.seekBarVertRes);
        textViewVert = (TextView) findViewById(R.id.tvVertRes);
    }






    public void updateDisplayRes(){
        //getting the screen res
        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        HOR_RES = disp.widthPixels;
        VERT_RES = disp.heightPixels;
        THIS_PHONES_SCREEN_RES = String.valueOf(disp.widthPixels);
        THIS_PHONES_SCREEN_RES += "x"+disp.heightPixels;


        TextView tv = (TextView) findViewById(R.id.tvRes);
        tv.setText("Your screen res currently = "+THIS_PHONES_SCREEN_RES+"\n\n"+
                " Multiple resizes may render phone unusable\nand require adb commands via usb to fix!");

    }



    public void bExec(View view) {

        String str = textViewHor.getText().toString().substring(textViewHor.getText().toString().indexOf(":")+2) + "x" +
        textViewVert.getText().toString().substring(textViewVert.getText().toString().indexOf(":")+2);

        executeCommands(str);
        Intent intent = getIntent();
        finish();
        startActivity(intent);


    }

    public void bReset(View view){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        executeCommands(settings.getString("ScreenRes", "n/a"));

    }




    /*Executes a terminal command as root
     *@size. Size of new screen resolution formatted as widthxheight
     */
    public void executeCommands(final String size) {
        //implementing the class that can do root things
        class exeRoot extends ExecuteAsRootBase {
            @Override
            protected ArrayList<String> getCommandsToExecute() {
                return new ArrayList<String>() {{
                    add("wm size " + size); //probs dodgy asf and shud use stringbuilder or something
                }};
            }


        }
        //Initializing class
        new exeRoot().execute();

    }

}
