package com.example.crashware.ui.navegacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.crashware.R;
import com.example.crashware.ui.api.Auth;
import com.example.crashware.ui.api.User;
import com.example.crashware.ui.aulas.ModuloHardware;
import com.example.crashware.ui.aulas.ModuloSoftware;
import com.example.crashware.ui.sistemas.Ofensiva_Manager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Inicio_fragment extends Fragment {

    //Iniciando as váriaveis, Objetos e funções que vão ser utilizadas
    private SharedPreferences.OnSharedPreferenceChangeListener listenerFoto;

    private TextView txtNomeInicio, txtAulasConcluidas, txtOfensiva, txtNivelInicio, txtXpInicio;
    private ShapeableImageView imgfotoInicio;

    private FirebaseAuth auth;
    private DatabaseReference db;

    private ValueEventListener nomeListener;

    ProgressBar BarraProgressoNivel;

    ConstraintLayout btnRetomarS, btnRetomarH;

    // Memória do app
    SharedPreferences prefs;

    Button btnRetomar;

    int XpParaBarra = 350;

    int nivel = 1;

    // =========================
    // OFENSIVA
    // =========================

    private static final String KEY_OFENSIVA = "ofensiva";
    private static final String KEY_ULTIMO_DIA = "ultimo_dia";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        prefs = requireContext().getSharedPreferences("CrashWare", Context.MODE_PRIVATE);

        // Verificando Token
        Verificar_Token();

        // Coleto as informações do usuário
        Perfil();



        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // variável que torna possível utilizar elementos do design no código
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // iniciando os elementos através do view
        txtNomeInicio       = view.findViewById(R.id.txtNomeInicio);
        imgfotoInicio       = view.findViewById(R.id.imgFotoInicio);
        txtAulasConcluidas  = view.findViewById(R.id.txtNumeroAulasConcluidas);
        txtOfensiva         = view.findViewById(R.id.txtDiasConsecutivos);
        btnRetomarH         = view.findViewById(R.id.btnRetomarH);
        btnRetomarS         = view.findViewById(R.id.btnRetomarS);
        txtNivelInicio      = view.findViewById(R.id.txtNivelInicio);
        BarraProgressoNivel = view.findViewById(R.id.BarraProgressoNivel);
        btnRetomar          = view.findViewById(R.id.btnRetomar);
        txtXpInicio         = view.findViewById(R.id.txtXPInicio);

        // funções que vão ser utilizadas
        carregarNomeFirebase();


        //Chamar função Ofensiva
        Ofensiva_Manager ofensivaManager =
                new Ofensiva_Manager(requireContext());

        int ofensiva = ofensivaManager.verificarOfensiva();

        txtOfensiva.setText(ofensiva + " dias");



        // Listener da foto
        listenerFoto = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals("foto")) {
                    carregarImagem();
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listenerFoto);

        btnRetomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarraXp();
            }
        });

        btnRetomarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Software = new Intent(getActivity(), ModuloSoftware.class);
                startActivity(Software);
            }
        });

        btnRetomarH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Hardware = new Intent(getActivity(), ModuloHardware.class);
                startActivity(Hardware);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Perfil();
    }

    // =========================
    // XP / NÍVEL
    // =========================

    private void PassarNivel() {

        nivel++;

        txtNivelInicio.setText("Nivel " + nivel);

        BarraProgressoNivel.setProgress(0);

        String XpAtual = XpParaBarra + "/" + BarraProgressoNivel.getMax() + "XP";
        txtXpInicio.setText(XpAtual);
    }//Função utilizada ao Passar de Nivel

    private void BarraXp()
    {

        XpParaBarra += 50;

        while (XpParaBarra >= BarraProgressoNivel.getMax()) {

            XpParaBarra -= BarraProgressoNivel.getMax();
            PassarNivel();
        }

        BarraProgressoNivel.setProgress(XpParaBarra);

        String XpAtual = XpParaBarra + "/" + BarraProgressoNivel.getMax() + "XP";
        txtXpInicio.setText(XpAtual);
    }//Função utilizada para Alterar a Barra de XP


    // =========================
    // TOKEN
    // =========================

    private void Verificar_Token() {

        Auth.verificarToken(requireContext(), prefs, true);
    }

    // =========================
    // PERFIL
    // =========================

    private void Perfil() {

        User.Perfil(requireContext(), prefs, new User.PerfilCallback() {
            @Override
            public void sucesso(User.PerfilResponse usuario) {

                String nome = usuario.nome;


                Integer nivel = usuario.nivel;
                String banner = usuario.banner;
                //Float xp = usuario.xp;


                String foto = usuario.foto;

                // Salvo os dados no SharedPreferences
                prefs.edit()
                        .putString("foto", foto)

                        .putString("nome", nome)

                        .putString("nome",nome)
                        .putString("banner",banner)
                        .putInt("nivel",nivel)

                        .commit();

                // Link da foto
                String link_foto =
                        "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/FOTOS/"
                                + foto
                                + "?t=" + System.currentTimeMillis();

                // Carrega foto
                Glide.with(requireContext())
                        .load(link_foto)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imgfotoInicio);

                // Atualiza nome
                txtNomeInicio.setText(nome);
            }
        });
    }

    // =========================
    // CARREGAR IMAGEM
    // =========================

    private void carregarImagem()
    {

        if (!isAdded()) return;

        String fotoPerfil = prefs.getString("foto", null);

        String link_foto =
                "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/FOTOS/"
                        + fotoPerfil
                        + "?t=" + System.currentTimeMillis();

        Glide.with(requireContext())
                .load(link_foto)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imgfotoInicio);
    }

    // =========================
    // FIREBASE (NOME)
    // =========================

    private void carregarNomeFirebase() {

        if (auth.getCurrentUser() == null) return;

        String uid = auth.getCurrentUser().getUid();

        nomeListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!isAdded()) return;

                if (snapshot.exists()) {

                    // Caso queira usar futuramente
                    // String nome_firebase = snapshot.child("nome").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                if (isAdded()) {
                    txtNomeInicio.setText("Erro");
                }
            }
        };

        db.child(uid).addValueEventListener(nomeListener);
    }

    // =========================
    // CLEANUP
    // =========================

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (prefs != null && listenerFoto != null) {
            prefs.unregisterOnSharedPreferenceChangeListener(listenerFoto);
        }
    }
}