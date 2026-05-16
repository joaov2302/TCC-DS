package com.example.crashware.ui.anotacoes;

public class Anotacao
{
    //Váriaveis presentes na anotação
    private String titulo;
    private String conteudo;

    // CONSTRUTOR
    public Anotacao(String titulo, String conteudo)
    {
        //linkando as variáveis com seus construtores
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    // GETTERS
    public String getTitulo()
    {
        return titulo;
    }

    public String getConteudo()
    {
        return conteudo;
    }
}
