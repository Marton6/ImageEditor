package com.marton.imageeditor.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.marton.imageeditor.surfaceView.Layer;
import com.marton.imageeditor.tool.effect.Effect;

public class ApplyEffectTask extends AsyncTask<Effect, Void, Boolean> {

    private Activity activity;

    private Layer layer;
    private int[] pixels, selection;
    private int w, h;
    private ProgressDialog dialog;

    public ApplyEffectTask(Activity activity, Layer layer, int[] pixels, int[] selection, int w, int h) {
        this.activity = activity;
        this.layer = layer;
        this.pixels = pixels;
        this.selection = selection;
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(activity, "",
                "Applying effect...", true);
    }

    @Override
    protected Boolean doInBackground(Effect... effects) {
        effects[0].apply(selection, pixels, w, h);
        layer.getSelection().clear();
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        layer.updateBitmap(pixels);
        dialog.dismiss();
    }
}
