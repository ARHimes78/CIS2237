//Alan Himes
//ahimes1@cnm.edu
//RedeemRewardsActivity.java

package com.cis2237.himes_p4.androidrewardsprogram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RedeemRewardsActivity extends AppCompatActivity {
    private TextView txtBalance, txtRemainingMilesB;
    private Button btnUpgrade, btnRedeemB;
    private EditText etxLength;

    private SharedPreferences sharedPrefRedeem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_rewards);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtBalance = (TextView)findViewById(R.id.txtBalance);
        txtRemainingMilesB = (TextView)findViewById(R.id.txtRemainingMilesB);
        btnUpgrade = (Button)findViewById(R.id.btnUpgrade);
        btnRedeemB = (Button)findViewById(R.id.btnRedeemB);
        etxLength = (EditText)findViewById(R.id.etxLength);

        sharedPrefRedeem = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);

        txtBalance.setText("Your balance is: " +
                sharedPrefRedeem.getInt(MainActivity.MILEAGE, 0));

        txtRemainingMilesB.setText("Remaining Miles: " +
                sharedPrefRedeem.getInt(MainActivity.MILEAGE, 0));

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int miles = upgradeStatusCheck();

                if (miles == 0)
                    Toast.makeText(RedeemRewardsActivity.this,
                            "You're not qualified to upgrade your seat!",
                            Toast.LENGTH_LONG).show();
                else
                    alert("Upgrade Your Seat", miles);
            }
        });

        btnRedeemB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int miles = Integer.parseInt(etxLength.getText().toString());

                    if (!milesCheck(miles))
                        Toast.makeText(RedeemRewardsActivity.this,
                                "Your status is " +
                                sharedPrefRedeem.getString(MainActivity.FFM_STATUS, "None") +
                                ". Please see the rules in the MenuBar.",
                                Toast.LENGTH_LONG).show();
                    else {

                        alert("Redeem Mileage", redeemPrice());
                    }
                } catch (NumberFormatException nfe) {
                    Toast.makeText(RedeemRewardsActivity.this,
                            "Please enter how many free miles you'd like.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_redeem_rewards, menu);
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

    private void alert(String title, int miles) {

        AlertDialog.Builder adb = new AlertDialog.Builder(RedeemRewardsActivity.this);
        adb.setTitle(title);
        adb.setMessage("Are you sure you want to spend " + miles + " miles?");

        //Re-used variable as a "final" for accessibility in the innerclass here.
        final int milesCalc = miles;

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtBalance.setText("Your balance WAS: " +
                        sharedPrefRedeem.getInt(MainActivity.MILEAGE, 0));

                int newAmount = sharedPrefRedeem.getInt(MainActivity.MILEAGE, 0) - milesCalc;
                Toast.makeText(RedeemRewardsActivity.this, milesCalc+" miles deducted.",
                        Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPrefRedeem.edit();
                editor.putInt(MainActivity.MILEAGE, newAmount);

                //I had a convenient method in MainActivity for setting the
                //status, so a MainActivity object will be temporarily used
                //here to access the getStatus() method.
                MainActivity main = new MainActivity();

                editor.putString(main.FFM_STATUS, main.getStatus(newAmount));
                editor.commit();

                txtRemainingMilesB.setText("Remaining Miles: " +
                        sharedPrefRedeem.getInt(MainActivity.MILEAGE, 0));
            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RedeemRewardsActivity.this,
                        "Never mind, then!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        AlertDialog dialog = adb.create();
        dialog.show();
    }

    private int upgradeStatusCheck() {
        String status = sharedPrefRedeem.getString(MainActivity.FFM_STATUS, "None");
        switch (status) {
            case ("Bronze"):
                return 15000;
            case ("Silver"):
                return 10000;
            case ("Gold"):
                return 5000;
            default:
                return 0;
        }
    }

    private boolean milesCheck(int miles) {
        String status = sharedPrefRedeem.getString(MainActivity.FFM_STATUS, "None");
        if (status.equals("None"))
            return false;
        else if (status.equals("Bronze")) {
            if (miles < 1000)
                return true;
            else
                return false;
        }
        else if (status.equals("Silver")) {
            if (miles >= 1000 && miles < 2000)
                return true;
            else
                return false;
        }
        else if (status.equals("Gold")) {
            if (miles >= 2000 && miles < 3000)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    private int redeemPrice() {
        String status = sharedPrefRedeem.getString(MainActivity.FFM_STATUS, "None");

        switch (status) {
            case ("Bronze"):
                return 25000;
            case ("Silver"):
                return 50000;
            case ("Gold"):
                return 75000;
            default:
                return 0;
        }
    }
}
