package com.syed.mainstream.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.syed.mainstream.Adapters.ImagesAdapter;
import com.syed.mainstream.Models.Image;
import com.syed.mainstream.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // We will get json from this url.
    protected final String mUrl = "http://entest-webappslab.rhcloud.com/data/data.json";

    protected ImagesAdapter mAdapter;
    protected RecyclerView mRvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find RecyclerView and bind to adapter
        mRvImages = (RecyclerView) findViewById(R.id.rvImages);

        // allows for optimizations
        mRvImages.setHasFixedSize(true);

        // create the list
        List<Image> images = new ArrayList<Image>();

        // Create an adapter
        mAdapter = new ImagesAdapter(this, images);LinearLayoutManager layout = new LinearLayoutManager(this);
        mRvImages.setLayoutManager(layout);


        // Bind adapter to list
        mRvImages.setAdapter(mAdapter);

        getImages();
    }

    protected void getImages(){
//        if(!Utils.isNetworkAvailable()){
//            // Toasts are not good but for sake of alert, I am showing this toast.
//            Toast.makeText(EarthNetworkApplication.getContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // creating  network client, to initiate the network request.
        AsyncHttpClient client = new AsyncHttpClient();

        // trigger the network request
        client.get(mUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray array = response.getJSONArray("album");
                    List<Image> images = Image.fromJSONArray(array);
                    mAdapter.addItems(images);
                    mAdapter.notifyItemInserted(images.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("ERROR: Wrong response.", response != null ? response.toString() : "onSuccess. Not expecting this....");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR", errorResponse != null ? errorResponse.toString() : "OnFailure. Network call failed. Please check your internet.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR", errorResponse != null ? errorResponse.toString() : "OnFailure. Network call failed.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("ERROR", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
