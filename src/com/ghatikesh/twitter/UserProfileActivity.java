package com.ghatikesh.twitter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghatikesh.twitter.fragments.SelectedUserTimelineFragment;
import com.ghatikesh.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		
		 String screen_name = getIntent().getStringExtra("screen_name");
		 if(screen_name != null) {
			 loadUserProfile(screen_name);
		 } else {
			 System.out.println("No user to load");
		 }
		 
		 SelectedUserTimelineFragment fragment =  (SelectedUserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSelectedUserTimeline);
		
		fragment.setArgs(screen_name);
		
		
		
	}

	private void loadUserProfile(String screen_name) {
		TwitterClientApp.getRestClient().getUserProfile(screen_name, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				User u = User.fromJson(jsonObject);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
			
			@Override
			public void onFailure(Throwable e, JSONArray arg1) {
				Log.d("ERROR", "error 1: " + e.toString());
				Toast.makeText(getBaseContext(), e + " Please try again later.",
						Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject arg1) {
				Log.d("ERROR", "error 1: " + e.toString());
				Toast.makeText(getBaseContext(),
						e + "Please try again later.",
						Toast.LENGTH_LONG).show();
			}

		});
	}
	
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvUserName);
		TextView tvTagline = (TextView) findViewById(R.id.tvUserTagline);
		TextView tvNumOfTweets = (TextView) findViewById(R.id.tvUserNumOfTweets);
		TextView tvFollowers = (TextView) findViewById(R.id.tvUserFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvUserFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivUserProfileImage);

		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvNumOfTweets.setText(u.getStatusesCount() + " Tweets");
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(),ivProfileImage);
	}
}
