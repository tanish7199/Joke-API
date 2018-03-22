package com.example.tanish.apisample;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Tanish on 22-03-2018.
 */

public class NetworkUtils {
    static String url = "http://api.icndb.com/jokes/random";

    public static String getJokes() {  //public because we have to call this in MainActivity
        String joke = "";
        try {
            URL jokesUrl = new URL(url);
            String json = null;
            try {
                json = setupHttpConnection(jokesUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            joke = parseJSON(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return joke;
    }

    private static String parseJSON(String json) {                 //to parse the converted JSON to return a joke
        String joke = "";

        try {
            JSONObject response = new JSONObject(json); //press Alt Enter to make it in try
            JSONObject value = response.getJSONObject("value");  // see api, we need to fetch value
            joke = value.getString("joke");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return joke;
    }

    public static String setupHttpConnection(URL url) throws IOException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String jsonResponse = "";
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);  //Timeout to make connection
            connection.setReadTimeout(15000);  //Timeout to specify time to fetch data (15 seconds)
            connection.connect();
            if (connection.getResponseCode() == 200) { //code 200 is Status OK , we will proceed only if this is 200 or else give error
                inputStream = connection.getInputStream();   //store the fetched bits into an inputStream
                jsonResponse = setupInputStream(inputStream); //calling other function
            } else {
                Log.d("Network Utils", "Error connecting to server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream!= null)
                inputStream.close();
            connection.disconnect();
        }
        return jsonResponse;
    }

    private static String setupInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();  //to make the converted characters into an String.
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));  //to change the bits fetched by API into Characters
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  //Made buffer to store the converted stream (default size is 10)
        //Example: to parse \n to make a new line, otherwise this will be printed as it is.
        String jsonString = "";
        try {
            String line = bufferedReader.readLine();  //it will work till \n is there, after that it will give the value to StringBuilder
            while (line != null) {    //
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            jsonString = stringBuilder.toString();  //Stringbuilder se string le kr apne variable mei rkh di

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
