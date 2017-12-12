package com.example.gebruiker.quiznativeapp.feature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Registratie extends AppCompatActivity {

    EditText Passwoord;
    Button Registreren;
    EditText Gebruikersnaam;
    TextView Error;
    TextView Succes;
    final String loginURL = "http://inifruits.be/php/createAccount.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registratie);

        Gebruikersnaam = findViewById(R.id.btnRegGebruikersnaam);
        Error = findViewById(R.id.lblErrorReg);
        Succes = findViewById(R.id.lblSucces);

        Passwoord = findViewById(R.id.btnRegPasswoord);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        Registreren = findViewById(R.id.btnVerzenden);
        Registreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Succes", response);
                        if(Gebruikersnaam.getText().length() < 5 || Passwoord.getText().length() < 5)
                        {
                            Error.setText("Gebruikersnaam en passwoord moeten minstens 5 karakters zijn.");
                        }
                        else {
                            switch (response.charAt(0)) {
                                case '1':
                                    Succes.setText("Account gemaakt!");
                                    startActivity(new Intent(Registratie.this, MainActivity.class));
                                    break;
                                case '2':
                                    Error.setText("Gebruikersnaam bestaat al!");
                                    break;

                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Fail", error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", Gebruikersnaam.getText().toString().toLowerCase());
                        params.put("password", Passwoord.getText().toString());
                        return params;
                    }
                };
                if(!(Gebruikersnaam.getText().length() < 5 || Passwoord.getText().length() < 5)) {
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
}

