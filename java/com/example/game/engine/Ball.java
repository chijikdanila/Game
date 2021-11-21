package com.example.game.engine;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.example.game.interfaces.IMovable;

public class Ball implements IMovable {

    private float startX;
    private float startY;
    public float xc;
    public float yc;
    public float r;
    Paint paint;
    public RectF hitBox;

    public Ball(float xc, float yc, float r, Paint paint) {
        this.xc = xc;
        this.yc = yc;
        this.r = r;
        this.paint = paint;
        this.hitBox = new RectF(xc-r, yc-r, xc+r, yc+r);
        this.startX = xc;
        this.startY = yc;
    }

    public void move(float x, float y, float mx, float my) {
        xc += x;
        yc += y;
        if(xc + r > mx || xc - r < 0)
            xc -= x;
        if(yc + r > my || yc - r < 0)
            yc -= y;
        hitBox.left = xc-r;
        hitBox.top = yc-r;
        hitBox.right = xc+r;
        hitBox.bottom = yc+r;
    }

    public void restart() {
        xc = startX;
        yc = startY;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(xc, yc, r, paint);
    }

    @Override
    public boolean contactPlayer(Ball ball) {
        return false;
    }

    @Override
    public void move(float mx, float my) {
        //ЫЫЫЫЫЫ
    }
}
