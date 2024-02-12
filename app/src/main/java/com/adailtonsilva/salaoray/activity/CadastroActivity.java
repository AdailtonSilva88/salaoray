package com.adailtonsilva.salaoray.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adailtonsilva.salaoray.Util.ConfiguraBD;
import com.adailtonsilva.salaoray.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autenticacao;
    public EditText campoNome,campoTelefone,campoEmail,campoSenha;
    Button botaoCadastrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.adailtonsilva.salaoray.R.layout.activity_cadastro);

        //chamada do metódo inicializar
        inicializar();
    }

    public void inicializar(){

        //Carregando campos da tela para variaveis
        campoNome = findViewById(com.adailtonsilva.salaoray.R.id.txtNome);
        campoTelefone = findViewById(com.adailtonsilva.salaoray.R.id.txtTelefone);
        campoEmail = findViewById(com.adailtonsilva.salaoray.R.id.txtEmail);
        campoSenha = findViewById(com.adailtonsilva.salaoray.R.id.txtSenha);
        botaoCadastrar = findViewById(com.adailtonsilva.salaoray.R.id.btnCadastrar);

    }
    public void validarCampos(View view){

        //Convertendo campos em String
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        //Verificando se campos vazios e Criada a exceção para cada caso
        if(!nome.isEmpty()){
            if(!telefone.isEmpty()){
                if(!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        //Passou da verificação então carrega os dados na classe usuario
                        usuario = new Usuario();

                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        //Chama o metodo cadastrar usuario
                        cadastrarUsuario();

                    } else {
                        Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Preencha o Email", Toast.LENGTH_SHORT).show();
                }
                
            }else{
                Toast.makeText(this, "Preencha o telefone", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }

    }

    private void cadastrarUsuario(){

        //Faz a comunicação com o Firebase pelo controle de acesso do proprio Firebase de Email e senha

        autenticacao = ConfiguraBD.FirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Verifica se todos os passos anteriores deram certo e retorna o aviso na tela
                    Toast.makeText(CadastroActivity.this, "Sucesso ao Cadastrar usuario", Toast.LENGTH_SHORT).show();
                }else{
                    //Coleta a excecao de acordo com o erro
                    String excecao = "";
                    try{
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um email valido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Usuario ja cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuario "+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}