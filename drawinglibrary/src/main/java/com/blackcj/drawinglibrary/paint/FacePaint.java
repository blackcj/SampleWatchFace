package com.blackcj.drawinglibrary.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

/**
 * Created by chris.black on 12/11/14.
 *
 * Contains all of the paint for a watch face
 */
public class FacePaint {
    public Paint backgroundPaint;
    public Paint borderPaint;
    public Paint smallTickPaint;
    public Paint largeTickPaint;
    public Paint radialPaint;
    public Paint mHourPaint;
    public Paint mMinutePaint;
    public Paint mSecondPaint;
    public Paint bitmapPaint;
    public Paint textPaint;

    protected float mRatio = 1;


    public FacePaint() {
        init();
        setDefaultPaint();
        setAntiAlias(true);
    }

    public void init() {
        backgroundPaint = new Paint();
        borderPaint = new Paint();
        smallTickPaint = new Paint();
        largeTickPaint = new Paint();
        radialPaint = new Paint();
        mHourPaint = new Paint();
        mMinutePaint = new Paint();
        mSecondPaint = new Paint();
        bitmapPaint = new Paint();
        textPaint = new TextPaint();
    }

    public void setDefaultPaint() {

        backgroundPaint.setColor(Color.WHITE);

        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        smallTickPaint.setColor(Color.BLACK);
        smallTickPaint.setStrokeWidth(2);

        largeTickPaint.setColor(Color.BLACK);
        largeTickPaint.setStrokeWidth(8);

        radialPaint.setColor(Color.WHITE);
        radialPaint.setStyle(Paint.Style.FILL);

        mHourPaint.setARGB(255, 200, 200, 200);
        mHourPaint.setStrokeWidth(5.f);
        mHourPaint.setStrokeCap(Paint.Cap.ROUND);

        mMinutePaint.setARGB(255, 200, 200, 200);
        mMinutePaint.setStrokeWidth(3.f);
        mMinutePaint.setStrokeCap(Paint.Cap.ROUND);

        mSecondPaint.setARGB(255, 255, 0, 0);
        mSecondPaint.setStrokeWidth(2.f);
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(16 * mRatio);
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    public void setRatio(float ratio) {
        mRatio = ratio;
        if(textPaint != null) {
            textPaint.setTextSize(16 * mRatio);
        }
    }

    public void setAntiAlias(boolean value) {
        backgroundPaint.setAntiAlias(value);
        borderPaint.setAntiAlias(value);
        smallTickPaint.setAntiAlias(value);
        largeTickPaint.setAntiAlias(value);
        mHourPaint.setAntiAlias(value);
        mMinutePaint.setAntiAlias(value);
        mSecondPaint.setAntiAlias(value);
        bitmapPaint.setAntiAlias(value);
        bitmapPaint.setFilterBitmap(value);
        textPaint.setAntiAlias(value);
    }
}
