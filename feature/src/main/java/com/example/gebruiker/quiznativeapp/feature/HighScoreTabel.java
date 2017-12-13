/*
*Scherm met highscore tabel per gekozen type/categorie
 */
package com.example.gebruiker.quiznativeapp.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HighScoreTabel extends AppCompatActivity {
    private TableLayout ScoreTabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_tabel);

        //Toolbar instellen met back-button
        Toolbar toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HighScoreTabel.this, Highscore.class));
            }
        });

        //Tabel maken
        getDataFromJsonArray(Highscore.Scores);
    }

    //Haalt alle JSONObjecten uit de JSONArray en geeft ze door aan addRow()
    private void getDataFromJsonArray(JSONArray jsonArray)
    {

        for(Integer i = 0; i < jsonArray.length(); i++){

            try {
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                addRow(jsonObject, i + 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Voegt een rij toe
    private void addRow(JSONObject jsonObject, Integer rank)
    {
        ScoreTabel = findViewById(R.id.ScoreTabel);
        TableRow tableRow = new TableRow(this);
        TextView[] Kolommen = new TextView[6];

        Kolommen[0] = new TextView(this);
        Kolommen[0].setText(rank.toString());
        Kolommen[1] = new TextView(this);
        Kolommen[2] = new TextView(this);
        Kolommen[3] = new TextView(this);
        Kolommen[4] = new TextView(this);
        Kolommen[5] = new TextView(this);

        try {
            Kolommen[1].setText(jsonObject.getString("username"));
            Kolommen[2].setText(jsonObject.getString("Categorie_naam"));
            Kolommen[3].setText(jsonObject.getString("Score"));
            Kolommen[4].setText(jsonObject.getString("Datum"));
            Kolommen[5].setText(jsonObject.getString("Locatie"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int Kolom = 0; Kolom < Kolommen.length ; Kolom++) {
            Kolommen[Kolom].setTextSize(2, 18);
            tableRow.addView(Kolommen[Kolom]);
        }

        ScoreTabel.addView(tableRow);

    }

}
