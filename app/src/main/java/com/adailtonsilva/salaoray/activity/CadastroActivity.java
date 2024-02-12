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
        inicializar();
    }

    public void inicializar(){
        campoNome = findViewById(com.adailtonsilva.salaoray.R.id.txtNome);
        campoTelefone = findViewById(com.adailtonsilva.salaoray.R.id.txtTelefone);
        campoEmail = findViewById(com.adailtonsilva.salaoray.R.id.txtEmail);
        campoSenha = findViewById(com.adailtonsilva.salaoray.R.id.txtSenha);
        botaoCadastrar = findViewById(com.adailtonsilva.salaoray.R.id.btnCadastrar);

    }
    public void validarCampos(View view){
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!nome.isEmpty()){
            if(!telefone.isEmpty()){
                if(!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        usuario = new Usuario();

                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        //cadastrar
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
        autenticacao = ConfiguraBD.FirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao Cadastrar usuario", Toast.LENGTH_SHORT).show();
                }else{
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