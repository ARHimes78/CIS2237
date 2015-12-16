package com.himese2.cis2237.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnMarimba, btnMerengue;
    MediaPlayer mpMarimba, mpMerengue;
    int playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMarimba = (Button)findViewById(R.id.btn_marimba);
        btnMerengue = (Button)findViewById(R.id.btn_merengue);
        mpMarimba = new MediaPlayer();
        mpMerengue = new MediaPlayer();

        playing = 0;
        mpMarimba = MediaPlayer.create(this, R.raw.marimba);
        mpMerengue = MediaPlayer.create(this, R.raw.merengue);

        btnMarimba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (playing) {
                    case 0:
                        mpMarimba.start();
                        playing = 1;
                        btnMarimba.setText("Pause Marimba");
                        btnMerengue.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mpMarimba.pause();
                        playing = 0;
                        btnMarimba.setText("Play Marimba");
                        btnMerengue.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        btnMerengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (playing) {
                    case 0:
                        mpMerengue.start();
                        playing = 1;
                        btnMerengue.setText("Pause Merengue");
                        btnMarimba.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mpMerengue.pause();
                        playing = 0;
                        btnMerengue.setText("Play Merengue");
                        btnMarimba.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
