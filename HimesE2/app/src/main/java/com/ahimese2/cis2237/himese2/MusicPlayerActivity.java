package com.ahimese2.cis2237.himese2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MusicPlayerActivity extends AppCompatActivity {
    private Button btnCheese, btnLackluster;
    private MediaPlayer mpCheese, mpLackluster;
    int playing;
    boolean keepPlayingA, keepPlayingB;
    private final static String TAG = "Lifecycle method:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        keepPlayingA = false;
        keepPlayingB = false;
        Log.i(TAG, "From onCreate()");
        btnCheese = (Button)findViewById(R.id.btnCheese);
        btnLackluster = (Button)findViewById(R.id.btnLackluster);
        mpCheese = MediaPlayer.create(this, R.raw.cheese_festival);
        mpLackluster = MediaPlayer.create(this, R.raw.lackluster_whimsy);
        playing = 0;

        btnCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (playing) {
                    case 0:
                        mpCheese.start();
                        playing = 1;
                        btnCheese.setText("Pause");
                        break;
                    case 1:
                        mpCheese.pause();
                        playing = 0;
                        btnCheese.setText("Cheese Festival");
                        break;
                }
            }
        });

        btnLackluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (playing) {
                    case 0:
                        mpLackluster.start();
                        playing = 1;
                        btnLackluster.setText("Pause");
                        break;
                    case 1:
                        mpLackluster.pause();
                        playing = 0;
                        btnLackluster.setText("Lackluster Whimsy");
                        break;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (playing == 1) {
            if (mpCheese.isPlaying()) {
                mpCheese.stop();
                keepPlayingA = true;
                outState.putBoolean("PlayingA", keepPlayingA);
            }
            if (mpLackluster.isPlaying()) {
                mpLackluster.stop();
                keepPlayingB = true;
                outState.putBoolean("PlayingB", keepPlayingB);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        keepPlayingA = savedInstanceState.getBoolean("PlayingA");
        keepPlayingB = savedInstanceState.getBoolean("PlayingB");
        if (keepPlayingA) {
            playing = 1;
            mpCheese.start();
            btnCheese.setText("Pause");
            keepPlayingA = false;
            Toast.makeText(this, "Phone tilted! Starting song over!", Toast.LENGTH_SHORT).show();

        }
        if (keepPlayingB) {
            playing = 1;
            mpLackluster.start();
            btnLackluster.setText("Pause");
            keepPlayingB = false;
            Toast.makeText(this, "Phone tilted! Starting song over!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "From onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "From onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "From onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "From onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "From onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "From onDestroy()");
    }
}
