package org.pb.android.uxdesign.data;

import org.pb.android.uxdesign.util.Util;

public class PowerManagerInfo {

    private static final float TEMP_MIN = 32f;
    private static final float TEMP_MAX = 70f;

    private boolean plugged;
    private int loadingState = 0;

        /*public enum TermalStatus {
            NORMAL, LIGHT, MODERATE, EMERGENCY, CRITICAL
        }

        public static String getTermalStatus() {
            return TermalStatus.values()[Util.getRandomBetween(0, 4)].name();
        }*/

    public float getTemperatureInCelsius() {
        return (float) Util.roundScale(Util.getRandomBetween(TEMP_MIN, TEMP_MAX));
    }

    public int getPowerSupplyInVolt() {
        return 5;
    }

    public float getCurrentConsumptionInMilliAmpere() {
        return (float) Util.roundScale(Util.getRandomBetween(.5f, 2.1f));
    }

    public boolean isPluggedIn() {
        plugged = Util.getRandomBoolean();
        return plugged;
    }

    public boolean isLoading() {
        return plugged && loadingState < 100;
    }

    public int getLoadingStateInPercent() {
        if (isPluggedIn() && isLoading()) {
            loadingState = Math.min(Util.getRandomBetween(loadingState, loadingState + 1), 100);
        } else {
            loadingState = Math.max(Util.getRandomBetween(loadingState - 1, loadingState), 0);
        }

        return loadingState;
    }
}
