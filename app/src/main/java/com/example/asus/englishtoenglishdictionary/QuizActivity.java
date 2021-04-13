package com.example.asus.englishtoenglishdictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView question;
    RadioButton optionA, optionB, optionC, optionD;
    ArrayList<WordDefination> allWords = new ArrayList<>();
    Button submitButton;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = (TextView) findViewById(R.id.question);
        optionA = (RadioButton)findViewById(R.id.optiona);
        optionB = (RadioButton)findViewById(R.id.optionb);
        optionC = (RadioButton)findViewById(R.id.optionc);
        optionD = (RadioButton)findViewById(R.id.optiond);
        submitButton = (Button) findViewById(R.id.submitanswer);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        databaseHelper = new DatabaseHelper(this, "Dictionary", null, 1);
        allWords = databaseHelper.getAllwords();
        //question.setText(Integer.toString(allWords.size()));
        int size = allWords.size();
        int seen[] = new int[size + 5];
        ArrayList<Integer> indexList =  new ArrayList<>();

        while (true){
            if (indexList.size() >= 4)
                break;
            int x = (int) (Math.random() * (size - 1));
            if (seen[x] == 0){
                indexList.add(x);
                seen[x] = 1;
            }
        }
        final int correctAnswer = (int) (Math.random() * 100) % 4;
        question.setText(allWords.get(indexList.get(correctAnswer)).getWord());
        optionA.setText(allWords.get(indexList.get(0)).getDefination());
        optionB.setText(allWords.get(indexList.get(1)).getDefination());
        optionC.setText(allWords.get(indexList.get(2)).getDefination());
        optionD.setText(allWords.get(indexList.get(3)).getDefination());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (optionA.isChecked() && correctAnswer == 0){
                    Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
                }
                else if (optionB.isChecked() && correctAnswer == 1){
                    Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
                }
                else if(optionC.isChecked() && correctAnswer == 2){
                    Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
                }
                else if (optionD.isChecked() && correctAnswer == 3){
                    Toast.makeText(QuizActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(QuizActivity.this, "Wrong Answer", Toast.LENGTH_LONG).show();
                }
                recreate();
            }
        });


    }
}
