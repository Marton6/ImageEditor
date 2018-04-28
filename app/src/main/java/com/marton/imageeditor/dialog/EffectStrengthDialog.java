package com.marton.imageeditor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marton.imageeditor.MainActivity;
import com.marton.imageeditor.R;
import com.marton.imageeditor.surfaceView.ImageProcessor;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.effect.Effect;

import org.w3c.dom.Text;

/**
 * Created by marton on 1/31/18.
 */

public class EffectStrengthDialog extends BaseDialog {
    private Effect effect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        effect = Tools.getEffectById((int)getArguments().get("effect"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.dialog_effect_strength, null);

        final TextView textView = (TextView) layout.findViewById(R.id.strengthTextView);
        final SeekBar seekBar = (SeekBar) layout.findViewById(R.id.strengthSeekBar);

        link(seekBar, textView, effect!=null?effect.getStrength():0);

        builder.setView(layout)
                // Add action buttons
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        effect.setStrength(seekBar.getProgress()*1.f/seekBar.getMax());
                        ((MainActivity)getActivity()).getImageProcessor().applyEffect(effect);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EffectStrengthDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
