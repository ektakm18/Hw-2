package com.example.ekta.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.ekta.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "mainactivity";
    private ProgressBar mProgress;
    private EditText mSearch;

    private RecyclerView recyclerView;
    private List<NewsItem> items;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressId);
        mSearch = (EditText) findViewById(R.id.searchQuery);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            String s = mSearch.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();
        }
        return true;
    }
    private void jsonDataView() {
        recyclerView.setVisibility(View.VISIBLE);
    }
    class NetworkTask extends AsyncTask<URL, Void, String> {
        String query;
        NetworkTask(String s) {
            query = s;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            URL url = NetworkUtils.makeURL(query, "stars");
            Log.d(TAG, "url: " + url.toString());
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String apiResults) {
            super.onPostExecute(apiResults);
            mProgress.setVisibility(View.GONE);
            if (apiResults != null && !apiResults.equals("")) {
                jsonDataView();
                try {
                    JSONObject obj = new JSONObject(apiResults);
                    JSONArray list =  obj.getJSONArray("articles");

                    items = new ArrayList<>(list.length());
                    for(int i=0; i< list.length(); i++){
                        JSONObject o = list.getJSONObject(i);
                        NewsItem item = new NewsItem(o.getString("title"),
                                o.getString("description"),
                                o.getString("urlToImage"),
                                o.getString("url"),
                                o.getString("publishedAt"));
                        items.add(item);

                        Log.d("onPostExecute :: Title", o.getString("title"));
                    }
                    Log.d("onPostExecute", String.valueOf(items.size()));

                    adapter = new ViewAdapter(items, getApplicationContext(), new ViewAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int clickedItemIndex) {
                            String url = items.get(clickedItemIndex).getUrl();
                            openWebPage(url);
                        }
                    });
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}