package com.ghatikesh.twitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ghatikesh.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private TweetsAdapter adapter;
	private PullToRefreshListView lvTweets;
	private int page = 0;
	private static final int COUNT = 25;
	protected ArrayList<Tweet> tweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
	//	lvTweets = (ListView) findViewById(R.id.lvTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadDataFromApi(COUNT, page, false);
			}
		});
		
		
        // Set a listener to be invoked when the list should be refreshed.
        lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	loadDataFromApi(COUNT, 0, true);
            }
        });

		TwitterClientApp.getRestClient().getHomeTimeline(COUNT, page,new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(getBaseContext(), jsonTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}

			@Override
			public void onFailure(Throwable e) {
				Log.d("ERROR", e.toString());
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
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			loadDataFromApi(COUNT, 0, true);
		}
	}

	private void loadDataFromApi(int count, int page, final boolean clear) {
		TwitterClientApp.getRestClient().getHomeTimeline(count, page,new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(getBaseContext(), jsonTweets);
						if(clear) {
							adapter.clear();
						}
						adapter.addAll(tweets);
						lvTweets.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable e) {
						Log.d("ERROR", e.toString());
					}
				});
	}

}
