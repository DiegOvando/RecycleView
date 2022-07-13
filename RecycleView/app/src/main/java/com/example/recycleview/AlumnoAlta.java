package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Modelo.AlumnosDb;

public class AlumnoAlta extends AppCompatActivity {

    private Button btnGuardar, btnRegresar, btnFoto, btnBorrar;
    private Alumno alumno;
    private EditText txtNombre, txtMatricula, txtGrado;
    private ImageView imgAlumno;
    private TextView lblFoto, txtId;
    //private String carrera = "Ing. Tec. Información";
    private int posicion;
    private AlumnosDb alumnoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);

        lblFoto = (TextView) findViewById(R.id.lblFoto);
        txtId = (TextView) findViewById(R.id.txtId);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnGuardar = (Button) findViewById(R.id.btnSalir);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        imgAlumno = (ImageView) findViewById(R.id.imgAlumno);

        Bundle bundle = getIntent().getExtras();
        alumno = (Alumno) bundle.getSerializable("alumno");
        posicion = bundle.getInt("posicion", posicion);

        if(posicion >= 0){
            txtMatricula.setText(alumno.getMatricula());
            txtNombre.setText(alumno.getNombre());
            txtGrado.setText(alumno.getCarrera());
            imgAlumno.setImageResource(alumno.getImg());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alumno == null){
                    //agregar un nuevo alumno
                    alumno = new Alumno();
                    alumno.setCarrera(txtGrado.getText().toString());
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setImg(R.drawable.logo);

                    if(validar()){
                        alumnoDb.insertAlumno(alumno);
                        Aplicacion.alumnos.add(alumno);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext()," Falto capturar datos ", Toast.LENGTH_SHORT).show();
                        txtMatricula.requestFocus();
                    }
                }

                if (posicion >= 0){
                    alumno.setId(Integer.parseInt(txtId.getText().toString()));
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setCarrera(txtGrado.getText().toString());

                    alumnoDb.updateAlumno(alumno);

                    Aplicacion.alumnos.get(posicion).setMatricula(alumno.getMatricula());
                    Aplicacion.alumnos.get(posicion).setNombre(alumno.getNombre());
                    Aplicacion.alumnos.get(posicion).setCarrera(alumno.getCarrera());

                    Toast.makeText(getApplicationContext(), " Se modifico con exito ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posicion >= 0){
                    Aplicacion.alumnos.remove(posicion);
                    alumnoDb.deleteAlumnos(alumno.getId());
                    finish();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private boolean validar(){
        boolean exito = true;
        Log.d("nombre","validar: " + txtNombre.getText());
        if(txtNombre.getText().toString().equals("")) exito = false;
        if(txtMatricula.getText().toString().equals("")) exito = false;
        if(txtGrado.getText().toString().equals("")) exito = false;

        return exito;
    }
}