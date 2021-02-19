package com.example.baloncesto_database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import database.Puntuacion;
import database.PuntuacionDB;

public class MainActivity extends AppCompatActivity {

    private int contLocal;
    private int contVisitante;

    private PuntuacionDB puntuacionDB;

    private TextView tvLocal, tvVisitante;
    private LinearLayout llPuntuaciones;
    private Button btLocal1, btLocal2, btLocal3, btVisitante1, btVisitante2, btVisitante3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Interfaz
        llPuntuaciones = (LinearLayout)findViewById(R.id.llPuntuaciones);

        // Local
        tvLocal = (TextView)findViewById(R.id.tvLocal);
        tvLocal.setBackgroundColor(getResources().getColor(R.color.yellow));

        btLocal1 = (Button)findViewById(R.id.btLocal1);
        btLocal1.setBackgroundColor(getResources().getColor(R.color.yellow));

        btLocal2 = (Button)findViewById(R.id.btLocal2);
        btLocal2.setBackgroundColor(getResources().getColor(R.color.yellow));

        btLocal3 = (Button)findViewById(R.id.btLocal3);
        btLocal3.setBackgroundColor(getResources().getColor(R.color.yellow));

        // Visitante
        tvVisitante = (TextView)findViewById(R.id.tvVisitante);
        tvVisitante.setBackgroundColor(getResources().getColor(R.color.green));

        btVisitante1 = (Button)findViewById(R.id.btVisitante1);
        btVisitante1.setBackgroundColor(getResources().getColor(R.color.green));

        btVisitante2 = (Button)findViewById(R.id.btVisitante2);
        btVisitante2.setBackgroundColor(getResources().getColor(R.color.green));

        btVisitante3 = (Button)findViewById(R.id.btVisitante3);
        btVisitante3.setBackgroundColor(getResources().getColor(R.color.green));

        // Database
        puntuacionDB = PuntuacionDB.getInstance(this);
        // Metodo getAllPuntuacions()
        cargarData();
    }

    private void cargarData() {
        llPuntuaciones.removeAllViews();
        LinkedList<Puntuacion> list = puntuacionDB.getPuntuaciones();
        for(Puntuacion puntuacion: list) {
            if(puntuacion.getEquipo() == Puntuacion.EQUIPO_VISITANTE){ // verde
                Button button = new Button(this);
                button.setText(puntuacion.getPuntos() + "");
                button.setBackgroundColor(getResources().getColor(R.color.green));
                deletePuntuacion(button, puntuacion);
                llPuntuaciones.addView(button);
                contVisitante += puntuacion.getPuntos();
            }
            else if(puntuacion.getEquipo() == Puntuacion.EQUIPO_LOCAL) { // amarillo
                Button button = new Button(this);
                button.setText(puntuacion.getPuntos() + "");
                button.setBackgroundColor(getResources().getColor(R.color.yellow));
                deletePuntuacion(button, puntuacion);
                llPuntuaciones.addView(button);
                contLocal += puntuacion.getPuntos();
            }
            Log.d("xxx", list.size() + "");
            Log.d("xxx",puntuacion.getId() + " " + puntuacion.getPuntos() + " " + puntuacion.getEquipo());
        }
        Log.d("xxx", list.size() + " Numero de elementos en el array");
        tvLocal.setText("Local " + contLocal);
        tvVisitante.setText("Visitante " + contVisitante);
    }

    private void deletePuntuacion(Button button, final Puntuacion puntuacion) {
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(puntuacion.getEquipo() == Puntuacion.EQUIPO_LOCAL) {
                    contLocal -= puntuacion.getPuntos();
                    tvLocal.setText("Local " + contLocal);
                }
                else if(puntuacion.getEquipo() == Puntuacion.EQUIPO_VISITANTE) {
                    contVisitante -= puntuacion.getPuntos();
                    tvVisitante.setText("Visitante " + contVisitante);
                }
                // Borramos de la tabla
                puntuacionDB.borrarPuntuacion(puntuacion.getId());
                // Borramos de la vista
                llPuntuaciones.removeView(v);
                return true;
            }
        });
    }

    public void añadirPuntos(Button botonId, View view, int colorEquipo) {
        int puntos = Integer.parseInt(botonId.getText().toString());

        Button button = new Button(this);
        button.setText(puntos + "");
        button.setBackgroundColor(getResources().getColor(colorEquipo));

        if(colorEquipo == R.color.yellow) {
            contLocal += puntos;
            tvLocal.setText("Local " + contLocal);
            puntuacionDB.insertarPuntuacion(new Puntuacion(puntos, Puntuacion.EQUIPO_LOCAL));
        }
        else if(colorEquipo == R.color.green) {
            contVisitante += puntos;
            tvVisitante.setText("Local " + contVisitante);
            puntuacionDB.insertarPuntuacion(new Puntuacion(puntos, Puntuacion.EQUIPO_VISITANTE));
        }
        llPuntuaciones.addView(button);
    }

    // Aquio empieza el programa
    public void onClickSumar(View view){
        switch(view.getId()){
            case R.id.btLocal1: // int
                añadirPuntos(btLocal1, view, R.color.yellow);
                break;
            case R.id.btLocal2:
                añadirPuntos(btLocal2, view, R.color.yellow);
                break;
            case R.id.btLocal3:
                añadirPuntos(btLocal3, view, R.color.yellow);
                break;
            case R.id.btVisitante1:
                añadirPuntos(btVisitante1, view, R.color.green);
                break;
            case R.id.btVisitante2:
                añadirPuntos(btVisitante2, view, R.color.green);
                break;
            case R.id.btVisitante3:
                añadirPuntos(btVisitante3, view, R.color.green);
                break;
        }
    }

    public void onClickReset(View view) {
        // Vaciamos la interfaz
        llPuntuaciones.removeAllViews();
        contLocal = 0;
        tvLocal.setText("Local " + contLocal);
        contVisitante = 0;
        tvVisitante.setText("Visitante " + contVisitante);
        // Vaciamos la tabla
        puntuacionDB.reset();
    }


}