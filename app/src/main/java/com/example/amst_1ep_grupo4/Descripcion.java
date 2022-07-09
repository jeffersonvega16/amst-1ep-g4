package com.example.amst_1ep_grupo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.util.ArrayUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Descripcion extends AppCompatActivity {

    private TextView nombre;
    private RequestQueue mRequestQueue2;
    private StringRequest mStringRequest2;
    private static final String TAG2 = Descripcion.class.getName();
    public ArrayList<String> Lista2 = new ArrayList<>();
    public ArrayList<String> nombrecompleto = new ArrayList<>();
    public ArrayList<String> foto = new ArrayList<>();
    public ArrayList<String> poderes1 = new ArrayList<>();
    public ArrayList<String> poderes2 = new ArrayList<>();
    public ArrayList<String> poderes3 = new ArrayList<>();
    public ArrayList<String> poderes4 = new ArrayList<>();
    public ArrayList<String> poderes5 = new ArrayList<>();
    public ArrayList<String> poderes6 = new ArrayList<>();
    TextView resultado1, resultado2, resultado3;
    private ImageView heroeimagen;
    private BarChart chart;
    private SeekBar seekBarX, seekBarY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        Bundle extras =  getIntent().getExtras();
        String item = extras.getString("objetoData");
        nombre = (TextView) findViewById(R.id.textNombre);
        String heroe= "Superheroe:" + item;
        nombre.setText(heroe);

        resultado2 = (TextView) findViewById(R.id.textView4);
        heroeimagen = (ImageView) findViewById(R.id.imagen);
        chart = findViewById(R.id.chart1);

        mRequestQueue2 = Volley.newRequestQueue(this);
        String url_temp = " https://www.superheroapi.com/api.php/10223479158232624/search/"+item;
        mStringRequest2 = new StringRequest(Request.Method.GET, url_temp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                        Lista2.add(json.getString("name"));
                        JSONObject poder = json.getJSONObject("powerstats");
                        poderes1.add(json.getJSONObject("powerstats").getString("intelligence"));
                        poderes1.add(json.getJSONObject("powerstats").getString("strength"));
                        poderes1.add(json.getJSONObject("powerstats").getString("speed"));
                        poderes1.add(json.getJSONObject("powerstats").getString("durability"));
                        poderes1.add(json.getJSONObject("powerstats").getString("power"));
                        poderes1.add(json.getJSONObject("powerstats").getString("combat"));
                        nombrecompleto.add(json.getJSONObject("biography").getString("full-name"));
                        foto.add(json.getJSONObject("image").getString("url"));
                        //Toast.makeText(getApplicationContext(),"Response :" + poderes.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),"Response :" + nombrecompleto.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),"Response :" + foto.toString(), Toast.LENGTH_LONG).show();
                        //prueba.setText(Lista.toString());
                        // resultado1.setText(valor);
                        //resultado1.setText(Lista2.toString());
                        for (String g : nombrecompleto) {
                            String nombreheroe = "Nombre completo:" + g;
                            resultado2.setText(nombreheroe);

                        }
                        for (String l : foto) {
                            //resultado3.setText(l);
                            Picasso.get().load(l).into(heroeimagen);

                        }

                        int alo = Lista2.indexOf(item);
                        //Toast.makeText(getApplicationContext(), "Response:" + alo, Toast.LENGTH_LONG).show();

                        ArrayList<IBarDataSet> datos = new ArrayList<>();
                        ArrayList<BarEntry> lista = new ArrayList<>();
                        Iterator<String> clave = poder.keys();
                        int num = 0;
                        while (clave.hasNext()) {
                            String clave_actual = clave.next();
                            try {
                                BarEntry bar = new BarEntry(0 + num, poder.getInt(clave_actual));
                                num += 1;
                                lista.add(bar);
                            } catch (Exception e) {


                            }


                        }

                        BarDataSet conj = new BarDataSet(lista, "Poder");
                        conj.setColors(ColorTemplate.PASTEL_COLORS);
                        conj.setDrawValues(true);
                        datos.add(conj);
                        BarData val = new BarData(datos);
                        chart.setData(val);
                        chart.setFitBars(true);
                        chart.invalidate();
                        chart.notifyDataSetChanged();


                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG2,"Error :" + error.toString());
            }
        });

        mRequestQueue2.add(mStringRequest2);
    }




}