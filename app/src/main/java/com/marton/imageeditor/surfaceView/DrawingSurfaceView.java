package com.marton.imageeditor.surfaceView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by marton on 1/3/18.
 */

public class DrawingSurfaceView extends SurfaceView implements Runnable{
    private SurfaceHolder holder;
    private ImageProcessor imageProcessor;
    public float scaleFactor = 1f;
    public float dx, dy;

    public DrawingSurfaceView(Context context) {
        super(context);
        init();
    }

    public DrawingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        holder = getHolder();
        imageProcessor = new ImageProcessor(getContext(), this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (true) {
            try {
                long dt = System.currentTimeMillis()-time;
                long sleepTime = (long)(1.0f/25.0f*1000)-dt;
                if(dt>0)Log.i("framerate", 1000.0f/(float)dt+"FPS");
                if(sleepTime > 0)Thread.sleep(sleepTime);
                time = System.currentTimeMillis();

                if (!holder.getSurface().isValid()) continue;
                Canvas canvas = holder.lockCanvas(null);
                if (canvas != null) {
                    canvas.translate(dx, dy);
                    canvas.translate(+canvas.getWidth()/2, +canvas.getHeight()/2);
                    canvas.scale(scaleFactor, scaleFactor);
                    canvas.translate(-canvas.getWidth()/2, -canvas.getHeight()/2);
                    canvas.save();
                    clear(canvas);
                    imageProcessor.render(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            } catch (Exception e) {
                ;
            }
        }
    }

    private void clear(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        //border's properties
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = calculate(event.getX(), getWidth(), dx);
        float y = calculate(event.getY(), getHeight(), dy);


        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageProcessor.onTapDown(x, y);
            //return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            imageProcessor.onTapHold(x, y);
            //return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            imageProcessor.onTapUp(x, y);
            //return true;
        }
        imageProcessor.onTouchEvent(event);
        return true;
    }

    private float calculate(float x, float dim, float d) {
        x -= d;
        x -= dim/2;
        x /= scaleFactor;
        x += dim/2;
        return x;
    }

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }
}
