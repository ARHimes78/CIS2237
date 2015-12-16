package com.himese3.cis2237.himese3;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class DiceActivity extends AppCompatActivity {
    private ImageView imgDice1, imgDice2;
    private Button btnRoll;
    private MediaPlayer soundEffect;
    private int dice1, dice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        imgDice1 = (ImageView)findViewById(R.id.imgDice1);
        imgDice2 = (ImageView)findViewById(R.id.imgDice2);
        randomDice();

        soundEffect = MediaPlayer.create(this, R.raw.bowling);

        btnRoll = (Button)findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundEffect.start();
                animSequence(imgDice1, DiceActivity.this);
                animSequence(imgDice2, DiceActivity.this);

                randomDice();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //store dice sides when program is interrupted
        outState.putInt("dice1", dice1);
        outState.putInt("dice2", dice2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dice1 = savedInstanceState.getInt("dice1");
        dice2 = savedInstanceState.getInt("dice2");

        assignImage(imgDice1, dice1);
        assignImage(imgDice2, dice2);
    }

    private void randomDice() {
        dice1 = (int)(Math.random() * 6);
        dice2 = (int)(Math.random() * 6);

        assignImage(imgDice1, dice1);
        assignImage(imgDice2, dice2);
    }

    private void assignImage(ImageView iv, int randomNum) {
        switch(randomNum) {
            case 0:
                iv.setImageResource(R.drawable.die1);
                break;
            case 1:
                iv.setImageResource(R.drawable.die2);
                break;
            case 2:
                iv.setImageResource(R.drawable.die3);
                break;
            case 3:
                iv.setImageResource(R.drawable.die4);
                break;
            case 4:
                iv.setImageResource(R.drawable.die5);
                break;
            case 5:
                iv.setImageResource(R.drawable.die6);
                break;
        }
    }

    private void animSequence(ImageView iv, Context ctx) {
        iv.startAnimation((AnimationUtils.loadAnimation(ctx, R.anim.animseq)));
    }
}
