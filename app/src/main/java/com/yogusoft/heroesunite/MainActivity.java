package com.yogusoft.heroesunite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yogusoft.heroesunite.utils.NewtworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView urlDisplay;
    TextView heroesResult;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

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
            if (s != null && !s.isEmpty()) {
                heroesResult.setText(s);
            } else {
                heroesResult.setText("Error al obtener respuesta");
                Log.i("MainActivity", s);
            }
        }
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

        if (launcherId == R.id.clear) heroesResult.setText("");

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlDisplay = (TextView) findViewById(R.id.urlDisplay);
        heroesResult = (TextView) findViewById(R.id.superheroe_searchResult);
    }

}