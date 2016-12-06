package com.example.ahmed.miniprojet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    Button aj,rech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         aj=(Button) findViewById(R.id.btnEdit);
        rech=(Button) findViewById(R.id.btnrechtrajet);


    }
    public void proposertrajet(View view) {
        Intent i= new Intent(this,ProposerTrajet.class);
        startActivity(i);

    }

    public void recherchetrajet(View view) {
        Intent a= new Intent(this,TrouverTrajet.class);
        startActivity(a);
    }

}
