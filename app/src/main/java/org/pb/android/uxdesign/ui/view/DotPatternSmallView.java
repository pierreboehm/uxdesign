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
public class DotPatternSmallView extends View {

    private Paint color;

    public DotPatternSmallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        color = new Paint();
        color.setColor(getContext().getColor(R.color.blue_deep_sky));
        color.setStyle(Paint.Style.FILL);

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawDotPattern(canvas);

        super.onDraw(canvas);
    }

    @UiThread
    public void refresh() {
        invalidate();
    }

    public void setDefaultColor(int defaultColor) {
        color.setColor(defaultColor);
    }

    private void drawDotPattern(Canvas canvas) {
        int width = canvas.getWidth();
        int dotCountX = 4;

        float square = (float) width / (float) (dotCountX + 1);
        float quarterSquare = square / (float) dotCountX;

        for (int dotX = 0; dotX < dotCountX; dotX++) {

            float dotXPosition = dotX * (square + quarterSquare);
            int dotCountY = Util.getRandomBetween(1, dotCountX);

            for (int dotY = 0; dotY < dotCountY; dotY++) {
                float dotYPosition = dotY * (square + quarterSquare);
                canvas.drawRect(dotXPosition, dotYPosition, dotXPosition + square, dotYPosition + square, color);
            }
        }
    }
}
