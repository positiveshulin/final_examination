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
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(RecordsItem item){
        Log.i("add", "add:db  ");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curdate", item.getCurDate());
        values.put("curtime", item.getCurTime());
        values.put("curcontent",item.getCurContent());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<RecordsItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RecordsItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curdate", item.getCurDate());
            values.put("curtime", item.getCurTime());
            values.put("curcontent",item.getCurContent());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(RecordsItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("curdate", item.getCurDate());
        //values.put("curtime", item.getCurTime());
        values.put("curcontent",item.getCurContent());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<RecordsItem> listAll(){
        List<RecordsItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<RecordsItem>();
            while(cursor.moveToNext()){
                RecordsItem item = new RecordsItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
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
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
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
}
