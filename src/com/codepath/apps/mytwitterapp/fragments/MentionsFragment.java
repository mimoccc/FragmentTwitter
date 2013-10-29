package com.codepath.apps.mytwitterapp.fragments;

import com.loopj.android.http.RequestParams;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {
	
	  private final static String MAX_ITEMS = "51";
	  private final static String URL = "statuses/mentions_timeline.json";
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  RequestParams params = new RequestParams();
		  params.put("count", MAX_ITEMS);
		  get(URL, params);
	  }
	  
	  @Override
	  protected String getUrl() {
		  return URL;
	  }
}
