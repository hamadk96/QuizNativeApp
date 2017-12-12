package com.example.gebruiker.quiznativeapp.feature;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private String gebruikteVragen = "";
    private int VraagTeller = 0;
    private String JuistAntwoord = "";
    private int Score = 0;
    private Button VolgendeVraag;
    private TextView VraagNummer;
    private TextView Vraag;
    private RadioButton Keuze1;
    private RadioButton Keuze2;
    private RadioButton Keuze3;
    private RadioButton Keuze4;
    private String getVraagURL = "http://inifruits.be/php/quizVraag.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        AndroidNetworking.initialize(getApplicationContext());
        //final RequestQueue requestVraag = Volley.newRequestQueue(this);

        VolgendeVraag = findViewById(R.id.btnVolgendeVraag);
        VraagNummer = findViewById(R.id.lblVraagCount);
        Vraag = findViewById(R.id.lblVraag);
        Keuze1 = findViewById(R.id.rbnKeuze1);
        Keuze2 = findViewById(R.id.rbnKeuze2);
        Keuze3 = findViewById(R.id.rbnKeuze3);
        Keuze4 = findViewById(R.id.rbnKeuze4);

        Start();

        VolgendeVraag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                volgendeVraag();
            }
        });



    }
    private void volgendeVraag()
    {
        RadioButton[] keuze = new RadioButton[4];
        keuze[0] = Keuze1;
        keuze[1] = Keuze2;
        keuze[2] = Keuze3;
        keuze[3] = Keuze4;
        for(RadioButton b : keuze){
            if(b.getText().toString() == JuistAntwoord && b.isChecked()){
                Score++;
            }
        }
        if(VraagTeller == 0){
            Keuze1.setVisibility(View.VISIBLE);
            Keuze2.setVisibility(View.VISIBLE);
            Keuze3.setVisibility(View.VISIBLE);
            Keuze4.setVisibility(View.VISIBLE);
            getQuestion();
            //getQuestion(_requestVraag);
        }else{
            if(VraagTeller < 15){
                if(VraagTeller == 14){

                }
                getQuestion();
                //getQuestion(_requestVraag);
            }
        }
    }

    private void getQuestion()
    {
        AndroidNetworking.post(getVraagURL)
                .addBodyParameter("volgendeVraag", gebruikteVragen)
                .addBodyParameter("categorie", Categorie.Categorie)
                .setTag("test")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Succes", response);
                        List<String> responeSplit = Arrays.asList(response.split(";"));
                        gebruikteVragen += ";" + responeSplit.get(0);
                        VraagNummer.setText(Vraag + String.valueOf(++VraagTeller));
                        Vraag.setText(responeSplit.get(0));
                        Keuze1.setText(responeSplit.get(1));
                        Keuze2.setText(responeSplit.get(2));
                        Keuze3.setText(responeSplit.get(3));
                        Keuze4.setText(responeSplit.get(4));
                        JuistAntwoord = responeSplit.get(5);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("Fail", anError.toString());
                    }
                });
    }

    /*private void getQuestion(RequestQueue _requestVraag)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getVraagURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Succes", response);
                List<String> responeSplit = Arrays.asList(response.split(";"));
                gebruikteVragen += ";" + responeSplit.get(0);
                VraagNummer.setText(Vraag + String.valueOf(++VraagTeller));
                Vraag.setText(responeSplit.get(0));
                Keuze1.setText(responeSplit.get(1));
                Keuze2.setText(responeSplit.get(2));
                Keuze3.setText(responeSplit.get(3));
                Keuze4.setText(responeSplit.get(4));
                JuistAntwoord = responeSplit.get(5);
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
                params.put("vorigeVragen", gebruikteVragen);
                params.put("categorie", Categorie.Categorie);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        _requestVraag.add(stringRequest);

    }*/

    private void Start()
    {
        VolgendeVraag.setText("StartQuiz");
        Keuze1.setVisibility(View.GONE);
        Keuze2.setVisibility(View.GONE);
        Keuze3.setVisibility(View.GONE);
        Keuze4.setVisibility(View.GONE);
    }
}
