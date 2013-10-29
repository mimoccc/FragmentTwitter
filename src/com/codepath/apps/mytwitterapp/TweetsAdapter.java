package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
  
  private final static int MAX_ITEMS = 50;
  
  private Long maxId = 0L;
  private Long sinceId = 0L;
  private List<Tweet> tweets;

  public TweetsAdapter(Context context, List<Tweet> tweets) {
    super(context, 0, tweets);
    this.tweets = tweets;
  }
  
  public Long getMaxId() {
    return this.maxId;
  }
  
  public Long getSinceId() {
    return this.sinceId;
  }
  
  public void push(ArrayList<Tweet> tweets) {
    if (tweets.isEmpty()) return;
    if (getCount() > MAX_ITEMS) {
      clear();
      this.maxId = 0L;
      this.sinceId = 0L;
    }
    
    if (this.sinceId == 0 && this.maxId == 0) {
      Log.i("PUSHBACK FIRST", String.valueOf(tweets.get(0).getId()));
      pushBack(tweets);
      this.maxId = getItem(getCount() - 1).getId() - 1;
      this.sinceId = getItem(0).getId();
      return;
    }
    
    Log.i("SINCE_ID", String.valueOf(this.sinceId));
    Log.i("1ST-ID", String.valueOf(tweets.get(0).getId()));
    Log.i("LAST-ID", String.valueOf(tweets.get(tweets.size() - 1).getId()));
    Log.i("MAX_ID", String.valueOf(this.maxId));
    
    if (tweets.get(0).getId() < this.maxId) {
      Log.i("PUSHBACK", String.valueOf(tweets.get(0).getId()));
      pushBack(tweets);
      this.maxId = getItem(getCount() - 1).getId() - 1;
    }
    
    if (tweets.get(tweets.size() - 1).getId() > this.sinceId) {
      Log.i("PUSHFRONT", String.valueOf(tweets.get(0).getId()));
      Log.i("SINCEID", String.valueOf(this.sinceId));
      pushFront(tweets);
      this.sinceId = getItem(0).getId();
    }
  }
  
  private void pushBack(ArrayList<Tweet> tweets) {
    int oldSize = this.tweets.size();
    addAll(tweets);
    int newSize = this.tweets.size();
    //this.tweets = this.tweets.subList(newSize - oldSize, newSize);
  }
  
  private void pushFront(ArrayList<Tweet> tweets) {
    int oldSize = this.tweets.size();
    for (int i = tweets.size() - 1; i >= 0; i--) {
      insert(tweets.get(i), 0);
    }
    //this.tweets = this.tweets.subList(0, oldSize);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.tweet_item, null);
      
      holder = new ViewHolder();
      holder.view = (ImageView)view.findViewById(R.id.lvProfile);
      holder.tvTime = (TextView)view.findViewById(R.id.tvTime);
      holder.tvName = (TextView)view.findViewById(R.id.tvName);
      holder.tvBody = (TextView)view.findViewById(R.id.tvBody);
      view.setTag(holder);
    } else {
      holder = (ViewHolder)view.getTag();
    }
    
    Tweet tweet = getItem(position);
    
    Log.i(String.valueOf(position), String.valueOf(tweet.getId()));
    
    ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), holder.view);
    
    holder.tvTime.setText(tweet.getCreationTime());
    
    String formattedName = "<b>" + tweet.getUser().getName() + "</b>" +
        " <small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
    holder.tvName.setText(Html.fromHtml(formattedName));
    
    holder.tvBody.setText(Html.fromHtml(tweet.getBody()));
    
    return view;
  }
  
  static class ViewHolder {
    ImageView view;
    TextView tvTime;
    TextView tvName;
    TextView tvBody;
  }
}
