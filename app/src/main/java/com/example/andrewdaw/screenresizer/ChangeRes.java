package com.example.andrewdaw.screenresizer;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChangeRes extends AppCompatActivity {


    ConstraintLayout cl;
    ImageView unlockAni;
    static int ahdee;
    static String THIS_PHONES_SCREEN_RES;
    DisplayMetrics disp;

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

}
