package com.ghatikesh.twitter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ghatikesh.twitter.fragments.HomeTimelineFragment;
import com.ghatikesh.twitter.fragments.MentionsFragment;
import com.ghatikesh.twitter.fragments.TweetListFragment;
import com.ghatikesh.twitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends FragmentActivity {

	private static final int REQUEST_CODE = 1;
	protected ArrayList<Tweet> tweets;
	PullToRefreshListView lvTweets;

	TweetListFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		 
		 setupTabs();
		 
		 fragment = (TweetListFragment) getSupportFragmentManager()
	              .findFragmentById(R.id.flContainer);
		 System.out.println(fragment);
	}


	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tabHome = actionBar
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show();
			
		//TODO	loadDataFromApi(COUNT, 0, true);
		}
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

}
