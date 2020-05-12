package com.tatvasoft.expansemangement.ui.intro.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tatvasoft.expansemangement.ui.category.model.AddCategoryModel;

import java.util.ArrayList;

public class CategoryDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CategoryData";
    private static final String TABLE_CATEGORY = "Categories";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY = "Categories";
    public CategoryDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POST_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CATEGORY + " TEXT," + " DATETIME DEFAULT CURRENT_DATE" + ")";
        db.execSQL(CREATE_POST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }

    public ArrayList<AddCategoryModel> listData() {
        String sql = "select * from " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddCategoryModel> dataModel = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                dataModel.add(new AddCategoryModel(category));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataModel;
    }

    public String selectedCategory(String selectedCategory) {

        String sql=  "SELECT * FROM " + TABLE_CATEGORY + " where " +
                COLUMN_CATEGORY + " = " + selectedCategory;
        SQLiteDatabase db = this.getReadableDatabase();
        String category=null;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String categorie = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                category=categorie;
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return category;
    }
    public void deleteCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COLUMN_CATEGORY + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void addDetails(AddCategoryModel dataModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY, dataModel.getCategory());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CATEGORY, null, contentValues);
    }
}
