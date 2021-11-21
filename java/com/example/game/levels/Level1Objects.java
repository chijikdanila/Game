package com.example.game.levels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.game.engine.Ball;
import com.example.game.interfaces.IDrawable;
import com.example.game.interfaces.IMovable;

import java.util.ArrayList;

class Maze1 implements IDrawable {
    
    ArrayList<RectF> rects = new ArrayList<>();
    float mx;
    float my;
    
    public Maze1(float mx, float my) {
        this.mx = mx;
        this.my = my;
        rects.add(new RectF(800f, 1300f, mx, my));
        rects.add(new RectF(860f, 1200f, mx, 1300f));
        rects.add(new RectF(560f, 1100f, mx, 1200f));
        rects.add(new RectF(300f, 850f, 400f, 1300f));
        rects.add(new RectF(740f, 470f, mx, 550f));
        rects.add(new RectF(0f, 350f,350f, 450f));
        rects.add(new RectF(0f, my-300f, mx, my));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        rects.forEach( rect -> canvas.drawRect(rect, paint) );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean contactPlayer(Ball ball) {
        for(RectF rect : rects) {
            if (rect.intersects(ball.hitBox.left, ball.hitBox.top, ball.hitBox.right, ball.hitBox.bottom)) {
                return true;
            }
        }
        return false;
    }
}

class Enemy1 implements IDrawable{

    ArrayList<PointF> points = new ArrayList<>();
    RectF hitBox;

    public Enemy1() {
        points.add(new PointF(570f,1050f));
        points.add(new PointF(680f,990f));
        points.add(new PointF(680f,1090f));
        hitBox = new RectF(570f, 990f, 680f, 1090f);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(140,0,20));
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.reset();
        path.moveTo(points.get(0).x, points.get(0).y);
        path.lineTo(points.get(1).x, points.get(1).y);
        path.lineTo(points.get(2).x, points.get(2).y);
        path.setLastPoint(points.get(2).x, points.get(2).y);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean contactPlayer(Ball ball) {
        return hitBox.intersects(ball.hitBox.left, ball.hitBox.top, ball.hitBox.right, ball.hitBox.bottom);
    }
}

class Enemy2 implements IMovable {

    RectF hitBox;
    private float speed;
    public Enemy2(float speed) {
        this.speed = speed;
        hitBox = new RectF(620f,605f,780f, 705f);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(140,0,20));
        paint.setStyle(Paint.Style.FILL);;
        canvas.drawRect(hitBox, paint);
    }

    @Override
    public boolean contactPlayer(Ball ball) {
        return hitBox.intersects(ball.hitBox.left, ball.hitBox.top, ball.hitBox.right, ball.hitBox.bottom);
    }

    @Override
    public void move(float mx, float my) {
        hitBox.right += speed;
        hitBox.left += speed;
        if (hitBox.right >= mx)
            speed = -speed;
        if (hitBox.left  <= 0)
            speed= -speed;
    }
}

class Finish implements IDrawable {
    RectF hitBox;
    private float x;
    private float y;

    public Finish() {
        this.x = 940f;
        this.y = 115f;
        this.hitBox = new RectF(x-20, y-20, x+20, y+20);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(hitBox, paint);
    }

    @Override
    public boolean contactPlayer(Ball ball) {
        return RectF.intersects(hitBox, ball.hitBox);
    }
}
