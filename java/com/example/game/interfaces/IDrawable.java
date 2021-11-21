package com.example.game.interfaces;

import android.graphics.Canvas;
import com.example.game.engine.Ball;

public interface IDrawable {
    void draw(Canvas canvas);
    boolean contactPlayer(Ball ball);
}
