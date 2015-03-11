package net.sourceforge.zbar.android.CameraTest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Overzestful-Fhon on 3/4/2015.
 */
public class BookDataSource {
    // Database fields
    public static final String TAG = "SQLite Database";
    private SQLiteDatabase database;
    private SimpleSQLiteHelper dbHelper;
    private String[] allColumns = { SimpleSQLiteHelper.COLUMN_ID,
            SimpleSQLiteHelper.COLUMN_NAME,
            SimpleSQLiteHelper.COLUMN_STAFF,
            SimpleSQLiteHelper.COLUMN_PASSWORD,
            SimpleSQLiteHelper.COLUMN_CONFIRM_PASSWORD,
         //   SimpleSQLiteHelper.COLUMN_TYPE,
            SimpleSQLiteHelper.COLUMN_HOUR };

    public BookDataSource(Context context) {
        dbHelper = new SimpleSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Book insertBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(SimpleSQLiteHelper.COLUMN_NAME, book.getName());
        values.put(SimpleSQLiteHelper.COLUMN_STAFF, book.getStaff());
        values.put(SimpleSQLiteHelper.COLUMN_PASSWORD, book.getPassword());
        values.put(SimpleSQLiteHelper.COLUMN_CONFIRM_PASSWORD, book.getConfirm_password());
       // values.put(SimpleSQLiteHelper.COLUMN_TYPE, book.getType());
        values.put(SimpleSQLiteHelper.COLUMN_HOUR, book.getHour());
        long insertId = database.insert(SimpleSQLiteHelper.TABLE_BOOKS, null,
                values);

        Cursor cursor = database.query(SimpleSQLiteHelper.TABLE_BOOKS,
                allColumns, SimpleSQLiteHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null, null);
        cursor.moveToFirst();
        return cursorToBook(cursor);
    }

    public void deleteBook(Book book) {
        long id = book.getId();
        Log.d(TAG, "Book deleted with id: " + id);
        database.delete(SimpleSQLiteHelper.TABLE_BOOKS,
                SimpleSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(SimpleSQLiteHelper.COLUMN_NAME, book.getName());
        values.put(SimpleSQLiteHelper.COLUMN_STAFF, book.getStaff());
        values.put(SimpleSQLiteHelper.COLUMN_PASSWORD, book.getPassword());
        values.put(SimpleSQLiteHelper.COLUMN_CONFIRM_PASSWORD, book.getConfirm_password());
       // values.put(SimpleSQLiteHelper.COLUMN_TYPE, book.getType());
        values.put(SimpleSQLiteHelper.COLUMN_HOUR, book.getHour());
        database.update(SimpleSQLiteHelper.TABLE_BOOKS, values,
                SimpleSQLiteHelper.COLUMN_ID + "=" + book.getId(), null);
    }

    public List<Book> getAllBook() {
        List<Book> comments = new ArrayList<Book>();
        Cursor cursor = database.query(SimpleSQLiteHelper.TABLE_BOOKS,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book comment = cursorToBook(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(0));
        book.setName(cursor.getString(1));
        book.setStaff(cursor.getString(2));
        book.setPassword(cursor.getString(3));
        book.setConfirm_password(cursor.getString(4));
      //  book.setType(cursor.getString(5));
        book.setHour(cursor.getString(5));
        return book;
    }

}
