package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.ProfileActivity;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class TweetsListFragment extends Fragment {
  private final static String MAX_ITEMS = "51";
  TweetsAdapter adapter;
  ListView lvTweets;
  ImageView ivProfileImage;
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
  }
  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    adapter = new TweetsAdapter(getActivity(), tweets);
    lvTweets = (ListView)getActivity().findViewById(R.id.lvTweets);
    lvTweets.setAdapter(adapter);
    lvTweets.setClickable(false);
    registerListeners();
  }
  
  public TweetsAdapter getAdapter() {
	  return this.adapter;
  }
  
  protected abstract String getUrl();
  
  
  protected void registerListeners() {
	    
	    lvTweets.setOnScrollListener(new EndlessScrollListener() {
	      @Override
	      public void onLoadTail(int page, int totalItemsCount) {
	        // TODO Auto-generated method stub
	        TweetsAdapter adapter = getAdapter();
	        RequestParams params = new RequestParams();
	        params.put("max_id", String.valueOf(adapter.getMaxId()));
	        params.put("count", MAX_ITEMS);
	        get(getUrl(), params);
	      }
	      
	      @Override
	      public void onLoadHead(int totalItemsCount) {
	        // TODO Auto-generated method stub
	        TweetsAdapter adapter = getAdapter();
	        RequestParams params = new RequestParams();
	        params.put("since_id", String.valueOf(adapter.getSinceId()));
	        params.put("count", MAX_ITEMS);
	        get(getUrl(), params);
	      }
	    });
	    
	    
	    lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TweetsAdapter adapter = getAdapter();
				Tweet tweet = adapter.getItem(position);
				Intent intent = new Intent(getActivity(), ProfileActivity.class);
				intent.putExtra("screen_name", tweet.getUser().getScreenName());
				startActivity(intent);
				
			}
	    	
	    });
	    
	    
	  }
  
  protected void get(String url, RequestParams params) {
	    MyTwitterApp.getRestClient().get(url, params, new JsonHttpResponseHandler() {
	      @Override
	      public void onSuccess(JSONArray jsonTweets) {
	    	ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
	    	getAdapter().push(tweets);
	    	getActivity().runOnUiThread(new Runnable() {
	          @Override
	          public void run() {
	            adapter.notifyDataSetChanged();            
	          }
	        }); 
	      }

	      public void onFailure(Throwable e, JSONObject errorResponse) {
	        Log.d("ERROR", errorResponse.toString());
	      }
	    });
}
  
}
