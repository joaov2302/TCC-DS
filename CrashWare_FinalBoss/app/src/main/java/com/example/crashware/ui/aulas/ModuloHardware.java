package com.example.crashware.ui.aulas;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crashware.R;

public class ModuloHardware extends AppCompatActivity {

    ImageView imgVoltarH;

    TextView txtTituloAula1M1H, txtTituloAula2M1H,txtTituloAula1M2H,txtTituloAula2M2H,txtTituloAula3M2H,txtTituloAula4M2H,
            txtTituloAula1M3H,txtTituloAula2M3H,txtTituloAula3M3H, txtTituloAula4M3H, txtTituloAula5M3H, txtTituloAula6M3H;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modulo_hardware);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ModuloHardware), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });//

        txtTituloAula1M1H = findViewById(R.id.txtTituloAula1M1H);
        txtTituloAula2M1H = findViewById(R.id.txtTituloAula2M1H);
        txtTituloAula1M2H = findViewById(R.id.txtTituloAula1M2H);
        txtTituloAula2M2H = findViewById(R.id.txtTituloAula2M2H);
        txtTituloAula3M2H = findViewById(R.id.txtTituloAula3M2H);
        txtTituloAula4M2H = findViewById(R.id.txtTituloAula4M2H);
        txtTituloAula1M3H = findViewById(R.id.txtTituloAula1M3H);
        txtTituloAula2M3H = findViewById(R.id.txtTituloAula2M3H);
        txtTituloAula3M3H = findViewById(R.id.txtTituloAula3M3H);
        txtTituloAula4M3H = findViewById(R.id.txtTituloAula4M3H);
        txtTituloAula5M3H = findViewById(R.id.txtTituloAula5M3H);
        txtTituloAula6M3H = findViewById(R.id.txtTituloAula6M3H);
        imgVoltarH        = findViewById(R.id.imgVoltarH       );

        imgVoltarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();

            }
        });//

        txtTituloAula1M1H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Aula1M1H();
            }
        });//




    }

    private void Aula1M1H()
    {
        Intent NovaAula = new Intent(ModuloHardware.this, ContainerSoftware.class);
        startActivity(NovaAula);

    }
}