/*
* Highscorescherm, user kan de highscores zien per type of categorie.
*
*/
package com.example.gebruiker.quiznativeapp.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Highscore extends AppCompatActivity {
    private String HighscoreURL = "http://inifruits.be/php/getHighScores.php";
    //Word geïnitializeerd door de parseString(String respone) methode
    //en wordt gebruikt door HighScoreTabel.java
    public static JSONArray Scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //start queue
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        findUIElements(requestQueue);

        //toolbar instellen met een back-button
        Toolbar toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Highscore.this, LoggedIn.class));
            }
        });



    }

    //onClickListeners voor alle buttons
    private void findUIElements(final RequestQueue requestQueue)
    {
        Button Top10 = findViewById(R.id.btnTop10);
        clickListeners(Top10, 1, requestQueue);

        Button Top100 = findViewById(R.id.btnTop100);
        clickListeners(Top100, 2,requestQueue);

        Button MijnTop100 = findViewById(R.id.btnMijnTop10);
        clickListeners(MijnTop100, 3,requestQueue);

        Button AlMijnScores = findViewById(R.id.btnAlMijnScores);
        clickListeners(AlMijnScores, 4,requestQueue);

        Button MeestRecente = findViewById(R.id.btnMeestRecente);
        clickListeners(MeestRecente, 5,requestQueue);

        Button Algemeen = findViewById(R.id.btnAlgemeen);
        clickListeners(Algemeen, 6,requestQueue);

        Button AA = findViewById(R.id.btnAA);
        clickListeners(AA, 7,requestQueue);

        Button Geschiedenis = findViewById(R.id.btnGeschiedenis);
        clickListeners(Geschiedenis, 8,requestQueue);

        Button Muziek = findViewById(R.id.btnMuziek);
        clickListeners(Muziek, 9,requestQueue);

        Button WetEnNatuur = findViewById(R.id.btnWetEnNatuur);
        clickListeners(WetEnNatuur, 10,requestQueue);
    }

    //onClickListener voor alle buttons
    private void clickListeners(final Button button, final Integer type, final RequestQueue requestQueue)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHighScore(type,requestQueue);
            }
        });
    }

    //Ophalen gegevens voor de score van de server met Volley
    //De Integer parameter is om aan te duiden op welke type/categorie te ranken
    private void getHighScore(final Integer _type, final RequestQueue requestQueue)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                HighscoreURL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Succes", response.toString());
                        parseString(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Fail", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("soortHighscore", _type.toString());
                params.put("username", LoggedIn.Gebruiker);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Convert String respone van de server naar een JSONArry
    private void parseString(String response)
    {
        try {
            Scores = new JSONArray(response);
            startActivity(new Intent(this, HighScoreTabel.class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
