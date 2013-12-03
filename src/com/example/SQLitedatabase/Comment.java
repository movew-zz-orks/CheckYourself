package com.example.SQLitedatabase;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Comment {

  // Database table
  public static final String TABLE_TODO = "todo";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_CATEGORY = "category";
  public static final String COLUMN_SUMMARY = "summary";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_YEAR="year";
  public static final String COLUMN_MONTH="month";
  public static final String COLUMN_DAY="day";
  public static final String COLUMN_DATE="currentdate";
  

  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table " + TABLE_TODO+ "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_CATEGORY + " text not null, " + COLUMN_SUMMARY + " text not null," + COLUMN_DESCRIPTION+ " text not null,"+ COLUMN_YEAR+" text not null, "+ COLUMN_MONTH+" text not null,"+ COLUMN_DAY+" text not null,"+ COLUMN_DATE+"text not null);";	

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,int newVersion) {
    Log.w(Comment.class.getName(), "Upgrading database from version "+ oldVersion + " to " + newVersion+ ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
    onCreate(database);
  }
} 
