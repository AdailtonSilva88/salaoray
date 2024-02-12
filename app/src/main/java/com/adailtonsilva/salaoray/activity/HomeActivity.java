package com.adailtonsilva.salaoray.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.adailtonsilva.salaoray.R;
import com.adailtonsilva.salaoray.Util.ConfiguraBD;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConfiguraBD.FirebaseAutenticacao();
    }

    public void deslogar(View view){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            Toast.makeText(this, "Algo deu erro"+ e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}