package com.marton.imageeditor.tool.brush;

import android.graphics.Color;

import com.marton.imageeditor.surfaceView.SelectionMask;
import com.marton.imageeditor.utility.Line;

/**
 * Created by marton on 2/1/18.
 */

public class SmartBrush extends Brush {
    protected int sensitivity;

    public SmartBrush(float size, int sensitivity) {
        super(size);
        this.sensitivity = sensitivity;
    }

    @Override
    protected void select(float x, float y) {
        for (int px = (int)(x - size); px <= x + size; px++) {
            for (int py = (int) (y - size); py <= y + size; py++) {
                if (py*w+px < w*h && py*w+px >= 0 && (int)y*w+(int)x >=0 && (int)y*w+(int)x < w*h && isSelected((px-x)*(px-x)+(py-y)*(py-y)) && isInColorRange(imgPixels[(int)y*w+(int)x], imgPixels[py*w+px])) {
                    selector.getSelectionMask().paintPixel(px, py);
                }
            }
        }
    }

    private boolean isInColorRange(int pixel, int pixel1) {
        int d = 0;
        d += (Color.red(pixel)-Color.red(pixel1))*(Color.red(pixel)-Color.red(pixel1));
        d += (Color.green(pixel)-Color.green(pixel1))*(Color.green(pixel)-Color.green(pixel1));
        d += (Color.blue(pixel)-Color.blue(pixel1))*(Color.blue(pixel)-Color.blue(pixel1));

        return d < sensitivity*sensitivity;
    }

    private boolean isSelected(float d) {
        return (d<=size*size);
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }
}
