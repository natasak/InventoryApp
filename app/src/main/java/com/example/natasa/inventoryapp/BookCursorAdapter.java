package com.example.natasa.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natasa.inventoryapp.data.BookContract.BookEntry;
import com.example.natasa.inventoryapp.data.BookDbHelper;

/**
 * Adapter for a list view that uses Cursor of book data as its data source.
 * This adapter knows how to create list items for each row of book data in the Cursor.
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new BookCursorAdapter
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set to the views yet.
     * @param context - app context
     * @param cursor - the cursor from which to get the data
     * @param parent - the parent to which new view is attached to
     * @return the newly created list item view
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Return the list item view
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Row ID
        final int id = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));

        // Find individual views that we want to modify in the list item layput
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Find the columns of book attributes that we are interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);

        // Extract properties from cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        Float bookPrice = cursor.getFloat(priceColumnIndex);
        final int bookQuantity = cursor.getInt(quantityColumnIndex);

        // Populate TextView fields with extracted properties for the current book
        nameTextView.setText(bookName);
        priceTextView.setText(String.valueOf(bookPrice));
        quantityTextView.setText(String.valueOf(bookQuantity));

        // When button SALE is clicked, quantity is decreased by 1
        // Update database value for quantity and it should not go under 0
        Button buttonSale = (Button) view.findViewById(R.id.buttonSale);
        buttonSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there are books in stock, decrease quantity by 1 when clicking the button
                if (bookQuantity > 0) {
                    int newQuantity = bookQuantity -1;

                    // Getting the URI with appended ID for row
                    Uri quantityUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);

                    // Update the values
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_QUANTITY, newQuantity);
                    context.getContentResolver().update(quantityUri, values, null, null);
                } else {
                    // Otherwise display a toast that there is no books in stock
                    Toast.makeText(context, R.string.out_of_stock, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
