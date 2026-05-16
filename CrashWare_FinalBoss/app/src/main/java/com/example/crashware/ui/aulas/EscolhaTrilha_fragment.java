package com.example.crashware.ui.aulas;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.crashware.R;
import com.example.crashware.ui.navegacao.Home;

public class EscolhaTrilha_fragment extends Fragment {

    Button btnExplorarH, btnExplorarS;

    public EscolhaTrilha_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_escolhatrilha, container, false);

        btnExplorarH = view.findViewById(R.id.btnExplorarH);
        btnExplorarS = view.findViewById(R.id.btnExplorarS);


        btnExplorarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent Software = new Intent(getActivity(), ModuloSoftware.class);
                startActivity(Software);
                //verifica a tela atual e redireciona para a Activity MSoftware

            }
        });// Interação com o Botão de Software levando para a tela do Módulo de Software


        btnExplorarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Hardware = new Intent(getActivity(), ModuloHardware.class);
                startActivity(Hardware);
                //verifica a tela atual e redireciona para a Activity MHardware

            }
        });// Interação com o Botão de Hardware levando para a tela do Módulo de Hardware

        return view;
    }
}