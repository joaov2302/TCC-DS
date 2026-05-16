package com.example.crashware.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.crashware.R;
import com.example.crashware.ui.api.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import androidx.activity.result.contract.ActivityResultContracts;

public class Perfil_Fragment extends Fragment {

    //Objetos que vão ser utilizados
    private SharedPreferences.OnSharedPreferenceChangeListener listenerFoto;

    private SharedPreferences.OnSharedPreferenceChangeListener listenerBanner;
    TextView txtNomePerfil, txtQuantXP, txtPatente, txtVerTodasConquistas;

    ImageView imgConfigPerfil;

    ShapeableImageView imgFotoPerfil, imgBanner;

    private ActivityResultLauncher<String[]> escolherFoto;

    private ActivityResultLauncher<String[]> escolherBanner;

    //Memória do app
    SharedPreferences prefs;

    private FirebaseAuth auth;
    private DatabaseReference db;

    private String tipoImagem = ""; // "perfil" ou "banner"





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil_Fragment()
    {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Perfil_Fragment newInstance(String param1, String param2) {
        Perfil_Fragment fragment = new Perfil_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SharedPreferences
        prefs = requireContext().getSharedPreferences("CrashWare", Context.MODE_PRIVATE);

        //Crrego a foto assim que a tela foi incializada
        Foto();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

        escolherFoto = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        setImage(uri);
                    }
                }
        );
        escolherBanner = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        setBanner(uri);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Inicia o Layout no Código
        txtNomePerfil         = view.findViewById(R.id.txtNomePerfil        );
        txtPatente            = view.findViewById(R.id.txtPatente           );
        txtQuantXP            = view.findViewById(R.id.txtQuantXP           );
        txtVerTodasConquistas = view.findViewById(R.id.txtVerTodasConquistas);
        imgFotoPerfil         = view.findViewById(R.id.imgFotoPerfil        );
        imgConfigPerfil       = view.findViewById(R.id.imgConfigPerfil      );
        imgBanner             = view.findViewById(R.id.imgBanner            );
        imgFotoPerfil         = view.findViewById(R.id.imgFotoPerfil        );
        imgBanner             = view.findViewById(R.id.imgBanner            );


        //Pego os dados no SharedPreferences
        String Nome = prefs.getString("nome", null);


        // Listener da foto
        listenerFoto = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals("foto")) {
                    carregarImagem();
                }
            }
        };

        // Listener do banner

        listenerBanner = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals("banner")) {
                    carregarBanner();
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listenerBanner);

        prefs.registerOnSharedPreferenceChangeListener(listenerFoto);



        txtNomePerfil.setText(Nome);
//        txtPatente.setText(Patente);
//        txtQuantDiasSeguidos.setText(Ofensiva);
//        txtQuantXP.setText(XP);
//        txtQuantGemas.setText(Gemas);



        txtVerTodasConquistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment conquistasFragmento = new Conquistas_Fragment();

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, conquistasFragmento)
                        .addToBackStack(null)
                        .commit();

            }
        });// interação com o texto que leva para a tela com todas as conquistas

        imgConfigPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment novoFragmento = new Configuracoes_Fragment();

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, novoFragmento)
                        .addToBackStack(null)
                        .commit();
            }
        });// interação com a imagem que leva para a tela de configurações

        imgFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tipoImagem = "perfil";
                escolherFoto.launch(new String[]{"image/*"});

            }
        });//Interação com a foto de perfil

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tipoImagem = "banner";
                escolherBanner.launch(new String[]{"image/*"});

            }
        });//Interação com o Banner

        //Carrega a imagem
        carregarImagem();

        //Carrego o Banner
        carregarBanner();

        return view;

    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//
//        Foto();
//    }

    private void Foto(){
        User.Perfil(requireContext(), prefs, new User.PerfilCallback()
        {
            @Override
            public void sucesso(User.PerfilResponse usuario) {

                String email = usuario.email;

                String foto = usuario.foto;
                String banner = usuario.banner;

                //Salvo o link da foto
                String link_foto =  "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/FOTOS/"
                        + foto
                        + "?t=" + System.currentTimeMillis();

                //Salvo o link do banner
                String link_banner =  "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/banner/"
                        + banner
                        + "?t=" + System.currentTimeMillis();

                //Carrega a foto atual do usuario
                Glide.with(requireContext())
                        .load(link_foto)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imgFotoPerfil);

                //Carrega o banner atual do usuario
                Glide.with(requireContext())
                        .load(link_banner)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imgBanner);
            }
        });
    }

    private void setImage(Uri uri) {
        if (!isAdded()) return;

        //Pego a foto do banco
        String foto = prefs.getString("foto", null);

        //Verifico se vai adicionar ou alterar
        if("default.png".equals(foto))
        {
            //Add a foto
            Toast.makeText(getContext(), "Adicionando Foto..", Toast.LENGTH_LONG).show();
            User.Adicionar_Foto(requireContext(),prefs,uri,imgFotoPerfil);


        }
        else
        {
            //Altero a foto
            Toast.makeText(getContext(), "Alterando Foto..", Toast.LENGTH_LONG).show();
            User.Alterar_Foto(requireContext(),prefs,uri,imgFotoPerfil);


        }


    }

    private void setBanner(Uri uri) {
        if (!isAdded()) return;

        //Pego o banner
        String banner = prefs.getString("banner", null);

        //Verifico se vai adicionar ou alterar
        if ("default.png".equals(banner)) {
            //Add o banner
            Toast.makeText(getContext(), "Adicionando Banner...", Toast.LENGTH_LONG).show();
            User.Adicionar_Banner(requireContext(), prefs, uri, imgBanner);


        } else {
            //Altero o banner
            Toast.makeText(getContext(), "Alterando Banner...", Toast.LENGTH_LONG).show();
            User.Alterar_Banner(requireContext(), prefs, uri, imgBanner );


        }
    }

    private void carregarImagem() {
        if (!isAdded()) return;

        //Pego a foto
        String fotoPerfil = prefs.getString("foto", null);

        //Salvo o link da foto
        String link_foto =  "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/FOTOS/"
                + fotoPerfil
                + "?t=" + System.currentTimeMillis();

        //Carrega a foto atual do usuario
        Glide.with(requireContext())
                .load(link_foto)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imgFotoPerfil);

    }

    private void carregarBanner() {
        if (!isAdded()) return;

        //Pego a foto
        String bannerPerfil = prefs.getString("banner", null);

        //Salvo o link da foto
        String link_banner =  "https://yegrosiecwjebeetlwwg.supabase.co/storage/v1/object/public/banner/"
                + bannerPerfil
                + "?t=" + System.currentTimeMillis();

        //Carrega o banner atual do usuario
        Glide.with(requireContext())
                .load(link_banner)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imgBanner);


    }

}