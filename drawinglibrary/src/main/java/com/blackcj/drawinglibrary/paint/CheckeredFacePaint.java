package com.blackcj.drawinglibrary.paint;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

/**
 * Created by chris.black on 3/6/15.
 */
public class CheckeredFacePaint extends FacePaint {

    public CheckeredFacePaint() {
        init();
        setDefaultPaint();
        setAntiAlias(true);
    }

    @Override
    public void init() {
        super.init();
        backgroundPaint = createCheckerBoard(40);
    }

    public void setDefaultPaint() {
        int color = 0xffdddddd;
        borderPaint.setColor(color);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        smallTickPaint.setColor(color);
        smallTickPaint.setStrokeWidth(2);

        largeTickPaint.setColor(color);
        largeTickPaint.setStrokeWidth(8);

        radialPaint.setColor(color);
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

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private Paint createCheckerBoard(int pixelSize)
    {
        Bitmap bitmap = Bitmap.createBitmap(pixelSize * 2, pixelSize * 2, Bitmap.Config.ARGB_8888);

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setShader(new RadialGradient(pixelSize, pixelSize,
                pixelSize / 2, Color.GRAY, Color.WHITE, Shader.TileMode.MIRROR));
        //fill.setColor(0xff565656);
        //fill.setColor(0xff5585AA);

        Paint fill2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill2.setStyle(Paint.Style.FILL);
        fill2.setColor(0xff343434);
        //fill2.setColor(0xff6BB3E4);

        Canvas canvas = new Canvas(bitmap);
        Rect rect2 = new Rect(0, 0, pixelSize * 2, pixelSize * 2);
        canvas.drawRect(rect2, fill2);
        Rect rect = new Rect(0, 0, pixelSize, pixelSize);
        canvas.drawRect(rect, fill);
        rect.offset(pixelSize, pixelSize);
        canvas.drawRect(rect, fill);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.REPEAT, BitmapShader.TileMode.REPEAT));
        return paint;
    }
}
