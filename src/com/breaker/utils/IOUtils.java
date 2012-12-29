package com.breaker.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class IOUtils {

    public static boolean isValidURL(String urlString) {
        boolean validUrl = false;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.connect();
                int response = httpConnection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK)
                    validUrl = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return validUrl;
    }

}
