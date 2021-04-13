package com.example.asus.englishtoenglishdictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity {

    private TextView userNameText;
    private ListView listResult;
    private Button searchButton;
    private EditText searchEdittext;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    ArrayList<WordDefination> allwords = new ArrayList<>();
    ArrayList<WordDefination> temp = new ArrayList<>();

    ArrayList<String> as = new ArrayList<>();

    public static double matchingParcentage(String word, String searched) {

        int ar[] = new int [80000];
        for (int i = 0; i < 80000; i++) {
            ar[i] = 0;
        }
        int match, notmatch;
        match = notmatch = 1;
        for (int i = 0; i < word.length(); i++) {
            int x = word.charAt(i);
            ar[x]++;
        }

        for (int i = 0; i < searched.length(); i++) {
            int x = searched.charAt(i);

            if (ar[x] > 0) {
                match++;
                ar[x]--;
                System.out.println(i + " " + "match");
            }
            else {
                notmatch++;
                System.out.println(i + " notmatch");
            }
        }

        return (match / (double) notmatch) * 100.0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        userNameText = (TextView) findViewById(R.id.userNameField);
        listResult = (ListView) findViewById(R.id.listResult);
        searchButton = (Button) findViewById(R.id.searchbutton);
        searchEdittext = (EditText) findViewById(R.id.searchEdittext);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        userNameText.setText("Welcome " + userName);

        databaseHelper = new DatabaseHelper(this, "Dictionary", null, 1);

        allwords = databaseHelper.getAllwords();

        boolean initialized = false;

        if (allwords.size() > 0){
            initialized = true;
        }

        if (!initialized){
            //Toast.makeText(this, "Initializing", Toast.LENGTH_SHORT).show();
            initializeDatabse();
            allwords = databaseHelper.getAllwords();
        }


        for (WordDefination word : allwords){
            String x = word.getWord();
            x = x.toLowerCase();
            word.setWord(x);
            temp.add(new WordDefination(x, word.getDefination()));
        }







        /*
        Start
         */


        searchEdittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String searchedWord = searchEdittext.getText().toString();
                searchedWord = searchedWord.toLowerCase();

                if (!searchedWord.equals("") && !searchedWord.equals(null)){
                    temp.clear();
                    for (WordDefination wd : allwords) {
                        String x = wd.getWord();
                        if (x.startsWith(searchedWord)) {
                            x = x.toLowerCase();
                            temp.add(new WordDefination(x, wd.getDefination()));
                        }
                    }
                }
                else {
                    for (WordDefination word : allwords){
                        String x = word.getWord();
                        x = x.toLowerCase();
                        word.setWord(x);
                        temp.add(new WordDefination(x, word.getDefination()));
                    }
                }

                //Toast.makeText(SearchActivity.this, Integer.toString(temp.size()) + " " + Integer.toString(allwords.size()), Toast.LENGTH_SHORT).show();

                listResult.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return temp.size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return null;
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        if (view == null){
                            view = getLayoutInflater().inflate(R.layout.list_item, null);
                        }
                        TextView textView = (TextView) view.findViewById(R.id.listitemtextview);
                        textView.setText(temp.get(i).getWord());
                        return view;
                    }
                });
                return false;
            }
        });



        searchEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchedWord = searchEdittext.getText().toString();
                searchedWord = searchedWord.toLowerCase();

                if (searchedWord.equals("") || searchedWord.equals(null))
                {
                    listResult.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return allwords.size();
                        }
                        @Override
                        public Object getItem(int i) {
                            return null;
                        }

                        @Override
                        public long getItemId(int i) {
                            return 0;
                        }

                        @Override
                        public View getView(int i, View view, ViewGroup viewGroup) {
                            if (view == null){
                                view = getLayoutInflater().inflate(R.layout.list_item, null);
                            }
                            TextView textView = (TextView) view.findViewById(R.id.listitemtextview);
                            textView.setText(allwords.get(i).getWord());
                            return view;
                        }
                    });
                }
                else {
                    for (WordDefination word : allwords){
                        String x = word.getWord();
                        x = x.toLowerCase();
                        word.setWord(x);
                        temp.add(new WordDefination(x, word.getDefination()));
                    }
                    listResult.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return temp.size();
                        }

                        @Override
                        public Object getItem(int i) {
                            return null;
                        }

                        @Override
                        public long getItemId(int i) {
                            return 0;
                        }

                        @Override
                        public View getView(int i, View view, ViewGroup viewGroup) {
                            if (view == null){
                                view = getLayoutInflater().inflate(R.layout.list_item, null);
                            }
                            TextView textView = (TextView) view.findViewById(R.id.listitemtextview);
                            textView.setText(temp.get(i).getWord());
                            return view;
                        }
                    });
                }
            }
        });


        /*
        End
         */


        listResult.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return temp.size();
            }
            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null){
                    view = getLayoutInflater().inflate(R.layout.list_item, null);
                }
                TextView textView = (TextView) view.findViewById(R.id.listitemtextview);
                textView.setText(temp.get(i).getWord());
                return view;
            }
        });

        listResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                intent.putExtra("word", temp.get(i).getWord());
                intent.putExtra("defination", temp.get(i).getDefination());
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchEdittext.getText().toString();

                WordDefination wordDefination = databaseHelper.getWord(str);

                if (wordDefination == null){
                    Toast.makeText(SearchActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    intent.putExtra("word", wordDefination.getWord());
                    intent.putExtra("defination", wordDefination.getDefination());
                    startActivity(intent);
                }
            }
        });


    }

    private void initializeDatabse() {
        InputStream inputStream = getResources().openRawResource(R.raw.dictionary);
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));
        WordLoader.loadData(scanner, databaseHelper);
    }

}