package edu.ncsu.soc.rms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class RingerVolumeActivity extends Activity {
	
	String  location=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ringervolumelayout);
        
    }
   


    
    public void onRadioButtonClick(View v){
    	
    	RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.RadioButtonGroup1);
    	int radioBtnChecked = radioGroup1.getCheckedRadioButtonId(); 
    	for(int i=0; i<radioGroup1.getChildCount(); i++) {
            RadioButton btn = (RadioButton) radioGroup1.getChildAt(i);
            if(btn.getId() == radioBtnChecked) {

            	RingerManagerActivity.volume = btn.getText().toString();
    	       
            }
    	}
    	
    	Intent intent =new Intent();
    	setResult(RESULT_OK, intent);
        finish();

    	
    }
    
  
}
