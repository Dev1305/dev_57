package com.example.edemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crypto.db";
    private static final String TABLE_NAME = "crypto_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PRICE";
    private static final String COL_4 = "SIZE";
    private static final String COL_5 = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE TEXT, SIZE TEXT, DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCryptoData(String name, String price, String size, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, price);
        contentValues.put(COL_4, size);
        contentValues.put(COL_5, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public ArrayList<Crypto> getAllCryptoData() {
        ArrayList<Crypto> cryptoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Crypto crypto = new Crypto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                cryptoList.add(crypto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cryptoList;
    }

    public boolean updateCryptoData(int id, String name, String price, String size, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, price);
        contentValues.put(COL_4, size);
        contentValues.put(COL_5, date);
        int result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Integer deleteCryptoData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(id)});
    }

    public Crypto getCryptoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_1, COL_2, COL_3, COL_4, COL_5},
                COL_1 + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Crypto crypto = new Crypto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
            return crypto;
        }
        return null;
    }
}
