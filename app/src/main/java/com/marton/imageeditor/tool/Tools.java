package com.marton.imageeditor.tool;

import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.brush.CircleBrush;
import com.marton.imageeditor.tool.brush.SmartBrush;
import com.marton.imageeditor.tool.effect.BlurEffect;
import com.marton.imageeditor.tool.effect.ColorShiftEffect;
import com.marton.imageeditor.tool.effect.ContrastEffect;
import com.marton.imageeditor.tool.effect.DarkenEffect;
import com.marton.imageeditor.tool.effect.Effect;

/**
 * Created by marton on 1/3/18.
 */

public class Tools {
    // render mode: which layers should be rendered
    public static final int RENDER_MODE_ALL_LAYERS = 0;
    public static final int RENDER_MODE_CURRENT_LAYER = 1;
    public static final int RENDER_MODE_ALL_LAYERS_BELOW = 2;

    private int renderMode = RENDER_MODE_CURRENT_LAYER;

    //tool
    public static final int TOOL_SELECT = 0;
    public static final int TOOL_MOVE = 1;

    private int currentTool = TOOL_MOVE;

    //brushes
    private static Brush[] brushes;
    private int crtBrush;
    public static final int BRUSH_CIRCLE = 0;

    public static final int BRUSH_SMART = 1;
    public static final int BRUSH_REGION = 2;

    //effects
    private static Effect[] effects;

    public static final int EFFECT_COLOR_SHIFT = 0;
    public static final int EFFECT_BLUR = 1;
    public static final int EFFECT_DARKEN = 2;
    public static final int EFFECT_CONTRAST = 3;

    public Tools(){
        brushes = new Brush[2];
        brushes[0] = new CircleBrush(40);
        brushes[1] = new SmartBrush(40, 50);

        effects = new Effect[6];
        effects[0] = new ColorShiftEffect(1, 1, 1, 1);
        effects[1] = new BlurEffect();
        effects[2] = new DarkenEffect();
        effects[3] = new ContrastEffect();

        crtBrush = 0;
    }

    public int getRenderMode() {
        return renderMode;
    }

    public String getRenderModeName() {
        switch (renderMode){
            case 0: return "ALL";
            case 1: return "CURRENT";
            case 2: return "ALL BELOW";
        }

        return null;
    }

    public void cycleRenderMode() {
        renderMode++;
        renderMode %= 3;
    }

    public void setCurrentTool(int currentTool) {
        this.currentTool = currentTool;
    }

    public int getCurrentTool() {
        return currentTool;
    }

    public void setCrtBrush(int crtBrush){
        this.crtBrush = crtBrush;
    }

    public Brush getCrtBrush() {
        return brushes[crtBrush];
    }

    public int getCrtBrushId() {
        return crtBrush;
    }

    public static Brush getBrushById(int id){
        return brushes[id];
    }

    public static Effect getEffectById(int effect) {
        return effects[effect];
    }
}
