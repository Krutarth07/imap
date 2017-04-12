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


public class StudentRepo {
    private DBHelper dbHelper;
    String name,desc;
    int id;
    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_title, student.title);
        values.put(Student.KEY_desc,student.desc);


        // Inserting Row
        long student_Id = db.insert(Student.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_title, student.title);
        values.put(Student.KEY_desc,student.desc);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[]{String.valueOf(student.student_ID)});
        db.close(); // Closing database connection
    }

    public ArrayList<String> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_title + ","+Student.KEY_ID+ "," + Student.KEY_desc +

                " FROM " + Student.TABLE;

        //Student student = new Student();
        ArrayList<String> studentList = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Student.KEY_title));
                desc = cursor.getString(cursor.getColumnIndex(Student.KEY_desc));

                studentList.add(cursor.getString(cursor.getColumnIndex(Student.KEY_title)));
                studentList.add(cursor.getString(cursor.getColumnIndex(Student.KEY_desc)));
                studentList.add(cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }



/*
    public ArrayList<HashMap<String, String>>  getStudentList(int Id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_title + ","+Student.KEY_ID+

                " FROM " + Student.TABLE + " WHERE " +Student.KEY_ID + " = " + Id ;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Student.KEY_title));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

*/

}