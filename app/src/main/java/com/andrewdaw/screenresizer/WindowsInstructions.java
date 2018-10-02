package com.andrewdaw.screenresizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.champ.andrewdaw.screenresizer.R;

public class WindowsInstructions extends AppCompatActivity {

    static int HOR_RES, VERT_RES;
    DisplayMetrics disp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows_instructions);
        updateDisplayRes();

    }

    public void updateDisplayRes(){
        //getting the screen res
        disp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(disp);
        HOR_RES = disp.widthPixels;
        VERT_RES = disp.heightPixels;



        TextView tv = (TextView) findViewById(R.id.tvRes);
        tv.setText("Your screen currently\n" +
                "         Width: "+ HOR_RES +"\n"+
                "       Height: " + VERT_RES);

    }
}
