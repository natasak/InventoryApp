package com.example.natasa.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {

    // Empty constructor to prevent from accidentally instantiating contract class.
    private BookContract() {}

    /**
     * CONTENT URI
     */

    // String constant for Content Authority, the same as in the manifest
    public static final String CONTENT_AUTHORITY = "com.example.natasa.inventoryapp";

    // The base of all URI's which apps will use to contact the content provider -
    // scheme + content authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible path - table name
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines constants for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        // Full Content URI to access the book data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);


        // Table name for books
        public static final  String TABLE_NAME = "books";

        // Column for unique ID number in the database, type: INTEGER
        public static final String _ID = BaseColumns._ID;

        // Column for product name, type: TEXT
        public static final String COLUMN_PRODUCT_NAME = "name";

        // Column for product price, type: REAL
        public static final String COLUMN_PRODUCT_PRICE = "price";

        // Column for quantity, type: INTEGER
        public static final String COLUMN_QUANTITY = "quantity";

        // Column for supplier name, type: TEXT
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";

        // Column for supplier phone number, type: TEXT
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

    }
}
