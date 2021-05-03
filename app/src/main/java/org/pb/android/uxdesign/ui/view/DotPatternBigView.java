package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.util.Util;

@EView
public class DotPatternBigView extends View {

    public enum DotPattern {
        UNDEFINED, TRIANGLE, SQUARE, CIRCLE, RANDOM
    }

    private Paint rasterColor;
    private Paint dotColor;

    private DotPattern dotPattern = DotPattern.UNDEFINED;

    public DotPatternBigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        rasterColor = new Paint();
        rasterColor.setColor(getContext().getColor(R.color.blue_light));
        rasterColor.setAlpha(128);
        rasterColor.setStyle(Paint.Style.FILL);

        dotColor = new Paint();
        dotColor.setColor(getContext().getColor(R.color.blue_cyan));
        dotColor.setStyle(Paint.Style.FILL);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {

        switch (dotPattern) {
            case TRIANGLE: {
                drawTrianglePattern(canvas);
                break;
            }
            case SQUARE: {
                drawSquarePattern(canvas);
                break;
            }
            case CIRCLE: {
                drawCirclePattern(canvas);
                break;
            }
            case RANDOM: {
                drawRandomSquarePattern(canvas);
                break;
            }

        }

        super.onDraw(canvas);
    }

    @UiThread
    public void update() {
        invalidate();
    }

    public void setDotPattern(DotPattern dotPattern) {
        this.dotPattern = dotPattern;
    }

    private void drawTrianglePattern(Canvas canvas) {
        /*
                       *
                      * *
                    * * * *
                  * * * * * *
                * * * * * * * *
         */

        float halfWidth = canvas.getWidth() / 2f;
        int height = canvas.getHeight();

        int dot = (int) ((float) height / 13f);
        int doubledDot = dot * 2;
        float halfDot = dot / 2f;

        int dotCount = 1;

        for (int dotY = doubledDot * 2; dotY < height; dotY += doubledDot) {

            float dotX = halfWidth - (dot * (dotCount - 1));

            for (int dotNumber = 0; dotNumber < dotCount; dotNumber++) {
                canvas.drawCircle(dotX, dotY, halfDot, Util.getRandomBoolean() ? dotColor : rasterColor);
                dotX += (float) doubledDot;
            }

            dotCount += (dotCount == 1) ? 1 : 2;

            if (dotCount > 8) {
                break;
            }
        }

    }

    private void drawSquarePattern(Canvas canvas) {
        /*
                * * * * * *
                * * * * * *
                * * * * * *
                * * * * * *
                * * * * * *
         */

        float halfWidth = canvas.getWidth() / 2f;
        int height = canvas.getHeight();

        int dot = (int) ((float) height / 13f);
        int doubledDot = dot * 2;
        float halfDot = dot / 2f;

        int lineCount = 0;
        int dotCount = 6;

        for (int dotY = doubledDot; dotY < height; dotY += doubledDot) {

            float dotX = halfWidth - (dot * (dotCount - 1));

            for (int dotNumber = 0; dotNumber < dotCount; dotNumber++) {
                canvas.drawCircle(dotX, dotY, halfDot, Util.getRandomBoolean() ? dotColor : rasterColor);
                dotX += (float) doubledDot;
            }

            if (++lineCount > 5) {
                break;
            }
        }
    }

    private void drawCirclePattern(Canvas canvas) {
        /*
                  * * * *
                * * * * * *
                * * * * * *
                * * * * * *
                  * * * *
         */

        float halfWidth = canvas.getWidth() / 2f;
        int height = canvas.getHeight();

        int dot = (int) ((float) height / 13f);
        int doubledDot = dot * 2;
        float halfDot = dot / 2f;

        int lineCount = 0;
        int dotCount = 4;

        for (int dotY = doubledDot; dotY < height; dotY += doubledDot) {

            float dotX = halfWidth - (dot * (dotCount - 1));

            for (int dotNumber = 0; dotNumber < dotCount; dotNumber++) {
                canvas.drawCircle(dotX, dotY, halfDot, Util.getRandomBoolean() ? dotColor : rasterColor);
                dotX += (float) doubledDot;
            }

            lineCount++;
            dotCount = (lineCount > 0 && lineCount < 5) ? 6 : 4;

            if (lineCount > 5) {
                break;
            }
        }
    }

    private void drawRandomSquarePattern(Canvas canvas) {
        float halfWidth = canvas.getWidth() / 2f;
        int height = canvas.getHeight();

        int lineCount = 0;
        int dotCount = 8;

        int dot = (int) ((float) height / (float) ((dotCount * 2) + 1));
        int doubledDot = dot * 2;
        float halfDot = dot / 2f;

        int defaultColor = dotColor.getColor();
        dotColor.setColor(getContext().getColor(R.color.white));

        for (int dotY = doubledDot; dotY < height; dotY += doubledDot) {

            float dotX = halfWidth - (dot * (dotCount - 1));

            for (int dotNumber = 0; dotNumber < dotCount; dotNumber++) {
                if (Util.getRandomBoolean()) {
                    canvas.drawCircle(dotX, dotY, halfDot, dotColor);
                }
                dotX += (float) doubledDot;
            }

            if (++lineCount > (dotCount - 1)) {
                break;
            }
        }

        dotColor.setColor(defaultColor);
    }
}
