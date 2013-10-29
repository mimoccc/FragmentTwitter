
package com.codepath.apps.mytwitterapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;

public class TimelineActivity extends FragmentActivity implements TabListener {
	
	HomeTimelineFragment homeFragment = new HomeTimelineFragment();
	MentionsFragment mentionsFragment = new MentionsFragment();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
    
    setupNavigationTabs();
  }


  private void setupNavigationTabs() {
	  ActionBar actionBar = getActionBar();
	  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	  actionBar.setDisplayShowTitleEnabled(true);
	  Tab tabHome = actionBar.newTab().setText("Home")
			  .setTag("HomeTimelineFragment")
			  .setIcon(R.drawable.ic_home)
			  .setTabListener(this);
	  
	  Tab tabMentions = actionBar.newTab().setText("Mentions")
			  .setTag("MentionsTimelineFragment")
			  .setIcon(R.drawable.ic_mentions)
			  .setTabListener(this);
	  
	  actionBar.addTab(tabHome);
	  actionBar.addTab(tabMentions);
	  actionBar.selectTab(tabHome);
	  
  }
  
  
  public void onProfileView(MenuItem item) {
	  Intent intent = new Intent(this, ProfileActivity.class);
	  startActivity(intent);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.timeline, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_compose:
        startComposeActivity(item);
        //Toast.makeText(getBaseContext(), "Options", Toast.LENGTH_LONG).show();
        break;
      default:
        break;
    }
    return true;
  }

  private void startComposeActivity(MenuItem item) {
    Intent intent = new Intent(getApplicationContext(), ComposeActivity.class);
    startActivity(intent);
  }



@Override
public void onTabReselected(Tab tab, FragmentTransaction ft) {
	// TODO Auto-generated method stub
	
}



@Override
public void onTabSelected(Tab tab, FragmentTransaction ft) {
	FragmentManager manager = getSupportFragmentManager();
	android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
	if (tab.getTag() == "HomeTimelineFragment") {
		fts.replace(R.id.frameFragment, homeFragment);
	} else {
		fts.replace(R.id.frameFragment, mentionsFragment);
	}
	fts.commit();
}



@Override
public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	// TODO Auto-generated method stub
	
}
  

}
