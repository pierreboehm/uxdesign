package org.pb.android.uxdesign.util;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class EKG {

    // for details look at: https://de.wikipedia.org/wiki/Elektrokardiogramm --> Wellen

    private static final int P = 5;
    private static final int Q = 4;
    private static final int R = 3;
    private static final int S = 2;
    private static final int T = 1;
    private static final int U = 0;

    private int ekgValue = 0;

    @AfterInject
    public void reset() {
        ekgValue = 0;
    }

    public boolean hasP() {
        return getBit(ekgValue, P);
    }

    public void setP() {
        ekgValue = setBit(P);
    }

    public boolean hasQ() {
        return getBit(ekgValue, Q);
    }

    public void setQ() {
        ekgValue = setBit(Q);
    }

    public boolean hasR() {
        return getBit(ekgValue, R);
    }

    public void setR() {
        ekgValue = setBit(R);
    }

    public boolean hasS() {
        return getBit(ekgValue, S);
    }

    public void setS() {
        ekgValue = setBit(S);
    }

    public boolean hasT() {
        return getBit(ekgValue, T);
    }

    public void setT() {
        ekgValue = setBit(T);
    }

    public boolean hasU() {
        return getBit(ekgValue, U);
    }

    public void setU() {
        ekgValue = setBit(U);
    }

    private boolean getBit(final int x, final int p) {
        return (x & (1 << (P - p))) != 0;
    }

    private int setBit(final int p) {
        return (1 << (P - p));
    }
}
