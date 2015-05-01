package com.blackcj.drawinglibrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.Log;

import com.blackcj.drawinglibrary.paint.FacePaint;


/**
 * Created by chris.black on 3/16/15.
 *
 * Base functionality for a watch face.
 */
public abstract class WatchFace implements IWatchFace {

    protected String[] numbers = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2"};

    public static int SMALL_TICK_LENGTH = 8;
    public static int LARGE_TICK_LENGTH = 12;
    public static int MARGIN = 8;
    public static int MARGIN_TEXT = 22;

    protected int faceWidth;
    protected int faceHeight;
    protected int halfWidth;
    protected int halfHeight;
    protected float mRatio = 1;
    protected int margin = (int)(MARGIN * mRatio);
    protected int textMargin = (int)(MARGIN_TEXT * mRatio);;

    protected RectF backgroundRect;
    protected RectF smallTickMask;
    protected int smallTickLength = (int)(SMALL_TICK_LENGTH * mRatio);

    protected RectF largeTickMask;
    protected int largeTickLength = (int)(LARGE_TICK_LENGTH * mRatio);

    protected RectF borderRect;
    protected int borderThickness = 2;

    protected boolean showRadialGradient = false;
    protected boolean showMinuteTicks = true;
    protected boolean showSecondHand = false;

    protected FacePaint facePaint;

    protected int radialColor = 0;

    public WatchFace() {
        setBounds(4, 4);
        facePaint = new FacePaint();
    }

    public WatchFace(FacePaint facepaint) {
        setBounds(4, 4);
        this.facePaint = facepaint;
    }

    public WatchFace(int width, int height) {
        setBounds(width, height);
        facePaint = new FacePaint();
    }

    public void setAntiAlias(Boolean value) {
        facePaint.setAntiAlias(value);
    }

    public void setBackgroundColor(int color) {
        facePaint.backgroundPaint.setColor(color);
    }

    public void setShowRadialGradient(boolean value) {
        showRadialGradient = value;
    }

    public void setShowSecondHand(boolean value) {
        showSecondHand = value;
    }

    public boolean getShowSecondHand() {
        return showSecondHand;
    }

    public void setShowMinuteTicks(boolean value) {
        showMinuteTicks = value;
    }

    public void setBounds(int width, int height) {
        this.faceWidth = width;
        this.faceHeight = height;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;

        backgroundRect = new RectF(0,0,faceWidth, faceHeight);

        borderRect = new RectF(borderThickness,borderThickness,faceWidth - borderThickness, faceHeight - borderThickness);
        smallTickMask = new RectF(smallTickLength,smallTickLength,faceWidth - smallTickLength, faceHeight - smallTickLength);
        largeTickMask = new RectF(largeTickLength,largeTickLength,faceWidth - largeTickLength, faceHeight - largeTickLength);
        if(facePaint != null) {
            setRadialColor(radialColor);
            facePaint.setRatio(mRatio * (280.0f / faceWidth));
        }
        setRatio(mRatio);
    }

    public void drawRadialGradient(final Canvas canvas) {
        canvas.drawCircle(this.halfWidth, this.halfHeight, this.halfWidth / 2, facePaint.radialPaint);
    }

    public void setRadialColor(int color) {
        radialColor = color;
        int start = Color.argb(75, Color.red(color), Color.green(color), Color.blue(color));
        int finish = Color.argb(0,Color.red(color),Color.green(color),Color.blue(color));
        facePaint.radialPaint.setShader(new RadialGradient(this.halfWidth, this.halfHeight, this.halfWidth / 2, start, finish, Shader.TileMode.MIRROR));
    }

    protected Bitmap hourHand;
    protected Bitmap minuteHand;
    protected Bitmap secondHand;
    protected Matrix matrix = new Matrix();

