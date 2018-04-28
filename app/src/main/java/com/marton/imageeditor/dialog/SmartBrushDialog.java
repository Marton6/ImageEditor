package com.marton.imageeditor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marton.imageeditor.R;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.brush.SmartBrush;

import org.w3c.dom.Text;

/**
 * Created by marton on 1/31/18.
 */

public class SmartBrushDialog extends BaseDialog {
    private SmartBrush brush;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brush = (SmartBrush) Tools.getBrushById((int)getArguments().get("brush"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View layout = inflater.inflate(R.layout.dialog_smart_brush, null);

        final TextView sizeTextView = (TextView) layout.findViewById(R.id.smartBrushSizeTextView);
        final SeekBar sizeSeekBar = (SeekBar) layout.findViewById(R.id.smartBrushSizeSeekBar);
        final TextView sensTextView = (TextView) layout.findViewById(R.id.smartBrushSensitivityTextView);
        final SeekBar sensSeekBar = (SeekBar) layout.findViewById(R.id.smartBrushSensitivitySeekBar);

        link(sizeSeekBar, sizeTextView, .5f);
        link(sensSeekBar, sensTextView, brush.getSensitivity()*1.f/sensSeekBar.getMax());

        builder.setView(layout)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        brush.setSize(sizeSeekBar.getProgress());
                        brush.setSensitivity(sensSeekBar.getProgress());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmartBrushDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
