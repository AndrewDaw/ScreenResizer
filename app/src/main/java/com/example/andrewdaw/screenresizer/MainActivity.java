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

        //somestuffystuffDW
        ArrayList<String>cmds = new ArrayList<String>();
        cmds.add("wm size 720x1280");
        final ArrayList<String> cmnds = cmds;


        class C extends ExecuteAsRootBase {
            @Override
            protected ArrayList<String> getCommandsToExecute(){
                return cmnds;
            }


        }

        C.canRunRootCommands();
        C ok = new C();
        ok.execute();





    }

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
