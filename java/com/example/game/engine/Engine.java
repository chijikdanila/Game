package com.example.game.engine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.game.Game;
import com.example.game.levels.Level1;

public class Engine {

    private SurfaceHolder.Callback callback;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private Level1 level1;
    Context context;
    private float x;
    private float y;

    private volatile boolean stopped;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.R)
    public Engine(Context context, SurfaceView surfaceView) {

        this.context = context;
        level1 = new Level1(context);

        this.surfaceView = ((Game) context).surfaceView;
        surfaceView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                    y = event.getY();
                    return true;
                }
                else {
                    x = 0f;
                    y = 0f;
                }
                return false;
            }
        });

        Thread engineThread = new Thread(threadRunnable, "EngineThread");
        engineThread.start();

        callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Engine.this.surfaceHolder = surfaceHolder;
                synchronized (Engine.this) {
                    Engine.this.notifyAll();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Engine.this.surfaceHolder = surfaceHolder;
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Engine.this.surfaceHolder = null;
            }
        };

        surfaceView.getHolder().addCallback(callback);
    }

    Runnable threadRunnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            while (!stopped)  {
                Canvas canvas;
                if (surfaceHolder == null || (canvas = surfaceHolder.lockCanvas()) == null) {
                    synchronized (Engine.this) {
                        try {
                            Engine.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (level1.isFinished) {
                        stop();
                        exit();
                    }
                    else {
                        level1.update(x, y);
                        level1.draw(canvas);
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    };

    public void stop() {
        this.stopped = true;
    }

    private void exit() {
        ((Game) context).backToMainMenu();
    }
}