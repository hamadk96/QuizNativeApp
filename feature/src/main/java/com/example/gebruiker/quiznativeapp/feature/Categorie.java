/*
* User kiest categorie van zijn spel
 */
package com.example.gebruiker.quiznativeapp.feature;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;




public class Categorie extends AppCompatActivity {

    private Button[] Categoriën;
    public static String Categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        initCategorieArray();

        //Toolbar instellen met back-button
        Toolbar toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Categorie.this, LoggedIn.class));
            }
        });




    }



    private void initCategorieArray()
    {
        Categoriën = new Button[5];
        Categoriën[0] = findViewById(R.id.btn1);
        Categoriën[1] = findViewById(R.id.btn2);
        Categoriën[2] = findViewById(R.id.btn3);
        Categoriën[3] = findViewById(R.id.btn4);
        Categoriën[4] = findViewById(R.id.btn5);

        onClickListeners();
    }

    //OnClickListeneners van alle buttons
    private void onClickListeners()
    {
        Categoriën[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categorie = Categoriën[0].getText().toString();
                startActivity(new Intent(Categorie.this, Quiz.class));
            }
        });

        Categoriën[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categorie = Categoriën[1].getText().toString();
                startActivity(new Intent(Categorie.this, Quiz.class));
            }
        });

        Categoriën[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categorie = Categoriën[2].getText().toString();
                startActivity(new Intent(Categorie.this, Quiz.class));
            }
        });

        Categoriën[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categorie = Categoriën[3].getText().toString();
                startActivity(new Intent(Categorie.this, Quiz.class));
            }
        });

        Categoriën[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categorie = Categoriën[4].getText().toString();
                startActivity(new Intent(Categorie.this, Quiz.class));
            }
        });
    }

}
