package com.incrazing.harrycalendar.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DAYS = "days";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String TAG = MySQLiteHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 4;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DAYS + "( " + COLUMN_DATE
            + " text primary key, "+COLUMN_VALUE+" integer, "+COLUMN_TIMESTAMP+" DATETIME DEFAULT CURRENT_TIMESTAMP );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
        onCreate(db);
    }

    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * @param tableName the the name of the table to parse
     * @return the table tableName as a string
     */
    public String getTableAsString(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }
}
