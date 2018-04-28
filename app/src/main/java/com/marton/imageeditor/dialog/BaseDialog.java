package com.marton.imageeditor.dialog;

import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by marton on 1/31/18.
 */

public abstract class BaseDialog extends DialogFragment {

    protected void link(final SeekBar seekBar, final TextView textView, float defProportion){
        int defVal = (int)(defProportion*seekBar.getMax());
        textView.setText(defVal+"");
        seekBar.setProgress(defVal);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!textView.getText().toString().matches(seekBar.getProgress()+""))textView.setText(seekBar.getProgress()+"");
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
