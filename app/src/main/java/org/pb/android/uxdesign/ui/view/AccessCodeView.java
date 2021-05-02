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
import org.pb.android.uxdesign.util.Util;

@EView
public class AccessCodeView extends View {

    private Paint color;

    public AccessCodeView(Context context, @Nullable AttributeSet attrs) {
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
        drawCodePattern(canvas);

        super.onDraw(canvas);
    }

    private void drawCodePattern(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int dotCountX = 128;
        int dotCountY = 16;

        float square = (float) width / (float) dotCountX;
        float quarterSquare = square / 4f;

        float maxDotYCount = height / (square + quarterSquare);
        int dotCountYStart = (int) ((maxDotYCount / 2f) - ((float) dotCountY / 3f));

        for (int dotX = 0; dotX < dotCountX; dotX++) {

            float dotXPosition = dotX * (square + quarterSquare);
            if (dotXPosition > width - square) {
                break;
            }

            for (int dotY = dotCountYStart; dotY < dotCountY; dotY++) {
                float dotYPosition = dotY * (square + quarterSquare);

                if (Util.getRandomBoolean()) {
                    canvas.drawRect(dotXPosition, dotYPosition, dotXPosition + square, dotYPosition + square, color);
                }
            }
        }
    }
}
