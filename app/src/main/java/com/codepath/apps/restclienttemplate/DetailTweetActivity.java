package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailTweetActivity extends AppCompatActivity {

    // the movie to display
    Tweet tweet;

    // the view objects
    TextView tvName;
    TextView tvUsername;
    TextView tvBody;
    ImageView ivProfile;

    // TODO Favorites retweet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail);

        // resolve the view objects
        tvName = (TextView) findViewById(R.id.tvRealName);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvBody = (TextView) findViewById(R.id.tvDetailBody);
        ivProfile = (ImageView) findViewById(R.id.ivDetailProfile);

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        // set the title and overview
        tvName.setText(tweet.user.name);
        tvUsername.setText(tweet.user.screenName);
        tvBody.setText(tweet.body);

        // load image using glide
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transform(new RoundedCorners(15)))
                .into(ivProfile);


    }

}

