package com.cis2237.himes_e4.fragmentcommunication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements SelectionFragment.SelectionInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void showToast (Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void rotateFace(boolean clockWise) {
        ImageFragment imageFragment = (ImageFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragImage);

        imageFragment.rotateFace(clockWise);
        imageFragment.switchFace();
    }
}
