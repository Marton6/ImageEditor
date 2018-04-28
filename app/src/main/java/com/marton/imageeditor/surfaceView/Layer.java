package com.marton.imageeditor.surfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;

import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.effect.Effect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

        this.w = bitmap.getWidth();
        this.h = bitmap.getHeight();

        this.selection = new SelectionMask(this, bitmap.getWidth(), bitmap.getHeight());
    }

    public void render(Canvas canvas, float left, float top, boolean downsampled) {
        if (!downsampled) {
            canvas.drawBitmap(bitmap, left, top, null);
        } else {
            canvas.scale(1 / DOWNSCALE_FACTOR, 1 / DOWNSCALE_FACTOR);
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

    public int[] getPixels(){
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
        //selection.updateBitmap();
    }

    public void save() {
        try {
            String path = Environment.getExternalStorageDirectory().toString().concat("/Image Editor/");
            File dir = new File(path);
            dir.mkdirs();

            Long time = System.currentTimeMillis()/1000;
            String timestamp = time.toString();

            File file = new File(path, "image-"+timestamp+".png");

            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
