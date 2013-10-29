package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		RequestParams params = null;
	    String userId = getIntent().getStringExtra("screen_name");
	    if (userId != null) {
	    	params = new RequestParams();
	    	params.put("screen_name", userId);
	    	Log.i("USER_TIMELINE", params.toString());
	    	MyTwitterApp.getRestClient().getUserTimeline(params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
					User user = tweets.get(0).getUser();
					getActionBar().setTitle("@" + user.getScreenName());
					populateProfileHeader(user);
				}
	    		
	    	});
	    } else {
			MyTwitterApp.getRestClient().getProfileInfo(params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					User user = User.fromJson(json);
					getActionBar().setTitle("@" + user.getScreenName());
					populateProfileHeader(user);
				}


			});
	    }
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(user.getName());
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTagline.setText(user.getUserTagline());
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	

}
