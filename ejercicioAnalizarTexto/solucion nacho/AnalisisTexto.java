package com.example.unidad2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AnalisisTexto extends AppCompatActivity {
    private Button analizarBt;
    private EditText inputEt;
    private TextView podiumPalabrasTv;
    public static final String TEXTO_CONTADOR = "13052003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_texto);
        inputEt = findViewById(R.id.u3a6idEtOutput);
        analizarBt = findViewById(R.id.u3a6idBtFinAnalisis);
        podiumPalabrasTv = findViewById(R.id.u3a6idTvPodium);
        ActivityResultLauncher<Intent> lanzadora = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            podiumPalabrasTv.setText(data.getStringExtra(ResultadoAnalisis.TEXTO_PODIUM));

                        }
                    }
                }

        );

        analizarBt.setOnClickListener(view -> {
            Intent i = new Intent(this, ResultadoAnalisis.class);
            i.putExtra(TEXTO_CONTADOR, inputEt.getText().toString());
            lanzadora.launch(i);

        });

    }
}