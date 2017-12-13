package com.example.gebruiker.quiznativeapp.feature;



import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity{

    private Button Login;
    private Button Registreren;
    private Button Bezoeker;
    private EditText Gebruikersnaam;
    private EditText Passwoord;
    private TextView Error;
    private String loginURL = "http://inifruits.be/php/Login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Gebruikersnaam = findViewById(R.id.txtGebruikersNaam);
        Gebruikersnaam.setHint("Gebruikersnaam");

        Passwoord = findViewById(R.id.txtPasswoord);
        Passwoord.setHint("Passwoord");

        Error = findViewById(R.id.lblError);

        Toolbar toolbar = findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Clickevent voor de "Login" button
        Login = findViewById(R.id.btnLogin);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Succes", response);

                                switch(response.charAt(0)){
                                    case '1':
                                        LoggedIn.Gebruiker = Gebruikersnaam.getText().toString();
                                        startActivity(new Intent(MainActivity.this, LoggedIn.class));
                                        break;
                                    case '2':
                                        Error.setText("Verkeerd gebruikersnaam!");
                                        break;
                                    case '3':
                                        Error.setText("Verkeerd Passwoord!");
                                        break;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog();
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", Gebruikersnaam.getText().toString().toLowerCase());
                        params.put("password", Passwoord.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        //Click event voor de Registreren button
        Registreren = findViewById(R.id.btnRegistreren);
        Registreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registratie.class));
            }
        });

        //Click event voor de "Bezoeker" button
        Bezoeker = findViewById(R.id.btnBezoeker);
        Bezoeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void alertDialog()
    {
    android.support.v7.app.AlertDialog.Builder AlertDialog = new AlertDialog.Builder(this);
        AlertDialog.setTitle("Oops");
        AlertDialog.setMessage("Er is een probleem met de connectie.");
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
}
