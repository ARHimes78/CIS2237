package com.cis2237.himes_p2.mobiletipster;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TipsterActivity extends AppCompatActivity {
    private TipsterCalc calcFifteen, calcCustom;
    private EditText editTxtBill, editTxtDiners;
    private SeekBar seekCustomTip;
    private TextView txt15Percent, txtPercentCustom;
    private TextView txtTip15, txtTipCustom;
    private TextView txtTotal15, txtTotalCustom;
    private TextView txtDiner15, txtDinerCustom;
    private Button btnReset;
    private DecimalFormat df;
    private double bill;
    private int diners, tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipster);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        df = new DecimalFormat("#.##");

        calcFifteen = new TipsterCalc();
        calcCustom = new TipsterCalc();

        editTxtBill = (EditText)findViewById(R.id.editTxtBill);
        editTxtDiners = (EditText)findViewById(R.id.editTxtDiners);
        seekCustomTip = (SeekBar)findViewById(R.id.seekCustomTip);
        txt15Percent = (TextView)findViewById(R.id.txt15Percent);
        txtPercentCustom = (TextView)findViewById(R.id.txtPercentCustom);
        txtTip15 = (TextView)findViewById(R.id.txtTip15);
        txtTipCustom = (TextView)findViewById(R.id.txtTipCustom);
        txtTotal15 = (TextView)findViewById(R.id.txtTotal15);
        txtTotalCustom = (TextView)findViewById(R.id.txtTotalCustom);
        txtDiner15 = (TextView)findViewById(R.id.txtDiner15);
        txtDinerCustom = (TextView)findViewById(R.id.txtDinerCustom);
        btnReset = (Button)findViewById(R.id.btnReset);
        bill = 0.0;
        diners = 1;
        tip = 15;

        editTxtBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    bill = Double.parseDouble(s.toString());
                    setFields();
                } catch (Exception ex) {
                    if (count == 0) {
                        bill = 0.0;
                        clearFields();
                    }
                    else
                        Toast.makeText(TipsterActivity.this, ex.toString(),
                                Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTxtDiners.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    diners = Integer.parseInt(s.toString());
                    setFields();
                } catch (Exception ex) {
                    if (count == 0) {
                        diners = 1;
                        clearFields();
                    }
                    else
                        Toast.makeText(TipsterActivity.this, ex.toString(),
                                Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekCustomTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tip = seekBar.getProgress();
                txtPercentCustom.setText(tip + "%");

                if (editTxtBill.getText().length() == 0 &&
                        editTxtDiners.getText().length() == 0) {
                    clearFields();
                } else
                    setFields();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekCustomTip.setProgress(15);
                txtPercentCustom.setText("Custom%");
                editTxtBill.setText("");
                editTxtDiners.setText("");
                clearFields();
            }
        });
    }

    private void setFields() {
        calcFifteen.setInputData(bill, diners, 15);
        calcCustom.setInputData(bill, diners, tip);

        txtTip15.setText("$" + df.format(calcFifteen.getTip()));
        txtTipCustom.setText("$" + df.format(calcCustom.getTip()));
        txtTotal15.setText("$" + df.format(calcFifteen.getTotal()));
        txtTotalCustom.setText("$" + df.format(calcCustom.getTotal()));
        txtDiner15.setText("$" + df.format(calcFifteen.getTotalEach()));
        txtDinerCustom.setText("$" + df.format(calcCustom.getTotalEach()));

    }

    private void clearFields() {
        txtTip15.setText("");
        txtTipCustom.setText("");
        txtTotal15.setText("");
        txtTotalCustom.setText("");
        txtDiner15.setText("");
        txtDinerCustom.setText("");
    }

}
