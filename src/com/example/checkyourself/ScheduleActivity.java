package com.example.checkyourself;


import java.util.ArrayList;
import java.util.List;

import com.example.SQLitedatabase.Comment;
import com.example.SQLitedatabase.MySQLiteHelper;
import com.example.checkyourself.R;
import com.example.contentprovider.MyTodoContentProvider;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ScheduleActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	
	 private static final int ACTIVITY_CREATE = 0;
	  private static final int ACTIVITY_EDIT = 1;
	  private static final int DELETE_ID = Menu.FIRST + 1;
	  // private Cursor cursor;
	  private SimpleCursorAdapter adapter;


	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.todo_list);
	    
	    //list뷰의 각행의 구분선을 없앰.
	    this.getListView().setDividerHeight(0);
	    //List View구성 
	    //fillData();
	    
	    registerForContextMenu(getListView());
	    
	  }
	     //필요 없을 꺼같음./..
	  // create the menu based on the XML defintion
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.listmenu, menu);
	    return true;
	  }
	  
	//필요 없을 것으로 보임.
	  // Reaction to the menu selection
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.insert:
	      createTodo();
	      return true;
	    }
	    return super.onOptionsItemSelected(item);
	  }
	  private void createTodo() {
		    Intent i = new Intent(this, TodoDetailActivity.class);
		    startActivity(i);
		  }


	  
	  @Override
	  public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case DELETE_ID:
	      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	      Uri uri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/"+ info.id);
	      getContentResolver().delete(uri, null, null);
	      fillData();
	      return true;
	    }
	    return super.onContextItemSelected(item);
	  }
	  
	
	  
	  
	  //ListView의 Row가 클릭 되었을때 두번째 액티비티를 띄움. 
	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    
	    Intent i = new Intent(this, TodoDetailActivity.class);
	    Uri todoUri = Uri.parse(MyTodoContentProvider.CONTENT_URI + "/" + id);
	    i.putExtra(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);
	    startActivity(i);
	  }

	  

	  private void fillData() {

	    // Fields from the database (projection)
	    // Must include the _id column for the adapter to work
	    String[] from = new String[] { Comment.COLUMN_SUMMARY };
	    
	    // Fields on the UI to which we map
	    int[] to = new int[] { R.id.label };
	    
	    
	    getLoaderManager().initLoader(0, null, this);
	    adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from,to, 0);
	    setListAdapter(adapter);
	    

	  }

	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	  }
	  
	  //initLoader()가 호출된 후에 새로운 loader를 생성함.
	  @Override
	  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    String[] projection = { Comment.COLUMN_ID,Comment.COLUMN_SUMMARY,Comment.COLUMN_DESCRIPTION  ,Comment.COLUMN_YEAR,Comment.COLUMN_MONTH,Comment.COLUMN_DAY,Comment.COLUMN_DATE};
	 
	    CursorLoader cursorLoader = new CursorLoader(this,MyTodoContentProvider.CONTENT_URI, projection, null, null, null);
	    return cursorLoader;
	  }

	  @Override
	  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
	    adapter.swapCursor(data);
	  }

	  @Override
	  public void onLoaderReset(Loader<Cursor> loader) {
	    // data is not available anymore, delete reference
	    adapter.swapCursor(null);
	  }
	  
	  public void addBtn(View view){
		switch(view.getId())
		{
			case R.id.addBtn2:
				Intent detailView=new Intent(this,TodoDetailActivity.class);
				startActivity(detailView);
			break;
		
			case R.id.backBtn2:
				finish();
			break;
		}  
	  }
	  
	  
	 

	} 
