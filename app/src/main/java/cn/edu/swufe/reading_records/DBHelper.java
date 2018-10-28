package cn.edu.swufe.reading_records;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "personal_book_records.db";
    public static final String RECORDS_TB_NAME = "personal_records";
    public static final String BOOK_TB_NAME = "personal_books";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+RECORDS_TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURBOOKNAME TEXT,CURDATE TEXT,CURTIME TEXT,CURCONTENT TEXT)");
        db.execSQL("CREATE TABLE "+BOOK_TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURBOOKNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}