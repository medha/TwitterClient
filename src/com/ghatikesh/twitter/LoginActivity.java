package com.ghatikesh.twitter;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This is tied to button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

	// OAuth authenticated successfully, launch Twitter timeline
    @Override
    public void onLoginSuccess() {
    	 Intent i = new Intent(this, TimelineActivity.class);
    	 startActivity(i);
    }
    
    // OAuth authentication flow failed, handle the error
    @Override
    public void onLoginFailure(Exception e) {
    	Toast.makeText(this, "Sorry, login failed. Please try again because: " + e.getMessage(), Toast.LENGTH_LONG).show();
        Log.d("ERROR", e.toString());
    }
    
}
