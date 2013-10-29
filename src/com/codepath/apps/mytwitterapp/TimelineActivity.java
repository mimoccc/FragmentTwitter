
package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TimelineActivity extends FragmentActivity {
  
  private final static String MAX_ITEMS = "51";
  ListView lvTweets;
  TweetsAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);
    initWidgets();
    registerListeners();
    RequestParams params = new RequestParams();
    params.put("count", MAX_ITEMS);
    getTweets(params);
  }

  private void getTweets(RequestParams params) {
    MyTwitterApp.getRestClient().getHomeTimeLine(params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(JSONArray jsonTweets) {
        Log.i("JSONARRAY", jsonTweets.toString());
        ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
        adapter.push(tweets);
        runOnUiThread(new Runnable() {
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

  private void initWidgets() {
    lvTweets = (ListView)findViewById(R.id.lvTweets);
    adapter = new TweetsAdapter(getBaseContext(), new ArrayList<Tweet>());
    lvTweets.setAdapter(adapter);
  }
  
  private void registerListeners() {
    lvTweets.setOnScrollListener(new EndlessScrollListener() {
      @Override
      public void onLoadTail(int page, int totalItemsCount) {
        // TODO Auto-generated method stub
        TweetsAdapter adapter = (TweetsAdapter)lvTweets.getAdapter();
        RequestParams params = new RequestParams();
        params.put("max_id", String.valueOf(adapter.getMaxId()));
        getTweets(params);
      }

      @Override
      public void onLoadHead(int totalItemsCount) {
        // TODO Auto-generated method stub
        TweetsAdapter adapter = (TweetsAdapter)lvTweets.getAdapter();
        RequestParams params = new RequestParams();
        params.put("since_id", String.valueOf(adapter.getSinceId()));
        getTweets(params);
      }
    });
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
  

}
