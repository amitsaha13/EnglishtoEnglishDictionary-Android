package com.example.asus.englishtoenglishdictionary;

import java.util.ArrayList;

public class WordDefination {
    private String word;
    private String defination;
    public WordDefination(String word, ArrayList<String>defination) {
        this.word = word;
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : defination){
            stringBuilder.append(str);
        }
        this.defination = stringBuilder.toString();
    }
    public WordDefination(String word, String defination){
        this.word = word;
        this.defination = defination;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefination() {
        return defination;
    }

    public void setDefination(String defination) {
        this.defination = defination;
    }
}
