package cn.edu.swufe.reading_records;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {

    private DBHelper dbHelper;
    private String RECORDSTBNAME;
    private String BOOKSTBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        RECORDSTBNAME = DBHelper.RECORDS_TB_NAME;
        BOOKSTBNAME=DBHelper.RECORDS_TB_NAME;
    }

    public void add(RecordsItem item){
        Log.i("add", "add:db222  ");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.i("", "add:it_name "+item.getCurBookName());
        values.put("curbookname",item.getCurBookName());
        values.put("curdate", item.getCurDate());
        values.put("curtime", item.getCurTime());
        values.put("curcontent",item.getCurContent());
        db.replace(RECORDSTBNAME, null, values);
        db.close();
    }

    public void addAll(List<RecordsItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RecordsItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curdate", item.getCurDate());
            values.put("curtime", item.getCurTime());
            values.put("curcontent",item.getCurContent());
            db.insert(RECORDSTBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(RECORDSTBNAME,null,null);
        db.close();
    }

    public void delete(String date,String time){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.delete(RECORDSTBNAME, "CURDATE=? and CURTIME=?", new String[]{date,time});
        db.close();
    }

    public void update(RecordsItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curdate", item.getCurDate());
        values.put("curtime", item.getCurTime());
        values.put("curcontent",item.getCurContent());
        db.update(RECORDSTBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<RecordsItem> listAll(){
        List<RecordsItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(RECORDSTBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<RecordsItem>();
            while(cursor.moveToNext()){
                RecordsItem item = new RecordsItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurBookName(cursor.getString(cursor.getColumnIndex("CURBOOKNAME")));
                item.setCurDate(cursor.getString(cursor.getColumnIndex("CURDATE")));
                item.setCurTime(cursor.getString(cursor.getColumnIndex("CURTIME")));
                item.setCurContent(cursor.getString(cursor.getColumnIndex("CURCONTENT")));

                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public RecordsItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(RECORDSTBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RecordsItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RecordsItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurDate(cursor.getString(cursor.getColumnIndex("CURDATE")));
            rateItem.setCurTime(cursor.getString(cursor.getColumnIndex("CURTIME")));
            rateItem.setCurContent(cursor.getString(cursor.getColumnIndex("CURCONTENT")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
    public RecordsItem find(String date,String time){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(RECORDSTBNAME, null, "CURDATE=? and CURTIME=?", new String[]{date,time}, null, null, null);
        RecordsItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new RecordsItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurDate(cursor.getString(cursor.getColumnIndex("CURDATE")));
            rateItem.setCurTime(cursor.getString(cursor.getColumnIndex("CURTIME")));
            rateItem.setCurContent(cursor.getString(cursor.getColumnIndex("CURCONTENT")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
    public Cursor search(String string){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + DBHelper.RECORDS_TB_NAME + " where CURCONTENT like '%"+ string +"%' order by CURDATE DESC,CURTIME DESC";
        Cursor cursor =db.rawQuery(sql, null);
        return cursor;
    }
//another table
    public void book_add(String book_name){
        Log.i("add", "add:db  ");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curbookname",book_name);
        db.insert(BOOKSTBNAME, null, values);
        db.close();
    }
    public void book_delete(String book_name){
        Log.i("add", "add:db  ");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(BOOKSTBNAME, "CURBOOKNAME", new String[]{book_name});
        db.close();
    }
    public List<RecordsItem> listAllBook(){
        List<RecordsItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(BOOKSTBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<RecordsItem>();
            while(cursor.moveToNext()){
                RecordsItem item = new RecordsItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurBookName(cursor.getString(cursor.getColumnIndex("CURBOOKNAME")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }
    public void deleteAllBook(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(BOOKSTBNAME,null,null);
        db.close();
    }
    public void deleteBook(String bookname){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.delete(RECORDSTBNAME, "CURBOOKNAME=?", new String[]{bookname});
        db.delete(BOOKSTBNAME, "CURBOOKNAME=?", new String[]{bookname});
        db.close();
    }

}
