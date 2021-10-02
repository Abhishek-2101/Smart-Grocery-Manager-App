package com.example.loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String table_name = "list_table";
    private static final String table_name2 = "list_items";
    private static final String col1 = "ID";
    private static final String col2 = "name";
    private static final String t2col1 = "ID";
    private static final String t2col2 = "list_id";
    private static final String t2col3 = "item";
    private static final String t2col4 = "quantity";
    private static final String t2col5 = "price";
    private static final String t2col6 = "date";

    public DatabaseHelper(Context context){
        super(context, table_name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + col2 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean addData(String item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,item);
        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }





    public Cursor getData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String query= "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query,null);
        return data;
    }



    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + col1 + " FROM " + table_name +
                " WHERE " + col2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateList(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + table_name + " SET " + col2 +
                " = '" + newName + "' WHERE " + col1 + " = '" + id + "'" +
                " AND " + col2 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void deleteList(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + table_name + " WHERE "
                + col1 + " = '" + id + "'" +
                " AND " + col2 + " = '" + name + "'";
        db.execSQL(query);
    }
}
