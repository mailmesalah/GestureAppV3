package com.project.gestureappv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoadActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		Button bZoomActivity = (Button) findViewById(R.id.load_zoom_activity);
		bZoomActivity.setOnClickListener(this);
		
		Button bFlipActivity = (Button) findViewById(R.id.load_flip_activity);
		bFlipActivity.setOnClickListener(this);
		
		Button bFlingActivity = (Button) findViewById(R.id.load_fling_activity);
		bFlingActivity.setOnClickListener(this);
		
		Button bOtherActivity = (Button) findViewById(R.id.load_other_activity);
		bOtherActivity.setOnClickListener(this);
		
		Button bKeyboardActivity = (Button) findViewById(R.id.load_keyboard_activity);
		bKeyboardActivity.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v) {
		try {
			//Load Zoom Activity
			if (((Button) v).getText().equals("Load Zoom Activity")) {
				ParameterPasser.selectedActivity=ParameterPasser.ZOOM_ACTIVITY;
				Intent i = new Intent(this, ImageActivity.class);
				startActivity(i);
			}
			
			//Load Zoom Activity
			if (((Button) v).getText().equals("Load Flip Activity")) {
				ParameterPasser.selectedActivity=ParameterPasser.FLIP_ACTIVITY;
				Intent i = new Intent(this, ImageActivity.class);
				startActivity(i);
			}
			
			//Load Zoom Activity
			if (((Button) v).getText().equals("Load Fling Activity")) {
				ParameterPasser.selectedActivity=ParameterPasser.FLING_ACTIVITY;
				Intent i = new Intent(this, ImageActivity.class);
				startActivity(i);
			}
			
			//Load Zoom Activity
			if (((Button) v).getText().equals("Load Other Activity")) {
				ParameterPasser.selectedActivity=ParameterPasser.OTHER_ACTIVITY;
				Intent i = new Intent(this, ImageActivity.class);
				startActivity(i);
			}
			
			//Load Keyboard Activity
			if (((Button) v).getText().equals("Load Keyboard Activity")) {
				Intent i = new Intent(this, KeyboardActivity.class);
				startActivity(i);
			}
		} catch (Exception e) {

		}

	}
}
