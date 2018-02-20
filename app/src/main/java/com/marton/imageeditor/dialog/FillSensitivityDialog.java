package com.marton.imageeditor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marton.imageeditor.R;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;

/**
 * Created by marton on 1/31/18.
 */

public class FillSensitivityDialog extends BaseDialog {
    private Brush brush;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brush = Tools.getBrushById((int)getArguments().get("brush"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.dialog_fill_sensitivity, null);

        final TextView textView = (TextView) layout.findViewById(R.id.fillSensitivityTextView);
        final SeekBar seekBar = (SeekBar) layout.findViewById(R.id.fillSensitivitySeekBar);

        link(seekBar, textView, 0);
        
        builder.setView(layout)
                // Add action buttons
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FillSensitivityDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
