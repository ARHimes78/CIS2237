//Alan Himes
//ahimes1@cnm.edu
//StatusActivity.java

package com.cis2237.himes_p4.androidrewardsprogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {
    private TextView txtStatus, txtRemainingMiles;
    private ImageView imgRewardsLevel;
    private Button btnRedeem;

    private SharedPreferences sharedPrefStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtStatus = (TextView)findViewById(R.id.txtStatus);
        txtRemainingMiles = (TextView)findViewById(R.id.txtRemaningMiles);
        imgRewardsLevel = (ImageView)findViewById(R.id.imgRewardsLevel);
        btnRedeem = (Button)findViewById(R.id.btnRedeem);

        sharedPrefStatus = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);

        updateStatus();

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatusActivity.this, RedeemRewardsActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.show_rules) {
            startActivity(new Intent(this, RulesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateStatus() {
        switch (sharedPrefStatus.getString(MainActivity.FFM_STATUS, "None")) {
            case ("None"):
                imgRewardsLevel.setImageResource(R.drawable.blank);
                break;
            case ("Bronze"):
                imgRewardsLevel.setImageResource(R.drawable.bronze);
                break;
            case ("Silver"):
                imgRewardsLevel.setImageResource(R.drawable.silver);
                break;
            case ("Gold"):
                imgRewardsLevel.setImageResource(R.drawable.gold);
                break;
        }

        txtStatus.setText(sharedPrefStatus.getString(MainActivity.USER_NAME, "None") +
                "\'s frequent flyer status: " +
                sharedPrefStatus.getString(MainActivity.FFM_STATUS, "None") +
                ". \nThe Rules are available in the Action Bar.");

        txtRemainingMiles.setText("Remaining Miles: " +
                sharedPrefStatus.getInt(MainActivity.MILEAGE, 0));
    }
}
