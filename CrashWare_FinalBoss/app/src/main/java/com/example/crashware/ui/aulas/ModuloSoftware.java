package com.example.crashware.ui.aulas;

import android.content.Intent;
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

public class ModuloSoftware extends AppCompatActivity {

    TextView txtTituloAula1M1, txtTituloAula2M1,txtTituloAula1M2,txtTituloAula2M2,txtTituloAula3M2,txtTituloAula4M2,txtTituloAula5M2,txtTituloAula6M2,txtTituloAula7M2,txtTituloAula8M2;

    ImageView imgBackS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modulo_software);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ModuloSoftware), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });//

        txtTituloAula1M1 = findViewById(R.id.txtTituloAula1M1S);
        txtTituloAula2M1 = findViewById(R.id.txtTituloAula2M1S);
        txtTituloAula1M2 = findViewById(R.id.txtTituloAula1M2S);
        txtTituloAula2M2 = findViewById(R.id.txtTituloAula2M2S);
        txtTituloAula3M2 = findViewById(R.id.txtTituloAula3M2S);
        txtTituloAula4M2 = findViewById(R.id.txtTituloAula4M2S);
        txtTituloAula5M2 = findViewById(R.id.txtTituloAula5M2S);
        txtTituloAula6M2 = findViewById(R.id.txtTituloAula6M2S);
        txtTituloAula7M2 = findViewById(R.id.txtTituloAula7M2S);
        txtTituloAula8M2 = findViewById(R.id.txtTituloAula8M2S);
        imgBackS         = findViewById(R.id.imgBackSoftware  );

        txtTituloAula1M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Aula1M1S();
            }
        });// interação com o titulo da aula 1

        imgBackS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();

            }
        });//Interação combotão voltar que finaliza a activity atual


    }//

    private void Aula1M1S()
    {
        Intent NovaAula = new Intent(ModuloSoftware.this, ContainerSoftware.class);
        startActivity(NovaAula);

    }


}