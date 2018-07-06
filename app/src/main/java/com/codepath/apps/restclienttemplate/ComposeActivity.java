package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
// import com.codepath.apps.restclienttemplate.models.TwitterClient;

public class ComposeActivity extends AppCompatActivity {

    Button btnShare;
    String message;
    EditText etMessage;
    TwitterClient client;

    public void makeToast(String str) {
        Toast.makeText(this,str,Toast.LENGTH_LONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = new TwitterClient(this);

        btnShare = (Button) findViewById(R.id.bnTweet);
        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etMessage = (EditText) findViewById(R.id.etCompose);
                message = etMessage.getText().toString();
                Log.e("sendTweet",String.valueOf(message.length()));

                if (message.length() > 140) {
                    makeToast("Char Limit: 140");
                } else {
                    client.sendTweet(message, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Tweet newTweet = Tweet.fromJSON(response);
                                //Log.d("SendTweet", tweet.body);

                                // Prepare data intent
                                Intent data = new Intent();
                                // Pass relevant data back as a result
                                data.putExtra("tweet", Parcels.wrap(newTweet));
                                //data.putExtra("code", 200); // ints work too
                                // Activity finished ok, return the data
                                setResult(RESULT_OK, data); // set result code and bundle data for response
                                finish(); // closes the activity, pass data to parent
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("SendTweet", errorResponse.toString());
                            //                        pb.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            Log.d("SendTweet", errorResponse.toString());
                            //                        pb.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("SendTweet", responseString);
                            //                        pb.setVisibility(ProgressBar.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

}
