package com.example.amst_1ep_grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText Busqueda;
    TextView prueba;
    private Button btnRequest;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static final String TAG = MainActivity.class.getName();
    public ArrayList<String> Lista = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Busqueda = (EditText) findViewById(R.id.editTextBusqueda);
        //prueba = (TextView) findViewById(R.id.textprueba);

        btnRequest = (Button) findViewById(R.id.buttonBuscar);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              buscarHeroe();

                                          }
                                      }

        );



    }

    private void buscarHeroe() {
        mRequestQueue = Volley.newRequestQueue(this);
        String url_temp = " https://www.superheroapi.com/api.php/10223479158232624/search/"+Busqueda.getText().toString();
        mStringRequest = new StringRequest(Request.Method.GET, url_temp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //display the response on screen
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject json = jsonArray.getJSONObject(i);
                        //Aquí se obtiene el dato y es guardado en una lista
                        Lista.add(json.getString("name"));
                        //Toast.makeText(getApplicationContext(),"Response :" + Lista.toString(), Toast.LENGTH_LONG).show();
                        //prueba.setText(Lista.toString());
                        Intent m = new Intent(MainActivity.this, Resultados.class );
                        m.putExtra("Lista", Lista);
                        startActivity(m);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


}