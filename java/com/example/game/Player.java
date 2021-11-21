package com.example.game;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Player implements Serializable {
    public String name;
    public int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + score;
    }
}
