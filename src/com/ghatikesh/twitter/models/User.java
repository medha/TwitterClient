package com.ghatikesh.twitter.models;

import org.json.JSONObject;

import com.activeandroid.Model;

public class User extends Model {

	private String name;
	private String screen_name;
	private String profile_image_url;
	private int followers_count;
	private int friends_count;
	private int statuses_count;
	private String description;
	

	public User(JSONObject jsonObject) {
		super();
	
		try {
			this.name = jsonObject.getString("name");
			this.screen_name = jsonObject.getString("screen_name");
			this.profile_image_url = jsonObject.getString("profile_image_url");
			this.followers_count = jsonObject.getInt("followers_count");
			this.friends_count = jsonObject.getInt("friends_count");
			this.statuses_count = jsonObject.getInt("statuses_count");
			this.description = jsonObject.getString("description");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getName() {
		return this.name;
	}

	public String getScreenName() {
		return this.screen_name;
	}

	public String getProfileImageUrl() {
		return this.profile_image_url;
	}

	public int getFollowersCount() {
		return followers_count;
	}

	public int getFriendsCount() {
		return friends_count;
	}

	public int getStatusesCount() {
		return statuses_count;
	}

	public String getTagline() {
		return description;
	}

	public static User fromJson(JSONObject jsonObject) {
		User u = new User(jsonObject);

		return u;
	}

}