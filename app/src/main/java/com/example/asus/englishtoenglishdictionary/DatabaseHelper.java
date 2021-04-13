package com.example.asus.englishtoenglishdictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DICTIONARY_DATABASE = "database";
    final static String ITEM_ID = "id";
    final static String WORD = "word";
    final static String DEFINATION = "defination";

    final static String CREATE_DATABASE_QUERY = "CREATE TABLE " + DICTIONARY_DATABASE + " ( " +
            ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD + " TEXT, " +
            DEFINATION + " TEXT)";
    final static String ON_UPGRADE_QUERY = "DROP TABLE " + DICTIONARY_DATABASE;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DICTIONARY_DATABASE, factory, version + 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_DATABASE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(ON_UPGRADE_QUERY);
        onCreate(database);
    }

    public void insertData(WordDefination wordDefination){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD, wordDefination.getWord());
        values.put(DEFINATION, wordDefination.getDefination());
        database.insert(DICTIONARY_DATABASE, null, values);
    }
    public long updateData(WordDefination wordDefination){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD, wordDefination.getWord());
        values.put(DEFINATION, wordDefination.getDefination());
        return database.update(DICTIONARY_DATABASE, values, WORD + " =?", new String[]{wordDefination.getWord()});
    }
    public void deleteData(WordDefination wordDefination){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + DICTIONARY_DATABASE +
                " WHERE " + WORD + " = '" +
                wordDefination.getWord() + "'";
        database.execSQL(query);
    }

    public ArrayList<WordDefination> getAllwords(){
        ArrayList<WordDefination>allwords = new ArrayList<WordDefination>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + DICTIONARY_DATABASE;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                WordDefination wordDefination = new WordDefination(cursor.getString(cursor.getColumnIndex(WORD)), cursor.getString(cursor.getColumnIndex(DEFINATION)));
                allwords.add(wordDefination);
            }
            while (cursor.moveToNext());
        }
        return allwords;
    }

    public WordDefination getWord(String word){
        SQLiteDatabase database = this.getReadableDatabase();
        WordDefination wordDefination = null;

        String query = "SELECT * FROM " + DICTIONARY_DATABASE +
        " WHERE " + WORD + " = '" + word + "'";

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            wordDefination = new WordDefination(cursor.getString(cursor.getColumnIndex(WORD)), cursor.getString(cursor.getColumnIndex(DEFINATION)));
        }
        return wordDefination;
    }
    public WordDefination getWord(long id){
        SQLiteDatabase database = this.getReadableDatabase();
        WordDefination wordDefination = null;

        String query = "SELECT * FROM " + DICTIONARY_DATABASE +
                " WHERE " + ITEM_ID + " = '" + id + "'";

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            wordDefination = new WordDefination(cursor.getString(cursor.getColumnIndex(WORD)), cursor.getString(cursor.getColumnIndex(DEFINATION)));
        }
        return wordDefination;
    }

    void initializeDatabase(ArrayList<WordDefination>wordDefinations){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("BEGIN");
        ContentValues contentValues = new ContentValues();

        for (WordDefination word: wordDefinations){
            contentValues.put(WORD, word.getWord());
            contentValues.put(DEFINATION, word.getDefination());
            database.insert(DICTIONARY_DATABASE, null, contentValues);
        }

        database.execSQL("COMMIT");
    }

}
