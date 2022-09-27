package com.project.gestureappv2;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ParameterPasser {

	
	
	public static Bitmap image = null;
	
	public static int currentImageIndex = 0;
	
	final static int ZOOM_ACTIVITY = 1;
    final static int FLIP_ACTIVITY = 2;
	final static int FLING_ACTIVITY = 3;
    final static int OTHER_ACTIVITY = 4;
    
    public static int selectedActivity = ZOOM_ACTIVITY;
	
	public static final String ImageFolder="/GestureApp";

	final static int FLIP_VERTICAL = 1;
    final static int FLIP_HORIZONTAL = 2;
    public static Bitmap flip(Bitmap src, int type) {
            // create new matrix for transformation
            Matrix matrix = new Matrix();
            // if vertical
            if(type == FLIP_VERTICAL) {
                matrix.preScale(1.0f, -1.0f);
            }
            // if horizonal
            else if(type == FLIP_HORIZONTAL) {
                matrix.preScale(-1.0f, 1.0f);
            // unknown type
            } else {
                return null;
            }

            // return transformed image
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }
}
