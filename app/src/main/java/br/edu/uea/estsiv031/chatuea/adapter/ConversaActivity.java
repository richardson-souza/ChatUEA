package br.edu.uea.estsiv031.chatuea.adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.edu.uea.estsiv031.chatuea.R;
import br.edu.uea.estsiv031.chatuea.config.ConfiguracaoFirebase;
import br.edu.uea.estsiv031.chatuea.helper.Base64Custom;
import br.edu.uea.estsiv031.chatuea.helper.Preferencias;
import br.edu.uea.estsiv031.chatuea.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private ListView listView;
    private ArrayList<String> mensagens;
    private ArrayAdapter adapter;
    private ValueEventListener valueEventListenerMensagem;

    private DatabaseReference firebase;

    //dados Destinatário
    private String nomeUsuarioDestinatario;
    private String idlUsuarioDestinatario;
    private String eUsuarioDestinatario;
    private String nUsuarioDestinatario;

    //dados remetente
    private String idUsuarioRemetente;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listView = (ListView) findViewById(R.id.lv_conversas);

        //dados do ususário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();

        Bundle extra = getIntent().getExtras();

        if( extra != null ){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            //eUsuarioDestinatario = extra.getString("e");
            //nUsuarioDestinatario = extra.getString("n");

            idlUsuarioDestinatario = Base64Custom.codificarBase64( emailDestinatario );
        }

        //configura toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        //Mosnta listView e adapter
        mensagens = new ArrayList<>();
        adapter = new ArrayAdapter(
                ConversaActivity.this,
                android.R.layout.simple_list_item_1,
                mensagens
        );

        listView.setAdapter(adapter);

        //Recuperar mensagens do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child( idUsuarioRemetente )
                .child( idlUsuarioDestinatario );

        //Criar Listener de mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar mensagens
                mensagens.clear();

                //recuperar mensagens
                for ( DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue( Mensagem.class );
                    mensagens.add( mensagem.getMensagem() );
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );

        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoMensagem = editMensagem.getText().toString();

                if(textoMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite uma mensagem para enviar", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario( idUsuarioRemetente );
                    mensagem.setMensagem( textoMensagem );

                    salvarMensagem(idUsuarioRemetente, idlUsuarioDestinatario, mensagem);
                    editMensagem.setText("");

                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try {

            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");
            firebase.child( idRemetente )
                    .child( idDestinatario )
                    .push()
                    .setValue( mensagem );

            return true;

        }catch ( Exception e ){
            e.printStackTrace();
            return false;

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
