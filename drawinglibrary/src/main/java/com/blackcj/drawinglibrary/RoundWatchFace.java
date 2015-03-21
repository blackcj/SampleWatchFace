package com.blackcj.drawinglibrary;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.blackcj.drawinglibrary.paint.FacePaint;

/**
 * Created by chris.black on 12/11/14.
 */
public class RoundWatchFace extends WatchFace {

    public RoundWatchFace() {
        super();
        largeTickLength = 35;
        textMargin = 60;
    }

    public RoundWatchFace(FacePaint facepaint) {
        super(facepaint);
    }

    public RoundWatchFace(int width, int height) {
        super(width, height);
        largeTickLength = 35;
        textMargin = 60;
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, halfWidth, facePaint.backgroundPaint);
    }

    public void drawSmallTicks(Canvas canvas, int numTicks) {
        drawTicks(canvas, numTicks, facePaint.smallTickPaint);
    }

    public void drawSmallTickMask(final Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, halfWidth - smallTickLength, facePaint.backgroundPaint);
    }

    public void drawBorder(Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, halfWidth - borderThickness, facePaint.borderPaint);
    }

    public void drawTickBorder(Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, halfWidth - smallTickLength, facePaint.borderPaint);
    }

    @Override
    public void drawTraditional(final Canvas canvas, Time time, Boolean isAmbientMode) {
        drawBackground(canvas);
        if(showMinuteTicks) {
            drawSmallTicks(canvas, 60);
            drawBorder(canvas);
            drawSmallTickMask(canvas);
            drawLargeTicks(canvas, 12);
            drawTickBorder(canvas);
            drawLargeTickMask(canvas);
        }else {
            drawCircleTicks(canvas, 12, 8);
        }
        drawNumbers(canvas, 12);
        if(showRadialGradient) {
            drawRadialGradient(canvas);
        }

        drawHands(canvas, time, isAmbientMode);
    }

    public void drawCircleTicks(Canvas canvas, int numTicks, int dotRadius) {
        drawCircles(canvas, numTicks, dotRadius, facePaint.largeTickPaint);
    }

    public void drawLargeTicks(Canvas canvas, int numTicks) {
        drawTicks(canvas, numTicks, facePaint.largeTickPaint);
    }

    public void drawLargeTickMask(final Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, halfWidth - largeTickLength, facePaint.backgroundPaint);
    }

    public void drawTicks(Canvas canvas, int numTicks, Paint paint) {
        int angle = 0;
        while (angle < 360) {
            canvas.drawLine(halfWidth, halfHeight, halfWidth + (int)(halfWidth * Math.cos(Math.toRadians(angle))), halfHeight + (int)(halfWidth * Math.sin(Math.toRadians(angle))), paint);
            angle += 360 / numTicks;
        }
    }

    @Override
    public void drawNumbers(final Canvas canvas, int num) {
        int start = 0;
        int jump = numbers.length / num;
        for(int n = 0; n < numbers.length; n += jump) {
            Rect bounds = new Rect();
            String timeText = numbers[n];
            facePaint.textPaint.getTextBounds(timeText, 0, timeText.length(), bounds);
            // Offset to account for the text center point
            int x = (bounds.width()) / 2;
            int y = (bounds.height()) / 2;
            canvas.drawText(timeText, halfWidth + (int) ((halfWidth - textMargin) * Math.cos(Math.toRadians(start))) - x, halfHeight + (int) ((halfWidth - textMargin) * Math.sin(Math.toRadians(start))) + y, facePaint.textPaint);

            start += 360 / num;
        }
    }

    public void drawCircles(Canvas canvas, int numCircles, int dotRadius, Paint paint) {
        int start = 0;
        while (start < 360) {
            canvas.drawCircle(halfWidth + (int) ((halfWidth - margin) * Math.cos(Math.toRadians(start))), halfHeight + (int) ((halfWidth - margin) * Math.sin(Math.toRadians(start))), dotRadius, paint);
            start += 360 / numCircles;
        }
    }
}
