package br.edu.uea.estsiv031.chatuea.helper;


import android.util.Base64;

/**
 * Created by rsouza on 17/10/17.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodificarBase64(String textoCodificado){
        return new String (Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
