package com.example.firstdemoandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;


import com.example.firstdemoandroid.giorgio.Helper.Nodo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CaricaHashmapBeacon extends AsyncTask<String,Void,String> {
    private Context context;
    private HashMap<String,Nodo> beaconNodo;
    private float density;

    public CaricaHashmapBeacon(Context context, float density){
        this.context = context;
        this.density = density;
        beaconNodo = new HashMap<>();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String ... url) {
        // azioni di invio
        URL paginaURL = null;
        InputStream risposta = null;
        try {
            paginaURL = new URL(url[0]);
            HttpURLConnection client = (HttpURLConnection) paginaURL.openConnection();
            risposta = new BufferedInputStream(client.getInputStream());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return mostroDati(risposta);


    }
    @Override
    protected void onPostExecute(String result) {

        try {

            JSONArray array = new JSONArray(result);
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                int x = (int)((Integer.parseInt(object.get("X").toString()) - 8 )* density);
                int y = (int)((Integer.parseInt(object.get("Y").toString())-64)* density);

                String idBeacon = object.get("IDBeacon").toString();
                System.out.println("sono qui"+ x+" "+ y + " "+ idBeacon);
                Nodo n = new Nodo(x, y);

                beaconNodo.put(idBeacon, n);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String mostroDati(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public HashMap<String, Nodo> getHashMap(){
        return beaconNodo;
    }
}
