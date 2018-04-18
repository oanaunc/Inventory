package com.oanaunciuleanu.inventorypart2.data;


import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

import com.oanaunciuleanu.inventorypart2.App;
import com.oanaunciuleanu.inventorypart2.R;

/**
 * API Contract for the Inventory app.
 */
public final class ProductContract {

    private ProductContract() {}

    public static final String CONTENT_AUTHORITY = "com.oanaunciuleanu.inventorypart2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    /**
     * Inner class that defines constant values for the products database table.
     * Each entry in the table represents a single product.
     */
    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        // Definition of the table
        public final static String TABLE_NAME = "products";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME ="name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        public final static String COLUMN_SUPPLIER_PHONE = "phone";
        public final static String COLUMN_PRODUCT_TYPE = "type";
        /**
         * Possible values for the types.
         */
        public static final int TYPE_FOOD = 0;
        public static final int TYPE_OBJECTS = 1;
        public static final int TYPE_MATERIALS = 2;

        public static boolean isValidType(int type) {
            if (type == TYPE_FOOD || type == TYPE_OBJECTS || type == TYPE_MATERIALS) {
                return true;
            }
            return false;
        }
    }

}

