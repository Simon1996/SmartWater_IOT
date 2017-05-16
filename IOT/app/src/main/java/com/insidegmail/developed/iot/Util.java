package com.insidegmail.developed.iot;

/**
 * Created by Admin on 16.05.2017.
 */


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * Created by Admin on 28.04.2017.
 */

public class Util {

    public static JSONObject getResponse(String urlString){
        StringBuilder parsedContentFromUrl = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; CrOS x86_64 14.4.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2738.0 Safari/537.36");
            uc.connect();
            BufferedInputStream in = new BufferedInputStream(uc.getInputStream());

            int ch;
            while ((ch = in.read()) != -1) {
                parsedContentFromUrl.append((char) ch);
            }

            JSONObject object = new JSONObject(parsedContentFromUrl.toString());
            return object;
        }catch (Exception e){
            return null;
        }
    }

    public static JSONObject getResponse(String urlString,JSONObject data){
        StringBuilder parsedContentFromUrl = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection uc = (HttpURLConnection)url.openConnection();
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Content-Type","application/json");
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; CrOS x86_64 14.4.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2738.0 Safari/537.36");
            uc.connect();

            OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream());
            out.write(data.toString());
            out.close();

            BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
            int ch;
            while ((ch = in.read()) != -1) {
                parsedContentFromUrl.append((char) ch);
            }

            JSONObject object = new JSONObject(parsedContentFromUrl.toString());
            return object;
        }catch (Exception e){
            return null;
        }
    }

    public static String getToken(JSONObject object){
        try {
            if (object.getString("status").equals("OK")) {
                return object.getString("token");
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public static int generateRandomInt(int x){
        int z = (int)(Math.random()*x);
        return z;
    }

}

