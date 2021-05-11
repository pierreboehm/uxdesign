package org.pb.android.uxdesign.data;

import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.util.Util;

public class PowerManagerInfo {

    private static final float TEMP_MIN = 30f;
    private static final float TEMP_MAX = 50f;

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

    public PowerManagerInfo() {
        temperature = (float) Util.roundScale(Util.getRandomBetween(TEMP_MIN, TEMP_MAX));
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
        return (float) Util.roundScale(temperature);
    }

    public int getPowerSupplyInVolt() {
        return (int) (voltage / 1000f);
    }

    public int getVoltageInMilliVolt() {
        updateBatteryStatus();
        return (int) voltage;
    }

    public float getCurrentConsumptionInMilliAmpere() {
        return (float) Util.roundScale(Util.getRandomBetween(500f, 2100f));
    }

    public void setPlugged(boolean isPlugged) {
        plugged = isPlugged;
    }

    public boolean isPluggedIn() {
        if (!plugged) {
            EventBus.getDefault().post(new Event.ReportError());
        }
        return plugged;
    }

    public void setCharging(boolean isCharging) {
        charging = isCharging;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setBatteryChargingLevelInPercent(float chargingLevel) {
        chargingState = (int) chargingLevel;
    }

    public int getBatteryStatusInPercent() {
        return chargingState;
    }

    private float getBatteryStatusInMilliVolt() {
        return chargingState * VOLTAGE_ACCU / 100f;
    }

    private void updateBatteryStatus() {
        if (plugged) {
            voltage = VOLTAGE_MAX;
            voltageBattery = getBatteryStatusInMilliVolt();
        } else {
            voltageBattery = getBatteryStatusInMilliVolt();
            voltage = voltageBattery;
        }
    }
}
