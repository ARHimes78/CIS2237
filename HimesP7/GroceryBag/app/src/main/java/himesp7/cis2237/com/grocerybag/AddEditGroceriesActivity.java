//Alan Himes
//ahimes1@cnm.edu
//AddEditGroceriesActivity.java

package himesp7.cis2237.com.grocerybag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditGroceriesActivity extends AppCompatActivity {
    private long rowId;
    private EditText etxBread;
    private EditText etxFruit;
    private EditText etxVegetable;
    private EditText etxMeat;
    private EditText etxCheese;
    private Button btnSave;
    private boolean isExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_groceries);

        etxBread = (EditText)findViewById(R.id.etxBread);
        etxFruit = (EditText)findViewById(R.id.etxFruit);
        etxVegetable = (EditText)findViewById(R.id.etxVegetable);
        etxMeat = (EditText)findViewById(R.id.etxMeat);
        etxCheese = (EditText)findViewById(R.id.etxCheese);

        Intent i = getIntent();
        rowId = i.getLongExtra(MainActivity.ROW_ID, 0);
        String[] bag = i.getStringArrayExtra(ViewGroceriesActivity.BAG);

        if (rowId == 0 && bag == null) {
            isExtras = false;
        }
        else {
            isExtras = true;
            etxBread.setText(bag[0]);
            etxFruit.setText(bag[1]);
            etxVegetable.setText(bag[2]);
            etxMeat.setText(bag[3]);
            etxCheese.setText(bag[4]);
        }

        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inspectFields = true;

                if (etxBread.getText().toString().equals("") ||
                        etxFruit.getText().toString().equals("") ||
                        etxVegetable.getText().toString().equals("") ||
                        etxMeat.getText().toString().equals("") ||
                        etxCheese.getText().toString().equals("")) {
                    inspectFields = false;
                }

                if (inspectFields) {
                    new AddOrUpdateTask().execute();
                }
                else
                    alertPopUp();
            }
        });
    }

    private void saveContact() {
        DatabaseConnector dbConn = new DatabaseConnector(this);

        if (isExtras) { //Update table row
            dbConn.updateGroceryBag(rowId,
                    etxBread.getText().toString(),
                    etxFruit.getText().toString(),
                    etxVegetable.getText().toString(),
                    etxMeat.getText().toString(),
                    etxCheese.getText().toString());
        }
        else { //Add table row
            dbConn.insertGroceryBag(
                    etxBread.getText().toString(),
                    etxFruit.getText().toString(),
                    etxVegetable.getText().toString(),
                    etxMeat.getText().toString(),
                    etxCheese.getText().toString());
        }
    }

    private void alertPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title_warning);
        builder.setMessage(R.string.alert_message_warning);

        builder.setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private class AddOrUpdateTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            saveContact();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
        }
    }
}
