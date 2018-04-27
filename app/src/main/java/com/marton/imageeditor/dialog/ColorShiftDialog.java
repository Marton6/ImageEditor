package com.marton.imageeditor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marton.imageeditor.MainActivity;
import com.marton.imageeditor.R;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.effect.ColorShiftEffect;
import com.marton.imageeditor.tool.effect.Effect;

/**
 * Created by marton on 1/31/18.
 */

public class ColorShiftDialog extends BaseDialog {
    private ColorShiftEffect effect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        effect = (ColorShiftEffect) Tools.getEffectById((int)getArguments().get("effect"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.dialog_color_shift_effect, null);

        final TextView rt = (TextView) layout.findViewById(R.id.redEditText);
        final TextView gt = (TextView) layout.findViewById(R.id.greenEditText);
        final TextView bt = (TextView) layout.findViewById(R.id.blueEditText);
        final SeekBar rs = (SeekBar) layout.findViewById(R.id.redSeekBar);
        final SeekBar gs = (SeekBar) layout.findViewById(R.id.greenSeekBar);
        final SeekBar bs = (SeekBar) layout.findViewById(R.id.blueSeekBar);

        link(rs, rt, effect.getStrength());
        link(gs, gt, effect.getStrength());
        link(bs, bt, effect.getStrength());

        builder.setView(layout)
                // Add action buttons
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        effect.setARGB(1, (float)rs.getProgress(), (float)gs.getProgress(), (float)bs.getProgress());
                        ((MainActivity)getActivity()).getImageProcessor().applyEffect(effect);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ColorShiftDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
