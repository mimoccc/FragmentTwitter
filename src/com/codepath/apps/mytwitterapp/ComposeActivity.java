
package com.codepath.apps.mytwitterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ComposeActivity extends Activity {

  private static final int MAX_CHARS = 140;
  EditText etTweet;
  TextView tvCount;
  Button submitButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);

    registerListeners();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.compose, menu);
    return true;
  }
  
  private void registerListeners() {
    tvCount = (TextView)findViewById(R.id.textView1);
    tvCount.setText(String.valueOf(MAX_CHARS));
    etTweet = (EditText)findViewById(R.id.editText1);
    submitButton = (Button)findViewById(R.id.button1);
    etTweet = (EditText)findViewById(R.id.editText1);
    Button submitButton = (Button)findViewById(R.id.button1);
    submitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO Auto-generated method stub
        //Toast.makeText(getBaseContext(), etTweet.getText().toString(), Toast.LENGTH_LONG).show();
        RequestParams params = new RequestParams("status", etTweet.getText().toString());
        MyTwitterApp.getRestClient().postTweet(params, new AsyncHttpResponseHandler() {
          @Override
          public void onSuccess(String response) {
            Log.i(getApplicationContext().toString(), response);
            Intent intent = new Intent(getBaseContext(), TimelineActivity.class);
            startActivity(intent);
          }
        });
      }
      
    });
    
    etTweet.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //int charLeft = MAX_CHARS - (start + count);
        Log.i("START:BEFORE:COUNT", String.valueOf(start) + ":" + String.valueOf(before) + ":" + String.valueOf(count));
        tvCount.setText(String.valueOf(MAX_CHARS - (start + count)));
      }

      @Override
      public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        
      }
    });

  }

}
