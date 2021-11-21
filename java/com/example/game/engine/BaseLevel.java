package com.example.game.engine;

import android.content.Context;
import android.graphics.Canvas;

public abstract class BaseLevel {
    Context context;

    public BaseLevel(Context context) {
        this.context = context;
    }

    public Boolean isFinished;
    public abstract void draw(Canvas canvas);
    public abstract void update(float x, float y);
}
