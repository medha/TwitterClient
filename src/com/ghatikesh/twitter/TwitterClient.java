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
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", String.valueOf(count));
		params.put("page", String.valueOf(page));
		client.get(apiUrl, params, handler);

	}

}