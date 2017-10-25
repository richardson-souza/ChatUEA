package br.edu.uea.estsiv031.chatuea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.uea.estsiv031.chatuea.R;
import br.edu.uea.estsiv031.chatuea.helper.Preferencias;
import br.edu.uea.estsiv031.chatuea.helper.RSA;
import br.edu.uea.estsiv031.chatuea.model.Mensagem;

/**
 * Created by rsouza on 25/10/17.
 */

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //verifica se a lista esta preenchida
        if(mensagens != null){

            //RSA
            RSA rsa = new RSA();

            //recuperar dados do usuário remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();
            String chavePivada = preferencias.getChavePrivada();
            String n = preferencias.getN();


            //inicializa o objeto para a montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recuperar mensagem
            Mensagem mensagem = mensagens.get( position );

            //monsta viwe a partir de um xml
            if(idUsuarioRemetente.equals(mensagem.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //recuperando elemento para exibição
            TextView textoMensagem = (TextView) view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText( mensagem.getMensagem() + "\n" + mensagem.getMensagemCifrada());
            if(mensagem.getIdUsuario().equals(idUsuarioRemetente)){
                textoMensagem.setText( mensagem.getMensagem());
            }else{
                //textoMensagem.setText( mensagem.getMensagemCifrada());
                textoMensagem.setText( rsa.decriptar(chavePivada,n,mensagem.getMensagemCifrada()) );
            }
            //textoMensagem.setText(mensagem.getMensagemCifrada());

        }


        return view;
    }
}
