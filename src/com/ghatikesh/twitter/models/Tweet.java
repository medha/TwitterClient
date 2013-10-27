package com.ghatikesh.twitter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends Model {

	@Column(name = "user")
	private User user;
	@Column(name = "body")
	private String body;
	@Column(name = "timestamp")
	private String timestamp;
	
	private static final long WEEK_IN_MILLIS = 604800000;
	private static final long SECOND_IN_MILLIS = 1000;

	public Tweet(Context c, JSONObject object) {
		super();

		try {
			this.user = User.fromJson(object.getJSONObject("user"));
			
			String dateString = object.getString("created_at");
			Date date = new Date();
			try {
				date = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH).parse(dateString);
			} catch (ParseException e) {
				Log.d("ERROR", e.toString());
			}
			
			CharSequence charSeq = DateUtils.getRelativeDateTimeString(c, date.getTime(), SECOND_IN_MILLIS,  WEEK_IN_MILLIS,  0);
			String str = charSeq.toString();
			this.timestamp = str.substring(0, str.indexOf(","));
			
			this.body = object.getString("text");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Tweet(String text, String timestamp, User user) {
		this.body = text;
		this.timestamp = timestamp;
		this.user = user;
	}

	public static ArrayList<Tweet> fromJson(Context c, JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = new Tweet(c, tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
	
	public String getBody() {
		return this.body;
	}

	public User getUser() {
		return user;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

}