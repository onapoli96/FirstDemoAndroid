package com.example.firstdemoandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.firstdemoandroid.giorgio.Helper.Edge;
import com.example.firstdemoandroid.giorgio.Helper.Nodo;


import org.jgrapht.*;
import org.jgrapht.graph.*;


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
import java.util.ArrayList;

public class InvioDati extends AsyncTask<String,Void,String> {
    private Context context;
    private Graph<Nodo, DefaultEdge> grafo;
    private ArrayList<Nodo> nodi;
    private ArrayList<Edge<Nodo,String>> archi;



    public InvioDati(Context context){
        this.context = context;
        this.grafo = new DefaultDirectedGraph<>(DefaultEdge.class);
        nodi = new ArrayList<>();
        archi = new ArrayList<>();

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


                Edge<Nodo,String> e1, e2;
                e1 = new Edge<>(n1,n2);
               // e2 = new Edge<>(n2,n1);
                archi.add(e1);
                //archi.add(e2);
                if(!nodi.contains(n1)) {
                    nodi.add(n1);
                }

                if(!nodi.contains(n2)) {
                    nodi.add(n2);
                }




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

    public Graph getGrafo(){
        for(Nodo n1: nodi){
            grafo.addVertex(n1);
        }
        for(Edge<Nodo,String> e: archi){

            if(grafo.containsVertex(e.getIn()) && grafo.containsVertex(e.getOut())) {
                grafo.addEdge(e.getIn(), e.getOut());
                grafo.addEdge(e.getOut(), e.getIn());
                System.out.println("Nell'edge set" + grafo.edgeSet());
            }
        }

        System.out.println("Questi sono i vertici  "+grafo.vertexSet()+"  " +grafo.vertexSet().size());
        System.out.println("Questi sono gli archi  "+grafo.edgeSet()+"  " +grafo.edgeSet().size());

        return grafo;
    }

    public ArrayList<Nodo> getAllNodes(){
        return nodi;
    }
}
