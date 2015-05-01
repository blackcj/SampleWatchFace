package com.sample.watchface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.View;

import com.blackcj.drawinglibrary.SquareWatchFace;
import com.blackcj.drawinglibrary.WatchFace;
import com.blackcj.drawinglibrary.paint.FacePaint;

import java.util.Calendar;

/**
 * Created by chris.black on 12/11/14.
 */
public class DrawView extends View {
    WatchFace watchFace;
    Time mTime;

    public DrawView(Context context) {
        super(context);
        watchFace = new SquareWatchFace();
        mTime = new Time();
    }

    @Override
    public void onDraw(Canvas canvas) {
        mTime.setToNow();
        if(watchFace != null) {
            watchFace.setBounds(this.getWidth(), this.getHeight());
            watchFace.drawTraditional(canvas, mTime, false);
        }

        this.setDrawingCacheEnabled(true);
    }

    public void setFaceBackgroundColor(int color) {
        watchFace.setBackgroundColor(color);
    }

    public void setRadialColor(int color) {
        watchFace.setRadialColor(color);
    }

    public void showSecondHand(boolean value) {
        watchFace.setShowSecondHand(value);
    }

    public void showMinuteTicks(boolean value) {
        watchFace.setShowMinuteTicks(value);
    }

    public void showRadialGradient(boolean value) {
        watchFace.setShowRadialGradient(value);
    }

}
