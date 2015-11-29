//Alan Himes
//ahimes1@cnm.edu
//DatabaseConnector.java

package himesp7.cis2237.com.grocerybag;// DatabaseConnector.java
// Provides easy connection and creation of UserContacts database.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector 
{
   // database name
   private static final String DATABASE_NAME = "UserGroceryBags";
   private SQLiteDatabase database; // database object
   private DatabaseOpenHelper databaseOpenHelper; // database helper

   // public constructor for DatabaseConnector
   public DatabaseConnector(Context context) 
   {
      // create a new DatabaseOpenHelper
      databaseOpenHelper = 
         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
   } // end DatabaseConnector constructor

   // open the database connection
   public void open() throws SQLException 
   {
      // create or open a database for reading/writing
      database = databaseOpenHelper.getWritableDatabase();
   } // end method open

   // close the database connection
   public void close() 
   {
      if (database != null)
         database.close(); // close the database connection
   } // end method close

   // inserts a new contact in the database
   public void insertGroceryBag(String bread, String fruit, String vegetable,
      String meat, String cheese)
   {
      ContentValues newGroceryBag = new ContentValues();
      newGroceryBag.put("bread", bread);
      newGroceryBag.put("fruit", fruit);
      newGroceryBag.put("vegetable", vegetable);
      newGroceryBag.put("meat", meat);
      newGroceryBag.put("cheese", cheese);

      open(); // open the database
      database.insert("grocerybags", null, newGroceryBag);
      close(); // close the database
   }

   // inserts a new contact in the database
   public void updateGroceryBag(long id, String bread, String fruit,
      String vegetable, String meat, String cheese)
   {
      ContentValues editGroceryBag = new ContentValues();
      editGroceryBag.put("bread", bread);
      editGroceryBag.put("fruit", fruit);
      editGroceryBag.put("vegetable", vegetable);
      editGroceryBag.put("meat", meat);
      editGroceryBag.put("cheese", cheese);

      open(); // open the database
      database.update("grocerybags", editGroceryBag, "_id=" + id, null);
      close(); // close the database
   }

   // return a Cursor with all contact information in the database
   public Cursor getAllGroceryBags()
   {
      return database.query("grocerybags",
              new String[] {"_id", "bread", "fruit", "vegetable", "meat", "cheese"},
         null, null, null, null, "_id");
   }

   // get a Cursor containing all information about the contact specified
   // by the given id
   public Cursor getOneGroceryBag(long id)
   {
      return database.query(
         "grocerybags", null, "_id=" + id, null, null, null, null);
   }

   // delete the contact specified by the given String name
   public void deleteGroceryBag(long id)
   {
      open(); // open the database
      database.delete("grocerybags", "_id=" + id, null);
      close(); // close the database
   }
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper 
   {
      // public constructor
      public DatabaseOpenHelper(Context context, String name,
         CursorFactory factory, int version) 
      {
         super(context, name, factory, version);
      }

      // creates the contacts table when the database is created
      @Override
      public void onCreate(SQLiteDatabase db) 
      {
//         String createQuery = "DROP TABLE grocerybags";
         // query to create a new table named contacts
         String createQuery = "CREATE TABLE grocerybags" +
            "(_id integer primary key autoincrement," +
            "bread TEXT, fruit TEXT, vegetable TEXT," +
            "meat TEXT, cheese TEXT);";

                  
         db.execSQL(createQuery); // execute the query
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
          int newVersion) 
      {
      }
   }
}


