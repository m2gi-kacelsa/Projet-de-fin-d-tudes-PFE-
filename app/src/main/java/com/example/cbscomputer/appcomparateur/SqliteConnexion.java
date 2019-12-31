package com.example.cbscomputer.appcomparateur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class SqliteConnexion extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBCompte.db";
    private static final String TABLE = "table_user";
    private static final int DB_VERSION = 1;
    public static final String COL_1 = "NOM_ASS";
    public static final String COL_2 = "NOM";
    public static final String COL_3 = "EMAIL";

    private static final String STRING_CREATE = "CREATE TABLE " + TABLE + " ( " + COL_1 + " TEXT PRIMARY KEY ,"
            + COL_2   +"TEXT "+ COL_3+" TEXT"+ ");";

    private  Context cont ;

    public SqliteConnexion(Context context) {
        super(context, DB_NAME, null, DB_VERSION); //une fois le constructeur est appellé la BDD est crée
        this.cont = context;
        this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE); //création de la table

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertDATA(String assurans, String nom, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, assurans);
        cv.put(COL_2, nom);
        cv.put(COL_3, email);
        long result = db.insert(TABLE, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM  table_compte ";
            cursor = db.rawQuery(query, null);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();

        }
        return cursor;
    }
}
