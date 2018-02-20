package com.marton.imageeditor.tool.effect;

/**
 * Created by marton on 1/6/18.
 */

public abstract class Effect {

    protected int strength;

    public Effect() {
    }

    public void apply(int[] selection, int[] pixels, int w, int h){
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
