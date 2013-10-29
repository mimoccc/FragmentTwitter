package com.codepath.apps.mytwitterapp.fragments;

import android.os.Bundle;

import com.loopj.android.http.RequestParams;

public class UserTimelineFragment extends TweetsListFragment {
	
  private final static String MAX_ITEMS = "51";
  private final static String URL = "statuses/user_timeline.json";
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  RequestParams params = new RequestParams();
	  params.put("count", MAX_ITEMS);
	  String userId = getActivity().getIntent().getStringExtra("screen_name");
	  if (userId != null) {
		  params.put("screen_name", userId);
	  }
	  get(URL, params);
  }

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return URL;
	}

}
