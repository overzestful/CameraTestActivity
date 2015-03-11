package net.sourceforge.zbar.android.CameraTest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Overzestful-Fhon on 3/4/2015.
 */
public class SimpleSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_BOOKS = "book";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STAFF = "staff";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CONFIRM_PASSWORD = "confirm_password";
    public static final String COLUMN_HOUR = "hour";
    //public static final String COLUMN_TYPE = "type";
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_BOOKS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text,"
            + COLUMN_STAFF + " text,"
            + COLUMN_PASSWORD + " text,"
            + COLUMN_CONFIRM_PASSWORD + " text,"
           // + COLUMN_TYPE + " text,"
            + COLUMN_HOUR + " text" + ");";


    public SimpleSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SimpleSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_BOOKS);
        onCreate(db);
    }


}
