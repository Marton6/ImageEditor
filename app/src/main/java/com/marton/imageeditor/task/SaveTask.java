package com.marton.imageeditor.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.marton.imageeditor.MainActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SaveTask extends AsyncTask<Void, Void, Boolean>{

    private Bitmap bitmap;
    private ProgressDialog dialog;
    private Activity activity;

    public SaveTask(Activity activity, Bitmap bitmap) {
        this.activity = activity;
        this.bitmap = bitmap;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(activity, "",
                "Saving...", true);

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String path = Environment.getExternalStorageDirectory().toString().concat("/Image Editor/");
            File dir = new File(path);

            dir.mkdirs();

            Long time = System.currentTimeMillis()/1000;
            String timestamp = time.toString();

            File file = new File(path, "image-"+timestamp+".png");

            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        dialog.dismiss();
    }
}
