package com.ghatikesh.twitter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghatikesh.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		loadProfileInfo();
	}

	private void loadProfileInfo() {
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
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
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvNumOfTweets = (TextView) findViewById(R.id.tvNumOfTweets);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvNumOfTweets.setText(u.getStatusesCount() + " Tweets");
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(),ivProfileImage);
	}

}
