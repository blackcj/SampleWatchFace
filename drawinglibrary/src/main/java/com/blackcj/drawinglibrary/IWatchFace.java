package com.blackcj.drawinglibrary;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.format.Time;

/**
 * Created by chris.black on 12/11/14.
 */
public interface IWatchFace {

    public void drawSmallTicks(Canvas canvas, int numTicks);
    public void drawLargeTicks(Canvas canvas, int numTicks);
    public void drawBackground(Canvas canvas);
    public void drawBorder(Canvas canvas);
    public void drawTraditional(Canvas canvas, Time time, Boolean isAmbientMode);
    public void drawNumbers(final Canvas canvas, int num);
}
