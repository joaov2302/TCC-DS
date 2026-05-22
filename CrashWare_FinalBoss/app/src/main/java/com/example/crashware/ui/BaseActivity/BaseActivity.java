package com.example.crashware.ui.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crashware.ui.api.Auth;

public class BaseActivity extends AppCompatActivity {

    protected SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SharedPreferences
        prefs = this.getSharedPreferences("CrashWare", Context.MODE_PRIVATE);

        Auth.verificarToken(this, prefs, true, null);
    }
}
