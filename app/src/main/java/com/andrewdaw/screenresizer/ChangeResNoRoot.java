package com.andrewdaw.screenresizer;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.champ.andrewdaw.screenresizer.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ChangeResNoRoot extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_res_no_root);


    }

    //get root permissions and gets 'unlocks' app
    public void bWindows(View view) {
            Intent i = new Intent(getApplicationContext(), WindowsInstructions.class);
            startActivity(i);

        }

    //get root permissions and gets 'unlocks' app
    public void bOSX(View view) {
        Intent i = new Intent(getApplicationContext(), OSXInstructions.class);
        startActivity(i);

    }

    //get root permissions and gets 'unlocks' app
    public void bLinux(View view) {
        Intent i = new Intent(getApplicationContext(), LinuxInstructions.class);
        startActivity(i);

    }


}















