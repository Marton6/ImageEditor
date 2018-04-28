package com.marton.imageeditor.surfaceView;

import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.utility.Line;
import com.marton.imageeditor.utility.Vector2;

import java.util.LinkedList;

/**
 * Created by marton on 4/9/18.
 */

public class Selector implements Runnable{
    private final SelectionMask selectionMask;
    private Brush crtBrush;
    private Vector2 lastPoint;
    private LinkedList<Line> lines;

    private boolean running;

    protected int[] imgPixels;
    protected int w, h;

    public Selector(Layer layer, SelectionMask selectionMask) {
        lines = new LinkedList<>();
        this.selectionMask = selectionMask;
        running = false;
        imgPixels = layer.getPixels();
        w = selectionMask.getW();
        h = selectionMask.getH();
    }

    @Override
    public void run() {
        while(!lines.isEmpty()){
            try {
                crtBrush.selectLine(lines.getFirst(), this);
                lines.removeFirst();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        running = false;
    }

    private void launchSelectorThread(){
        if(!running){
            running = true;
            new Thread(this).start();
        }
    }

    public void startSelection(Brush crtBrush, float px, float py){
        this.crtBrush = crtBrush;
        lastPoint = new Vector2(px, py);
    }

    public void continueSelection(float px, float py){
        if(lastPoint != null) {
            lines.add(new Line(lastPoint, new Vector2(px, py)));
            launchSelectorThread();
        }
        startSelection(crtBrush, px, py);
    }

    public void endSelection(float px, float py) {
        continueSelection(px, py);
        lastPoint = null;
    }

    public int[] getImgPixels() {
        return imgPixels;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public SelectionMask getSelectionMask() {
        return selectionMask;
    }
}
