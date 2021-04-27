package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.util.Util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@EView
public class VitalGraphView extends View {

    private static final String TAG = VitalGraphView.class.getSimpleName();

    private Timer timer;

    private Paint rasterColor;
    private Paint graphColor;

    private List<PointF> cardiacWave;
    private List<PointF> respirationWave;

    private boolean initiated;


    public VitalGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        timer = null;
    }

    @AfterViews
    public void initView() {
        setWillNotDraw(false);

        rasterColor = new Paint();
        rasterColor.setColor(getContext().getColor(R.color.blue_light));
        rasterColor.setAlpha(128);
        rasterColor.setStyle(Paint.Style.FILL_AND_STROKE);

        graphColor = new Paint();
        graphColor.setColor(getContext().getColor(R.color.blue_cyan));
        graphColor.setStyle(Paint.Style.STROKE);
        graphColor.setStrokeWidth(Util.convertDpToPx(getContext(), 5f));
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);

        drawBreathGraph(canvas);
        drawHeartBeatGraph(canvas);

        super.onDraw(canvas);
    }

    @UiThread
    public void update() {
        // 1) check if wave list is full. if yes, empty it
        // 2) add new point
        // 3) call invalidate()
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 60);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void drawBreathGraph(Canvas canvas) {

    }

    private void drawHeartBeatGraph(Canvas canvas) {

    }



    private void drawRaster(Canvas canvas) {
        Log.d(TAG, "drawRaster() called");

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int count = 100;
        int square = width / (count * 2);
        int line = 0;

        for (int y = 0; y < height - square; y += square * 2) {
            for (int x = 0; x < width - square; x += square * 2) {
                canvas.drawRect(x, y, x + square, y + square, rasterColor);
            }

            if (++line == 7) {
                y += square * 2;
            }
        }
    }
}
