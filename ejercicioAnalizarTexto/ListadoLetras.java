package com.example.unidad3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListadoLetras extends AppCompatActivity {

    public static String CLAVE_ANALISIS = "analisis";
    LinkedHashMap<Character, Integer> mapa;
    private static String regex = "[.,\\/#!$%\\^&\\*;:{}=\\-_`~()”“\"…\n123456789]";
    private static final int LETRAS_A_PASAR = 3;

    Button end;

    List<Map.Entry<Character, Integer>> lista;

    ListView listadoDeletras;
    ArrayList<String> textoDelListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_letras);
        end = findViewById(R.id.idBtEndAnalisis);
        listadoDeletras = findViewById(R.id.idPrueba);
        textoDelListado = new ArrayList<>();
        mapa = new LinkedHashMap<>();
        Bundle info = getIntent().getExtras();
        String texto = info.getString(EntradaTexto.CLAVE_TEXTO);
        texto = texto.replace(" ", "").replaceAll(regex, "").toLowerCase();
        if (!texto.isEmpty()) {
            int i = 0;
            while (i < texto.length()) {
                char letra = texto.charAt(i);
                if (mapa.containsKey(letra)) {
                    mapa.put(letra, mapa.get(letra) + 1);
                } else {
                    mapa.put(letra, 1);
                }
                i++;
            }

            lista = new ArrayList<Map.Entry<Character, Integer>>(mapa.entrySet());
            Iterator<Map.Entry<Character, Integer>> iterador = lista.iterator();
            while (iterador.hasNext()) {
                Map.Entry<Character, Integer> letra = iterador.next();
                textoDelListado.add("La letra " + letra.getKey() + " tiene " + letra.getValue() + "\n");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.prueba, textoDelListado);
            listadoDeletras.setAdapter(adapter);
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Error");
            alerta.setMessage("El texto no tiene letras para analizar");
            alerta.show();
        }

        end.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if (lista.isEmpty()) {

                                           Intent data = new Intent();
                                           setResult(Activity.RESULT_CANCELED, data);
                                           finish();
                                       } else {
                                           Collections.sort(lista, (o1, o2) -> {
                                               int res = 0;
                                               if (o1.getValue() > o2.getValue()) {
                                                   res = -1;
                                               } else if (o1.getValue() < o2.getValue()) {
                                                   res = 1;
                                               }
                                               return res;
                                           });
                                           Intent data = new Intent();
                                           setResult(Activity.RESULT_OK, data);
                                           int in = 0;
                                           Iterator<Map.Entry<Character, Integer>> it = lista.iterator();
                                           String mandar = "";
                                           while (in < LETRAS_A_PASAR && it.hasNext()) {
                                               Map.Entry<Character, Integer> letra = it.next();
                                               mandar += "La letra " + letra.getKey() + " tiene " + letra.getValue() + " apariciones\n";
                                               in++;
                                           }
                                           data.putExtra(CLAVE_ANALISIS, mandar);
                                           finish();
                                       }

                                   }
                               }

        );
    }

}