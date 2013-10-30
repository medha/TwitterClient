package com.ghatikesh.twitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghatikesh.twitter.R;
import com.ghatikesh.twitter.TweetsAdapter;
import com.ghatikesh.twitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public abstract class TweetListFragment extends Fragment {

	private TweetsAdapter adapter;
	private PullToRefreshListView lvTweets;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tweet_list, parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (PullToRefreshListView) getActivity().findViewById(
				R.id.lvTweets);
		
		lvTweets.setAdapter(adapter);
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
	
//	public abstract void loadMore();
	
	public PullToRefreshListView getListView() {
		return lvTweets;
	}

}
