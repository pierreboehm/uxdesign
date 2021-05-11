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
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;

import java.util.Timer;
import java.util.TimerTask;

@EView
public class ScanningView extends View {

    private static final String TAG = ScanningView.class.getSimpleName();

    private Timer timer;
    private Paint rasterColor;
    private Paint graphColor;

    private int scannerIndex = 0;
    private int canvasWidth = 0;
    private int squareWidth = 0;

    public ScanningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        rasterColor = new Paint();
        rasterColor.setColor(getContext().getColor(R.color.blue_light));
        rasterColor.setStyle(Paint.Style.FILL);
        rasterColor.setAlpha(128);

        graphColor = new Paint();
        graphColor.setColor(getContext().getColor(R.color.blue_deep_sky));
        graphColor.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);

        if (scannerIndex > 0) {
            drawScan(canvas);
        }

        super.onDraw(canvas);
    }

    @UiThread
    public void update() {
        if (scannerIndex > canvasWidth / 2) {
            scannerIndex = 0;
        }

        scannerIndex += squareWidth * 2;

        invalidate();
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 125);

        EventBus.getDefault().post(new Event.ReportTimerStarted());
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;

            EventBus.getDefault().post(new Event.ReportTimerStopped());
        }

        // cleanup drawings
        scannerIndex = -(squareWidth * 2);

        invalidate();
    }

    private void drawScan(Canvas canvas) {
        int height = canvas.getHeight();
        int halfWidth = canvasWidth / 2;
        int doubledSquareWidth = squareWidth * 2;

        int xRight = halfWidth  + scannerIndex - doubledSquareWidth;
        int xLeft = halfWidth - scannerIndex;

        for (int y = 0; y < height; y += doubledSquareWidth) {
            canvas.drawRect(xRight, y, xRight + squareWidth, y + squareWidth, graphColor);
            canvas.drawRect(xLeft, y, xLeft + squareWidth, y + squareWidth, graphColor);
        }
    }

    private void drawRaster(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int square = width / 40;
        int doubledSquareWidth = square * 2;
        int effectiveWidth = width + doubledSquareWidth;

        canvasWidth = width;
        squareWidth = square;

        for (int y = 0; y < height; y += doubledSquareWidth) {
            for (int x = 0; x <= effectiveWidth; x += doubledSquareWidth) {
                canvas.drawRect(x, y, x + square, y + square, rasterColor);
            }
        }
    }
}
