package com.marton.imageeditor.tool.brush;

import com.marton.imageeditor.surfaceView.SelectionMask;
import com.marton.imageeditor.surfaceView.Selector;
import com.marton.imageeditor.utility.Line;
import com.marton.imageeditor.utility.Vector2;

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
        /*for (int px = (int)(x - size); px <= x + size; px++) {
            for (int py = (int) (y - size); py <= y + size; py++) {
                if (py*w+px < w*h && py*w+px >= 0 && isSelected((px-x)*(px-x)+(py-y)*(py-y))) {
                    selector.getSelectionMask().paintPixel(px, py);
                    //pixels[py*w+px] = 0xffff00ff;
                }
            }
        }*/
        selector.getSelectionMask().paintCircle(x, y, this.size);
    }

    private boolean isSelected(float d) {
        return (d<=size*size);
    }
}
