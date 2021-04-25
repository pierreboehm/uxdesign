package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.pb.android.uxdesign.R;

@EView
public class RasterView extends View {

    private Paint color;

    public RasterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        color = new Paint();
        color.setColor(getContext().getColor(R.color.blue_light));
        color.setAlpha(128);
        color.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);
        super.onDraw(canvas);
    }

    private void drawRaster(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int count = 100;
        int square = width / (count * 2);
        int line = 0;

        for (int y = 0; y < height - square; y += square * 2) {
            for (int x = 0; x < width - square; x += square * 2) {
                canvas.drawRect(x, y, x + square, y + square, color);
            }

            if (++line == 7) {
                y += square * 2;
            }
        }
    }
}
