package com.ghatikesh.twitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ghatikesh.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private TweetsAdapter adapter;
	private ListView lvTweets;
	private int page = 0;
	private int count = 25;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(page);
			}
		});

		TwitterClientApp.getRestClient().getHomeTimeline(count, page, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(getBaseContext(), jsonTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("ERROR", arg0.toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void onComposeAction(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("adapter", adapter);
		startActivity(i);
	}

	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int page) {
		
		TwitterClientApp.getRestClient().getHomeTimeline(count, page, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(getBaseContext(), jsonTweets);
				adapter.addAll(tweets);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("ERROR", arg0.toString());
			}
		});
	}


}
