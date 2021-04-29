package org.pb.android.uxdesign.util;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.event.Event;

import java.util.Timer;
import java.util.TimerTask;

// NOTE: ekg specification is taken from https://de.wikipedia.org/wiki/Elektrokardiogramm --> Wellen
/*
    This class simulates the real heart activity.
    It uses a cascade control based on an alternating bpm-timer.

    Cascade means here that each stage of one heart beat depends on previous stage.
    Staging starts with P-puls and ends with optional U-puls.
 */

@EBean(scope = EBean.Scope.Singleton)
public class EKG {

    private static final String TAG = EKG.class.getSimpleName();

    private static final int BPM_MIN = 60;
    private static final int BPM_MAX = 120;

    private static final int P = 5;
    private static final int Q = 4;
    private static final int R = 3;
    private static final int S = 2;
    private static final int T = 1;
    private static final int U = 0;

    private int ekgValue = 0;
    private int waveStage = 0;

    private volatile int bpm = BPM_MIN;

    private Timer timer;

    @AfterInject
    void reset() {
        ekgValue = 0;
        waveStage = 0;
    }

    public void start() {
        if (timer != null) {
            return;
        }

        timer = new Timer();
        timer.schedule(getTimerTask(), 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public float getCardiacAmplitude(int cardiacIndex) {
        float cardiacAmplitude = ((float) (Math.sin(cardiacIndex * 0.1f)) * 1.2f) + (float) Math.random();
        cardiacAmplitude += getCardiacAmplitudeExtra();
        return cardiacAmplitude;
    }

    private float getCardiacAmplitudeExtra() {
        if (hasP()) {
            return getPAmplitude();
        } else if (hasQ()) {
            return getQAmplitude();
        } else if (hasR()) {
            return getRAmplitude();
        } else if (hasS()) {
            return getSAmplitude();
        } else if (hasT()) {
            return getTAmplitude();
        } else if (hasU()) {
            return getUAmplitude();
        } else {
            return 0f;
        }
    }

    private void restartTimer() {
        if (timer == null) {
            return;
        }

        timer.purge();

        // re-calculate bpm
        int newBpm = bpm + Util.getRandomBetween(-1, 1);

        // keep bpm in natural range ;)
        bpm = (newBpm > BPM_MAX) ? BPM_MAX : Math.max(newBpm, BPM_MIN);

        Log.i(TAG, bpm + "/min");
        EventBus.getDefault().post(new Event.BpmUpdate(bpm));

        // prepare and start heart beat
        prepareCascade();
        startCascade();

        float bpMilliSeconds = (float) bpm / 60f * 1000f;

        // schedule next heart beat
        timer.schedule(getTimerTask(), (long) bpMilliSeconds);
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                restartTimer();
            }
        };
    }

    private void prepareCascade() {
        // TODO: pre-calculate all values for upcoming cascade

    }

    private void startCascade() {
        //setP();
    }


    private boolean hasP() {
        return getBit(ekgValue, P);
    }

    private float getPAmplitude() {
        return 0f;
    }

    private void setP() {
        ekgValue = setBit(P);
        waveStage = setBit(0);
    }

    private boolean hasQ() {
        return getBit(ekgValue, Q);
    }

    private float getQAmplitude() {
        return 0f;
    }

    private void setQ() {
        ekgValue = setBit(Q);
        waveStage = setBit(0);
    }

    private boolean hasR() {
        return getBit(ekgValue, R);
    }

    private float getRAmplitude() {
        return 0f;
    }

    private void setR() {
        ekgValue = setBit(R);
        waveStage = setBit(0);
    }

    private boolean hasS() {
        return getBit(ekgValue, S);
    }

    private float getSAmplitude() {
        return 0f;
    }

    private void setS() {
        ekgValue = setBit(S);
        waveStage = setBit(0);
    }

    private boolean hasT() {
        return getBit(ekgValue, T);
    }

    private float getTAmplitude() {
        return 0f;
    }

    private void setT() {
        ekgValue = setBit(T);
        waveStage = setBit(0);
    }

    private boolean hasU() {
        return getBit(ekgValue, U);
    }

    private float getUAmplitude() {
        return 0f;
    }

    private void setU() {
        ekgValue = setBit(U);
        waveStage = setBit(0);
    }

    private boolean getBit(final int x, final int p) {
        return (x & (1 << (P - p))) != 0;
    }

    private int setBit(final int p) {
        return (1 << (P - p));
    }
}
