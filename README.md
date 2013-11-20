# Tweeter
### The simple yet stunning Twitter client

![Screenshots](/res/drawable/twitter.jpg "Screenshots")

## Overview

Tweeter is a Twitter client. The following things are supported:

 * Sign in using Twitter OAuth login flow
 * View tweets from user's home timeline
 * View tweets from Mentions timeline
 * See user profile, body and timestamp for each tweet
 * New tweets load when you scroll to the bottom of the list of tweets
 * Compose new tweet
 * See a counter with total number of characters left for tweet 
 * Refresh timeline by pulling down (i.e pull-to-refresh)
 * View user profile information, number of tweets, followers, following.
 * View user tweets timeline

The following libraries are used to make this possible:

 * [scribe-java](https://github.com/fernandezpablo85/scribe-java) - Simple OAuth library for handling the authentication flow.
 * [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
 * [codepath-oauth](https://github.com/thecodepath/android-oauth-handler) - Custom-built library for managing OAuth authentication and signing of requests
 * [UniversalImageLoader](https://github.com/nostra13/Android-Universal-Image-Loader) - Used for async image loading and caching them in memory and on disk.
 * [ActiveAndroid](https://github.com/pardom/ActiveAndroid) - Simple ORM for persisting a local SQLite database on the Android device
