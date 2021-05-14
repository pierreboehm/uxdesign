package org.pb.android.uxdesign.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;

import java.util.Random;

public class Util {

    public static float convertDpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static float convertPxToDp(Context context, float valueInPx) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (valueInPx / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float convertPxToSp(Context context, float valueInPx) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return valueInPx / scaledDensity;
    }

    public static double roundScale(double d) {
        return Math.rint(d * 100) / 100.;
    }

    public static float getCardiacRandom() {
        // 0.0 --> 1.0      r/10 = 0.00 .. 0.1  r/100 = 0.000 .. 0.01
        return (float) Math.random() / 10f;
    }

    // TODO: also works with negatives?
    public static double getRandomBetween(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    // TODO: also works with negatives?
    public static float getRandomBetween(float min, float max) {
        return min + (max - min) * new Random().nextFloat();
    }

    public static int getRandomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - (min)) + 1));
    }

    public static boolean getRandomBoolean() {
        return getRandomBetween(0, 1) == 1;
    }

    public static Pair<Integer, String> getProgressValueAndRelation(int min, int max) {
        int random = getRandomBetween(min, max);
        String relation = getRelation((float) random, (float) min, (float) max);
        return new Pair<>(random, relation);
    }

    public static Pair<Float, String> getProgressValueAndRelation(float min, float max) {
        float random = getRandomBetween(min, max);
        String relation = getRelation(random, min, max);
        return new Pair<>(random, relation);
    }

    public static float getProgressValue(float value, float maxValue) {
        return value * 100f / maxValue;
    }

    public static int getProgressValue(int value, int maxValue) {
        return (int) ((float) value * 100f / (float) maxValue);
    }

    public static String getRelation(float value, float min, float max) {
        float difference = max - min;
        float relation = difference / 4f;

        int factor = (int)((value - min) / relation);

        if (factor == 0) {
            return "low";
        } else if (factor == 3) {
            return "high";
        } else {
            return "normal";
        }
    }
}
