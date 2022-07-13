package com.example.recycleview;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import Modelo.AlumnosDb;

public class Aplicacion extends Application {
    static public ArrayList<Alumno> alumnos;
    private MiAdaptador adaptador;
    private AlumnosDb alumnosDb;

    public static ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public MiAdaptador getAdaptador() {
        return adaptador;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        alumnosDb = new AlumnosDb(getApplicationContext());
        alumnos = alumnosDb.allAlumnos();
        adaptador = new MiAdaptador(alumnos,this);
        //Log.d("", "onCreate: tamaño array list " + alumnos.size());
    }
}
