package com.krutarth07.development.imap;

/**
 * Created by Krutarth on 02-06-2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;


public class manipulate {
    private DBHelper dbHelper;
    String name,desc;
    int id;
    public manipulate(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Rows student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Rows.title, student.title);
        values.put(Rows.desc,student.desc);


        // Inserting Row
        long student_Id = db.insert(Rows.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Rows.TABLE, Rows.id + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(Rows student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Rows.title, student.title);
        values.put(Rows.desc,student.desc);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Rows.TABLE, values, Rows.id + "= ?", new String[]{String.valueOf(student.id)});
        db.close(); // Closing database connection
    }

  /*  public ArrayList<String> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Rows.title + ","+ Rows.id+ "," + Rows.desc +

                " FROM " + Rows.TABLE;

        //Rows student = new Rows();
        ArrayList<String> valuelist = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                //id = cursor.getInt(cursor.getColumnIndex(Rows.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Rows.title));
                desc = cursor.getString(cursor.getColumnIndex(Rows.desc));

                valuelist.add(cursor.getString(cursor.getColumnIndex(Rows.title)));
                valuelist.add(cursor.getString(cursor.getColumnIndex(Rows.desc)));
                //valuelist.add(cursor.getString(cursor.getColumnIndex(Rows.KEY_ID)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return valuelist;

    }
*/



    public String getname(int Id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT title FROM " + Rows.TABLE + " WHERE " +Rows.id + " = " + Id ;

        //Rows student = new Rows();
      //  ArrayList<HashMap<String, String>> valuelist = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

       // if (cursor.moveToFirst()) {
         //   do {

           //     id = cursor.getInt(cursor.getColumnIndex(Rows.id));
                name = cursor.getString(cursor.getColumnIndex(Rows.title));

            //} while (cursor.moveToNext());
        //}

        cursor.close();
        db.close();
        return name;

    }

    public String getdesc(int Id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT desc FROM " + Rows.TABLE + " WHERE " +Rows.id + " = " + Id ;

        //Rows student = new Rows();
        //ArrayList<HashMap<String, String>> valuelist = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //if (cursor.moveToFirst()) {
          //  do {

                desc = cursor.getString(cursor.getColumnIndex(Rows.desc));
            //    name = cursor.getString(cursor.getColumnIndex(Rows.title));

            //} while (cursor.moveToNext());
       // }

     //   cursor.close();
        db.close();
        return desc;

    }


}