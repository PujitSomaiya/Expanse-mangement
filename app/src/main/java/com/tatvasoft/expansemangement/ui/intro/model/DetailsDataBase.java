package com.tatvasoft.expansemangement.ui.intro.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DetailsDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DetailsData";
    private static final String TABLE_POST = "Posts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIMESTAMP = "blogTimePost";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_SPEND = "spend";
    private static final String COLUMN_REMARK = "remark";
    private static final String COLUMN_DATE = "date";
    private DetailsModel dataModel;

    public DetailsDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POST_TABLE = "CREATE TABLE " + TABLE_POST + "(" + COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CATEGORY + " TEXT," + COLUMN_SPEND + " TEXT," + COLUMN_REMARK + " TEXT," + COLUMN_DATE + " TEXT," + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_DATE" + ")";
        db.execSQL(CREATE_POST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        onCreate(db);
    }

    public ArrayList<DetailsModel> listData() {
        String sql = "select * from " + TABLE_POST;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DetailsModel> dataModel = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String spend = cursor.getString(cursor.getColumnIndex(COLUMN_SPEND));
                String remark = cursor.getString(cursor.getColumnIndex(COLUMN_REMARK));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_REMARK));
                dataModel.add(new DetailsModel(spend, remark, category,date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataModel;
    }

    public ArrayList<DetailsModel> selectedDate(String selectedDate) {

        String sql=  "SELECT * FROM " + TABLE_POST + " where " +
                COLUMN_DATE + " = " + selectedDate;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DetailsModel> dataModel = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String spend = cursor.getString(cursor.getColumnIndex(COLUMN_SPEND));
                String remark = cursor.getString(cursor.getColumnIndex(COLUMN_REMARK));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_REMARK));
                dataModel.add(new DetailsModel(spend, remark, category,date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataModel;
    }
    public void deleteBlog(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POST, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void addDetails(DetailsModel dataModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY, dataModel.getCategory());
        contentValues.put(COLUMN_SPEND, dataModel.getMoneySpend());
        contentValues.put(COLUMN_REMARK, dataModel.getRemark());
        contentValues.put(COLUMN_DATE, dataModel.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_POST, null, contentValues);
    }
}
