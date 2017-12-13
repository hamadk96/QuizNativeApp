package com.example.gebruiker.quiznativeapp.feature;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Profiel extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView ProfielTitel;

    private EditText NieuweGebruikersnaam;
    private Button VeranderGebruikersnaam;

    private EditText OudePasswoord;
    private EditText NieuwePasswoord;
    private EditText NieuwePasswoord2;
    private Button VeranderPasswoord;

    private final String VeranderGebruikersnaamURL = "http://inifruits.be/php/veranderUsername.php";
    private final String VeranderPasswoordURL = "http://inifruits.be/php/veranderPasswoord.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);
        profielActivity();
    }

    private void profielActivity()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        setTootlbar();

        ProfielTitel = findViewById(R.id.lblTitelProfiel);

        NieuweGebruikersnaam = findViewById(R.id.txtNieuwGebruikersnaam);
        NieuweGebruikersnaam.setHint("Nieuwe Gebruikersnaam");

        VeranderGebruikersnaam = findViewById(R.id.btnVeranderUsername);
        gebruikersnaamVeranderen(VeranderGebruikersnaam, NieuweGebruikersnaam, requestQueue);

        OudePasswoord = findViewById(R.id.txtOudePasswoord);
        OudePasswoord.setHint("Oude passwoord");
        NieuwePasswoord = findViewById(R.id.txtNieuwePasswoord);
        NieuwePasswoord.setHint("Nieuwe passwoord");
        NieuwePasswoord2 = findViewById(R.id.txtNieuwePasswoord2);
        NieuwePasswoord2.setHint("Nieuwe passwoord");
        VeranderPasswoord = findViewById(R.id.btnVeranderPasswoord);
        passwoordVeranderen(VeranderPasswoord,
                OudePasswoord,
                NieuwePasswoord,
                NieuwePasswoord2,
                requestQueue
                );
    }

    private void setTootlbar()
    {
        toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profiel.this, LoggedIn.class));
            }
        });
    }

    private void gebruikersnaamVeranderen(final Button veranderGebruikersnaam, final EditText nieuweGebruikersnaam, final RequestQueue requestQueue)
    {
        veranderGebruikersnaam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(nieuweGebruikersnaam.getText().length() < 5)) {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            VeranderGebruikersnaamURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    alertDialog(veranderGebruikersnaam,false, response.charAt(0));
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertDialog(veranderGebruikersnaam,true, '0');
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("oudeUsername", LoggedIn.Gebruiker);
                            params.put("nieuweUsername", nieuweGebruikersnaam.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else
                {
                    alertDialog(veranderGebruikersnaam,false,'4');
                }
            }
        });
    }

    private void passwoordVeranderen(final Button veranderPasswoord, final EditText oudePasswoord, final EditText nieuwePasswoord, final EditText nieuwePasswoord2, final RequestQueue requestQueue)
    {
        veranderPasswoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oudePassEqualsNieuwePass(nieuwePasswoord,nieuwePasswoord2) && !(nieuwePasswoord.getText().length() < 5)) {

                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            VeranderPasswoordURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    alertDialog(veranderPasswoord,false, response.charAt(0));
                                    Log.i("request uitgevoerd", "Mag niet");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            alertDialog(veranderPasswoord,true, '0');
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("user", LoggedIn.Gebruiker);
                            params.put("oudePasswoord", oudePasswoord.getText().toString());
                            params.put("nieuwePasswoord", nieuwePasswoord.getText().toString());

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else
                {
                    if(!(nieuwePasswoord.getText().length() < 5)) {
                        alertDialog(veranderPasswoord,true, '0');

                    }
                    else
                    {
                        alertDialog(veranderPasswoord,false, '4');
                    }
                }
            }

        });
    }

    private Boolean oudePassEqualsNieuwePass(final EditText nieuwePasswoord, final EditText nieuwePasswoord2)
    {
        String val1 = nieuwePasswoord.getText().toString();
        String val2 = nieuwePasswoord2.getText().toString();
        if(val2.equals(val1)) { return true; }
        else { return false; }
    }


    private void alertDialog(Button welkeButton,Boolean isError, char response)
    {
        if(isError) {
            AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this);
            AlertDialog.setTitle(welkeButton.getId() == R.id.btnVeranderPasswoord ? "" : "Oops!");
            AlertDialog.setMessage(
                    welkeButton.getId() == R.id.btnVeranderPasswoord ? "Wachtwoorden komen niet overeen": "Er is een probleem met de connectie"
            );
            AlertDialog.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }
            );
            AlertDialog showDialog = AlertDialog.create();
            showDialog.show();
        }
        else
        {
            alertDialog(welkeButton,response);
        }
    }

    private void alertDialog(Button welkeButton,char response)
    {
        AlertDialog.Builder AlertDialog;
        AlertDialog showDialog;

        switch (response) {
            case '1':
                AlertDialog = new AlertDialog.Builder(this);
                AlertDialog.setMessage((welkeButton.getId() == R.id.btnVeranderPasswoord ? "Passwoord" : "Gebruikersnaam") + " is veranderd.");
                AlertDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
                showDialog = AlertDialog.create();
                showDialog.show();
                break;
            case '2':
                AlertDialog = new AlertDialog.Builder(this);
                AlertDialog.setMessage("Verkeerde gebruikersnaam");
                AlertDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
                showDialog = AlertDialog.create();
                showDialog.show();
                break;
            case '3':
                AlertDialog = new AlertDialog.Builder(this);
                AlertDialog.setMessage("Verkeerdde wachtwoord");
                AlertDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
                showDialog = AlertDialog.create();
                showDialog.show();
                break;
            case '4':
                AlertDialog = new AlertDialog.Builder(this);
                AlertDialog.setMessage((welkeButton.getId() == R.id.btnVeranderUsername ? "Gebruikersnaam " : "Wachtwoord ")
                                        + "moet minsten 5 karakters zijn");
                AlertDialog.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
                showDialog = AlertDialog.create();
                showDialog.show();
                break;

        }


    }



}
