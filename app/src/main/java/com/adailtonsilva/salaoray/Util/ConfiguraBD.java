package com.adailtonsilva.salaoray.Util;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguraBD {
    private static FirebaseAuth auth;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public static FirebaseAuth FirebaseAutenticacao(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

}
