package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.pb.android.uxdesign.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@EView
public class VitalGraphView extends View {

    private static final String TAG = VitalGraphView.class.getSimpleName();

    private static final float GRAPH_STROKE_WIDTH = 3f;
    private static final float GRADIENT_GRAPH_STROKE_WIDTH = 12f;
    private static final int RASTER_COUNT_X_DIRECTION = 100;

    private Context context;
    private Timer timer;

    private Paint rasterColor;
    private Paint graphColor;
    private Paint gradientGraphColor;

    private List<PointF> cardiacWave = new ArrayList<>();
    private Path cardiacGraph = new Path();

    private List<PointF> respirationSinusWave = new ArrayList<>();
    private Path respirationSinusGraph = new Path();
    private List<PointF> respirationCosinusWave = new ArrayList<>();
    private Path respirationCosinusGraph = new Path();

    private int cardiacIndex = 0;
    private int respirationIndex = 0;
    private int canvasWidth = 0;

    private float yReferenceCardiac = 0f;
    private float yReferenceRespiration = 0f;

    public VitalGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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
        graphColor.setStyle(Paint.Style.FILL_AND_STROKE);
        graphColor.setStrokeWidth(GRAPH_STROKE_WIDTH);
        graphColor.setAntiAlias(true);

        gradientGraphColor = new Paint();
        gradientGraphColor.setStyle(Paint.Style.STROKE);
        gradientGraphColor.setStrokeWidth(GRADIENT_GRAPH_STROKE_WIDTH);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);

        if (respirationIndex > 0) {
            drawBreathGraph(canvas);
        }

        if (cardiacIndex > 0) {
            drawHeartBeatGraph(canvas);
        }

        super.onDraw(canvas);
    }

    @UiThread
    public void update() {
        if (canvasWidth == 0 || timer == null) {
            return;
        }

        updateCardiacWave();
        updateRespirationWave();

        invalidate();
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // cleanup drawings
        cardiacIndex = 0;
        cardiacWave.clear();
        invalidate();
    }

    private void updateCardiacWave() {
        if (cardiacIndex > canvasWidth) {
            cardiacIndex = 0;
            cardiacWave.clear();
        }

        float cardiacAmplitude = ((float) (Math.sin(cardiacIndex * 0.1f)) * 1.2f) + (float) Math.random();

        if (cardiacIndex == canvasWidth / 2) {
            cardiacAmplitude += 25f;
        } else if (cardiacIndex == canvasWidth / 2 + 1) {
            cardiacAmplitude += 50f;
        } else if (cardiacIndex == canvasWidth / 2 + 2) {
            cardiacAmplitude += 25f;
        } else if (cardiacIndex == canvasWidth / 2 + 4) {
            cardiacAmplitude -= 5f;
        } else if (cardiacIndex == canvasWidth / 2 + 5) {
            cardiacAmplitude -= 15f;
        } else if(cardiacIndex == canvasWidth / 2 + 6) {
            cardiacAmplitude -= 5f;
        }

        PointF pointCardiacWave = new PointF(cardiacIndex, cardiacAmplitude);

        cardiacWave.add(pointCardiacWave);
        cardiacIndex++;
    }

    private void updateRespirationWave() {
        if (respirationIndex > canvasWidth) {
            respirationIndex = 0;
        }

        float respirationSinusAmplitude = ((float) (Math.sin(respirationIndex * 0.05f)) * (4f + (float) Math.random() * 10f)) + (float) Math.random();
        float respirationCosinusAmplitude = ((float) (Math.cos(respirationIndex * 0.05f)) * 4f) + (float) Math.random();

        PointF pointRespirationWave = new PointF(respirationIndex, respirationSinusAmplitude);
        if (respirationIndex >= respirationSinusWave.size()) {
            respirationSinusWave.add(pointRespirationWave);
        } else {
            respirationSinusWave.set(respirationIndex, pointRespirationWave);
        }

        pointRespirationWave = new PointF(respirationIndex, respirationCosinusAmplitude);
        if (respirationIndex >= respirationCosinusWave.size()) {
            respirationCosinusWave.add(pointRespirationWave);
        } else {
            respirationCosinusWave.set(respirationIndex, pointRespirationWave);
        }

        respirationIndex++;
    }

    private void drawBreathGraph(Canvas canvas) {
        respirationSinusGraph.reset();
        respirationCosinusGraph.reset();

        PointF wavePoint = respirationSinusWave.get(0);
        respirationSinusGraph.moveTo(wavePoint.x, yReferenceRespiration - wavePoint.y);

        wavePoint = respirationCosinusWave.get(0);
        respirationCosinusGraph.moveTo(wavePoint.x, yReferenceRespiration - wavePoint.y);

        int waveSize = respirationSinusWave.size();
        for (int waveXPosition = 1; waveXPosition < waveSize; waveXPosition++) {
            wavePoint = respirationSinusWave.get(waveXPosition);
            respirationSinusGraph.lineTo(waveXPosition, yReferenceRespiration - wavePoint.y);

            wavePoint = respirationCosinusWave.get(waveXPosition);
            respirationCosinusGraph.lineTo(waveXPosition, yReferenceRespiration - wavePoint.y);
        }

        graphColor.setStyle(Paint.Style.STROKE);

        canvas.drawPath(respirationCosinusGraph, graphColor);
        //canvas.drawPath(respirationSinusGraph, graphColor);
    }

    private void drawHeartBeatGraph(Canvas canvas) {
        int index = cardiacIndex - 1;
        float cardiacAmplitude = cardiacWave.get(index).y;

        cardiacGraph.reset();

        int waveSize = cardiacWave.size();
        PointF wavePoint = cardiacWave.get(0);
        cardiacGraph.moveTo(wavePoint.x, yReferenceCardiac - wavePoint.y);

        for (int waveXPosition = 1; waveXPosition < waveSize; waveXPosition++) {
            wavePoint = cardiacWave.get(waveXPosition);
            cardiacGraph.lineTo(wavePoint.x, yReferenceCardiac - wavePoint.y);
        }

        // reconfigure color gradient
        LinearGradient linearGradient = new LinearGradient(0, 0, waveSize, GRADIENT_GRAPH_STROKE_WIDTH,
                Color.TRANSPARENT, context.getColor(R.color.blue_cyan), Shader.TileMode.CLAMP);

        gradientGraphColor.setShader(linearGradient);

        // underlying wave
        canvas.drawPath(cardiacGraph, gradientGraphColor);

        // leading point
        graphColor.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(index, yReferenceCardiac - cardiacAmplitude, 8f, graphColor);
    }

    private void drawRaster(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int square = width / (RASTER_COUNT_X_DIRECTION * 2);
        int line = 0;

        int effectiveWidth = width - square;
        int effectiveHeight = height - square;

        canvasWidth = width - square;

        for (int y = 0; y < effectiveHeight; y += square * 2) {
            for (int x = 0; x < effectiveWidth; x += square * 2) {
                canvas.drawRect(x, y, x + square, y + square, rasterColor);
            }

            if (++line == 7) {
                yReferenceRespiration = y / 2f;     // half length between top and current y

                y += square * 2;

                yReferenceCardiac = y + ((effectiveHeight - y) / 2f);   // half length between current y and bottom added to current y
            }
        }
    }
}
