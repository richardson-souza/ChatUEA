package br.edu.uea.estsiv031.chatuea.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.edu.uea.estsiv031.chatuea.config.ConfiguracaoFirebase;
import br.edu.uea.estsiv031.chatuea.helper.RSA;

/**
 * Created by rsouza on 15/10/17.
 */

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String e;
    private String d;
    private String n;
    private RSA rsa;


    public Usuario() {
        rsa = new RSA();
        rsa.gerarChaves();
        this.e = rsa.getE().toString();
        this.d = rsa.getD().toString();
        this.n = rsa.getN().toString();
    }

    public void salvar() {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child(getId()).setValue(this);

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Exclude
    public String getD() {
        return d;
    }

    public void setD(String e) {
        this.d = d;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

}
