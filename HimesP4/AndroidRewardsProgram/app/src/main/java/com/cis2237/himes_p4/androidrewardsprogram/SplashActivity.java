//Alan Himes
//ahimes1@cnm.edu
//SplashActivity.java

package com.cis2237.himes_p4.androidrewardsprogram;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mp = MediaPlayer.create(this, R.raw.flyby);
        mp.start();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };
        Timer opening = new Timer();
        opening.schedule(task, 2000);
    }
}
