package com.marton.imageeditor.surfaceView;

import android.view.ScaleGestureDetector;

/**
 * Created by marton on 1/7/18.
 */
public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private DrawingSurfaceView drawingSurfaceView;

    public ScaleListener(DrawingSurfaceView drawingSurfaceView) {
        this.drawingSurfaceView = drawingSurfaceView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        drawingSurfaceView.scaleFactor *= detector.getScaleFactor();

        // Don't let the object get too small or too large.
        drawingSurfaceView.scaleFactor = Math.max(0.5f, Math.min(drawingSurfaceView.scaleFactor , 5.0f));

        return true;
    }
}
