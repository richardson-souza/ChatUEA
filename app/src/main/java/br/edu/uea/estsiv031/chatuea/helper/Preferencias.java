package br.edu.uea.estsiv031.chatuea.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by rsouza on 15/10/17.
 */

public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "ueachat.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_PRIVADA = "chavePrivada";
    private final String CHAVE_PUBLICA = "chavePublica";
    private final String CHAVE_N = "chaveN";



    public Preferencias( Context contextoParametro ){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados( String identificadorUsuario, String privada, String publica,  String n ){

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_PRIVADA, privada);
        editor.putString(CHAVE_PUBLICA, publica);
        editor.putString(CHAVE_N, n);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }

    public String getChavePrivada(){
        return preferences.getString(CHAVE_PRIVADA,null);
    }

    public String getN(){
        return preferences.getString(CHAVE_N,null);
    }

}
