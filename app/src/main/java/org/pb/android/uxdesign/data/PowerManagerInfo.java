package org.pb.android.uxdesign.data;

import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.util.Util;

public class PowerManagerInfo {

    private static final float TEMP_MIN = 18f;
    private static final float TEMP_MAX = 60f;

    private static final float CURR_MIN = 500f;
    private static final float CURR_MAX = 3000f;

    private static final float VOLTAGE_MAX = 5000f;
    private static final float VOLTAGE_ACCU = 3800f;    // mV
    private static final float CAPACITY_ACCU = 3000f;   // mAh, 11.5Wh

    private static final String TYP_ACCU = "Li-Ion";
    private static final String MODEL_ACCU = "B2PZC100";

    private float voltage;
    private float voltageBattery;

    private boolean plugged;
    private boolean charging;
    private int chargingState;
    private float temperature;
    private float consumption;

    public PowerManagerInfo() {
        temperature = (float) Util.roundScale(Util.getRandomBetween(TEMP_MIN, TEMP_MIN + (TEMP_MIN * .5f)));
        consumption = (float) Util.roundScale(Util.getRandomBetween(CURR_MIN, CURR_MIN + (CURR_MIN * .5f)));
        updateBatteryStatus();
    }

    public String getBatteryTyp() {
        return TYP_ACCU;
    }

    public String getBatteryModel() {
        return MODEL_ACCU;
    }

    public float getBatteryCapacity() {
        return CAPACITY_ACCU;
    }

    public float getTemperatureInCelsius() {
        temperature += (float) Util.getRandomBetween(-5, 5) / 100f;

        temperature = Math.min(temperature, TEMP_MAX);
        temperature = Math.max(temperature, TEMP_MIN);

        return (float) Util.roundScale(temperature);
    }

    public int getPowerSupplyInVolt() {
        return (int) (voltage / 1000f);
    }

    public synchronized int getVoltageInMilliVolt() {
        updateBatteryStatus();
        return (int) voltage;
    }

    public float getCurrentConsumptionInMilliAmpere() {
        consumption += (float) Util.getRandomBetween(-CURR_MIN, CURR_MIN) / 100f;

        consumption = Math.min(consumption, CURR_MAX);
        consumption = Math.max(consumption, CURR_MIN);

        return (float) Util.roundScale(consumption);
    }

    public synchronized void setPlugged(boolean isPlugged) {
        plugged = isPlugged;
    }

    public synchronized boolean isPluggedIn() {
        if (!plugged) {
            EventBus.getDefault().post(new Event.ReportError());
        }
        return plugged;
    }

    public synchronized void setCharging(boolean isCharging) {
        charging = isCharging;
    }

    public synchronized boolean isCharging() {
        return charging;
    }

    public synchronized void setBatteryChargingLevelInPercent(float chargingLevel) {
        chargingState = (int) chargingLevel;
    }

    public synchronized int getBatteryStatusInPercent() {
        return chargingState;
    }

    private synchronized float getBatteryStatusInMilliVolt() {
        return (float) chargingState * VOLTAGE_ACCU / 100f;
    }

    private synchronized void updateBatteryStatus() {
        if (plugged) {
            voltage = VOLTAGE_MAX;
            voltageBattery = getBatteryStatusInMilliVolt();
        } else {
            voltageBattery = getBatteryStatusInMilliVolt();
            voltage = voltageBattery;
        }
    }
}
