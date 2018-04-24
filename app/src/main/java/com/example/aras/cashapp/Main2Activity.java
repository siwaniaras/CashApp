package com.example.aras.cashapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void startButtonClick(View view) {
        Intent intent = new Intent(this, Main3Activity.class);


        EditText timl= (EditText) findViewById(R.id.timlon);
        EditText sk = (EditText) findViewById(R.id.skatt);
        int timlon = Integer.parseInt(timl.getText().toString());
        int skatt = Integer.parseInt(sk.getText().toString());
        Log.d(" A2 skatt", String.valueOf(skatt));
        Log.d("A2 timlon", String.valueOf(timlon));
        intent.putExtra("timlon", timlon);
        intent.putExtra("skatt", skatt);
        startActivity(intent);

    }
}

