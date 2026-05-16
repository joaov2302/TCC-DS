package com.example.crashware.ui.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.crashware.ui.login.Login;
import com.example.crashware.ui.navegacao.Home;
import com.example.crashware.ui.navegacao.carregamento;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class Auth {

    //Token
    static class TokenResponse{
        Integer id;

        public Integer getId() {
            return id;
        }

    }


    //Token
    static interface token {
        @POST("/auth/verificar_token")
        Call<TokenResponse> verificar(
                @Header("Authorization") String token
        );
    }


    //Função de verificar Token
    public static void verificarToken(Context context, SharedPreferences prefs,Boolean home)
    {

        //Pego o valor do token
        String token = prefs.getString("token",null);

        //Preparo ele para enviar para o header da requisição
        token = "Bearer " + token;

        // Criando a API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-crashware.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //

        // Fazendo que a interface da API seja utilizavel:
        token api = retrofit.create(token.class);

        // Monto a chamada da API:
        Call<TokenResponse> requisicao = api.verificar(token);

        requisicao.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(
                    Call<TokenResponse> requisicao,
                    retrofit2.Response<TokenResponse> resposta
            ) {
                //Se o token for valido
                if (resposta.isSuccessful())
                {

                    if(home == false)
                    {
//                        // Envia para a tela de HOME:
//                        prefs.edit().putBoolean("EstadoToken", true).apply();
//                        //

                        Intent i = new Intent(context, Home.class);
                        context.startActivity(i);
                        ((Activity) context).finish();
                    } else
                    {
                        //Ignoro
                    }
                } else {
                    //Sair da conta

                    //Deleto o token e o refresh_token
                    prefs.edit()
                            .remove("token")
                            .remove("refresh_token")
                            .apply();

                    //Vou para o login

//                        String carregar = "true";
//                        prefs.edit()
//                                .putString("EstadoToken", carregar)
//                                .apply();

                    Intent i = new Intent(context, Login.class);
                    context.startActivity(i);
                    ((Activity) context).finish();

                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                // Caso deu erro na requisição
                // erro de conexão (internet, URL, servidor fora)
                Toast.makeText(
                        context,
                        "Erro de conexão: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });



    }//Verificar Token




}