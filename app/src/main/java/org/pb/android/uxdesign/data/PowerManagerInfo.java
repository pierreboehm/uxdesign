package org.pb.android.uxdesign.data;

import org.pb.android.uxdesign.util.Util;

public class PowerManagerInfo {

    private static final float TEMP_MIN = 32f;
    private static final float TEMP_MAX = 70f;

    private static final float VOLTAGE_MAX = 5000f;
    private static final float VOLTAGE_ACCU = 3800f;    // mV
    private static final float CAPACITY_ACCU = 3000f;   // mAh, 11.5Wh

    private static final String TYP_ACCU = "Li-Ion";
    private static final String MODEL_ACCU = "B2PZC100";

    private float voltage;
    private float voltageBattery;
    private float resistor;

    private boolean plugged;
    private int chargingState = 50;
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
        // FIXME: make depended from resistor, consumption and charging state ?
        temperature += (float) Util.getRandomBetween(-5, 5) / 100f;
        return (float) Util.roundScale(temperature);
    }

    public int getPowerSupplyInVolt() {
        return (int) (voltage / 1000f);
    }

    public int getVoltageInMilliVolt() {
        return (int) voltage;
    }

    public float getCurrentConsumptionInMilliAmpere() {
        return (float) Util.roundScale(Util.getRandomBetween(500f, 2100f));
    }

    public boolean isPluggedIn() {
        plugged = true; //Util.getRandomBoolean();
        return plugged;
    }

    public boolean isCharging() {
        return plugged && chargingState < 100;
    }

    public int getBatteryStatusInPercent() {
        if (plugged && isCharging()) {
            chargingState = Math.min(Util.getRandomBetween(chargingState, chargingState + 1), 100);
        } else {
            chargingState = Math.max(Util.getRandomBetween(chargingState - 1, chargingState), 0);
        }

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
