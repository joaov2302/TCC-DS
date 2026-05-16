package com.example.crashware.ui.anotacoes;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.crashware.ui.navegacao.Inicio_fragment;
import com.example.crashware.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Anotacoes_fragment extends Fragment {

    //Váriaveis e Funções que serão utilizadas e iniciadas no código
    private ArrayList<Anotacao> listaAnotacoes = new ArrayList<>();

    RecyclerView rvListaAnotacoes;

    Anotacao_Adapter adapter;
    SharedPreferences prefs;

    EditText txtbarraPesquisa;

    ConstraintLayout cardAnotacao1,cardAnotacao2,cardAnotacao3;

    ImageView imgAddAnotacoes, imgLayoutLogo;

    TextView txtTitulo1, txtConteudo1, txtTitulo2, txtConteudo2;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    public Anotacoes_fragment()
    {
        // Required empty public constructor
    }
    //

    // TODO: Rename and change types and number of parameters
    public static Anotacoes_fragment newInstance(String param1, String param2) {
        Anotacoes_fragment fragment = new Anotacoes_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anotacoes, container, false);

        imgAddAnotacoes  = view.findViewById(R.id.imgAddAnotacoes   );
        txtbarraPesquisa = view.findViewById(R.id.txtBarraPesquisa  );
        rvListaAnotacoes = view.findViewById(R.id.recyclerView);

        //LayoutManager para atualizar a cada anotação
        rvListaAnotacoes.setLayoutManager(new LinearLayoutManager(getContext()));

        // 2. Adapter depois
        adapter = new Anotacao_Adapter(listaAnotacoes, new Anotacao_Adapter.OnItemClickListener()
        {
            @Override
            public void onClick(Anotacao anotacao, int position)
            {
                //Leva para o fragment usado para editar anotações
                EditarAnotacao_Fragment fragment = new EditarAnotacao_Fragment();

                //ao ir para o fragment, puxa as informações necessárias para alterar o correto
                Bundle bundle = new Bundle();
                bundle.putString("titulo", anotacao.getTitulo());
                bundle.putString("conteudo", anotacao.getConteudo());
                bundle.putInt("position", position); // puxa a posição na arraylist da anotação selecionada

                fragment.setArguments(bundle);


                //Tramite de troca de fragmento
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });//Interação com A anotação desejada da lista

        //Selecionando o adapter para a ArrayList
        rvListaAnotacoes.setAdapter(adapter);





//        //Iniciando o SharedPreferences que contém as anotações
//        prefs = requireActivity().getSharedPreferences("dados", MODE_PRIVATE);
//
//        // Recupera os dados salvos
//        String tituloSalvo = prefs.getString("Titulo", "");
//        String anotacaoSalva = prefs.getString("Anotacao", "");
//        String NovaAnotacaoSalva2 = prefs.getString("NovaAnotacao2","");
//        String NovoTituloAnotacaoSalvo2 = prefs.getString("TituloNovaAnotacao2","");








        imgAddAnotacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment novoFragmento = new NovaAnotacao_Fragment();

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, novoFragmento)
                        .addToBackStack(null)
                        .commit();
            }
        });//Interação com a imagem, levando a tela de adicionar nova anotação



        return view;

    }
    @Override
    public void onResume()
    {
        super.onResume();

        carregarAnotacoes();

        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    private void carregarAnotacoes()
    {


        try
        {
            SharedPreferences prefs =
                    requireActivity().getSharedPreferences("dados", MODE_PRIVATE);

            String json = prefs.getString("lista_anotacoes", "[]");

            JSONArray array = new JSONArray(json);

            listaAnotacoes.clear();

            for (int i = 0; i < array.length(); i++)
            {
                JSONObject obj = array.getJSONObject(i);

                String titulo = obj.getString("titulo");
                String conteudo = obj.getString("conteudo");

                listaAnotacoes.add(
                        new Anotacao(titulo, conteudo)
                );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}