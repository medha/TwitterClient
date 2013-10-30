package com.ghatikesh.twitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ghatikesh.twitter.fragments.HomeTimelineFragment;
import com.ghatikesh.twitter.fragments.MentionsFragment;
import com.ghatikesh.twitter.fragments.TweetListFragment;
import com.ghatikesh.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends FragmentActivity {

	private static final int REQUEST_CODE = 1;
	protected ArrayList<Tweet> tweets;
	PullToRefreshListView lvTweets;
	private static final int PAGE = 0;
	private static final int COUNT = 25;
	TweetListFragment fragment;
	private ActionBar actionBar;
	private Tab tabHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		 
		 setupTabs();
		 
	}

	private void setupTabs() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		tabHome = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "HomeTimelineFragment",
						HomeTimelineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome);

		Tab tabMentions = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mention)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsFragment>(R.id.flContainer, this, "MentionsTimelineFragment",
			    		MentionsFragment.class));

		actionBar.addTab(tabMentions);
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
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			
			fragment = (TweetListFragment) getSupportFragmentManager()
		              .findFragmentById(R.id.flContainer);
			System.out.println("Calling Get HomeTImeline from onActivityResult: " + PAGE);
			loadDataFromApi(COUNT, PAGE, true);
		}
	}
	
	private void loadDataFromApi(int count, int page, final boolean clear) {
		TwitterClientApp.getRestClient().getHomeTimeline(count, page,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(fragment.getActivity(),
								jsonTweets);
						if( fragment.getTag().equals("HomeTimelineFragment")){
							if (clear) {
								if (fragment.getAdapter() != null) {
									fragment.getAdapter().clear();
								}
							}
							fragment.getAdapter().addAll(tweets);
							if (clear) {
								if (lvTweets != null) {
									lvTweets.onRefreshComplete();
								}
							}
						}
					}

					@Override
					public void onFailure(Throwable e) {
						Log.d("ERROR", e.toString());
						Toast.makeText(fragment.getActivity(),
								e + "Please try again later.",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(Throwable e, JSONObject arg1) {
						Log.d("ERROR", "error 1: " + e.toString());
						Toast.makeText(fragment.getActivity(),
								"Too many requests. Please try again later.",
								Toast.LENGTH_LONG).show();
						lvTweets.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable e, JSONArray arg1) {
						Log.d("ERROR", "error 2: " + e.toString());
						Toast.makeText(fragment.getActivity(),
								e + "Please try again later.",
								Toast.LENGTH_LONG).show();
					}

				});
	}
	


}
