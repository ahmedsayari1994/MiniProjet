package com.example.ahmed.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProposerTrajet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposer_trajet);
    }

    public void annulerTrajet(View view) {
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
