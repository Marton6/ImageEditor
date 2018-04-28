package com.marton.imageeditor.tool.effect;

import android.graphics.Color;

public class ContrastEffect extends Effect{

    @Override
    public void apply(int[] selection, int[] pixels, int w, int h) {
        float tstrength = strength * 510 - 255;
        float factor = (259 * (tstrength + 255)) / (255 * (259 - tstrength));

        for(int i=0;i<w*h;i++){
            if(selection[i] != 0 && pixels[i] != 0) {
                int pa = (pixels[i] >> 24) & 0x000000FF;
                int pr = (pixels[i] >> 16) & 0x000000FF;
                int pg = (pixels[i] >> 8) & 0x000000FF;
                int pb = pixels[i] & 0x000000FF;

                pr = normalize((int)((pr-128)*factor+128));
                pg = normalize((int)((pg-128)*factor+128));
                pb = normalize((int)((pb-128)*factor+128));

                pixels[i] = Color.argb(pa, pr, pg, pb);
            }
        }
    }

    private int normalize(int x){
        if(x<0) return 0;
        if(x>255) return 255;
        return x;
    }
}
