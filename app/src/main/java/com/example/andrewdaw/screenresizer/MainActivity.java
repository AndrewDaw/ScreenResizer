package com.example.andrewdaw.screenresizer;

import android.app.ActionBar;
import android.content.Context;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout cl;
    ImageView unlockAni;
    static int ahdee;
    static String THIS_PHONES_SCREEN_RES;
    DisplayMetrics disp;


    // NDK is probably going to be useful, plus i wana play around with it
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unlockAni = new ImageView(this);
        unlockAni.setImageResource(R.drawable.unlock_screen);
        unlockAni.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));






        cl = (ConstraintLayout) findViewById(R.id.lay);

        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());



    }

    public void updateDisplayRes(){
        //getting the screen res
        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        THIS_PHONES_SCREEN_RES = String.valueOf(disp.widthPixels);
        THIS_PHONES_SCREEN_RES += "x"+disp.heightPixels;

        TextView tv = (TextView) findViewById(R.id.tvRes);
        tv.setText("Yer screen res currently = "+THIS_PHONES_SCREEN_RES+"\n\nPlz Note"+
                " multiple resizes may cause you belly ache\nand require adb commands via usb to fix!");

    }


    //checks the new res isnt something crazy before running it
    public void bExec(View view) {

        Boolean nokwidth = false;
        Boolean nokheight = false;


        TextView tv = (TextView) findViewById(R.id.etRes);
        String str = (String) tv.getText().toString();

        int newwidth, newheight;

        newwidth = Integer.parseInt(str.substring(0,str.indexOf('x')));
        newheight = Integer.parseInt(str.substring(str.indexOf('x')+1,str.length()));

        if(Math.abs((int)disp.widthPixels - newwidth) > 200){
            nokwidth = true;
        }
        if(Math.abs((int)disp.heightPixels - newheight)> 300){
            nokheight = true;
        }
        if(nokheight || nokwidth){
            tv = (TextView) findViewById(R.id.tvRes);
            tv.setText("Your new resolution is too far out man!\n Be more careful\n\n"+
                            "Yer screen res currently = "+THIS_PHONES_SCREEN_RES+"\n\nPlz Note"+
                    " multiple resizes may cause you belly ache\nand require adb commands via usb to fix!");
        }else {
            executeCommands(str);
            updateDisplayRes();
        }

    }

    //get root permissions and gets 'unlocks' app
    public void bGetRoot(View view) {
        //get root permissions and gets 'unlocks' app
        if (
                ExecuteAsRootBase.canRunRootCommands()) {
            findViewById(R.id.ivRestricted).setVisibility(View.GONE);
            findViewById(R.id.bExec).setVisibility(View.VISIBLE);
            findViewById(R.id.tvNoRoot).setVisibility(View.GONE);
            findViewById(R.id.bGetRoot).setVisibility(View.GONE);
//            displayAnimation(); extremely stupid.. do later maybe
            updateDisplayRes();

        }

    }

    public void displayAnimation() {
        int id;
        new Thread(new Runnable() {
            int ids;

            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {


                        cl.addView(unlockAni);
                        unlockAni.generateViewId();
                        ahdee = unlockAni.getId();
                                System.out.println("\n*\n*\n*\n*\n*\n*\n*\n*\n\n"+unlockAni.getId()+"\n*\n*\n*\n*\n*\n*\n*\n*\n\n");
                        System.out.println("\n*\n*\n*\n*\n*\n*\n*\n*\n\n"+ahdee+"\n*\n*\n*\n*\n*\n*\n*\n*\n\n");

                    }

                });


                try {
            SystemClock.sleep(2000);
                } catch (Exception e) {
                    //and do nothing!
                }

                runOnUiThread(new Runnable() {
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {


                                System.out.println("\n*\n*\n*\n##\n##\n##\n##\n##\n\n"+ahdee+"\n*\n*\n*\n*\n*\n*\n*\n*\n\n");
                                findViewById(ahdee).setVisibility(View.GONE);
                            }
                        });


                    }
                });


            }


        }).start();


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





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


}
