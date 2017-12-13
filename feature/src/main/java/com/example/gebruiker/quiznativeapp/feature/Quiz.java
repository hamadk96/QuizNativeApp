package com.example.gebruiker.quiznativeapp.feature;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private String gebruikteVragen = "random";
    private Integer VraagTeller = 0;
    private String JuistAntwoord = "";
    private Integer Score = 0;
    private Button VolgendeVraag;
    private TextView VraagNummer;
    private TextView Vraag;
    private RadioButton Keuze1;
    private RadioButton Keuze2;
    private RadioButton Keuze3;
    private RadioButton Keuze4;
    private String getVraagURL = "http://inifruits.be/php/quizVraag.php";
    private String SendScoreURL = "http://inifruits.be/php/sendScore.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        VolgendeVraag = findViewById(R.id.btnVolgendeVraag);
        VraagNummer = findViewById(R.id.lblVraagCount);
        Vraag = findViewById(R.id.lblVraag);
        Keuze1 = findViewById(R.id.rbnKeuze1);
        Keuze2 = findViewById(R.id.rbnKeuze2);
        Keuze3 = findViewById(R.id.rbnKeuze3);
        Keuze4 = findViewById(R.id.rbnKeuze4);

        Start();
        radioButtons();

        //onClickListener voor button
        VolgendeVraag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                volgendeVraag(requestQueue);
            }
        });

        Toolbar toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog("Opgelet!","Weet je zeker dat je wilt stoppen?");
            }
        });
    }

    //Methode om naar volgende vraag te gaan
    private void volgendeVraag(final RequestQueue requestQueue)
    {
        RadioButton[] keuze = new RadioButton[4];
        keuze[0] = Keuze1;
        keuze[1] = Keuze2;
        keuze[2] = Keuze3;
        keuze[3] = Keuze4;



        for(RadioButton b : keuze){
            String GekozenAntwoord = b.getText().toString();

            if(GekozenAntwoord.equals(JuistAntwoord) && b.isChecked()){
                Score++;
                Log.i("Juist", JuistAntwoord);
            }
        }


        if(VraagTeller == 0){

            Keuze1.setVisibility(View.VISIBLE);
            Keuze2.setVisibility(View.VISIBLE);
            Keuze3.setVisibility(View.VISIBLE);
            Keuze4.setVisibility(View.VISIBLE);
            VolgendeVraag.setBackgroundColor(ContextCompat.getColor(this,R.color.black));
            VolgendeVraag.setText("Volgendre vraag");

            getQuestion(requestQueue);
        }else{
            if(VraagTeller < 15){
                if(VraagTeller == 14){
                    VolgendeVraag.setBackgroundColor(ContextCompat.getColor(this,R.color.red));
                    VolgendeVraag.setText("Einde");

                }

                getQuestion(requestQueue);
            }
            else{
                sendScore(requestQueue);
                alertDialog(false);
            }
        }

    }

    //Vraag en bijhorende antwoorden opvragen bij de server
    private void getQuestion(final RequestQueue requestQueue)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getVraagURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> responeSplit = Arrays.asList(response.split(";"));
                        gebruikteVragen += ";" + responeSplit.get(0);
                        ++VraagTeller;
                        VraagNummer.setText("Vraag " + VraagTeller.toString());
                        Vraag.setText(responeSplit.get(0));
                        Keuze1.setText(responeSplit.get(1));
                        Keuze2.setText(responeSplit.get(2));
                        Keuze3.setText(responeSplit.get(3));
                        Keuze4.setText(responeSplit.get(4));
                        JuistAntwoord = responeSplit.get(5);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog(true);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vorigeVragen", gebruikteVragen);
                params.put("categorie", Categorie.Categorie);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Zorgt dat als één radioButton geslecteerd is de andere niet geslecteerd zijn
    private void radioButtons()
    {
        Keuze1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keuze2.setChecked(false);
                Keuze3.setChecked(false);
                Keuze4.setChecked(false);
            }
        });
        Keuze2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keuze1.setChecked(false);
                Keuze3.setChecked(false);
                Keuze4.setChecked(false);
            }
        });
        Keuze3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keuze1.setChecked(false);
                Keuze2.setChecked(false);
                Keuze4.setChecked(false);
            }
        });
        Keuze4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Keuze1.setChecked(false);
                Keuze2.setChecked(false);
                Keuze3.setChecked(false);
            }
        });
    }

    //Dialoogscherm pakt één parameter
    //parameter wordt gebruikt om te zien of het voor een error is
    //Als het false is wordt de dialoogscherm gebruikt om de score te laten op het einde
    private void alertDialog(Boolean isError)
    {
        AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this);
        AlertDialog.setTitle(isError ? "Oops" : "Score!");
        AlertDialog.setMessage(isError ? "Er is een probleem met de connectie." : "Je score: "+ Score + "/15");
        AlertDialog.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Quiz.this, LoggedIn.class));
                    }
                }
        );
        AlertDialog showDialog = AlertDialog.create();
        showDialog.show();
    }

    private void Start()
    {
        VolgendeVraag.setBackgroundColor(ContextCompat.getColor(this,R.color.green ));
        VolgendeVraag.setTextColor(ContextCompat.getColor(this,R.color.textColor));
        Keuze1.setVisibility(View.GONE);
        Keuze2.setVisibility(View.GONE);
        Keuze3.setVisibility(View.GONE);
        Keuze4.setVisibility(View.GONE);
    }

    //Methode van de back button op je smartphone
    @Override
    public void onBackPressed() {


        alertDialog("Opgelet!","Weet je zeker dat je wilt stoppen?");
    }

    //Dialoogscherm om te vragen of gebruiker wilt stoppen met spelen?
    //Gebruikt in onBackPressed() en back button in de actionbar
    private void alertDialog(String title, String message)
    {
        AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this);
        AlertDialog.setTitle(title);
        AlertDialog.setMessage(message);
        AlertDialog.setPositiveButton(
                "Ja",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Score = 0;
                        gebruikteVragen = " ";
                        VraagTeller = 0;
                        startActivity(new Intent(Quiz.this, LoggedIn.class));

                    }
                }
        );
        AlertDialog.setNegativeButton(
                "Nee",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }
        );
        AlertDialog showDialog = AlertDialog.create();
        showDialog.show();
    }

    private void sendScore(final RequestQueue requestQueue)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                SendScoreURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog(true);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("score", Score.toString());
                params.put("username",LoggedIn.Gebruiker.toLowerCase());
                params.put("categorie",Categorie.Categorie);
                params.put("locatie","undefined");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
