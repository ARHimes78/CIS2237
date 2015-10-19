//Alan Himes
//ahimes1@cnm.edu
//RulesActivity.java

package com.cis2237.himes_p4.androidrewardsprogram;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RulesActivity extends AppCompatActivity {
    private TextView txtRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtRules = (TextView)findViewById(R.id.txtRules);
        try {
            txtRules.setText(getRulesText(getResources().openRawResource(R.raw.rules)));
        } catch (IOException IOe) {
            Toast.makeText(this, "The rules are missing!!", Toast.LENGTH_LONG).show();
            txtRules.setText("Go back, it didn\'t work out...");
        }

    }

    private String getRulesText(InputStream iStr) throws IOException {
        StringBuilder sb = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(iStr);
        BufferedReader br = new BufferedReader(isr);

        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        br.close();

        return sb.toString();
    }
}
