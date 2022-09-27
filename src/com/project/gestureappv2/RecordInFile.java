package com.project.gestureappv2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;

public class RecordInFile {

	public static void writeToText(String text,String fileName){
	
		File dir = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/GestureApp");

		if (!dir.exists()) {
			dir.mkdir();
        }
		
		File file = new File(dir, fileName);

		
		try {
			FileOutputStream f = new FileOutputStream(file,true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(f);
		    myOutWriter.append(text);
		    myOutWriter.close();
		    f.close();
		} catch (FileNotFoundException e) {
			Log.d("Error: ", e.getMessage());
		} catch (IOException e) {
			Log.d("Error: ", e.getMessage());
		}
	}
}
