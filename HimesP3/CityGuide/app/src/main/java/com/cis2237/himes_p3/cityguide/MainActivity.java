package com.cis2237.himes_p3.cityguide;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    String[] attraction = {"Art Institute of Chicago", "Magnificent Mile", "Willis Tower",
            "Navy Pier", "Water Tower"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, R.id.travel,
                attraction));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.artic.edu/")));
                break;
            case 1:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.themagnificentmile.com/")));
                break;
            case 2:
                startActivity(new Intent(this, WillisTowerActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, NavyPierActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, WaterTowerActivity.class));
                break;
        }
    }
}
