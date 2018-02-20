package com.marton.imageeditor.surfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.effect.Effect;

/**
 * Created by marton on 1/3/18.
 */

public class Layer {
    public static final float DOWNSCALE_FACTOR = .1f;

    private Bitmap bitmap, lowResBitmap;
    private int pixels[];
    private int w, h;
    private SelectionMask selection;

    public Layer(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.bitmap.setHasAlpha(true);
        this.lowResBitmap = Bitmap.createScaledBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true), (int)(bitmap.getWidth()*DOWNSCALE_FACTOR), (int)(bitmap.getHeight()*DOWNSCALE_FACTOR), true );
        this.selection = new SelectionMask(bitmap.getWidth(), bitmap.getHeight());

        this.w = bitmap.getWidth();
        this.h = bitmap.getHeight();
    }

    public void render(Canvas canvas, float left, float top, boolean downsampled) {
        if(!downsampled)
            canvas.drawBitmap(bitmap, left, top, null);
        else {
            canvas.scale(1/DOWNSCALE_FACTOR, 1/DOWNSCALE_FACTOR);
            canvas.drawBitmap(lowResBitmap, left, top, null);
            canvas.scale(DOWNSCALE_FACTOR, DOWNSCALE_FACTOR);
        }
        Paint paint = new Paint();
        paint.setAlpha(128);
        canvas.drawBitmap(selection.getAsBitmap(), left, top, paint);

    }

    public SelectionMask getSelection() {
        return selection;
    }

    public void select(Brush crtBrush, float x, float y) {
        crtBrush.select(getPixels(), selection.getPixels(), x, y, w, h);
        selection.updateBitmap();
    }

    private int[] getPixels(){
        if(this.pixels != null) return this.pixels;
        int[] pixels = new int[w*h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        this.pixels = pixels;
        return this.pixels;
    }

    // TODO: add loading dialog
    public void applyEffect(Effect effect){
        int[] pixels = getPixels();
        effect.apply(selection.getPixels(), pixels, w, h);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        lowResBitmap = Bitmap.createScaledBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true), (int)(bitmap.getWidth()*DOWNSCALE_FACTOR), (int)(bitmap.getHeight()*DOWNSCALE_FACTOR), true );;
        selection.updateBitmap();
    }
}
