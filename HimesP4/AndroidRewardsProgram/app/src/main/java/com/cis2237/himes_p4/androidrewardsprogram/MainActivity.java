//Alan Himes
//ahimes1@cnm.edu
//MainActivity.java

package com.cis2237.himes_p4.androidrewardsprogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_NAME = "Preference_name";
    public static final String USER_NAME = "User_name";
    public static final String MILEAGE = "Mileage";
    public static final String FFM_STATUS = "FFM_Status";
    private EditText etxName;
    private EditText etxMiles;
    private Button btnStatus;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etxName = (EditText)findViewById(R.id.etxName);
        etxMiles = (EditText)findViewById(R.id.etxMiles);
        btnStatus = (Button)findViewById(R.id.btnStatus);

        sharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();

                try {
                    int miles = sharedPref.getInt(MILEAGE, 0);

                    editor.putString(USER_NAME, etxName.getText().toString());

                    if (miles == 0) {
                        miles = Integer.parseInt(etxMiles.getText().toString());
                        editor.putInt(MILEAGE, Integer.parseInt(etxMiles.getText().toString()));
                    }
                    else {
                        miles += Integer.parseInt(etxMiles.getText().toString());
                        editor.putInt(MILEAGE, miles);
                    }

                    editor.putString(FFM_STATUS, getStatus(miles));

                    editor.commit();

                    startActivity(new Intent(MainActivity.this, StatusActivity.class));
                } catch (NumberFormatException nfe) {
                    Toast.makeText(MainActivity.this, "Please enter the miles flown amount.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public String getStatus(int miles) {
        if (miles < 25000)
            return "None";
        else if (miles >= 25000 && miles < 50000)
            return "Bronze";
        else if (miles >= 50000 && miles < 75000)
            return "Silver";
        else
            return "Gold";
    }
}
