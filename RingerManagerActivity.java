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
import android.widget.TextView;

public class RingerManagerActivity extends Activity {
	
	
	private RingerManagerService mService=null;
	
	public static String volume, prevPlace="Unknown";
	List<String> activityList = new ArrayList<String>();
	List<String> placesList = new ArrayList<String>();
	String place=null;
	


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startService(new Intent(this, RingerManagerService.class)); 
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
    	inflater.inflate(R.menu.main_menu,menu);
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()){
    	case R.id.register:
    		if(mService!=null){
    			mService.registerToPlatysMiddleware();
        			mService.updateCurrentPlace();
        			place= mService.getCurrentPlace();
        			
        		
    			
    			
    		}
    	    break;
    	
    	case R.id.unregister:
    		if(mService!=null){
    			mService.unregisterToPlatysMiddleware();
    		}
    		break;
    	case R.id.request_place:
    		if(mService!=null){
    			mService.updateCurrentPlace();
    			place= mService.getCurrentPlace();
    			mService.updateCurrentActivity();
    			activityList.addAll(mService.getCurrentActivities());	
    		}
    		TextView placeResponseView = (TextView) findViewById(R.id.placeResponseTextView);
    		if(place !=null && !place.equals("")){
    			placeResponseView.setText(place);
    		}
    		else{
    			placeResponseView.setText("Unknown");
    		}
    		
    		TextView activityResponseTextView = (TextView) findViewById(R.id.activityResponseTextView);
    		StringBuilder activities = new StringBuilder();
    		for(String activity : activityList){
    			if(activity!=null){
    				activities.append(activity + " ");
    			}
    		
    		}
    		if(activities.length()== 0){
    			activities.append("Unknown");
    		}
    		activityResponseTextView.setText(activities.toString());
    		break;
    		
    	case R.id.setringer:
    		Intent myintent=new Intent(this,SetRingerVolumeActivity.class);
    		this.startActivity(myintent);
    		break;
    		
    	case R.id.updateringermode:
    		if(mService!=null){
    			mService.updateCurrentPlace();
    			place= mService.getCurrentPlace();
    			mService.updateAllPlaces();
    			placesList.addAll(mService.getAllPlaces());
    		}
    		if(!prevPlace.equals(place)){
    			AudioManager am= (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    	    	if(place.equals(placesList.get(0).toString())){
    	    		am.setRingerMode(SetRingerVolumeActivity.ringLoc1);
    	    	}
    	    	else if(place.equals(placesList.get(1).toString())){
    	    		am.setRingerMode(SetRingerVolumeActivity.ringLoc2);
    	    	}
    	    	else if(place.equals(placesList.get(2).toString())){
    	    		am.setRingerMode(SetRingerVolumeActivity.ringLoc3);
    	    	}
    	    	else if(place.equals(placesList.get(3).toString())){
    	    		am.setRingerMode(SetRingerVolumeActivity.ringLoc4);
    	    	}
    	    	else if(place.equals(placesList.get(4).toString())){
    	    		am.setRingerMode(SetRingerVolumeActivity.ringLoc5);
    	    	}
    	    	else{
    	    		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    	    	}
			}
    		break;
    		
    	case R.id.quit:
    		if(mService!=null){
        		unbindService(mConnection);
        		mService=null;
        	}
    		finish();
    		break;
    	}
    	
    	return true;
    	
    }
    
    	
    


    
}