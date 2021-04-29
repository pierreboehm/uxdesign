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
import android.view.View;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.util.EKG;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@EView
public class VitalGraphView extends View {

    private static final String TAG = VitalGraphView.class.getSimpleName();

    private static final float GRAPH_STROKE_WIDTH = 4f;
    private static final float GRADIENT_GRAPH_STROKE_WIDTH = 12f;
    private static final int RASTER_COUNT_X_DIRECTION = 100;

    private Context context;
    private Timer cardiacWaveTimer;

    private Paint rasterColor;
    private Paint graphColor;
    private Paint gradientGraphColor;

    private List<PointF> cardiacWave = new ArrayList<>();
    private Path cardiacGraph = new Path();

    private List<PointF> bpmNewWave = new ArrayList<>();
    private Path bpmNewGraph = new Path();
    private List<PointF> bpmOldWave = new ArrayList<>();
    private Path bpmOldGraph = new Path();

    private int cardiacIndex = 0;
    private int bpmIndex = 0;
    private int canvasWidth = 0;

    private float yReferenceCardiac = 0f;
    private float yReferenceBpm = 0f;

    @Bean
    EKG ekg;

    public VitalGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        cardiacWaveTimer = null;
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
        gradientGraphColor.setStrokeJoin(Paint.Join.ROUND);
        gradientGraphColor.setStrokeWidth(GRADIENT_GRAPH_STROKE_WIDTH);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawRaster(canvas);

        if (bpmIndex > 0) {
            drawBpmWave(canvas);
        }

        if (cardiacIndex > 0) {
            drawCardiacWave(canvas);
        }

        super.onDraw(canvas);
    }

    @UiThread
    public void update() {
        if (canvasWidth == 0 || cardiacWaveTimer == null) {
            return;
        }

        updateCardiacWave();
        invalidate();
    }

    @UiThread
    public void updateBpm() {
        if (canvasWidth == 0 || cardiacWaveTimer == null) {
            return;
        }

        updateBpmWave();
    }

    public void start() {
        cardiacWaveTimer = new Timer();

        cardiacWaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1);

        cardiacWaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBpm();
            }
        }, 0, 250);

        ekg.start();
    }

    public void stop() {
        ekg.stop();

        if (cardiacWaveTimer != null) {
            cardiacWaveTimer.cancel();
            cardiacWaveTimer = null;
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

        float cardiacAmplitude = ekg.getCardiacAmplitude(cardiacIndex);
        PointF pointCardiacWave = new PointF(cardiacIndex, cardiacAmplitude);

        cardiacWave.add(pointCardiacWave);
        cardiacIndex++;
    }

    private void updateBpmWave() {
        if (bpmIndex > canvasWidth) {
            bpmIndex = 0;

            if (!bpmNewWave.isEmpty()) {
                bpmOldGraph.reset();
                bpmOldWave.clear();
                bpmOldWave.addAll(0, bpmNewWave);
            }

            bpmNewWave.clear();
        }

        float bpmAmplitude = ekg.getBpmAmplitude();
        PointF pointBpmWave = new PointF(bpmIndex, bpmAmplitude);

        bpmNewWave.add(pointBpmWave);
        bpmIndex++;
    }

    private void drawBpmWave(Canvas canvas) {
        bpmNewGraph.reset();

        int waveSize = bpmNewWave.size();
        PointF wavePoint = bpmNewWave.get(0);
        bpmNewGraph.moveTo(wavePoint.x, yReferenceBpm - wavePoint.y);

        for (int waveXPosition = 1; waveXPosition < waveSize; waveXPosition++) {
            wavePoint = bpmNewWave.get(waveXPosition);
            bpmNewGraph.lineTo(wavePoint.x, yReferenceBpm - wavePoint.y);
        }

        graphColor.setStyle(Paint.Style.STROKE);

        if (!bpmOldWave.isEmpty() && bpmOldGraph.isEmpty()) {
            waveSize = bpmOldWave.size();
            wavePoint = bpmOldWave.get(0);
            bpmOldGraph.moveTo(wavePoint.x, yReferenceBpm - wavePoint.y);

            for (int waveXPosition = 1; waveXPosition < waveSize; waveXPosition++) {
                wavePoint = bpmOldWave.get(waveXPosition);
                bpmOldGraph.lineTo(wavePoint.x, yReferenceBpm - wavePoint.y);
            }
        }

        if (!bpmOldGraph.isEmpty()) {
            graphColor.setAlpha(128);
            canvas.drawPath(bpmOldGraph, graphColor);
        }

        graphColor.setAlpha(255);
        canvas.drawPath(bpmNewGraph, graphColor);
    }

    private void drawCardiacWave(Canvas canvas) {
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
                // FIXME
                yReferenceBpm = 180 - square;     // half length between top and current y

                y += square * 2;

                yReferenceCardiac = y + ((effectiveHeight - y) / 2f);   // half length between current y and bottom added to current y
                yReferenceCardiac += square * 2;                        // add 2 lines extra space
            }
        }
    }
}
