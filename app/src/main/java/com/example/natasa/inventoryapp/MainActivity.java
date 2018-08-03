package com.example.natasa.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.natasa.inventoryapp.data.BookContract.BookEntry;

/**
 * Displays list of books that were entered and stored in the app
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Identifier for the book data loader
    private static final int BOOK_LOADER = 0;

    // Adapter for the ListView
    BookCursorAdapter mCursorAdapter;

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

        // Find the ListView which will be populated with the book data
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on ListView, when the list has 0 items
        View emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);


        // Setup an Adapter to create a list for each row of book data in the Cursor
        // There is no book data yet (until the laoder finishes) so pass in null for the Cursor
        mCursorAdapter = new BookCursorAdapter(this, null);
        bookListView.setAdapter(mCursorAdapter);

        // kick off the loader
        getLoaderManager().initLoader(BOOK_LOADER, null, this);
    }

    /**
     * CURSOR LOADER
     */

    // Called when a new loader needs to be created
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Projection that specifies the columns from the table we care about
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_QUANTITY
        };

        // Loader will execute ContentProvider's query method on a bacground thread
        return new CursorLoader(this,
                BookEntry.CONTENT_URI,    // The content URI
                projection,               // Columns to return for each row
                null,
                null,
                null);
    }

    // Called when a previously created loader has finished loading
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update BookCursorAdapter with this new cursor containing updated book data
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data need to be deleted
        mCursorAdapter.swapCursor(null);

    }
}
