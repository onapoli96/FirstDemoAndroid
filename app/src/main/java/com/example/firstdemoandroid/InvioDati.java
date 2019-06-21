package com.example.firstdemoandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.firstdemoandroid.giorgio.graph.stuffs.Graph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.Nodo;
import com.example.firstdemoandroid.giorgio.graph.stuffs.SparseGraph;

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

public class InvioDati extends AsyncTask<String,Void,String> {
    private Context context;
    private SparseGraph<Nodo,String> grafo;


    public InvioDati(Context context){
        this.context = context;
        this.grafo = new SparseGraph<Nodo,String>();
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

                int x1 = (int)(Integer.parseInt(object.get("X1").toString()) * 2.8);
                int y1 = (int)(Integer.parseInt(object.get("Y1").toString()) * 2.8);
                int x2 = (int)(Integer.parseInt(object.get("X2").toString()) * 2.8);
                int y2 = (int)(Integer.parseInt(object.get("Y2").toString()) * 2.8);

                Nodo n1 = new Nodo(x1, y1);
                Nodo n2 = new Nodo(x2, y2);

                /*grafo.addVertex(n1);
                grafo.addVertex(n2);*/

                grafo.addUndirectedEdge(n1,n2,"");

            }

            for(Nodo n : grafo.vertices()){
                System.out.println(n.toString());
            }
            System.out.println(grafo.vertices().size());

            System.out.println(grafo.getAllEdges().size());

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

    public Graph<Nodo,String> getGrafo(){
        return grafo;
    }
}
