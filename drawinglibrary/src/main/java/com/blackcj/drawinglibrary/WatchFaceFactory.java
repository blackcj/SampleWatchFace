package com.blackcj.drawinglibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Created by chris.black on 12/11/14.
 */
public class WatchFaceFactory {

    public static String SQUARE_TYPE = "square_watch_face";
    public static String ROUND_TYPE = "round_watch_face";

    public static WatchFace getWatchFace(String type) {
        if(type.equals(SQUARE_TYPE)) {
            return new SquareWatchFace();
        } else {
            return new RoundWatchFace();
        }
    }
}
