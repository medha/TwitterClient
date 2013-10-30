package com.ghatikesh.twitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * This is the object responsible for communicating with a REST API. 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "r3X7Xl42uqDe2qbHSkQEvw";
	public static final String REST_CONSUMER_SECRET = "QwC31gNAmnPIgkyhWCUk4R3UrOPq4rdgMJS1nstaIs";
	public static final String REST_CALLBACK_URL = "oauth://mytwitterapp";
	private int mentionsCount;
	private int getHomeTimeline;
	private int getUserTimeline;
	private int getUserProfile;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams("status", body);
		getClient().post(apiUrl, params, handler);
	}

	public void getHomeTimeline(int count, int page,
			JsonHttpResponseHandler handler) {
		System.out.println("Num call to getHomeTimeline: " + getHomeTimeline);
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", String.valueOf(count));
		params.put("page", String.valueOf(page));
		getClient().get(apiUrl, params, handler);
		getHomeTimeline++;
	}
	
	public void getMentions(int count, int page, JsonHttpResponseHandler handler) {
		System.out.println("Num call to getMentions: " + mentionsCount);
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", String.valueOf(count));
		params.put("page", String.valueOf(page));
		getClient().get(apiUrl, params, handler);
		mentionsCount++;
	}
	
	public void getMyInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);
	}
	
	public void getUserTimeline(int count, int page, AsyncHttpResponseHandler handler) {
		System.out.println("Num call to getUserTimeline: " + getUserTimeline);
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", String.valueOf(count));
		params.put("page", String.valueOf(page));
		getClient().get(apiUrl, params, handler);
		getUserTimeline++;
	}

	public void getUserProfile(String screen_name,
			JsonHttpResponseHandler handler) {
		System.out.println("Num call to getUserProfile: " + getUserProfile);
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screen_name);
		getClient().get(apiUrl, params, handler);
		getUserProfile++;
	}

	public void getUserTimeline(String screen_name, int count, int page,
			JsonHttpResponseHandler handler) {
		System.out.println("Num call to getUserTimeline: " + getUserTimeline);
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screen_name);
		params.put("count", String.valueOf(count));
		params.put("page", String.valueOf(page));
		getClient().get(apiUrl, params, handler);
		getUserTimeline++;
	}

}