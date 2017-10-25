package br.edu.uea.estsiv031.chatuea.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.edu.uea.estsiv031.chatuea.R;
import br.edu.uea.estsiv031.chatuea.adapter.ContatoAdapter;
import br.edu.uea.estsiv031.chatuea.adapter.ConversaActivity;
import br.edu.uea.estsiv031.chatuea.config.ConfiguracaoFirebase;
import br.edu.uea.estsiv031.chatuea.helper.Preferencias;
import br.edu.uea.estsiv031.chatuea.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);

        adapter = new ContatoAdapter(getActivity(), contatos);

        listView.setAdapter(adapter);

        //recuperando contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String idetificadorUsuarioLogado = preferencias.getIdentificador();

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("contatos")
                    .child( idetificadorUsuarioLogado );

        //Listener para recuperar contatos

        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //limpar lista
                contatos.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(position);

                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                intent.putExtra("e", contato.getE());
                intent.putExtra("n", contato.getN());

                startActivity(intent);

            }
        });

        return view;
    }

}
