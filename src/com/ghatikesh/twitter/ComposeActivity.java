package com.ghatikesh.twitter;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {

	EditText etTweetText;
	Button bCancel;
	Button bTweet;
	TextView tvCharCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);

		setupViews();
		tvCharCount.setText("0");

		etTweetText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 140) {
					tvCharCount.setTextColor(Color.RED);
					bTweet.setEnabled(false);
				} else {
					tvCharCount.setTextColor(Color.BLACK);
					bTweet.setEnabled(true);
				}
				tvCharCount.setText(String.valueOf(s.length()));
			}

		});
	}

	public void onSubmitTweet(View v) {
		String tweetText = etTweetText.getText().toString();
		TwitterClientApp.getRestClient().postTweet(tweetText,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						setResult(RESULT_OK);
						finish();
					}

					@Override
					public void onFailure(Throwable e, JSONObject error) {
						// Handle the failure and alert the user to retry
						Log.e("ERROR", e.toString());
					}
				});
	}

	public void onCancelTweet(View v) {
		finish();
	}

	private void setupViews() {
		etTweetText = (EditText) findViewById(R.id.etTweetText);
		bCancel = (Button) findViewById(R.id.bCancel);
		bTweet = (Button) findViewById(R.id.bTweet);
		tvCharCount = (TextView) findViewById(R.id.tvCharCount);
	}
}
