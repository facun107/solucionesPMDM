package com.example.unidad2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultadoAnalisis extends AppCompatActivity {
    public static final String TEXTO_PODIUM = "14052003";
    Button finAnalisisBt;
    TextView outputTv;
    private StringBuilder constructorCadenas = new StringBuilder();
    private String podium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_analisis);
        finAnalisisBt = findViewById(R.id.u3a6idBtFinAnalisis);
        outputTv = findViewById(R.id.u3a6idTvOutput);
        Bundle info = getIntent().getExtras();
        String textoEntrada = info.getString(AnalisisTexto.TEXTO_CONTADOR);

        Map<Character, Integer> conteoLetras = new LinkedHashMap<>();
        for (char letra : textoEntrada.toCharArray()) {
            // Limpiar el carácter de caracteres no alfabéticos y convertirlo a minúsculas
            if (Character.isLetter(letra)) {
                letra = Character.toLowerCase(letra);
                conteoLetras.put(letra, conteoLetras.getOrDefault(letra, 0) + 1);
            }
        }
        List<Map.Entry<Character, Integer>> listaOrdenada = new ArrayList<>(conteoLetras.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
       /*comparator echo sin lambda
        listaOrdenada.sort(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> entry1, Map.Entry<Character, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue();
            }
        });

        */
        constructorCadenas.append("las letras más repetidas son:\n");
        constructorCadenas.append(listaOrdenada.get(0) + "\n");
        constructorCadenas.append(listaOrdenada.get(1) + "\n");
        constructorCadenas.append(listaOrdenada.get(2) + "\n");
        podium = constructorCadenas.toString();
        for (Map.Entry<Character, Integer> entry : listaOrdenada) {
            String salida;
            salida = entry.getKey().toString() + ": " + entry.getValue().toString() + " veces";
            outputTv.append(salida + "\n");
        }


        finAnalisisBt.setOnClickListener(view -> {
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            data.putExtra(TEXTO_PODIUM, podium);
            finish();
        });
    }
}