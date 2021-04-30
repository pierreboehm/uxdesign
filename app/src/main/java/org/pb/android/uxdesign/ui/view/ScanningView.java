package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.pb.android.uxdesign.R;

@EView
public class ScanningView extends View {

    private static final String TAG = ScanningView.class.getSimpleName();

    private Paint rasterColor;

    public ScanningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        rasterColor = new Paint();
        rasterColor.setColor(getContext().getColor(R.color.blue_light));
        rasterColor.setAlpha(128);
        rasterColor.setStyle(Paint.Style.FILL_AND_STROKE);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);
        super.onDraw(canvas);
    }

    public void start() {

    }

    public void stop() {

    }

    private void drawRaster(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int square = width / 40;
        int effectiveWidth = width + (square * 2);

        for (int y = 0; y < height; y += square * 2) {
            for (int x = 0; x <= effectiveWidth; x += square * 2) {
                canvas.drawRect(x, y, x + square, y + square, rasterColor);
            }
        }
    }
}
