package com.adailtonsilva.salaoray.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutenticado {

    //Classe criada pra verificar se o usuario ja esta logado
    public static FirebaseUser usuarioLogado(){
        FirebaseAuth usuario = ConfiguraBD.FirebaseAutenticacao();
        return usuario.getCurrentUser();
    }
}