    public void drawHands(final Canvas canvas, Time mTime, Boolean isAmbientMode) {
        float centerX = faceWidth / 2f;
        float centerY = faceHeight / 2f;

        // Used for simple lines
        float secRot = mTime.second / 30f * (float) Math.PI;
        int minutes = mTime.minute;
        float minRot = minutes / 30f * (float) Math.PI;
        float hrRot = ((mTime.hour + (minutes / 60f)) / 6f ) * (float) Math.PI;
        float secLength = centerX - 20;
        float minLength = centerX - 40;
        float hrLength = centerX - 5;
        float scale = (250.0f / faceHeight);

        // Used for bitmap images
        int hour = mTime.hour;
        int min = mTime.minute;
        int sec = mTime.second;
        int rotHr = (int) (30 * hour + 0.5f * min);
        int rotMin = 6 * min;
        int rotSec = 6 * sec;

        if (!isAmbientMode && showSecondHand) {
            // Draw second hand
            if(secondHand == null) {
                float secX = (float) Math.sin(secRot) * secLength;
                float secY = (float) -Math.cos(secRot) * secLength;
                canvas.drawLine(centerX, centerY, centerX + secX, centerY + secY, facePaint.mSecondPaint);
            } else {
                float px = (faceWidth - secondHand.getWidth() * scale) / 2;
                float py = (faceHeight - secondHand.getHeight() * scale) / 2;
                matrix.reset();
                matrix.setRotate(rotSec, secondHand.getWidth()/2, secondHand.getHeight()/2);
                matrix.postScale(scale, scale);
                matrix.postTranslate(px, py);
                canvas.drawBitmap(minuteHand, matrix, facePaint.bitmapPaint);
            }

        }
        // Draw minute
        if(minuteHand == null) {
            float minX = (float) Math.sin(minRot) * minLength;
            float minY = (float) -Math.cos(minRot) * minLength;
            canvas.drawLine(centerX, centerY, centerX + minX, centerY + minY, facePaint.mMinutePaint);
        } else {
            float px = (faceWidth - minuteHand.getWidth() * scale) / 2;
            float py = (faceHeight - minuteHand.getHeight() * scale) / 2;
            matrix.reset();
            matrix.setRotate(rotMin, minuteHand.getWidth()/2, minuteHand.getHeight()/2);
            matrix.postScale(scale, scale);
            matrix.postTranslate(px, py);
            canvas.drawBitmap(minuteHand, matrix, facePaint.bitmapPaint);
        }

        // Draw hour
        if(hourHand == null) {
            float hrX = (float) Math.sin(hrRot) * hrLength;
            float hrY = (float) -Math.cos(hrRot) * hrLength;
            canvas.drawLine(centerX, centerY, centerX + hrX, centerY + hrY, facePaint.mHourPaint);
        } else {

            float px = (faceWidth - hourHand.getWidth() * scale) / 2.0f;
            float py = (faceHeight - hourHand.getHeight() * scale) / 2.0f;
            matrix.reset();
            matrix.setRotate(rotHr, hourHand.getWidth()/2, hourHand.getHeight()/2);
            matrix.postScale(scale, scale);
            matrix.postTranslate(px, py);
            canvas.drawBitmap(hourHand, matrix, facePaint.bitmapPaint);
        }
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
        if(facePaint != null) {
            facePaint.setRatio(mRatio);
        }
        if(faceWidth != 4) {
            margin = (int) (MARGIN * mRatio * (280.0f / faceWidth));
            textMargin = (int) (MARGIN_TEXT * mRatio * (280.0f / faceWidth));
            smallTickLength = (int) (SMALL_TICK_LENGTH * mRatio * (280.0f / faceWidth));
            largeTickLength = (int) (LARGE_TICK_LENGTH * mRatio * (280.0f / faceWidth));
        }
    }

    public void setMinuteHand(Drawable drawable) {
        minuteHand = ((BitmapDrawable) drawable).getBitmap();
    }

    public void setHourHand(Drawable drawable) {
        hourHand = ((BitmapDrawable) drawable).getBitmap();
    }

    public void setBorderThickness(int thickness) {
        borderThickness = thickness;
        borderRect = new RectF(borderThickness,borderThickness,faceWidth - borderThickness, faceHeight - borderThickness);
    }

    public int findX(int angle, int y) {
        return (int)(Math.cos(Math.toRadians(angle)) / Math.sin(Math.toRadians(angle)) * y);
    }

    public int findY(int angle, int halfHeight) {
        return (int)(Math.tan(Math.toRadians(angle)) * halfHeight);
    }
}
