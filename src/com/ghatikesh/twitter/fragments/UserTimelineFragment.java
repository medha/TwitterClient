package com.ghatikesh.twitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ghatikesh.twitter.EndlessScrollListener;
import com.ghatikesh.twitter.TwitterClientApp;
import com.ghatikesh.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class UserTimelineFragment extends TweetListFragment {

	PullToRefreshListView lvTweets;
	private static final int PAGE = 0;
	private static final int COUNT = 25;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadDataFromApi(COUNT, PAGE, true);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lvTweets = getListView();

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				loadDataFromApi(COUNT, page, false);
			}
		});

		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadDataFromApi(COUNT, PAGE, true);
			}
		});
	}

	private void loadDataFromApi(int count, int page, final boolean clear) {
		TwitterClientApp.getRestClient().getUserTimeline(count, page,
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(getActivity(),
								jsonTweets);
						if (clear) {
							getAdapter().clear();
						}
						getAdapter().addAll(tweets);
						if (clear) {
							if (lvTweets != null) {
								lvTweets.onRefreshComplete();
							}
						}
					}

					@Override
					public void onFailure(Throwable e) {
						Log.d("ERROR", e.toString());
						Toast.makeText(getActivity(),
								e + "Please try again later.",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(Throwable e, JSONObject arg1) {
						Log.d("ERROR", "error 1: " + e.toString());
						Toast.makeText(getActivity(),
								"Too many requests. Please try again later.",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFailure(Throwable e, JSONArray arg1) {
						Log.d("ERROR", "error 2: " + e.toString());
						Toast.makeText(getActivity(),
								e + "Please try again later.",
								Toast.LENGTH_LONG).show();
					}

				});

	}
}
