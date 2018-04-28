package com.marton.imageeditor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marton.imageeditor.dialog.BrushSizeDialog;
import com.marton.imageeditor.dialog.ColorShiftDialog;
import com.marton.imageeditor.dialog.EffectStrengthDialog;
import com.marton.imageeditor.dialog.FillSensitivityDialog;
import com.marton.imageeditor.dialog.SmartBrushDialog;
import com.marton.imageeditor.menu.BrushFragment;
import com.marton.imageeditor.menu.EffectFragment;
import com.marton.imageeditor.surfaceView.DrawingSurfaceView;
import com.marton.imageeditor.surfaceView.ImageProcessor;
import com.marton.imageeditor.surfaceView.Layer;
import com.marton.imageeditor.tool.Tools;
import com.marton.imageeditor.tool.brush.Brush;
import com.marton.imageeditor.tool.effect.ColorShiftEffect;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Tools tools;
    private DrawingSurfaceView drawingSurfaceView;
    private ImageProcessor imageProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingSurfaceView = findViewById(R.id.drawingSurfaceView);
        imageProcessor = drawingSurfaceView.getImageProcessor();
        tools = imageProcessor.getTools();

        /*Bitmap bitmap = null;
        try {
            bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test_image)).getBitmap();
            imageProcessor.addLayer(new Layer(bitmap));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleImage(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
        if (type.startsWith("image/")) {
            handleSendMultipleImages(intent); // Handle multiple images being sent
        }
    }

}

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            for(Uri imgUri:imageUris){
                try {
                    loadImageFromUri(imgUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri == null) return;
        try {
            loadImageFromUri(imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImageFromUri(Uri imageUri) throws FileNotFoundException {
        InputStream is = getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(is);

        imageProcessor.addLayer(new Layer(this, bitmap));
    }

    // button onClick methods
    public void onClickRenderModeButton(View view){
        tools.cycleRenderMode();
        ((Button) view).setText(tools.getRenderModeName());
    }

    public void onClickSave(View view){
        imageProcessor.saveImage();
    }

    // bottom menu
    public void onClickMoveButton(View view){
        if(tools.getCurrentTool() == Tools.TOOL_MOVE) return;
        addMenuFragment(null);
        tools.setCurrentTool(Tools.TOOL_MOVE);
    }

    public void onClickSelectButton(View view){
        tools.setCurrentTool(Tools.TOOL_SELECT);
        addMenuFragment(new BrushFragment());
    }

    public void onClickEffectsButton(View view){
        addMenuFragment(new EffectFragment());
    }

    // select Menu / brush Menu
    public void onClickCircleBrushButton(View view){
        tools.setCrtBrush(Tools.BRUSH_CIRCLE);
        showBrushDialog(new BrushSizeDialog());
    }

    public void onClickSmartBrushButton(View view){
        tools.setCrtBrush(Tools.BRUSH_SMART);
        showBrushDialog(new SmartBrushDialog());
    }
    public void onClickRegionFillButton(View view){
        tools.setCrtBrush(Tools.BRUSH_REGION);
        showBrushDialog(new FillSensitivityDialog());
    }

    public void onClickSelectAllButton(View view){
        if(imageProcessor.hasSelected())
            imageProcessor.clearSelection();
        else
            imageProcessor.selectAll();
    }

    private void showBrushDialog(DialogFragment dialog) {
        Bundle bundle = new Bundle();
        bundle.putInt("brush", tools.getCrtBrushId());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "brushDialog");
    }

    // effects menu
    public void onClickColorEffectButton(View view){
        showEffectDialog(new ColorShiftDialog(), Tools.EFFECT_COLOR_SHIFT);
    }

    public void onClickBlurEffectButton(View view){
        showEffectDialog(new EffectStrengthDialog(), Tools.EFFECT_BLUR);
    }

    public void onClickDarkenEffectButton(View view){
        showEffectDialog(new EffectStrengthDialog(), Tools.EFFECT_DARKEN);
    }

    public void onClickContrastEffectButton(View view){
        showEffectDialog(new EffectStrengthDialog(), Tools.EFFECT_CONTRAST);
    }

    private void showEffectDialog(DialogFragment dialog, int effectId) {
        Bundle bundle = new Bundle();
        bundle.putInt("effect", effectId);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "effectDialog");
    }

    // swapping menus
    private void addMenuFragment(Fragment fragment) {
        if(fragment == null){
            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.menuBar)).commit();
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menuBar, fragment);
        transaction.commit();
    }


    public void test(View view){
        drawingSurfaceView.getImageProcessor().applyEffect(new ColorShiftEffect(1, 2, .5f, .5f));
        drawingSurfaceView.getImageProcessor().clearSelection();
    }

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }

    public Tools getTools() {
        return tools;
    }
}
