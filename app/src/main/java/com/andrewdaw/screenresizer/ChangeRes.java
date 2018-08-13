package com.andrewdaw.screenresizer;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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
    ImageView unlockAni;
    static int ahdee, HOR_RES, VERT_RES;
    static String THIS_PHONES_SCREEN_RES;
    DisplayMetrics disp;
    private SeekBar seekBarHor, seekBarVert;
    private TextView textViewHor, textViewVert;
    private static final String TAG = "ChangeRes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_res);
        unlockAni = new ImageView(this);
        unlockAni.setImageResource(R.drawable.unlock_screen);
        unlockAni.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));






        cl = (ConstraintLayout) findViewById(R.id.lay);

        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        updateDisplayRes();
        initializeVariables();



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



        //checks if were on our way back from the draw new screen size view, if we are then
        //use the details
        Bundle extras = getIntent().getExtras();
        if(extras != null){

            //have a look at what we got
//            Log.i(TAG,""+extras.getInt("x1"));
//            Log.i(TAG,""+extras.getInt("y1"));
//            Log.i(TAG,""+extras.getInt("x2"));
//            Log.i(TAG,""+extras.getInt("y2"));

            //get extras from intent
            int x1 = extras.getInt("x1");
            int y1 = extras.getInt("y1");
            int x2 = extras.getInt("x2");
            int y2 = extras.getInt("y2");


            //convert finds the width and sets the fields (which are used for executing the resize
            //command)
            textViewHor.setText("New Horizontal Resolution: "+(x2-x1));
            textViewVert.setText("New Vertical Resolution: "+ (y2-y1));
            getIntent().removeExtra("x1");
            getIntent().removeExtra("x2");
            getIntent().removeExtra("y1");
            getIntent().removeExtra("y2");

        }
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



    public void drawResize(View view){
        Intent i = new Intent(getApplicationContext(), DrawResize.class);
        startActivity(i);
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
