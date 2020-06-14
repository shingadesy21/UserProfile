package com.example.userprofile.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COL_1 ="ID";
    public static final String DATABASE_NAME = "register";
    public static final String TABLE_NAME = "userregister";
    public static final String USER_NAME = "U_NAME";
    public static final String USER_EMAIL = "U_EMAIL";
    public static final String USER_PASSWORD = "U_PASSWORD";
    public static final String USER_GENDER = "U_GENDER";
    public static final Integer VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + USER_NAME + "TEXT PRIMARY KEY" + "," + USER_EMAIL + "TEXT" + "," + USER_PASSWORD + "TEXT" + ")");//+ ", " + USER_GENDER + "TEXT"
        String sql_table="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY  KEY AUTOINCREMENT,U_NAME TEXT,U_EMAIL TEXT,U_PASSWORD TEXT,U_GENDER,image BLOB)";
        db.execSQL(sql_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userregister" );
        onCreate(db);
    }

    public long adduser(String user_name, String user_email, String user_password, String user_gender,byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

            values.put(USER_NAME, user_name);
            values.put(USER_EMAIL, user_email);
            values.put(USER_PASSWORD, user_password);
            values.put(USER_GENDER, user_gender);
            values.put("image",image);
            long res = database.insert(TABLE_NAME, null, values);
            database.close();
            return res;
    }

    public boolean check_user_data(String user_name,String user_password){
        SQLiteDatabase database=getReadableDatabase();
        String[] column= {COL_1};
        String selection="U_NAME"+"=?"+" and "+"U_PASSWORD"+"=?";
        String[] selectionarrays={user_name,user_password};
        Cursor cursor=database.query(TABLE_NAME,column,selection,selectionarrays,null,null,null);
        int count=cursor.getCount();
        cursor.close();
        database.close();
        if(count > 0){
            return true;
        }
        else
        {
            return false;
        }
    }
    public Cursor getdata(String name,String pass)
    {
            SQLiteDatabase database=getReadableDatabase();
            String[] column={COL_1,USER_NAME,USER_EMAIL,USER_GENDER};
            String selection="U_NAME"+"=?"+" and "+"U_PASSWORD"+"=?";
            String[] selectionarrays={name,pass};
            Cursor cursor=database.query(TABLE_NAME,column,selection,selectionarrays,null,null,null);

            return cursor;



    }

}

