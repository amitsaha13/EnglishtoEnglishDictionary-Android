package com.example.asus.englishtoenglishdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button enterButton;
    private EditText userNameText;
    private Button quizbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterButton = (Button) findViewById(R.id.enterButton);
        userNameText = (EditText) findViewById(R.id.usernameText);
        quizbutton = (Button) findViewById(R.id.quizbutton);

        Intent intent;
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("USER_NAME", userNameText.getText().toString());
                startActivity(intent);
            }
        });

        quizbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("USER_NAME", userNameText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
