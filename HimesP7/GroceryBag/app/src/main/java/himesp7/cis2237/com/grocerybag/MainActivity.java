//Alan Himes
//ahimes1@cnm.edu
//MainActivity.java

package himesp7.cis2237.com.grocerybag;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
    public static final String ROW_ID = "row_id";
    private SimpleCursorAdapter simpleCursorAdapter;
    private DatabaseConnector dbConn;
    private Cursor allGroceries;
    private ListView lvGroceries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lvGroceries = getListView();
        lvGroceries.setOnItemClickListener(listItemClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add){
            startActivity(new Intent(this, AddEditGroceriesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetGroceriesTask().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleCursorAdapter.getCursor().close();
        simpleCursorAdapter.changeCursor(null);
    }

    ListView.OnItemClickListener listItemClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(MainActivity.this, ViewGroceriesActivity.class);

            //column #0 for getLong() contains the row ids.
            long rowId = simpleCursorAdapter.getCursor().getLong(0);
            i.putExtra(ROW_ID, rowId);
            startActivity(i);
        }
    };

    private class GetGroceriesTask extends AsyncTask {
        public GetGroceriesTask() {
            dbConn = new DatabaseConnector(MainActivity.this);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            dbConn.open();
            allGroceries = dbConn.getAllGroceryBags();

            //Populate database table if there are no rows.
            if (allGroceries.getCount() <= 0) {
                dbConn.close();
                dbConn.insertGroceryBag("Rye", "Pineapple", "Asparagus", "Pork", "Mozzarella");
                dbConn.insertGroceryBag("Wheat", "Apple", "Carrot", "Beef", "Cheddar");
                dbConn.insertGroceryBag("Baguette", "Orange", "Broccoli", "Chicken", "Swiss");
                dbConn.open();
                allGroceries = dbConn.getAllGroceryBags();
            }

            return allGroceries;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String[] from = allGroceries.getColumnNames();
            int[] to = new int[]{
                    R.id.txtGroceriesId,
                    R.id.txtGroceriesBread,
                    R.id.txtGroceriesFruit,
                    R.id.txtGroceriesVegetables,
                    R.id.txtGroceriesMeat,
                    R.id.txtGroceriesCheese};

            if (simpleCursorAdapter == null) {
                simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this,
                        R.layout.groceries_list_item,
                        allGroceries,
                        from, to,
                        0);
            }
            else {
                simpleCursorAdapter.changeCursor((Cursor) o);
            }

            lvGroceries.setAdapter(simpleCursorAdapter);
            dbConn.close();
        }
    }
}
