package com.mfk.donotdistrub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String dbname="mydb";
    public static final String tblname="doknotdisturb4";
    public static final String col1="ID";
    public static final String col2="NAME";
    public static final String col3="PHONE";
    public static final String col4="MESSAGE";
    public static final String col5="BUSY";
    public static final String col6="DAYTO";
    public static final String col7="TOTIME";
    public static final String col8="FROMTIME";
    public static final String col9="DAYFROM";




    public DatabaseHelper(Context context) {

        super(context, dbname, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createtbl="CREATE TABLE "+tblname+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT,PHONE TEXT,MESSAGE TEXT,BUSY INTEGER,DAYTO INTEGER,TOTIME INTEGER,FROMTIME INTEGER,DAYFROM INTEGER)";
        sqLiteDatabase.execSQL(createtbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
             db.execSQL(String.format("DROP TABLE IF EXISTS "+ tblname));
             onCreate(db);
    }
    public boolean addData(String name,String phone,String message,int busy,int dayto,int dayfrom,int timeto, int timefrom){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,phone);
        contentValues.put(col4,message);
        contentValues.put(col5,busy);
        contentValues.put(col6,dayto);
        contentValues.put(col7,timeto);
        contentValues.put(col8,timefrom);
        contentValues.put(col9,dayfrom);
        long result=db.insert(tblname,null,contentValues);
        if(result==-1)
        {
            return false;

        }
        else {
            return true;
        }
    }

    public ArrayList<Contact> getAll()
    {

        ArrayList<Contact>C=new ArrayList <Contact>();
        SQLiteDatabase db=this.getWritableDatabase();
       // Cursor data=db.rawQuery("SELECT * FROM doknotdisturb WHERE PHONE='"+number+"'",null);
        Cursor data=db.rawQuery("SELECT * FROM "+tblname,null);
        if(data.getCount()==0);
        {
          while(data.moveToNext())
          {
              String id=data.getString(0);
              String name=data.getString(1);
              String phone=data.getString(2);
              String message=data.getString(3);
              int busy=data.getInt(4);
              int timeto=data.getInt(6);
              int timefrom=data.getInt(7);
              int dayto=data.getInt(5);
              int dayfrom=data.getInt(8);

              String label=null;

              Contact c=new Contact(id,name,phone,label,busy,dayto,timeto,timefrom,dayfrom,message);
              C.add(c);
          }
        }
        return C;
    }
    public boolean updateStatus(String num)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            String query="UPDATE " + tblname + " SET BUSY=1 WHERE PHONE='"+num+"'";
            db.execSQL(query);
            System.out.println(query);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
           /// System.out.println(e.getCause());
            return false;
        }

    }


}
