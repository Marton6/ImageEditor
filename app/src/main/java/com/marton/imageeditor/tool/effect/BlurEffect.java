package com.marton.imageeditor.tool.effect;

import android.graphics.Color;

/**
 * Created by marton on 2/1/18.
 */

public class BlurEffect extends Effect {

    @Override
    public void apply(int[] selection, int[] pixels, int w, int h) {
        strength*=50;

        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                if(selection[y*w+x] != 0){
                    long sumR = 0;
                    long sumG = 0;
                    long sumB = 0;
                    int cnt = 0;

                    for(int x1=x-(int)strength/2;x1<=x+(int)strength/2;x1++){
                        for(int y1=y-(int)strength/2;y1<=y+(int)strength/2;y1++){
                            if(y1*w+x1>=0 && y1*w+x1< w*h) {
                                sumR+= Color.red(pixels[y1*w+x1]);
                                sumG+= Color.green(pixels[y1*w+x1]);
                                sumB+= Color.blue(pixels[y1*w+x1]);
                                cnt++;
                            }
                        }
                    }
                    pixels[y*w+x] = Color.argb(255, (int)(sumR/cnt), (int)(sumG/cnt), (int)(sumB/cnt));
                }
            }
        }
    }
}
