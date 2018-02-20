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

import com.marton.imageeditor.R;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;

import org.w3c.dom.Text;

/**
 * Created by marton on 1/31/18.
 */

public class BrushSizeDialog extends BaseDialog {
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

        final View layout = inflater.inflate(R.layout.dialog_brush_size, null);

        final TextView textView = (TextView) layout.findViewById(R.id.brushSizeTextView);
        final SeekBar seekBar = (SeekBar) layout.findViewById(R.id.brushSizeSeekBar);

        link(seekBar, textView, (int)brush.getSize());

        builder.setView(layout)
                // Add action buttons
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        brush.setSize(((SeekBar)layout.findViewById(R.id.brushSizeSeekBar)).getProgress());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BrushSizeDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
