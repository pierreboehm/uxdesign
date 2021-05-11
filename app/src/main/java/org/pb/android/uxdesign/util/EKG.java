package org.pb.android.uxdesign.util;

import android.media.AudioManager;
import android.media.ToneGenerator;
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

    private static final int WAVE_STAGE_INDEX_MAX = 4;

    private static final int P = 5;
    private static final int Q = 4;
    private static final int R = 3;
    private static final int S = 2;
    private static final int T = 1;
    private static final int U = 0;

    private int ekgValue = 0;
    private int waveStageIndex = 0;
    private long qSetTime = 0L;

    private volatile int bpm = BPM_MIN;

    private Timer timer;
    private ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 70);

    @AfterInject
    void reset() {
        ekgValue = 0;
        waveStageIndex = 0;
        qSetTime = 0L;
    }

    public void start() {
        if (timer != null) {
            return;
        }

        timer = new Timer();
        timer.schedule(getRestartTimerTask(), Util.getRandomBetween(200, 500));

        EventBus.getDefault().post(new Event.ReportTimerStarted());
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;

            EventBus.getDefault().post(new Event.ReportTimerStopped());
        }
    }

    public float getCardiacAmplitude(int cardiacIndex) {
        float cardiacAmplitude = ((float) (Math.sin(cardiacIndex * 0.1f)) * 1.2f) + (float) Math.random();
        cardiacAmplitude += getCardiacAmplitudeExtra();
        return cardiacAmplitude;
    }

    public float getBpmAmplitude() {
        return (float) bpm;
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

        float bpMilliSeconds = 60f / (float) bpm * 1000f;

        // schedule next heart beat
        timer.schedule(getRestartTimerTask(), (long) bpMilliSeconds);
    }

    private TimerTask getRestartTimerTask() {
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
        setP();
    }


    private boolean hasP() {
        return getBit(ekgValue, P);
    }

    private float getPAmplitude() {
        float value = 0f;   // TODO
        waveStageIndex++;

        if (waveStageIndex == 0) {
            value = 7.5f;
        } else if (waveStageIndex == 1) {
            value = 14f;
        } else if (waveStageIndex == 2) {
            value = 27f;
        } else if (waveStageIndex == 3) {
            value = 15f;
        } else if (waveStageIndex == 4) {
            value = 4.5f;
        }

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;

            long pqDelay = Util.getRandomBetween(160, 200);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setQ();
                }
            }, pqDelay);
        }

        return value;
    }

    private void setP() {
        ekgValue = setBit(P);
        waveStageIndex = 0;
    }

    private boolean hasQ() {
        return getBit(ekgValue, Q);
    }

    private float getQAmplitude() {
        float value = 0f;   // TODO
        waveStageIndex++;

        if (waveStageIndex == 0) {
            value = -7.5f;
        } else if (waveStageIndex == 1) {
            value = -15f;
        } else if (waveStageIndex == 2) {
            value = (float) Util.getRandomBetween(-30, -40);
        } else if (waveStageIndex == 3) {
            value = -15f;
        } else if (waveStageIndex == 4) {
            value = -7.5f;
        }

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;
            setR();
        }

        return value;
    }

    private void setQ() {
        ekgValue = setBit(Q);
        waveStageIndex = 0;
        qSetTime = System.currentTimeMillis();
    }

    private boolean hasR() {
        return getBit(ekgValue, R);
    }

    private float getRAmplitude() {
        float value = 0f;   // TODO
        waveStageIndex++;

        if (waveStageIndex == 0) {
            value = 17.5f;
        } else if (waveStageIndex == 1) {
            value = 35f;
        } else if (waveStageIndex == 2) {
            value = (float) Util.getRandomBetween(70, 90);
            beep();
        } else if (waveStageIndex == 3) {
            value = 35f;
        } else if (waveStageIndex == 4) {
            value = 17.5f;
        }

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;
            setS();
        }

        return value;
    }

    private void setR() {
        ekgValue = setBit(R);
        waveStageIndex = 0;
    }

    private boolean hasS() {
        return getBit(ekgValue, S);
    }

    private float getSAmplitude() {
        float value = 0f;   // TODO
        waveStageIndex++;

        if (waveStageIndex == 0) {
            value = -11.25f;
        } else if (waveStageIndex == 1) {
            value = -22.5f;
        } else if (waveStageIndex == 2) {
            value = (float) Util.getRandomBetween(-35, -45);
        } else if (waveStageIndex == 3) {
            value = -22.5f;
        } else if (waveStageIndex == 4) {
            value = -11.25f;
        }

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;

            long qsTime = System.currentTimeMillis() - qSetTime;
            // FIXME
            //long stDelay = (long) (((float) Util.getRandomBetween(390, 440) - (float) qsTime) / 2f);
            long stDelay = Math.max(120 - qsTime, 100);

            //Log.d(TAG, "qsTime = " + qsTime + "ms, stDelay = " + stDelay + "ms");

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setT();
                }
            }, stDelay);
        }

        return value;
    }

    private void setS() {
        ekgValue = setBit(S);
        waveStageIndex = 0;
    }

    private boolean hasT() {
        return getBit(ekgValue, T);
    }

    private float getTAmplitude() {
        float value = 0f;   // TODO
        waveStageIndex++;

        if (waveStageIndex == 0) {
            value = 2.5f;
        } else if (waveStageIndex == 1) {
            value = 17.5f;
        } else if (waveStageIndex == 2) {
            value = 31f;
        } else if (waveStageIndex == 3) {
            value = 22.1f;
        } else if (waveStageIndex == 4) {
            value = 4.5f;
        }

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;
            setU();
        }

        return value;
    }

    private void setT() {
        ekgValue = setBit(T);
        waveStageIndex = 0;
    }

    private boolean hasU() {
        return getBit(ekgValue, U);
    }

    private float getUAmplitude() {
        waveStageIndex++;

        if (waveStageIndex > WAVE_STAGE_INDEX_MAX) {
            ekgValue = 0;
        }

        return 0f;
    }

    private void setU() {
        ekgValue = setBit(U);
        waveStageIndex = 0;
    }

    private boolean getBit(final int x, final int p) {
        return (x & (1 << (P - p))) != 0;
    }

    private int setBit(final int p) {
        return (1 << (P - p));
    }

    @UiThread(propagation = UiThread.Propagation.REUSE)
    void beep() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP,50);
    }
}
