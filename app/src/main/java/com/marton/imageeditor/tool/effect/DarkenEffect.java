package com.marton.imageeditor.tool.effect;

import android.graphics.Color;

public class DarkenEffect extends Effect {

    @Override
    public void apply(int[] selection, int[] pixels, int w, int h) {

        for(int i=0;i<w*h;i++){
            if(selection[i] != 0 && pixels[i] != 0) {
                int pa = (pixels[i] >> 24) & 0x000000FF;
                int pr = (pixels[i] >> 16) & 0x000000FF;
                int pg = (pixels[i] >> 8) & 0x000000FF;
                int pb = pixels[i] & 0x000000FF;

                pixels[i] = Color.argb(pa, (int)(pr*strength), (int)(pg*strength), (int)(pb*strength));
            }
        }
    }
}
