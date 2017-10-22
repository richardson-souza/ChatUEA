package br.edu.uea.estsiv031.chatuea.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.edu.uea.estsiv031.chatuea.R;
import br.edu.uea.estsiv031.chatuea.config.ConfiguracaoFirebase;
import br.edu.uea.estsiv031.chatuea.helper.Base64Custom;
import br.edu.uea.estsiv031.chatuea.helper.Preferencias;
import br.edu.uea.estsiv031.chatuea.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.edit_login_email);
        senha = (EditText) findViewById(R.id.edit_login_senha);
        botaoLogar = (Button) findViewById(R.id.bt_logar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email.getText().toString();
                String s = senha.getText().toString();

                if(e.isEmpty() || s.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campo!", Toast.LENGTH_LONG).show();
                }else {
                    usuario = new Usuario();
                    usuario.setEmail(e);
                    usuario.setSenha(s);
                    validarLogin();

                }
            }
        });

    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private  void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.salvarDados(identificadorUsuario,usuario.getD(),usuario.getE(),usuario.getN());

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer Login!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Erro ao fazer Login!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity( intent );

    }
}
