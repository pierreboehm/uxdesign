package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.util.Util;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_processing_console)
public class ProcessingConsoleView extends LinearLayout {

    private static final int UPDATE_INTERVAL = 500;

    @ViewById(R.id.tvConsole)
    TextView tvConsole;

    private Timer timer;
    private long offset = 0L;

    public ProcessingConsoleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        tvConsole.post(new Runnable() {
            @Override
            public void run() {
                setup();
            }
        });
    }

    @UiThread
    public void appendConsoleData() {
        Pair<String, String> hexDump = generate_8Byte_Dump();
        String leftValues = hexDump.first;
        String charValues = hexDump.second;

        hexDump = generate_8Byte_Dump();
        String rightValues = hexDump.first;
        charValues += hexDump.second;

        String line = String.format("%08X: %s- %s   %s\n", offset, leftValues, rightValues, charValues);
        tvConsole.append(line);

        offset += 16;
        if (offset > Integer.MAX_VALUE) {
            offset = 0L;
            tvConsole.append("\n");
        }
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                appendConsoleData();
            }
        }, 0, UPDATE_INTERVAL);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void setup() {
        float fontSizeInPixeln = (float) tvConsole.getWidth() / 50f;

        tvConsole.setTextSize(Util.convertPxToSp(getContext(), fontSizeInPixeln));
        tvConsole.setMovementMethod(new ScrollingMovementMethod());
    }

    private Pair<String, String> generate_8Byte_Dump() {
        String hexValues = "";
        String charValues = "";

        for (int i = 0; i < 8; i++) {
            int value = Util.getRandomBetween(0, 255);
            hexValues += String.format("%02X ", value);
            charValues += String.format("%c", value < 33 ? 46 : value);
        }

        return new Pair<>(hexValues, charValues);
    }
}
