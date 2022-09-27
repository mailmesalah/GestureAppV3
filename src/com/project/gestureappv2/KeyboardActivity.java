package com.project.gestureappv2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class KeyboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboard);
		
		EditText keyboardText=(EditText)findViewById(R.id.keyboardText);
		keyboardText.addTextChangedListener(new TextWatcher() {          
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { 
            	
                    
            }                       
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            	
            	SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
            	String dateS = datenTime.format(new Date());
            	String text =dateS+" beforeTextChanged "+s+" \n";
        		RecordInFile.writeToText(text, "KeyBoard.txt");
                                          
            }                       
            @Override
            public void afterTextChanged(Editable s) {
                 
            	SimpleDateFormat datenTime = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a ");
            	String dateS = datenTime.format(new Date());
            	String text =dateS+" afterTextChanged "+s+" \n";
        		RecordInFile.writeToText(text, "KeyBoard.txt");

            }
        });
		
		keyboardText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
								
				return false;
			}
		});
	}
	
	}
