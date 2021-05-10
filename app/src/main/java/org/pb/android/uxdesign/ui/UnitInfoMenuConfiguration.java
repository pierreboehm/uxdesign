package org.pb.android.uxdesign.ui;

import org.pb.android.uxdesign.R;

public enum UnitInfoMenuConfiguration {

    BUTTON0(R.id.btn_0) {
        @Override
        public void onClick() {
            action_btn0();
        }
    },
    BUTTON1(R.id.btn_1) {
        @Override
        public void onClick() {
            action_btn1();
        }
    },
    BUTTON2(R.id.btn_2) {
        @Override
        public void onClick() {
            action_btn2();
        }
    },
    BUTTON3(R.id.btn_3) {
        @Override
        public void onClick() {
            action_btn3();
        }
    },
    BUTTON4(R.id.btn_4) {
        @Override
        public void onClick() {
            action_btn4();
        }
    },
    BUTTON5(R.id.btn_5) {
        @Override
        public void onClick() {
            action_btn5();
        }
    };

    public final int resourceId;

    UnitInfoMenuConfiguration(int resourceId) {
        this.resourceId = resourceId;
    }

    public static UnitInfoMenuConfiguration getButtonById(int resourceId) {
        for (UnitInfoMenuConfiguration unitInfoMenuConfiguration : UnitInfoMenuConfiguration.values()) {
            if (unitInfoMenuConfiguration.resourceId == resourceId) {
                return unitInfoMenuConfiguration;
            }
        }
        return null;
    }

    public abstract void onClick();

    public void action_btn0() {
        // TODO: implement
    }

    public void action_btn1() {
        // TODO: implement
    }

    public void action_btn2() {
        // TODO: implement
    }

    public void action_btn3() {
        // TODO: implement
    }

    public void action_btn4() {
        // TODO: implement
    }

    public void action_btn5() {
        // TODO: implement
    }
}
