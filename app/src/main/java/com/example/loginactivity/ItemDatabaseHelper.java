package com.example.loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String table_name = "list_table";
    private static final String table_name2 = "list_items";
    private static final String t2col1 = "ID";
    private static final String t2col2 = "list_id";
    private static final String t2col3 = "item";
    private static final String t2col4 = "quantity";
    private static final String t2col5 = "price";
    private static final String t2col6 = "date";




    public ItemDatabaseHelper(Context context) {
        super(context, table_name2,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable2 = "CREATE TABLE " + table_name2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + t2col2 + " INTEGER, " + t2col3 + " TEXT, " +
                t2col4 + " INTEGER, " + t2col5 + " INTEGER, " + t2col6 + " TEXT, FOREIGN KEY ( " + t2col2 + ") REFERENCES " + table_name + "(ID))";
        Log.d("table" , createTable2);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);
        onCreate(db);
    }

    public boolean addItemData(String itemName, Integer prc, Integer qty, String d, Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t2col2,id);
        contentValues.put(t2col3,itemName);
        contentValues.put(t2col4,qty);
        contentValues.put(t2col5,prc);
        contentValues.put(t2col6,d);
        long result=db.insert(table_name2,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getItemData(Integer id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String query= "SELECT * FROM " + table_name2 + " WHERE " +t2col2+ " = " + id;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getListItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + t2col1 + " FROM " + table_name2 +
                " WHERE " + t2col3 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemDataForDisplay(Integer id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String query= "SELECT * FROM " + table_name2 + " WHERE ID = "+ id;
        Log.d("query",query);
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void deleteItem(Integer id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String query= "DELETE FROM " + table_name2 + " WHERE ID = "+ id;
        db.execSQL(query);
    }

    public Cursor notification()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + table_name2;
        Log.d("Notification query",query);
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
