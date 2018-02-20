package com.marton.imageeditor.surfaceView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.nio.IntBuffer;

/**
 * Created by marton on 1/3/18.
 */

public class SelectionMask {
    private int w, h;
    private int[] selection;
    private Bitmap bitmap;
    private boolean changed;

    public SelectionMask(int w, int h) {
        selection = new int[w * h];
        this.w = w;
        this.h = h;
        this.bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    public Bitmap getAsBitmap(){
        if(changed){
            bitmap.setPixels(selection, 0, w, 0, 0, w, h);
            changed = false;
        }
        return bitmap;
    }

    public void paintPixel(int x, int y){
        selection[y*w+x] = 0xffff00ff;
    }

    public int[] getPixels() {
        return selection;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void clear() {
        for(int i=0;i<w*h;i++){
            selection[i]=0;
        }
        updateBitmap();
    }

    public void fill() {
        for(int i=0;i<w*h;i++){
            selection[i]=0xffff00ff;
        }
        updateBitmap();
    }

    public boolean hasSelected() {
        for(int i=0;i<w*h;i++){
            if(selection[i] != 0) return true;
        }
        return false;
    }

    public void updateBitmap() {
        changed = true;
    }
}
