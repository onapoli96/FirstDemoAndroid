package com.example.firstdemoandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstdemoandroid.giorgio.Helper.BeaconHelper;
import com.example.firstdemoandroid.giorgio.Helper.MqttHelper;
import com.example.firstdemoandroid.giorgio.customs.CustomView;
import com.example.firstdemoandroid.giorgio.customs.CustomViewEdge;
import com.example.firstdemoandroid.giorgio.graph.algorithms.search.MinPathDijkstra;
import com.example.firstdemoandroid.giorgio.graph.stuffs.Edge;
import com.example.firstdemoandroid.giorgio.graph.stuffs.Graph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.Nodo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private BeaconHelper beaconHelper;
    private Graph<Nodo,String> grafo;
    private Button startReadingBeaconsButton;
    private Button stopReadingBeaconsButton;
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap operations;
    private ImageView imageView;
    private BitmapDrawable ambp;
    private TextView hiddenTextView;
    private InvioDati invio;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hiddenTextView = (TextView) findViewById(R.id.hiddenTextView);
        hiddenTextView.addTextChangedListener(this);
        beaconHelper = new BeaconHelper(this, hiddenTextView, 1);
        startReadingBeaconsButton = (Button) findViewById(R.id.startReadingBeaconsButton);
        stopReadingBeaconsButton = (Button) findViewById(R.id.stopReadingBeaconsButton);

        imageView = (ImageView) findViewById(R.id.mappa);
        ambp = (BitmapDrawable) imageView.getDrawable();
        bitmap = Bitmap.createBitmap(1000,1000, Bitmap.Config.RGB_565);
        bitmap = ambp.getBitmap();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            System.out.println("API grande");
            invio = (InvioDati) new InvioDati(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.174/DemoWebBeacon/caricaGrafo.php?piano=55");
        }
        else {
            invio = (InvioDati) new InvioDati(this).execute("http://192.168.1.174/DemoWebBeacon/caricaGrafo.php?piano=55");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askForLocationPermissions();
            askForBluetooth();
        }
        startMqtt();

    }

    private boolean askForBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            showToastMessage(getString(R.string.not_support_bluetooth_msg));
            return false;
        }
        else if (!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        }
        //Controllo la risposta
        if(mBluetoothAdapter.isEnabled()){
            return true;
        }
        return false;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askForLocationPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_COARSE_LOCATION);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.funcionality_limited);
                    builder.setMessage(getString(R.string.location_not_granted));
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });
                    builder.show();
                    askForLocationPermissions();
                }
                return;
            }
        }
    }


    private void showToastMessage (String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconHelper.clear();
    }

    public void drawGraph(Graph<Nodo,String> graph){
        operations = Bitmap.createBitmap(imageView.getWidth()*2,imageView.getHeight()*2, Bitmap.Config.RGB_565);

        canvas = new Canvas(operations);
        canvas.drawBitmap(bitmap,0,0,null);
        ArrayList<Nodo> nodi = graph.vertices();
        for (Nodo nod: nodi) {
            CustomView cv = new CustomView(this, nod);
            cv.draw(canvas);
            ArrayList<Edge<Nodo,String>> archi = graph.getAllEdges();
            for (Edge e : archi){

                CustomViewEdge cve = new CustomViewEdge(this, (Nodo) e.getIn(), (Nodo) e.getOut());
                cve.draw(canvas);
            }
        }
        imageView.setImageDrawable( new BitmapDrawable(getResources(),operations));

    }

    public void drawMinPath(ArrayList<Nodo> toDraw){
        operations = Bitmap.createBitmap(imageView.getWidth()*2,imageView.getHeight()*2, Bitmap.Config.RGB_565);

        canvas = new Canvas(operations);
        canvas.drawBitmap(bitmap,0,0,null);

        Nodo start = toDraw.get(0);

        for(Nodo nod: toDraw) {
            CustomView cv = new CustomView(this, nod);
            cv.changeColor();
            cv.draw(canvas);
            System.out.println(start.toString()+"Secondo nodo: " +nod.toString());
            CustomViewEdge cve = new CustomViewEdge(this, start, nod );
            cve.draw(canvas);
            start = nod;
        }

        imageView.setImageDrawable( new BitmapDrawable(getResources(),operations));
    }

    public void newPath(String s) {
        grafo = invio.getGrafo();
        if(!grafo.vertices().isEmpty()) {
            s = s.substring(s.length()-3);
            System.out.println(s);
            ArrayList<Nodo> nodi = grafo.vertices();
            MinPathDijkstra<Nodo, String> dijkstra = new MinPathDijkstra<Nodo, String>();

            Nodo sorgente = new Nodo(0,0);

            if(s.equals("149")){
                sorgente = nodi.get(2);
            }
            else if(s.equals("649")){
                sorgente = nodi.get(3);
            }
            else if(s.equals("947")){
                sorgente = nodi.get(4);
            }

            ArrayList<Nodo> result = dijkstra.minPath(grafo,sorgente, nodi.get(10));

            System.out.println(sorgente+"  " +nodi.get(6));
            if (result != null) {
                //Toast.makeText(this, "Cammino trovato!!", Toast.LENGTH_SHORT).show();
                drawMinPath(result);

            } else {
                //Toast.makeText(this, "Cammino non trovato!!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Carica prima il grafo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void caricaGrafo(View v){
        grafo = invio.getGrafo();
        if(!grafo.vertices().isEmpty()) {

            drawGraph(grafo);
        }
        else{
            Toast.makeText(this, "Nessun grafo è stato caricato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        inviaMessaggio("L'utente TEST USER è passato dal beacon con id: "+ hiddenTextView.getText()+" al tempo "+ dateFormat.format(date) );
        newPath(hiddenTextView.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void detectBeaconInMap(View v){
        askForBluetooth();
        beaconHelper.startDetectingBeacons();
        changeStartButtonColors(stopReadingBeaconsButton, v);
    }

    public void stopDetectBeaconInMap(View v){
        beaconHelper.stopDetectingBeacons();
        changeStartButtonColors(startReadingBeaconsButton, v);
    }

    private void changeStartButtonColors(View able, View disable){

        able.setEnabled(true);
        able.setAlpha(1);

        disable.setEnabled(false);
        disable.setAlpha(.5f);
    }

    private void inviaMessaggio(String messaggio){
        MqttMessage message = new MqttMessage(messaggio.getBytes());
        try {
            mqttHelper.publica(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
