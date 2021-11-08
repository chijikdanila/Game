package com.example.game;

import java.util.ArrayList;

public class Player {
    public String name;
    public int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "\t" + score;
    }
}
