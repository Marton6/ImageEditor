package com.marton.imageeditor.tool.brush;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marton on 1/6/18.
 */

public abstract class Brush implements Runnable, Serializable{
    protected float size;

    // running
    private boolean running;

    // passed params
    protected int[] pixels;
    protected int[] imgPixels;
    protected int w, h;

    protected List<Vector2> points;

    public Brush() {
        running = false;
        points = new ArrayList<>();
    }

    public Brush(float size) {
        this.size = size;
        running = false;
        points = new ArrayList<>();
    }

    @Override
    public void run() {
        while(points.size() > 0) {
            select(points.get(0).x, points.get(0).y);
            points.remove(0);
        }
        running = false;
    }

    protected void select(float x, float y) {

    }

    public void select(int[] imgPixels, int[] pixels, float x, float y, int w, int h){
        this.pixels = pixels;
        this.imgPixels = imgPixels;
        points.add(new Vector2(x, y));
        this.w = w;
        this.h = h;
        if(!running) new Thread(this).run();
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
