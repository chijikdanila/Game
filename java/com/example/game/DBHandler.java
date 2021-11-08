package com.example.game;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

class DBHandler {

    private String dbName = "game.db";
    private String tableName = "Players";
    private Context context;

    DBHandler(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDB() {
        return context.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    }

    private Boolean makeQuery(String sql) {
        SQLiteDatabase db = openDB();
        try{
            db.execSQL(sql);
            db.close();
            return true;
        }
        catch (Exception e) {
            Log.d("makeQuery", e.getMessage().toString());
        }
        db.close();
        return false;
    }

    public Boolean addToDB(Player newPlayer) {
        return makeQuery("INSERT INTO " + tableName + " VALUES ('" + newPlayer.name + "', " + newPlayer.score + ");");
    }

    public Boolean rmFromDB(Player rmPlayer) {
        return makeQuery("DELETE FROM " + tableName + " WHERE (Name = '" + rmPlayer.name + "');");
    }

    public Boolean updInDB(Player updPlayer) {
        return makeQuery("UPDATE " + tableName + " SET Score = " + updPlayer.score + " WHERE Name = '"+ updPlayer.name + "';");
    }

    public Boolean createDB() {
        makeQuery("DROP TABLE IF EXISTS " + tableName + ";");
        makeQuery("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "Name TEXT NOT NULL PRIMARY KEY, " +
                "Score INTEGER);");
        makeQuery("INSERT INTO " + tableName + " VALUES " +
                "('Bard', 3700), " +
                "('Alexandr', 232), " +
                "('Billy', 420), " +
                "('Van', 419);");
        return true;
    }

    public ArrayList<Player> getFromDB() {
        SQLiteDatabase db = openDB();
        ArrayList<Player> players = new ArrayList<Player>();
        Cursor query = null;
        try{
            query = db.rawQuery("SELECT * FROM " + tableName, null);
            while (query.moveToNext())
                players.add(new Player(query.getString(0), query.getInt(1)));
        }
        catch (Exception e) {
            Log.d("getFromDB", e.getMessage().toString());
        }
        query.close();
        db.close();
        return players;
    }
}
