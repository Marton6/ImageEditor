package com.marton.imageeditor.surfaceView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.widget.ArrayAdapter;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by marton on 1/3/18.
 */

public class SelectionMask {
    private int w, h;
    private Bitmap bitmap;
    private Canvas canvas;

    private Selector selector;

    private Paint paint;

    public SelectionMask(Layer l, int w, int h) {
        this.w = w;
        this.h = h;
        this.bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);

        this.paint = new Paint();
        paint.setColor(Color.MAGENTA);

        this.selector = new Selector(l, this);
    }

    public Bitmap getAsBitmap(){
        return bitmap;
    }

    public void paintPixel(int x, int y){
        canvas.drawPoint(x, y, paint);
    }

    public int[] getPixels() {
        int[] pixels = new int[w*h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        return pixels;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void clear() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void fill() {
        canvas.drawColor(Color.MAGENTA);
    }

    public boolean hasSelected() {
        //TODO implement method
        return true;
    }

    public Selector getSelector() {
        return selector;
    }

    public void paintCircle(float x, float y, float size) {
        canvas.drawCircle(x, y, size, paint);
    }
}
