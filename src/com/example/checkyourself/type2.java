package com.example.checkyourself;

import com.example.checkyourself.gsCalendar.gsCalendarColorParam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class type2 extends Activity implements OnClickListener 
{
 
	TextView tvs[] ;
	Button btns[] ;
    

	class myGsCalendar extends gsCalendar
	{

		public myGsCalendar(Context context, LinearLayout layout) 
		{
			super(context, layout);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void myClickEvent(int yyyy, int MM, int dd) 
		{
			// TODO Auto-generated method stub
			
			cal.redraw( ) ;
			cal.applyHoliday( ) ;
			
			//선택된 날짜의 셀과 색상 설정.
	        cal.setSelectedDay( 0xffffffff,0xff009999 ) ;
	        
	       /// ���õ� ��¥�� �۾��� ����
			//cal.setSelectedDayTextColor( 0xff009999 ) ;
			
			super.myClickEvent(yyyy, MM, dd);
		}
		
	}
	
	
	myGsCalendar cal ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.set2);
        

        LinearLayout lv = (LinearLayout)findViewById( R.id.calendar_lLayout ) ;
        
        //년 월 표시 텍스트뷰 설
        tvs = new TextView[3] ;
        tvs[0] = (TextView)findViewById( R.id.tv1 ) ;
        tvs[1] = (TextView)findViewById( R.id.tv2 ) ;
        tvs[2] = null ; 
        
        //이전 이후 버튼 설
        btns = new Button[4] ;
        btns[0] = null ; //전년도 이동 
        btns[1] = null ; // 다음년도 이동 
        btns[2] = (Button)findViewById( R.id.Button03 ) ;
        btns[3] = (Button)findViewById( R.id.Button04 ) ;
        
        /// calendar생성 
        cal = new myGsCalendar( this, lv ) ;
        
        /// ���� ������ ��ü ��
        gsCalendarColorParam cParam = new gsCalendarColorParam( ) ;
        
        cParam.m_cellColor = 0x00000000 ;
      //  cParam.m_textColor = 0x002f4f4f ;
        cParam.m_saturdayTextColor = 0xff33ccff ;
        cParam.m_lineColor = 0xff000000 ;
        cParam.m_topCellColor = 0xff003333 ;
        cParam.m_topTextColor = 0xffffffff ;
       // cParam.m_topSundayTextColor = 0xffffffff ;
       // cParam.m_topSaturdatTextColor = 0xffffffff ;
        
        //각각의 디바이스 크기에 따라 캘린더크기 변경을 위해 사
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int deviceWidth = size.x;
        int deviceHeight = size.y-220;
        
        
        cal.setColorParam( cParam ) ;
        Drawable img = getResources( ).getDrawable( R.drawable.bg ) ;
        // ��� �̹��� ��~
        cal.setBackground( img );
        
        /// �޷��� ��� ũ�� ����
        cal.setCalendarSize( deviceWidth, deviceHeight ) ;
        
        //요일 텍스트 사이즈 설정.
        cal.setTextSize(20);
        
        //이놈 추가됨.
        //cal.setCellSize(60, 50, 50);
        //달력의 요일 항목 높이 설정 
        cal.setTopCellSize( 80 ) ;
        
        /// gsCalendar 클래스에 위에서 생성한 버튼을 파라미터로 넘겨줌.
        cal.setControl( btns ) ;
        
        /// �� �� ���� ��� �ؽ�Ʈ�� ����
        cal.setViewTarget( tvs ) ;
        
        cal.initCalendar( ) ;
        
        /// ���õ� ��¥�� ��� �̹����� ����
      //  cal.setSelectedDay( getResources( ).getDrawable( R.drawable.icon ) ) ;
        
        /// ���õ� ��¥�� �۾��� ����
        cal.setSelectedDayTextColor( 0xff009999 ) ;
        
        ///// ���õ� ��¥�� �۾��� ����� ����
        //cal.setSelectedDay( 0xff000000, 0xffffffff ) ;
        
        /// 3�� 24���� �����̶�� ���
        cal.addHoliday( 324 ) ;
        
        /// ������ �Ͽ��ϰ� ���� �۾������� �����Ѵ�.
        cal.applyHoliday( ) ;
        
        
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void backBtn(View view){
		finish();
	}

}