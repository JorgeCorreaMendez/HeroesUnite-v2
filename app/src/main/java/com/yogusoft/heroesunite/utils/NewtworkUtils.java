package com.yogusoft.heroesunite.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NewtworkUtils {
    final static String SUPERHERO_BASE_URL = "https://akabab.github.io/superhero-api/api";
    final static String ALL_SUPERHERO_URL = "/all.json";

    static int id;
    final static String SEARCH_SUPERHERO_URS = "/id/" + id + ".json";

}
