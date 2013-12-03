package com.example.checkyourself;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity  {
	Button calendarBtn;
	Button scheduleBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calendarBtn=(Button)findViewById(R.id.Calendar);
		scheduleBtn=(Button)findViewById(R.id.Schedule);
		
	}
	
	public void btnClick(View view){
		switch(view.getId()){
		case R.id.Calendar:
			Intent calendarActivity =new Intent(this,type2.class);
			startActivity(calendarActivity);
			break;
			
		case R.id.Schedule:	
			Intent scheduleActivity=new Intent(this,ScheduleActivity.class);
			startActivity(scheduleActivity);
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
}
