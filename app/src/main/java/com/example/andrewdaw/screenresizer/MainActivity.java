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
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());




        //STUFF starts here TODO: lots, lol
        //commands to be executed
        ArrayList<String>cmds = new ArrayList<String>();
        cmds.add("wm size 720x1280");//command to be executed currently using a dummy
        final ArrayList<String> cmnds = cmds;


        //implementing the class that can do root things
        class C extends ExecuteAsRootBase {
            @Override
            protected ArrayList<String> getCommandsToExecute(){
                return cmnds;
            }


        }

        //get root permissions
        C.canRunRootCommands();

        //initializing the command execution class
        C ok = new C();
        ok.execute();//executes commands working





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
