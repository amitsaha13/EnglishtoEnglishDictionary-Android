package com.example.asus.englishtoenglishdictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView searchedWord;
    private TextView searchedDefination;
    private TextView displayDefination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        searchedWord = (TextView) findViewById(R.id.wordresult);
        searchedDefination = (TextView) findViewById(R.id.worddefination);
        displayDefination = (TextView) findViewById(R.id.displayview);


        searchedWord.setText(getIntent().getStringExtra("word"));
        displayDefination.setText(getIntent().getStringExtra("defination"));


    }
}
