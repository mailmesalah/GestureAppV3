package com.project.gestureappv2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class FlipActivity extends Activity implements GestureDetector.OnGestureListener, OnTouchListener{

private GestureDetector gestures;
	
	// these PointF objects are used to record the point(s) the user is touching
		PointF start = new PointF();
		PointF mid = new PointF();
		float oldDist = 1f;

		private int mode = NONE;
		
		// The 3 states (events) which the user is trying to perform
		static final int NONE = 0;		
		static final int DRAG = 1;

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flip);
		
		ImageView view = (ImageView) findViewById(R.id.imageHolderFlip);
		view.setImageBitmap(ParameterPasser.image);
		gestures = new GestureDetector(FlipActivity.this,this);
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		gestures.onTouchEvent(event); 
		
		ImageView view = (ImageView) v;
		view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: // first finger down only
			//savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());			
			mode = DRAG;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:

			mode = NONE;			
			break;

		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			
			if (oldDist > 5f) {
				//savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = NONE;				
			}
			break;

		case MotionEvent.ACTION_MOVE:

			if (mode == DRAG) {
				
				
				Bitmap bitmap=((BitmapDrawable)((ImageView)view).getDrawable()).getBitmap();
				
				//if on x axis finger is moved more that 5f difference should do a flip on that axis
				if(Math.abs(event.getX() - start.x)>5f){
					bitmap = ParameterPasser.flip(bitmap,ParameterPasser.FLIP_HORIZONTAL);
				}
				
				//if on y axis finger is moved more that 5f difference should do a flip on that axis 
				if(Math.abs(event.getY()- start.y)>5f){
					bitmap = ParameterPasser.flip(bitmap,ParameterPasser.FLIP_VERTICAL);
				}
				
				
				view.setImageBitmap(ParameterPasser.flip(bitmap,1));				
				
			} 
			break;
		}
		
		return true;
	}


	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);

		return (float) Math.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

}
