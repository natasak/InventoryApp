package com.example.natasa.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.natasa.inventoryapp.data.BookContract.BookEntry;
import com.example.natasa.inventoryapp.data.BookDbHelper;

/**
 * Displays list of books that were entered and stored in the app
 */
public class MainActivity extends AppCompatActivity {

    // Database helper that will provide us access to the database
    private BookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity
        dbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Projection
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };

        // Call to the query method
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_book);

        try {
             // Create a header in TextView that looks like this
             // _id - name - price - quantity - supplier name - supplier phone number
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BookEntry._ID + " - "
                    + BookEntry.COLUMN_PRODUCT_NAME + " - "
                    + BookEntry.COLUMN_PRODUCT_PRICE + " - "
                    + BookEntry.COLUMN_QUANTITY + " - "
                    + BookEntry.COLUMN_SUPPLIER_NAME + " - "
                    + BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            // Iterate through the rows in the cursor and
            // display values from each column of the current row in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhoneNumber));
            }
        } finally {
            // Close the cursor
            cursor.close();
        }
    }


}
