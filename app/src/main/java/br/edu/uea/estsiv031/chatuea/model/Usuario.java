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
    private RSA chave;
    private String e;
    private String n;


    public Usuario() {
        RSA rsa = new RSA();
        rsa.gerarChaves();
        this.chave = rsa;
        this.e = chave.getE().toString();
        this.n = chave.getN().toString();
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

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public RSA getChave() {
        return chave;
    }

    public void setChave(RSA chave) {
        this.chave = chave;
    }
}
