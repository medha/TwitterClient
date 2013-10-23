package com.ghatikesh.twitter.models;

import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model {

	@Column(name = "name")
	private String name;
	@Column(name = "screen_name")
	private String screen_name;
	@Column(name = "profile_image_url")
	private String profile_image_url;

	public User(JSONObject jsonObject) {
		super();
	
		try {
			this.name = jsonObject.getString("name");
			this.screen_name = jsonObject.getString("screen_name");
			this.profile_image_url = jsonObject.getString("profile_image_url");
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

	public static User fromJson(JSONObject jsonObject) {
		User u = new User(jsonObject);

		return u;
	}

}