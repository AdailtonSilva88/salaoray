package com.adailtonsilva.salaoray.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adailtonsilva.salaoray.R;
import com.adailtonsilva.salaoray.Util.ConfiguraBD;
import com.adailtonsilva.salaoray.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText campoEmail,campoSenha;
    Button botaoAcessar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicia a conexão com o Firebase pra verificar se ja esta logado
        auth = ConfiguraBD.FirebaseAutenticacao();
        inicializarComponentes();
    }
    //Override deleta a config padrão do onStart
    @Override
    protected void onStart() {
        super.onStart();
        //Pega o usuario atual e verifica se ja esta cadastrado,caso sim ele loga automatico
        FirebaseUser usuarioAuth = auth.getCurrentUser();
        if(usuarioAuth != null){
            abrirHome();
        }
    }

    //Carrega os dados da tela para as variaveis
    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.txtEmailLogin);
        campoSenha = findViewById(R.id.txtSenhaLogin);
        botaoAcessar = findViewById(R.id.btnAcessar);
    }

    //Acionado pelo botao
    public void validarAutenticacao(View view){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        //Verifica se campos vazios
        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);
                //Caso tudo certo chama o método logar
                logar(usuario);

            }else{
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }

    }

    private void logar(Usuario usuario) {
        //Faz o login padrao com Email e Senha
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Caso sucesso abre a Home
                if(task.isSuccessful()){
                    abrirHome();
                }else{
                    //Tratamento de possiveis erros
                    String excecao = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuario não está cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou senha invalido";
                    }catch (Exception e){
                        excecao = "Erro ao logar o usuario" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Chama as Tela Home
    private void abrirHome() {
        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(i);
    }

    //Chama a tela Cadastrar
    public void cadastrar(View v){
        Intent i = new Intent(this,CadastroActivity.class);
        startActivity(i);
    }






}