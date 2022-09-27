package com.project.gestureappv2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class FlingActivity extends Activity implements
		GestureDetector.OnGestureListener, OnTouchListener {

	private GestureDetector gestures;

	private List<Bitmap> images = new ArrayList<Bitmap>();

	private static int currentIndex = 0;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fling);

		images = getAllImages();

		ImageView view = (ImageView) findViewById(R.id.imageHolderFling);
		view.setImageBitmap(ParameterPasser.image);
		currentIndex = ParameterPasser.currentImageIndex;

		gestures = new GestureDetector(FlingActivity.this, this);
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestures.onTouchEvent(event);
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

		// Toast.makeText(FlingActivity.this, "Swipe",
		// Toast.LENGTH_SHORT).show();
		if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			// Toast.makeText(FlingActivity.this, "Left Swipe",
			// Toast.LENGTH_SHORT).show();
			// Right to left
			currentIndex += 1;// Increment current Index
			if (currentIndex >= images.size()) {
				currentIndex = 0;
			}

			ImageView view = (ImageView) findViewById(R.id.imageHolderFling);
			view.setImageBitmap(images.get(currentIndex));
			return false;
		} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			// Toast.makeText(FlingActivity.this, "Right Swipe",
			// Toast.LENGTH_SHORT).show();
			// Left to right

			currentIndex -= 1;// Increment current Index
			if (currentIndex < 0) {
				currentIndex = images.size() - 1;
			}

			ImageView view = (ImageView) findViewById(R.id.imageHolderFling);
			view.setImageBitmap(images.get(currentIndex));

			return false;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestures.onTouchEvent(event);

	}

	private List<Bitmap> getAllImages() {
		ArrayList<Bitmap> imageList = new ArrayList<>();

		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath()
				+ ParameterPasser.ImageFolder);
		if (!dir.exists()) {
			dir.mkdir();
		}

		String[] files = dir.list();

		if (files != null) {
			// Sort to read it in order
			sortFilesByNumber(files);
			for (String f : files) { // loop and print all file
				try {
					if (f.lastIndexOf(".txt") == -1) {

						Bitmap bitmap = BitmapFactory.decodeFile(sdCard
								.getAbsolutePath()
								+ ParameterPasser.ImageFolder + "/" + f);
						imageList.add(bitmap);
					}
				} catch (Exception e) {
					Log.d("Error in GridItemsAdapter.getAllImages()",
							e.getMessage());
				}
			}
		}
		return imageList;
	}

	private String[] sortFilesByNumber(String[] files) {

		Arrays.sort(files, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int n1 = extractNumber(o1);
				int n2 = extractNumber(o2);
				return n1 - n2;
			}

			private int extractNumber(String name) {
				int i = 0;
				try {
					int s = name.indexOf('_') + 1;
					int e = name.lastIndexOf('.');
					String number = name.substring(s, e);
					i = Integer.parseInt(number);
				} catch (Exception e) {
					i = 0; // if filename does not match the format
							// then default to 0
				}
				return i;
			}
		});

		return files;
	}

}
