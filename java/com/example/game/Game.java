package com.example.game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.game.engine.Engine;
import com.example.game.levels.Level1;

public class Game extends AppCompatActivity {

    private Player currentPlayer;
    public SurfaceView surfaceView;
    Engine engine;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle intent = getIntent().getExtras();

        if (intent != null) {
            currentPlayer = (Player) intent.get("player");
        }
        else
            return;

        surfaceView = findViewById(R.id.surface);
        engine = new Engine(this, surfaceView);
    }

    public void backToMainMenu() {
        DBHandler db = new DBHandler(this);
        Intent intent = new Intent(this, MainActivity.class);
        currentPlayer.score += 50;
        db.updInDB(currentPlayer);
        intent.putExtra("player", currentPlayer);
        startActivity(intent);
        finish();
//        Intent intent = new Intent();
//        currentPlayer.score += 50;
//        intent.putExtra("player", currentPlayer);
//        setResult(RESULT_OK, intent);
//        finish();
    }
}