package com.marton.imageeditor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
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

        final View colorBox = layout.findViewById(R.id.colorBox);

        final TextView rt = (TextView) layout.findViewById(R.id.redEditText);
        final TextView gt = (TextView) layout.findViewById(R.id.greenEditText);
        final TextView bt = (TextView) layout.findViewById(R.id.blueEditText);
        final SeekBar rs = (SeekBar) layout.findViewById(R.id.redSeekBar);
        final SeekBar gs = (SeekBar) layout.findViewById(R.id.greenSeekBar);
        final SeekBar bs = (SeekBar) layout.findViewById(R.id.blueSeekBar);

        link(rs, rt, effect.getStrength(), colorBox, Color.RED);
        link(gs, gt, effect.getStrength(), colorBox, Color.GREEN);
        link(bs, bt, effect.getStrength(), colorBox, Color.BLUE);

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

    protected void link(final SeekBar seekBar, final TextView textView, float defProportion, final View viewToColor, final int channel){
        int defVal = (int)(defProportion*seekBar.getMax());
        textView.setText(defVal+"");
        seekBar.setProgress(defVal);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!textView.getText().toString().matches(seekBar.getProgress()+""))textView.setText(seekBar.getProgress()+"");

                int color = Color.WHITE;
                Drawable background = viewToColor.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();

                switch (channel){
                    case Color.RED:
                        viewToColor.setBackgroundColor(Color.rgb(seekBar.getProgress(), Color.green(color), Color.blue(color)));
                        break;

                    case Color.GREEN:
                        viewToColor.setBackgroundColor(Color.rgb(Color.red(color), seekBar.getProgress(), Color.blue(color)));
                        break;

                    case Color.BLUE:
                        viewToColor.setBackgroundColor(Color.rgb(Color.red(color), Color.green(color), seekBar.getProgress()));
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        textView.setSelectAllOnFocus(true);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(seekBar.getProgress() != Integer.parseInt(textView.getText().toString()))seekBar.setProgress(Integer.parseInt(textView.getText().toString()));
                return true;
            }
        });
    }
}
