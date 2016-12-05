package com.example.ahmed.miniprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void propsertrajet(View view) {
        Intent i= new Intent(this,ProposerTrajet.class);
        startActivity(i);

    }

    public void recherchetrajet(View view) {
        Intent a= new Intent(this,TrouverTrajet.class);
        startActivity(a);
    }

}
