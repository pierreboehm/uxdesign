package org.pb.android.uxdesign.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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

}
