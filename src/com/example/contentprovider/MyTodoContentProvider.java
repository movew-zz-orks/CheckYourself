package com.example.contentprovider;


import android.database.Cursor;
import android.net.Uri;
import java.util.Arrays;
import java.util.HashSet;

import com.example.SQLitedatabase.Comment;
import com.example.SQLitedatabase.MySQLiteHelper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;


public class MyTodoContentProvider extends ContentProvider {

  // database
  private MySQLiteHelper database;

  // used for the UriMacher
  private static final int TODOS = 10;
  private static final int TODO_ID = 20;

  private static final String AUTHORITY = "moveworks.todos.contentprovider";

  private static final String BASE_PATH = "todos";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/" + BASE_PATH);

  public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/todos";
  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/todo";

  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  
  static {
    sURIMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
  }

  @Override
  public boolean onCreate() {
    database = new MySQLiteHelper(getContext());
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {

    // SQLiteQueryBuilder를 통해 qury시작. 
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    //넘겨받은 column의 값의 유효성 체크 
    checkColumns(projection);

    // 테이블 설정 
    queryBuilder.setTables(Comment.TABLE_TODO);

    int uriType = sURIMatcher.match(uri);
    switch (uriType) {
    case TODOS:
      break;
    case TODO_ID:
    //ID에 해당하는 부분을 original query에 덧붙임.
      queryBuilder.appendWhere(Comment.COLUMN_ID + "="+ uri.getLastPathSegment());
      break;
      
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    SQLiteDatabase db = database.getWritableDatabase();
    Cursor cursor = queryBuilder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
    //잠재적인 listener에게 notify함.
    cursor.setNotificationUri(getContext().getContentResolver(), uri);

    return cursor;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    long id = 0;
    switch (uriType) {
    
    case TODOS:
      id = sqlDB.insert(Comment.TABLE_TODO, null, values);
      break;
      
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.parse(BASE_PATH + "/" + id);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    int rowsDeleted = 0;
    switch (uriType) {
    
    case TODOS:
      rowsDeleted = sqlDB.delete(Comment.TABLE_TODO, selection,
          selectionArgs);
      break;
      
    case TODO_ID:
      String id = uri.getLastPathSegment();
      if (TextUtils.isEmpty(selection)) 
      {
    	  rowsDeleted = sqlDB.delete(Comment.TABLE_TODO,Comment.COLUMN_ID + "=" + id, null);
      } else {
    	  rowsDeleted = sqlDB.delete(Comment.TABLE_TODO,Comment.COLUMN_ID + "=" + id + " and " + selection,selectionArgs);
      }
      break;
    default:
      throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsDeleted;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {

    int uriType = sURIMatcher.match(uri);
    SQLiteDatabase sqlDB = database.getWritableDatabase();
    int rowsUpdated = 0;
    switch (uriType) {
    
    	case TODOS:
    		rowsUpdated = sqlDB.update(Comment.TABLE_TODO, values, selection,selectionArgs);
    		break;
      
    	case TODO_ID:
    		String id = uri.getLastPathSegment();
    		if (TextUtils.isEmpty(selection)) 
    		{
    			rowsUpdated = sqlDB.update(Comment.TABLE_TODO, values,Comment.COLUMN_ID + "=" + id, null);
    		} else {
    			rowsUpdated = sqlDB.update(Comment.TABLE_TODO, values,Comment.COLUMN_ID + "=" + id + " and " + 
    					selection,selectionArgs);
    		}
    		break;
    
    	default:
    		throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return rowsUpdated;
  }

  private void checkColumns(String[] projection) {
    String[] available = {Comment.COLUMN_CATEGORY,Comment.COLUMN_SUMMARY, Comment.COLUMN_DESCRIPTION,Comment.COLUMN_ID ,Comment.COLUMN_YEAR,Comment.COLUMN_MONTH,Comment.COLUMN_DAY ,Comment.COLUMN_DATE};
    
    if (projection != null) {
      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
      // check if all columns which are requested are available
      if (!availableColumns.containsAll(requestedColumns)) {
        throw new IllegalArgumentException("Unknown columns in projection");
      }
    }
  }

} 