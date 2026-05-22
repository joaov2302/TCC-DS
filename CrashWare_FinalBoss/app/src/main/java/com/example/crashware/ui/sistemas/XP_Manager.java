package com.example.crashware.ui.sistemas;

import android.content.Context;
import android.content.SharedPreferences;

public class XP_Manager
{
    private SharedPreferences prefs;

    // Nome do SharedPreferences
    private static final String PREFS_NAME = "CrashWare";

    // Chaves
    private static final String KEY_XP = "xp";
    private static final String KEY_NIVEL = "nivel";

    // XP máximo por nível
    private static final int XP_MAXIMO = 500;

    public XP_Manager(Context context)
    {
        prefs = context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
        );
    }

    // =========================
    // GETTERS
    // =========================

    public float getXp()
    {
        return prefs.getFloat(KEY_XP, 0);
    }

    public int getNivel()
    {
        return prefs.getInt(KEY_NIVEL, 1);
    }

    public int getXpMaximo()
    {
        return XP_MAXIMO;
    }

    // =========================
    // ADICIONAR XP
    // =========================

    public void adicionarXp(float quantidade)
    {
        float xpAtual = getXp();

        int nivelAtual = getNivel();

        xpAtual += quantidade;

        // Verifica passagem de nível
        while (xpAtual >= XP_MAXIMO)
        {
            xpAtual -= XP_MAXIMO;

            nivelAtual++;
        }

        // Salva
        prefs.edit()
                .putFloat(KEY_XP, xpAtual)
                .putInt(KEY_NIVEL, nivelAtual)
                .apply();
    }

    // =========================
    // RESETAR XP
    // =========================

    public void resetar()
    {
        prefs.edit()
                .putFloat(KEY_XP, 0)
                .putInt(KEY_NIVEL, 1)
                .apply();
    }
}