package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;


public class MainActivity extends AppCompatActivity implements Asynchtask {

    private TextView[] textViews = new TextView[5];
    private ImageButton[] imageButtons = new ImageButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews[0] = findViewById(R.id.txt1);
        textViews[1] = findViewById(R.id.txt2);
        textViews[2] = findViewById(R.id.txt3);
        textViews[3] = findViewById(R.id.txt4);
        textViews[4] = findViewById(R.id.txt5);

        imageButtons[0] = findViewById(R.id.img_Btn1);
        imageButtons[1] = findViewById(R.id.img_Btn2);
        imageButtons[2] = findViewById(R.id.img_Btn3);
        imageButtons[3] = findViewById(R.id.img_Btn4);
        imageButtons[4] = findViewById(R.id.img_Btn5);

        Map<String, String> datos = new HashMap<String, String>();

        WebService ws = new WebService("https://fakestoreapi.com/products",
                datos, MainActivity.this, MainActivity.this);

        ws.execute("GET", "Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
    }


    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray lugaresArray = new JSONArray(result);

        int numItemsToShow = Math.min(5, lugaresArray.length());

        for (int i = 0; i < numItemsToShow; i++) {
            JSONObject lugar = lugaresArray.getJSONObject(i);
            String categoria = lugar.optString("category", "");
            String precio = lugar.optString("price", "");
            String descripcion = lugar.optString("description", "");
            String titulo = lugar.optString("title", "");
            String imagenUrl = lugar.optString("image", "");

            textViews[i].setText("Categoria: " + categoria + "\nPrecio: " + precio + "\n" + descripcion + "\n");

            final String finalDescripcion = descripcion;
            final String finalCategoria = categoria;
            final String finalTitulo = titulo;
            final String finalPrecio = precio;
            final String finalImagenUrl = imagenUrl;

            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, producto_seleccionado.class);
                    intent.putExtra("descripcion", finalDescripcion);
                    intent.putExtra("categoria", finalCategoria);
                    intent.putExtra("precio", finalPrecio);
                    intent.putExtra("imagenUrl", finalImagenUrl);
                    intent.putExtra("titulo", finalTitulo);
                    startActivity(intent);
                }
            });


        }
    }
}