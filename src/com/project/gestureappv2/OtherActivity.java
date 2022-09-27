package com.project.gestureappv2;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class OtherActivity extends Activity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, OnTouchListener{

	private GestureDetector gestures;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		
		ImageView view = (ImageView) findViewById(R.id.imageHolderOther);
		view.setImageBitmap(ParameterPasser.image);	
		gestures = new GestureDetector(OtherActivity.this,this);
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {			
		
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onSingleTapConfirmed"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public boolean onDoubleTap(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onDoubleTap"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onDoubleTapEvent"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public boolean onDown(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onDown"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onShowPress"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onSingleTapUp"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onScroll"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
		
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
    	String dateS = datenTime.format(new Date());
    	String text =dateS+" onLongPress"+" \n";
		RecordInFile.writeToText(text, "Gesture.txt");
		
		Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {		
		
		return false;
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) 
    {
      return gestures.onTouchEvent(event);      

    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		return gestures.onTouchEvent(event);
	}
	
}
