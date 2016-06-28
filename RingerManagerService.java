package edu.ncsu.soc.rms;

import static edu.ncsu.mas.platys.applications.constants.PlatysConstants.REMOTE_PACKAGE_NAME;
import static edu.ncsu.mas.platys.applications.constants.PlatysConstants.REMOTE_SERVICE_NAME;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.mas.platys.android.stub.IPlatysMiddlewareRemoteService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class RingerManagerService extends Service {
  private static final String TAG = "RingerManagerService";
  
  private final IBinder mbinder = new RingerManagerBinder();
  
  public class RingerManagerBinder extends Binder{
	RingerManagerService getService(){
		return RingerManagerService.this;
	}
  }
  
  
  @Override
  public IBinder onBind(Intent intent) {
    return mbinder;
  }

  static final String APP_NAME = "RingerManagerService";
  static final String APP_SUMMARY = "A ringer manager client with a service";

  static final String ACTION_REQUEST_PLACE_UPDATES = "edu.ncsu.mas.samples.request_place_updates";

  static String privateKey;
  static String currentPlace;
  public static List<String> currentActivities = new ArrayList<String>();
  public static List<String> allPlaces = new ArrayList<String>();
  
  AlarmManager am;
  
  @Override
  public void onCreate() {
    Log.i(TAG, "Service created");
    am = (AlarmManager) getSystemService (Context.ALARM_SERVICE);
  }
  @Override
  public int onStartCommand(Intent intent, int flags, int startId){
	  if(intent.getAction()!=null && intent.getAction().equals(ACTION_REQUEST_PLACE_UPDATES)){
		  Log.i(TAG,"Update current Place");
		  updateCurrentPlace();
	  }
	  return START_STICKY;
  }
  
  private IPlatysMiddlewareRemoteService mService=null;
  
  ServiceConnection mConnection = new ServiceConnection(){
	  public void onServiceConnected(ComponentName className, IBinder service){
		  mService=IPlatysMiddlewareRemoteService.Stub.asInterface(service);
		  try{
			  privateKey= mService.registerApplication(APP_NAME, APP_SUMMARY);
			  Log.i(TAG,"Private Key: " + privateKey);
			  schedulePlaceUpdates();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  }
	  }

	public void onServiceDisconnected(ComponentName arg0) {
		privateKey=null;
		mService=null;
		
	}
  };
  
  public void registerToPlatysMiddleware(){
	  Intent intent=new Intent();
	  intent.setClassName(REMOTE_PACKAGE_NAME, REMOTE_SERVICE_NAME);
	  bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }
  
  public void unregisterToPlatysMiddleware(){
	  try{
		  if(mService!= null){
			  mService.unregisterApplication(privateKey);
		  }
	  }
	  catch(Exception e){
		  e.printStackTrace();
	  }
  }
  
  public String getCurrentPlace() {
	    return currentPlace;
	  }
  
  public void updateCurrentPlace() {
	try{
		if(mService != null && privateKey != null){
			currentPlace = mService.getCurrentPlace(privateKey);
			Log.i(TAG,"Current Place is : " + currentPlace);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
   }
  public List<String> getCurrentActivities() {
	
	return currentActivities;
    }

  public void updateCurrentActivity(){
	try{
		if(mService!=null && privateKey!=null){
			currentActivities.clear();
			currentActivities.addAll(mService.getCurrentActivities(privateKey));
			Log.i(TAG, "Current Activities are: " + currentActivities);
			
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
  }
  
  public List<String> getAllPlaces(){
	  return allPlaces;
  }
  
  public void updateAllPlaces(){
	  try{
		  if(mService!=null && privateKey!=null){
			  allPlaces.addAll(mService.getAllPlaces(privateKey));
			  Log.i(TAG,"The Places Tagged are: " + allPlaces);
		  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
  public void schedulePlaceUpdates() {
	    Log.i(TAG, "Scheduling place updates");

	    Intent intent = new Intent(this, RingerManagerService.class);
	    intent.setAction(ACTION_REQUEST_PLACE_UPDATES);
	    PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

	    // Repeat every 10 minutes, starting now
	    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 60 * 1000, pendingIntent);
	  }

	  public void stopPlaceUpdates() {
	    Log.i(TAG, "Stop place updates");

	    Intent intent = new Intent(this, RingerManagerService.class);
	    intent.setAction(ACTION_REQUEST_PLACE_UPDATES);
	    PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

	    am.cancel(pendingIntent);
	  }
  
  
}
