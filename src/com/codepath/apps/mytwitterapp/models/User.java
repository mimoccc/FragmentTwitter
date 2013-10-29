package com.codepath.apps.mytwitterapp.models;

import org.json.JSONObject;

public class User {
  
  private Long id;
  private String name;
  private String screenName;
  private String profileImageUrl;
  private int tweetsCount;
  private int followersCount;
  private int friendsCount;
  private String description;
  
  private User() {
    // TODO Auto-generated constructor stub
    this.id = 0L;
    this.name = "";
    this.screenName = "";
    this.profileImageUrl = "";
    this.tweetsCount = 0;
    this.followersCount = 0;
    this.friendsCount = 0;
    this.description = "";
  }

  public Long getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }

  public String getScreenName() {
    return this.screenName;
  }
  
  public String getProfileImageUrl() {
    return this.profileImageUrl;
  }
  
  public int getTweetsCount() {
    return this.tweetsCount;
  }
  
  public String getUserTagline() {
	  return this.description;
  }
  
  public int getFollowersCount() {
    return this.followersCount;
  }
  
  public int getFriendsCount() {
    return this.friendsCount;
  }

  public static User fromJson(JSONObject json) {
      User user = new User();

      try {
          user.id = json.getLong("id");
          user.name = json.getString("name");
          user.screenName = json.getString("screen_name");
          user.profileImageUrl = json.getString("profile_image_url");
          user.tweetsCount = json.getInt("statuses_count");
          user.followersCount = json.getInt("followers_count");
          user.friendsCount = json.getInt("friends_count");
          user.description = json.getString("description");
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }

      return user;
  }
  
}


