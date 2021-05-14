package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.util.Util;

@EView
public class SquarePatternView extends View {

    private final static String TAG = SquarePatternView.class.getSimpleName();

    private Paint color;

    public SquarePatternView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        setDrawingCacheEnabled(true);
        setDrawingCacheBackgroundColor(Color.TRANSPARENT);

        color = new Paint();
        color.setColor(getContext().getColor(R.color.blue_deep_sky));
        color.setStyle(Paint.Style.FILL);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawPattern(canvas);
        super.onDraw(canvas);
    }

    @UiThread
    public void refresh() {
        invalidate();
    }

    public void setDefaultColor(int defaultColor) {
        color.setColor(defaultColor);
    }

    private void drawPattern(Canvas canvas) {
        int width = canvas.getWidth();
        int squareCountXY = 2;

        float square = (float) width / (float) (squareCountXY + 1);
        float quarterSquare = square / 4f;

        for (int squareX = 0; squareX < squareCountXY; squareX++) {

            float squareXPosition = squareX * (square + quarterSquare);

            for (int squareY = 0; squareY < squareCountXY; squareY++) {
                float dotYPosition = squareY * (square + quarterSquare);
                color.setAlpha(Util.getRandomBoolean() ? 255 : 96);
                canvas.drawRect(squareXPosition, dotYPosition, squareXPosition + square, dotYPosition + square, color);
            }
        }

        sendDrawingCacheContent(getDrawingCache());
    }

    private void sendDrawingCacheContent(Bitmap drawingCacheBitmap) {
        EventBus.getDefault().post(new Event.DrawingCacheBitmapUpdate((String) getContentDescription(), drawingCacheBitmap));
    }
}
