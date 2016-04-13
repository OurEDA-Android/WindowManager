package com.app.windowmanagerdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by OurEDA on 2016/4/13.
 */
public class MainService extends Service {

    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int id) {
        String msg = "This is a text message";
        final WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_system, null);
        //view.setBackgroundColor(Color.argb(50, 102, 102, 204));
        TextView tv = (TextView) view.findViewById(R.id.textview);
        tv.setText(msg);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.width = 400;
        params.height = 400;
        //params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        params.format = PixelFormat.TRANSLUCENT;
        //params.format = PixelFormat.TRANSPARENT;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wm.addView(view, params);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            wm.removeViewImmediate(view);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, id);
    }
}