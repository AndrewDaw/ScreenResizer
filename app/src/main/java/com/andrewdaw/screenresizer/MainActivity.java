package com.andrewdaw.screenresizer;

import android.app.ActionBar;
import android.content.Intent;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.champ.andrewdaw.screenresizer.R;

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


    //get root permissions and gets 'unlocks' app
    public void bGetRoot(View view) {
        //get root permissions and gets 'unlocks' app
        if (
                ExecuteAsRootBase.canRunRootCommands()) {
//            displayAnimation(); extremely stupid.. do later maybe
            Intent i = new Intent(getApplicationContext(), ChangeRes.class);
            startActivity(i);

        }

    }



}
