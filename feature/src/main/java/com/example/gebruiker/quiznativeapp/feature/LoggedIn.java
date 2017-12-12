/*Klasse van Homepagina van de gebruiker.
Kan Opties van zijn account zien en een spel beginnen.
Verbonden met activity_logged_in.xml
Heeft een OptieLijst menu dat initieel onzichtbaar/gone is.
*/

package com.example.gebruiker.quiznativeapp.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LoggedIn extends AppCompatActivity {

    Button OptiesButton;
    Button SpelenButton;
    Button ProfielButton;
    Button HighscoresButton;
    Button LogUitButton;
    View LoggedView;
    RelativeLayout OptieLijst;
    boolean IsButtonOptiesClicked = false;
    public static String Gebruiker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        //Clickevent van de "Opties" Button
        OptiesButton = findViewById(R.id.btnOpties);
        OptiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OptieLijst = findViewById(R.id.frgOptieLijst);
                if(IsButtonOptiesClicked == true) {
                    OptieLijst.setVisibility(View.GONE);
                    IsButtonOptiesClicked = false;
                }
                else{
                    OptieLijst.setVisibility(View.VISIBLE);
                    IsButtonOptiesClicked = true;
                }
            }
        });

        //Click event van de "Spelen" button
        SpelenButton = findViewById(R.id.btnSpelen);
        SpelenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedIn.this, Categorie.class));
            }
        });

        //Clickevent voor de hele view
        LoggedView = findViewById(R.id.LLoggedIn);
        LoggedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptieLijst = findViewById(R.id.frgOptieLijst);
                OptieLijst.setVisibility(View.GONE);
            }
        });

        //Click event van de "Profiel" button in de Optielijst menu
        ProfielButton = findViewById(R.id.btnProfiel);
        ProfielButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedIn.this, Profiel.class));
            }
        });

        //Click event van de "Highscore" button in de Optielijst menu
        HighscoresButton = findViewById(R.id.btnHighscores);
        HighscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedIn.this,Highscore.class));
            }
        });

        //Click event van de "Log uit" button in de Optielijst menu
        LogUitButton = findViewById(R.id.btnUitLoggen);
        LogUitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedIn.this,MainActivity.class));
            }
        });
    }
}
