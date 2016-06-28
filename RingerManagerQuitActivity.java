package edu.ncsu.soc.rms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class RingerManagerQuitActivity extends Activity{

	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.ringermanagerquitlayout);
	        
	    }

	    public boolean onCreateOptionsMenu(Menu menu){
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.quit_menu,menu);
	    	return true;
	    }
	 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	    	super.onOptionsItemSelected(item);

	    	Intent intent=new Intent(this,RingerManagerActivity.class);
	    	this.startActivity(intent);
	    	finish();
	    return true;	
	    }
	    	
	
}
