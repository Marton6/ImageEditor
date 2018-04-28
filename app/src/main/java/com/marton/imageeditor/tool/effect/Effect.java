package com.marton.imageeditor.tool.effect;

/**
 * Created by marton on 1/6/18.
 */

public abstract class Effect {

    protected float strength;

    public Effect() {
    }

    public void apply(int[] selection, int[] pixels, int w, int h){
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }
}
