package com.example.game.levels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.example.game.Player;
import com.example.game.engine.Ball;
import com.example.game.engine.BaseLevel;
import com.example.game.interfaces.IDrawable;
import com.example.game.interfaces.IMovable;

import java.util.ArrayList;

public class Level1 extends BaseLevel {

    float mx;
    float my;
    Paint paint;
    Ball ball;
    ArrayList<IMovable> movables = new ArrayList<>();
    ArrayList<IDrawable> drawables = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.R)
    public Level1(Context context) {
        super(context);
        super.isFinished = false;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mx = wm.getDefaultDisplay().getWidth();
        this.my = wm.getDefaultDisplay().getHeight();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3f);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        ball = new Ball(mx/2, my-my*0.2f, 50f, paint);

        drawables.add(new Maze1(mx,my));
        drawables.add(new Enemy1());
        drawables.add(new Finish());

        movables.add(new Enemy2(4f));
        movables.add(ball);
        drawables.addAll(movables);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0f, 0f, canvas.getWidth(), canvas.getHeight(), paint);
        paint.setStyle(Paint.Style.STROKE);
        this.drawables.forEach(drawable -> drawable.draw(canvas));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void update(float x, float y) {
        if (x != 0 && y != 0)
            ball.move(7.5f * Math.signum(x-ball.xc), 7.5f * Math.signum(y-ball.yc), mx, my);
        this.movables.forEach( movable -> movable.move(mx, my) );
        drawables.forEach( drawable -> {
            if (drawable.getClass() != Ball.class) {
                if (drawable.contactPlayer(ball)) {
                    Object aClass = drawable.getClass();
                    if (aClass == Maze1.class) {
                        ball.move(-7.5f * Math.signum(x-ball.xc), -7.5f * Math.signum(y-ball.yc), mx, my);
                    } else if (aClass == Enemy1.class || aClass == Enemy2.class) {
                        ball.restart();
                    } else if (aClass == Finish.class) {
                        super.isFinished = true;
                    }
                }
            }}
        );
    }
}
