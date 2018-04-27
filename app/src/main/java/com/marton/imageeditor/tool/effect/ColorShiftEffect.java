package com.marton.imageeditor.tool.effect;

import android.graphics.Color;

/**
 * Created by marton on 1/6/18.
 */

public class ColorShiftEffect extends Effect{
    private float a, r, g, b;

    public ColorShiftEffect(float a, float r, float g, float b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
        this.strength = 0;
    }

    @Override
    public void apply(int[] selection, int[] pixels, int w, int h) {
        a-=strength*a/100;

        float sum = 0;
        int cnt = 0;
        for(int i=0;i<w*h;i++){
            if(selection[i] != 0 && pixels[i] != 0) {
                int pb = pixels[i] & 0x000000FF;
                int pg = (pixels[i] >> 8) & 0x000000FF;
                int pr = (pixels[i] >> 16) & 0x000000FF;

                float grayscale = (pr+pg+pb)/3.0f/255.0f;

                sum += grayscale;
                cnt++;
            }
        }

        float avg = sum/cnt;

        for(int i=0;i<w*h;i++){
            if(selection[i] != 0 && pixels[i] != 0){
                //(alpha << 24) | (red << 16) | (green << 8) | blue
                int pb = pixels[i] & 0x000000FF;
                int pg = (pixels[i] >> 8) & 0x000000FF;
                int pr = (pixels[i] >> 16) & 0x000000FF;
                int pa = (pixels[i] >> 24) & 0x000000FF;
/*
                pb = (int) Math.min(pb * b, 0x000000FF);
                pg = (int) Math.min(pg * g, 0x000000FF);
                pr = (int) Math.min(pr * r, 0x000000FF);
                pa = (int) Math.min(pa * a, 0x000000FF);
*/

                float grayscale = (pr+pg+pb)/3.0f/255.0f;

                grayscale = 1-(avg-grayscale);
                pr = Math.min(255, (int)(grayscale*r));
                pg = Math.min(255, (int)(grayscale*g));
                pb = Math.min(255, (int)(grayscale*b));

                pixels[i] = Color.argb(pa, pr, pg, pb);
            }
        }
    }

    public void setARGB(float a, float r, float g, float b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
