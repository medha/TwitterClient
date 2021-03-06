package com.ghatikesh.twitter;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghatikesh.twitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	
	Context context;

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		
		ImageView ivProfile = (ImageView)view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfile);
		
		ivProfile.setTag(tweet.getUser().getScreenName());
		
		
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + "<small><font color='#777777'> @" + tweet.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));
		
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		tvBody.setText(Html.fromHtml(tweet.getBody()));
		
		TextView tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
		String formattedTimestamp = "<small><font color='#777777'>" + tweet.getTimestamp() + "</font></small>";
		tvTimestamp.setText(Html.fromHtml(formattedTimestamp));
		
		
		ivProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadUserProfile(v);
			}
		});

		return view;
		
	}

	protected void loadUserProfile(View v) {
		
		Intent i = new Intent(context, UserProfileActivity.class);
		i.putExtra("screen_name", v.getTag().toString());
		context.startActivity(i);
	}
}
