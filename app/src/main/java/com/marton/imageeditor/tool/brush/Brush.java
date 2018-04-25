package com.marton.imageeditor.tool.brush;

import com.marton.imageeditor.surfaceView.SelectionMask;
import com.marton.imageeditor.surfaceView.Selector;
import com.marton.imageeditor.utility.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marton on 1/6/18.
 */

public abstract class Brush implements Serializable{
    protected float size;

    // running
    private boolean running;

    protected List<Vector2> points;

    //passed stuff
    protected Selector selector;
    protected int[] imgPixels;
    protected int w, h;

    public Brush() {
        running = false;
        points = new ArrayList<>();
    }

    public Brush(float size) {
        this.size = size;
        running = false;
        points = new ArrayList<>();
    }

    protected void select(float x, float y) {

    }

    public void selectLine(Line line, Selector selector){
        this.selector = selector;
        w = selector.getW();
        h = selector.getH();

        imgPixels = selector.getImgPixels();

        if(line.p1.x>line.p2.x){
            com.marton.imageeditor.utility.Vector2 p3 = line.p1;
            line.p1 = line.p2;
            line.p2 = p3;
        }

        float slope = (line.p1.y-line.p2.y)/(line.p1.x-line.p2.x);
        float dev1 = line.p1.y-line.p1.x*slope;

        for(int x1 = (int)line.p1.x; x1<=line.p2.x; x1++){
            float y1 =  dev1+x1*slope;
            select(x1, y1);
        }
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    class Vector2{
        public float x, y;

        public Vector2(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
