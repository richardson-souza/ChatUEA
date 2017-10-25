package br.edu.uea.estsiv031.chatuea.model;

/**
 * Created by rsouza on 19/10/17.
 */

public class Mensagem {

    private String idUsuario;
    private String mensagem;

    public Mensagem() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
