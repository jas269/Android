package edu.ncsu.soc.rms;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.soc.rms.RingerManagerService.RingerManagerBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SetRingerVolumeActivity extends Activity{
	private RingerManagerService mService=null;
	public static final int GET_CODE=0;
	public static int ringLoc1=1, ringLoc2=1, ringLoc3=1, ringLoc4=1, ringLoc5=1;
	List<String> placesList = new ArrayList<String>();
	String location=null,place=null;
	
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.setringervolume_layout);
	        
	  }
	 @Override
	    public void onStart(){
	    	super.onStart();
	    	Intent intent = new Intent(this, RingerManagerService.class);
	    	bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	    	
	    }
	    
	   @Override
	   public void onStop(){
	    	super.onStop();
	    	if(mService!=null){
	    		unbindService(mConnection);
	    		mService=null;
	    	}
	    }
	  
	  private final ServiceConnection mConnection= new ServiceConnection(){
	    	public void onServiceConnected(ComponentName className, IBinder service){
	    		RingerManagerBinder binder= (RingerManagerBinder) service;
	    		mService = binder.getService();
	    	}
	    	public void onServiceDisconnected(ComponentName arg0){
	    		mService=null;
	    	}
		
	    };
	    
	    public boolean onCreateOptionsMenu(Menu menu){
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.setringervolume_menu,menu);
	    	return true;
	    }
	    
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	    	super.onOptionsItemSelected(item);
	    	
	    
	if(mService!=null){
		mService.updateCurrentPlace();
		place=mService.getCurrentPlace();
		mService.updateAllPlaces();
		placesList.addAll(mService.getAllPlaces());
	}
	TextView[] allplaceResponseTextView= new TextView[5];
	allplaceResponseTextView[0]=(TextView) findViewById(R.id.allplaceResponseTextView1);
	allplaceResponseTextView[1]=(TextView) findViewById(R.id.allplaceResponseTextView2);
	allplaceResponseTextView[2]=(TextView) findViewById(R.id.allplaceResponseTextView3);
	allplaceResponseTextView[3]=(TextView) findViewById(R.id.allplaceResponseTextView4);
	allplaceResponseTextView[4]=(TextView) findViewById(R.id.allplaceResponseTextView5);
	
	int i=0;
	for(String allPlace: placesList){
		if(allPlace!=null){
			
			allplaceResponseTextView[i].setText(allPlace);
			i++;
		}
	}
	return true;
	    }
	    public void onRadioButton1Click(View v){
	    	TextView allplaceResponseTextView1=(TextView) findViewById(R.id.allplaceResponseTextView1);
	    	Intent myIntent = new Intent(this, RingerVolumeActivity.class);
	    	location=allplaceResponseTextView1.getText().toString();
	    	this.startActivityForResult(myIntent,GET_CODE);
	    	Button b1=(Button) findViewById(R.id.button1);
	    	b1.setClickable(true);
	    }
	    
	    public void onRadioButton2Click(View v){
	    	TextView allplaceResponseTextView2=(TextView) findViewById(R.id.allplaceResponseTextView2);
	    	Intent myIntent = new Intent(this, RingerVolumeActivity.class);
	    	location=allplaceResponseTextView2.getText().toString();
	    	this.startActivityForResult(myIntent,GET_CODE);
	    	Button b2=(Button) findViewById(R.id.button2);
	    	b2.setClickable(true);
	    }
	    
	    public void onRadioButton3Click(View v){
	    	TextView allplaceResponseTextView3=(TextView) findViewById(R.id.allplaceResponseTextView3);
	    	Intent myIntent = new Intent(this, RingerVolumeActivity.class);
	    	location=allplaceResponseTextView3.getText().toString();
	    	this.startActivityForResult(myIntent,GET_CODE);
	    	Button b3=(Button) findViewById(R.id.button3);
	    	b3.setClickable(true);

	    }
	    
	    public void onRadioButton4Click(View v){
	    	TextView allplaceResponseTextView4=(TextView) findViewById(R.id.allplaceResponseTextView4);
	    	Intent myIntent = new Intent(this, RingerVolumeActivity.class);
	    	location=allplaceResponseTextView4.getText().toString();
	    	this.startActivityForResult(myIntent,GET_CODE);
	    	Button b4=(Button) findViewById(R.id.button4);
	    	b4.setClickable(true);

	    }
	    public void onRadioButton5Click(View v){
	    	TextView allplaceResponseTextView5=(TextView) findViewById(R.id.allplaceResponseTextView5);
	    	Intent myIntent = new Intent(this, RingerVolumeActivity.class);
	    	location=allplaceResponseTextView5.getText().toString();
	    	this.startActivityForResult(myIntent,GET_CODE);
	    	Button b5=(Button) findViewById(R.id.button5);
	    	b5.setClickable(true);
	    }
	     @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     

	     if (requestCode == GET_CODE && resultCode == RESULT_OK){
	      
	    	 
	    	 if(mService!=null){
	        	 mService.updateCurrentPlace();
	    			place= mService.getCurrentPlace();
	    	
	         }
	    	 AudioManager am= (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    	 if(location==(((TextView) findViewById(R.id.allplaceResponseTextView1)).getText().toString())){
	    		 if(location.equals(place))
		  		   {
		   			
		   			
		   		  if(RingerManagerActivity.volume.equals("Silent")){
		   		
		   			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		   			ringLoc1=AudioManager.RINGER_MODE_SILENT;
		   		   }
		   		   else if(RingerManagerActivity.volume.equals("Vibrate")){
		   			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		   			ringLoc1=AudioManager.RINGER_MODE_VIBRATE;
		   		   }
		   		   else if(RingerManagerActivity.volume.equals("Normal")){
		   			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		   			ringLoc1=AudioManager.RINGER_MODE_NORMAL;
		  		       }
		   		  
		  		   }
	   	   }
	   	  
	   	  if(location==(((TextView) findViewById(R.id.allplaceResponseTextView2)).getText().toString())){
	  		  
	   		
	   		if(location.equals(place))
	  		   {
	   			
	   			
	   		  if(RingerManagerActivity.volume.equals("Silent")){
	   		
	   			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	   			ringLoc2=AudioManager.RINGER_MODE_SILENT;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Vibrate")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	   			ringLoc2=AudioManager.RINGER_MODE_VIBRATE;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Normal")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	   			ringLoc2=AudioManager.RINGER_MODE_NORMAL;
	  		       }
	   		
	  		   }
	   		
	   	   }
	   	  
	   	  if(location==(((TextView) findViewById(R.id.allplaceResponseTextView3)).getText().toString())){
	  		 
	   		
	   		if(location.equals(place))
	  		   {
	   			
	   			
	   		  if(RingerManagerActivity.volume.equals("Silent")){
	   		
	   			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	   			ringLoc3=AudioManager.RINGER_MODE_SILENT;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Vibrate")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	   			ringLoc3=AudioManager.RINGER_MODE_VIBRATE;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Normal")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	   			ringLoc3=AudioManager.RINGER_MODE_NORMAL;
	  		       }
	   		
	  		   }
	   		
	   	   }
	   	  
	   	  if(location==(((TextView) findViewById(R.id.allplaceResponseTextView4)).getText().toString())){
	  		 
	   		
	   		if(location.equals(place))
	  		   {
	   			
	   			
	   		  if(RingerManagerActivity.volume.equals("Silent")){
	   		
	   			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	   			ringLoc4=AudioManager.RINGER_MODE_SILENT;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Vibrate")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	   			ringLoc4=AudioManager.RINGER_MODE_VIBRATE;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Normal")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	   			ringLoc4=AudioManager.RINGER_MODE_NORMAL;
	  		       }
	   		
	  		   }
	   		
	   	   }
	   	  
	   	  if(location==(((TextView) findViewById(R.id.allplaceResponseTextView5)).getText().toString())){
	  		  
	   		
	   		if(location.equals(place))
	  		   {
	   			
	   			
	   		  if(RingerManagerActivity.volume.equals("Silent")){
	   		
	   			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	   			ringLoc5=AudioManager.RINGER_MODE_SILENT;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Vibrate")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	   			ringLoc5=AudioManager.RINGER_MODE_VIBRATE;
	   		   }
	   		   else if(RingerManagerActivity.volume.equals("Normal")){
	   			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	   			ringLoc5=AudioManager.RINGER_MODE_NORMAL;
	  		       }
	   		
	  		   }
	   		
	   	   }
	   	
	     }
	     
	    }
	     public void onButtonClick(View view){
	    	
	   	
	         Intent intent=new Intent(this, RingerManagerQuitActivity.class);
	       this.startActivity(intent);
	       
	       
	       
	     }
	    
}
