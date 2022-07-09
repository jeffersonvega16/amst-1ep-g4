package com.example.amst_1ep_grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Resultados extends AppCompatActivity {

    TextView resultado;
    public ArrayAdapter<String> adaptador;
    public ListView listado;
    private Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        resultado = (TextView) findViewById(R.id.textResultado);
        listado = findViewById(R.id.lista);
        recibirDatos();

        btnRequest = (Button) findViewById(R.id.buttonAtras);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              Retroceder();

                                          }
                                      }

        );

    }

    private void recibirDatos(){
        Bundle extras =  getIntent().getExtras();
        ArrayList<String> Lista = extras.getStringArrayList("Lista");
        int tamanio = Lista.size();
        String l = "Resultado:" + String.valueOf(tamanio);
        resultado.setText(l);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Lista);
        listado.setAdapter(adaptador);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent v = new Intent(Resultados.this, Descripcion.class );
                v.putExtra("objetoData",Lista.get(position));
                startActivity(v);
            }
        });
    }

    private void Retroceder(){

        Intent m = new Intent(Resultados.this, MainActivity.class );
        startActivity(m);


    }


}