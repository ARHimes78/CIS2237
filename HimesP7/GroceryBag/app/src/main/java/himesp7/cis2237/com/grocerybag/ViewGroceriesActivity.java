//Alan Himes
//ahimes1@cnm.edu
//ViewGroceriesActivity.java

package himesp7.cis2237.com.grocerybag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewGroceriesActivity extends AppCompatActivity {
    public static final String BAG = "bag";

    private DatabaseConnector dbConn;
    private Cursor tableRow;
    private TextView txtBread;
    private TextView txtFruit;
    private TextView txtVegetable;
    private TextView txtMeat;
    private TextView txtCheese;
    private long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groceries);

        txtBread = (TextView)findViewById(R.id.txtBread);
        txtFruit = (TextView)findViewById(R.id.txtFruit);
        txtVegetable = (TextView)findViewById(R.id.txtVegetable);
        txtMeat = (TextView)findViewById(R.id.txtMeat);
        txtCheese = (TextView)findViewById(R.id.txtCheese);

        rowId = getIntent().getLongExtra(MainActivity.ROW_ID, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_groceries_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.edit:
                Intent i = new Intent(this, AddEditGroceriesActivity.class);

                String[] bag = new String[]{
                        txtBread.getText().toString(),
                        txtFruit.getText().toString(),
                        txtVegetable.getText().toString(),
                        txtMeat.getText().toString(),
                        txtCheese.getText().toString()
                };
                i.putExtra(MainActivity.ROW_ID, rowId);
                i.putExtra(BAG, bag);

                startActivity(i);
                break;
            case R.id.delete:
                deleteGroceries();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadGroceriesTask().execute(rowId);
    }

    private void deleteGroceries() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_message);

        builder.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DeleteGroceriesTask().execute(new Long[]{ rowId });
            }
        });

        builder.setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private class LoadGroceriesTask extends AsyncTask {
        public LoadGroceriesTask() {
            dbConn = new DatabaseConnector(ViewGroceriesActivity.this);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            dbConn.open();
            tableRow = dbConn.getOneGroceryBag(Integer.parseInt(params[0].toString()));
            return tableRow;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            tableRow.moveToFirst();
            txtBread.setText(tableRow.getString(1));
            txtFruit.setText(tableRow.getString(2));
            txtVegetable.setText(tableRow.getString(3));
            txtMeat.setText(tableRow.getString(4));
            txtCheese.setText(tableRow.getString(5));

            tableRow.close();
            dbConn.close();
        }
    }

    private class DeleteGroceriesTask extends AsyncTask<Long, Object, Object> {
        public DeleteGroceriesTask() {
            dbConn = new DatabaseConnector(ViewGroceriesActivity.this);
        }

        @Override
        protected Object doInBackground(Long... params) {
            dbConn.deleteGroceryBag(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
        }
    }
}
