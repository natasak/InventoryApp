package com.example.natasa.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.natasa.inventoryapp.data.BookContract.BookEntry;
import com.example.natasa.inventoryapp.data.BookDbHelper;

/**
 * Allows user to create a new book entry or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the book's name */
    private EditText nameEditText;

    /** EditText field to enter price of the product */
    private EditText priceEditText;

    /** EditText field to enter quantity */
    private EditText quantityEditText;

    /** EditText field to enter supplier name */
    private EditText supplierNameEditText;

    /** EditText field to enter supplier phone number */
    private EditText supplierPhoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        nameEditText = (EditText) findViewById(R.id.edit_product_name);
        priceEditText = (EditText) findViewById(R.id.edit_product_price);
        quantityEditText = (EditText) findViewById(R.id.edit_quantity);
        supplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        supplierPhoneNumberEditText = (EditText) findViewById(R.id.edit_supplier_phone_number);
    }

    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook() {
        // Read from input fields
        // User trim to eliminate leading or trailing white space
        String nameString = nameEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();
        float price = Float.parseFloat(priceString);
        String quantityString = quantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = supplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = supplierPhoneNumberEditText.getText().toString().trim();

        // Create database helper
        BookDbHelper dbHelper = new BookDbHelper(this);

        // Gets the databse in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are keys,
        // and books attributes from the editor are values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(BookEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(BookEntry.COLUMN_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);

        // Insert a new row for book in database, returning the ID of that new row
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();

                // Exit activity
                finish();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
