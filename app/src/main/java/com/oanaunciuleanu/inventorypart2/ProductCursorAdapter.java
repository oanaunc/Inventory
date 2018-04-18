package com.oanaunciuleanu.inventorypart2;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.oanaunciuleanu.inventorypart2.data.ProductContract;
import com.oanaunciuleanu.inventorypart2.data.ProductContract.ProductEntry;


public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        Button sellButton= (Button) view.findViewById(R.id.sell_book_button);

        // Find the columns of product attributes that we're interested in
        int idColumnIndex = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        String productQuantity = cursor.getString(quantityColumnIndex);

        if (TextUtils.isEmpty(productPrice)) {
            productPrice = context.getString(R.string.zero);
        }

        if (TextUtils.isEmpty(productQuantity)) {
            productQuantity = context.getString(R.string.zero);
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(productQuantity);

        //get currentProductUri
        final Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, idColumnIndex);
        final int currentProductQuantity = cursor.getInt(quantityColumnIndex);

        //set listener for each button in the list view
        sellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentResolver resolver = v.getContext().getContentResolver();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                if (currentProductQuantity > 0) {
                    int quantity = currentProductQuantity;
                    --quantity;
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
                    resolver.update(
                            currentProductUri,
                            values,
                            null,
                            null
                    );
                    context.getContentResolver().notifyChange(currentProductUri, null);

                } else {
                    // Otherwise, the reduce cannot be performed and we display a toast.
                    Toast.makeText(context, R.string.quantity_reduce_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

