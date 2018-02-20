package com.marton.imageeditor.tool.brush;

/**
 * Created by marton on 1/6/18.
 */

public class CircleBrush extends Brush{

    public CircleBrush() {
    }

    public CircleBrush(float size) {
        super(size);
    }

    @Override
    public void select(float x, float y) {
        for (int px = (int)(x - size); px <= x + size; px++) {
            for (int py = (int) (y - size); py <= y + size; py++) {
                if (py*w+px < w*h && py*w+px >= 0 && isSelected((px-x)*(px-x)+(py-y)*(py-y))) {
                    pixels[py*w+px] = 0xffff00ff;
                }
            }
        }
    }

    private boolean isSelected(float d) {
        return (d<=size*size);
    }
}
