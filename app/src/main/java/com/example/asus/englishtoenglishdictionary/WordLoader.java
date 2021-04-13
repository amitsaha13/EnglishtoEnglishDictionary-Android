package com.example.asus.englishtoenglishdictionary;

import android.util.Log;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class WordLoader {
    public static void loadData(Scanner scanner, DatabaseHelper dictionaryDatabaseHelper) {

        ArrayList<WordDefination> allWords=new ArrayList<>();

        try {

            Scanner sc = scanner;
            while (sc.hasNext()){
                String word = sc.nextLine();
                String defination = sc.nextLine();
                allWords.add(new WordDefination(word, defination));
            }
            try {
                dictionaryDatabaseHelper.initializeDatabase(allWords);
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
