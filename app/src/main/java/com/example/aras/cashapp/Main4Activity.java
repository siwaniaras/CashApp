package com.example.aras.cashapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TextView slutsats = (TextView)findViewById(R.id.slut);
        Intent intent = getIntent();
        int skatt = intent.getIntExtra("skatt", 0);
        int timlon = intent.getIntExtra("timlon", 0);
        double summa = intent.getDoubleExtra("summa", 0);

        float skatten;
        skatten = (float) 1 - (float) skatt/100;

        double totalsumma;
        totalsumma = skatten * timlon * summa;
        slutsats.setText(String.valueOf(totalsumma));


    }



    public void startButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
