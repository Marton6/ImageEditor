package com.marton.imageeditor.surfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.effect.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marton on 1/3/18.
 */

public class ImageProcessor {
    //layers
    private List<Layer> layers;
    private int crtLayer;

    //tools
    private Tools tools;

    //move gesture
    private float startX;
    private float startY;
    /*
    private float dx;
    private float dy;
    */

    private int touchCnt = 0;

    private DrawingSurfaceView drawingSurfaceView;
    private ScaleGestureDetector scaleDetector;

    public ImageProcessor(Context context, DrawingSurfaceView drawingSurfaceView) {
        layers = new ArrayList<>();
        crtLayer = -1;
        tools = new Tools();

        this.drawingSurfaceView = drawingSurfaceView;
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener(drawingSurfaceView));
    }

    public void render(Canvas canvas){
        if(layers.size() <= 0)  return;

        switch (tools.getRenderMode()){
            case Tools.RENDER_MODE_ALL_LAYERS:
                for(Layer l:layers){
                    l.render(canvas, 0, 0, isMoving());
                }
                break;

            case Tools.RENDER_MODE_ALL_LAYERS_BELOW:
                for(int i=0;i<=crtLayer;i++){
                    layers.get(i).render(canvas, 0, 0, isMoving());
                }
                break;

            case Tools.RENDER_MODE_CURRENT_LAYER:
                layers.get(crtLayer).render(canvas, 0, 0, isMoving());

        }
    }

    public Tools getTools() {
        return tools;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
        crtLayer = layers.size()-1;
    }

    public void onTapDown(float x, float y) {
        if(tools.getCurrentTool() == Tools.TOOL_MOVE){
            startX = x;
            startY = y;
            touchCnt++;
        }

    }

    public void onTapHold(float x, float y) {
        if(tools.getCurrentTool() == Tools.TOOL_SELECT) {
            layers.get(crtLayer).select(tools.getCrtBrush(), x, y);
        }
        else if(tools.getCurrentTool() == Tools.TOOL_MOVE){
            drawingSurfaceView.dx += x-startX;
            drawingSurfaceView.dy += y-startY;
        }
    }

    public void onTapUp(float x, float y) {
        if(tools.getCurrentTool() == Tools.TOOL_MOVE)touchCnt--;
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        scaleDetector.onTouchEvent(motionEvent);
    }

    public void applyEffect(Effect e){
        if(e==null) return;
        layers.get(crtLayer).applyEffect(e);
    }

    public void clearSelection() {
        layers.get(crtLayer).getSelection().clear();
    }

    public void selectAll() {
        layers.get(crtLayer).getSelection().fill();
    }

    public boolean hasSelected() {
        return layers.get(crtLayer).getSelection().hasSelected();
    }

    public boolean isMoving() {
        return touchCnt != 0;
    }
}
