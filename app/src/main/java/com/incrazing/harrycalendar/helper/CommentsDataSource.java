package com.incrazing.harrycalendar.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_DATE,
            MySQLiteHelper.COLUMN_VALUE,
            MySQLiteHelper.COLUMN_TIMESTAMP};
    private static String TAG = CommentsDataSource.class.getSimpleName();

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void recordValue(String date, int value) {
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_VALUE, value);
//        values.put(MySQLiteHelper.COLUMN_DATE, date);
        database.execSQL("insert or replace into "+MySQLiteHelper.TABLE_DAYS+" ("+MySQLiteHelper.COLUMN_DATE+", "+MySQLiteHelper.COLUMN_VALUE+") values (\""+date+"\", "+value+")");
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Comment newComment = cursorToComment(cursor);
//        cursor.close();
//        return newComment;
    }

    public int getValueByDate(String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_DAYS, new String[] { MySQLiteHelper.COLUMN_VALUE}, MySQLiteHelper.COLUMN_DATE + "=?",
                new String[] { date }, null, null, null, null);
        int value = 3;
        if (cursor != null  && cursor.moveToFirst()) {
            value = cursor.getInt(0);
            cursor.close();
        }
        return value;
    }

   public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
*/
}