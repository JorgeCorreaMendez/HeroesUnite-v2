package com.yogusoft.heroesunite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yogusoft.heroesunite.utils.NewtworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView urlDisplay;
    TextView heroesResult;
    TextView errorMessageDisplay;

    ProgressBar requestProgress;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            requestProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String gitHubSearchResults = null;

            try {
                gitHubSearchResults = NewtworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            requestProgress.setVisibility(View.INVISIBLE);

            if (s != null) {
                showJsonData();
                heroesResult.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showJsonData() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        heroesResult.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        errorMessageDisplay.setVisibility(View.VISIBLE);
        heroesResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int launcherId = item.getItemId();

        if (launcherId == R.id.launcher) {
            Log.i("MainActivity", "El usuario a pulsado launcher");

            URL superheroe_Url = NewtworkUtils.bluidUrlAllSuperHeroes();
            urlDisplay.setText(superheroe_Url.toString());

            new GithubQueryTask().execute(superheroe_Url);
        }

        if (launcherId == R.id.clear) {
            heroesResult.setText("");
            errorMessageDisplay.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlDisplay = (TextView) findViewById(R.id.urlDisplay);
        heroesResult = (TextView) findViewById(R.id.superheroe_searchResult);
        errorMessageDisplay = (TextView) findViewById(R.id.error_message_display);

        requestProgress = (ProgressBar) findViewById(R.id.request_progress);
    }

}