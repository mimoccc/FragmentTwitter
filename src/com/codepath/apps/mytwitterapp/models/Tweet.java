package com.codepath.apps.mytwitterapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
  
  private JSONObject jsonObject;
  private User user;
  private Long id;
  private String text;
  private String creationTime;
  
  private Tweet() {
    // TODO Auto-generated constructor stub
    this.jsonObject = null;
    this.user = null;
    this.id = null;
    this.text = "";
    this.creationTime = "";
  }

  public User getUser() {
      return this.user;
  }

  public String getBody() {
      return this.text;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getCreationTime() {
    return this.creationTime;
  }
  
  public JSONObject getJsonObject() {
    return this.jsonObject;
  }


  public static Tweet fromJson(JSONObject jsonObject) {
      Tweet tweet = new Tweet();
      try {
          tweet.jsonObject = jsonObject;
          tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
          tweet.id = jsonObject.getLong("id");
          tweet.text = jsonObject.getString("text");
          tweet.creationTime = jsonObject.getString("created_at");
      } catch (JSONException e) {
          e.printStackTrace();
          return null;
      }
      return tweet;
  }

  public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
      ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

      for (int i=0; i < jsonArray.length(); i++) {
          JSONObject tweetJson = null;
          try {
              tweetJson = jsonArray.getJSONObject(i);
          } catch (Exception e) {
              e.printStackTrace();
              continue;
          }

          Tweet tweet = Tweet.fromJson(tweetJson);
          if (tweet != null) {
              tweets.add(tweet);
          }
      }

      return tweets;
  }
}
