package com.example.huilv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RateManager {
    private DBHelper dbHelper;
    private String TB_NAME;

    public RateManager(Context context){
        this.dbHelper = new DBHelper(context);
        this.TB_NAME = DBHelper.TB_NAME;
    }

    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("curName",item.getCurName());
        values.put("curRate",item.getCurRate());

        long r = db.insert(TB_NAME,null,values);
        db.close();

        Log.i(TAG,"add:写入结果r=" + r);
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TB_NAME, null, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TB_NAME,"ID=?", new String[]{String.valueOf(id)});
            db.close();
    }

    public void update(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase( );
        ContentValues values = new ContentValues();
        values.put ( "curName" , item.getCurName());
        values.put ( "curRate" , item.getCurRate());
        db.update(TB_NAME,values,"ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<RateItem> listAll(){
        List<RateItem> retList = new ArrayList<RateItem>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME,null,null,
                null,null,null,null);
        if (cursor != null){
            while(cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                retList.add(item);
            }
            cursor.close();
        }
        db.close();
        return retList;
    }

    public RateItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME,null,"ID=?" , new String[]{String.valueOf(id)},
                null,null,null);
        RateItem rateItem = null;
        if(cursor != null && cursor. moveToFirst()){
            rateItem = new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName( cursor.getString( cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate( cursor.getString( cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
}
