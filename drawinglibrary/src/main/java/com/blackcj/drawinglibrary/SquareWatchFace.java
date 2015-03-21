package com.blackcj.drawinglibrary;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.blackcj.drawinglibrary.paint.FacePaint;

/**
 * Created by chris.black on 12/11/14.
 */
public class SquareWatchFace extends WatchFace {

    public SquareWatchFace() {
        super();
        smallTickLength = 10;
        largeTickLength = 15;
    }

    public SquareWatchFace(FacePaint facepaint) {
        super(facepaint);
    }

    public SquareWatchFace(int width, int height) {
        super(width, height);
        smallTickLength = 10;
        largeTickLength = 15;
    }

    public void drawTraditional(final Canvas canvas, Time time, Boolean isAmbientMode) {
        drawBackground(canvas);
        if(showMinuteTicks) {
            drawSmallTicks(canvas, 60);
            drawBorder(canvas);
            drawSmallTickMask(canvas);
        }

        drawLargeTicks(canvas, 12);
        drawLargeTickMask(canvas);

        drawNumbers(canvas, 12);

        if(showRadialGradient) {
            drawRadialGradient(canvas);
        }

        drawHands(canvas, time, isAmbientMode);

    }

    public void drawCircleTicks(Canvas canvas, int numTicks, int dotRadius) {
        drawCircles(canvas, numTicks, dotRadius, facePaint.largeTickPaint);
        drawTicks(canvas, 12, facePaint.smallTickPaint);
    }

    public void drawBackground(final Canvas canvas) {
        canvas.drawRoundRect(backgroundRect, 0, 0, facePaint.backgroundPaint);
    }

    public void drawSmallTicks(final Canvas canvas, int numTicks) {
        drawTicks(canvas, numTicks, facePaint.smallTickPaint);
    }

    public void drawSmallTickMask(final Canvas canvas) {
        canvas.drawRoundRect(smallTickMask, 0, 0, facePaint.backgroundPaint);
    }

    public void drawBorder(final Canvas canvas) {
        canvas.drawRoundRect(borderRect, 0, 0, facePaint.borderPaint);
    }

    public void drawLargeTicks(final Canvas canvas, int numTicks) {
        drawTicks(canvas, numTicks, facePaint.largeTickPaint);
    }

    public void drawLargeTickMask(final Canvas canvas) {
        canvas.drawRoundRect(largeTickMask, 0, 0, facePaint.backgroundPaint);
    }

    public void drawTicks(final Canvas canvas, int numTicks, Paint paint) {
        int start = 0;
        while (start < 360) {
            if(start <=45 || start > 315) {
                canvas.drawLine(halfWidth, halfHeight, faceWidth, halfHeight + findY(start, halfHeight), paint);
            } else if(start <= 135) {
                canvas.drawLine(halfWidth, halfHeight, halfWidth + findX(start, halfWidth), faceHeight, paint);
            } else if(start <= 225) {
                canvas.drawLine(halfWidth, halfHeight, 0, halfHeight + findY(start, -halfHeight), paint);
            } else if(start <= 315) {
                canvas.drawLine(halfWidth, halfHeight, halfWidth + findX(start, -halfWidth), 0, paint);
            }

            start += 360 / numTicks;
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
            int x = (bounds.width()) / 2;
            int y = (bounds.height()) / 2;

            if(start <=45 || start > 315) {
                canvas.drawText(timeText, faceWidth - textMargin - x, halfHeight + findY(start, halfHeight - textMargin) + y, facePaint.textPaint);
            }else if(start <= 135) {
                canvas.drawText(timeText, halfWidth + findX(start, halfWidth - textMargin) - x, faceHeight - textMargin + y, facePaint.textPaint);
            } else if(start <= 225) {
                canvas.drawText(timeText, textMargin - x, halfHeight + findY(start, -halfHeight + textMargin) + y, facePaint.textPaint);
            } else if(start <= 315) {
                canvas.drawText(timeText, halfWidth + findX(start, -halfWidth + textMargin) - x, 0 + textMargin + y, facePaint.textPaint);
            }

            start += 360 / num;
        }
    }

    public void drawCircles(Canvas canvas, int numCircles, int dotRadius, Paint paint) {
        int start = 0;
        while (start < 360) {
            if(start <=45 || start > 315) {
                canvas.drawCircle(faceWidth - margin, halfHeight + findY(start, halfHeight - textMargin), dotRadius, paint);
            }else if(start <= 135) {
                canvas.drawCircle(halfWidth + findX(start, halfWidth - textMargin), faceHeight - margin, dotRadius, paint);
            } else if(start <= 225) {
                canvas.drawCircle(margin, halfHeight + findY(start, -halfHeight + textMargin), dotRadius, paint);
            } else if(start <= 315) {
                canvas.drawCircle(halfWidth + findX(start, -halfWidth + textMargin), margin, dotRadius, paint);
            }

            start += 360 / numCircles;
        }
    }
}
