package com.example.unidad3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EntradaTexto extends AppCompatActivity {

    public static final String CLAVE_TEXTO = "texto";

    TextView resultado;
    EditText entrada;
    Button analizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_texto);
        resultado=findViewById(R.id.idTvResultadoAnalisis);
        entrada=findViewById(R.id.ideTEntradaTexto);
        analizar=findViewById(R.id.idBtAnalisisTexto);
        ActivityResultLauncher<Intent> lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            resultado.setText(data.getStringExtra(ListadoLetras.CLAVE_ANALISIS));
                        }
                        else{
                            resultado.setText("El texto no tiene caracteres validos para ser analizados");
                        }
                    }
                });

        analizar.setOnClickListener(v -> {
            if(!entrada.getText().toString().replace(" ","").isEmpty()){
                Intent i = new Intent(this, ListadoLetras.class);
                i.putExtra(CLAVE_TEXTO, entrada.getText().toString());
                lanzador.launch(i);
            }
        });

    }
}