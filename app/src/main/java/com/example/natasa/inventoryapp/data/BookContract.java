package com.example.natasa.inventoryapp.data;

import android.provider.BaseColumns;

public final class BookContract {

    // Empty constructor to prevent from accidentally instantiating contract class.
    private BookContract() {}

    /**
     * Inner class that defines constants for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

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
