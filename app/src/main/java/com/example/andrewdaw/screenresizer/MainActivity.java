package com.example.andrewdaw.screenresizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    // NDK is probably going to be useful, plus i wana play around with it
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());




        //STUFF starts here TODO: lots, lol
        //commands to be executed






        //get root permissions
        ExecuteAsRootBase.canRunRootCommands();


        executeCommands("wm size 720x1280");





    }


    /*Executes a terminal command as root
    *@cmd Command to be executed
     */
    public void executeCommands(final String cmd){
        //implementing the class that can do root things
        class exeRoot extends ExecuteAsRootBase {
            @Override
            protected ArrayList<String> getCommandsToExecute(){
                return  new ArrayList<String>() {{
                    add(cmd);

                }};
            }


        }
        //Initializing class
        new exeRoot().execute();

    }


    //might be useful later when we get screen size from user etc
//    public void runCommands(ArrayList<String> cmds){
////        final ArrayList cmnds = cmds;
////        new Thread(new Runnable() {
////            public void run() {
////
////
////
////            }
////        }).start();
////    }








    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


}
