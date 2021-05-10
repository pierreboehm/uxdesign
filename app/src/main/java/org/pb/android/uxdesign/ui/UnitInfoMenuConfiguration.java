package org.pb.android.uxdesign.ui;

import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;

public enum UnitInfoMenuConfiguration {

    BUTTON0(R.id.btn_0) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn0(isSelected);
        }
    },
    BUTTON1(R.id.btn_1) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn1(isSelected);
        }
    },
    BUTTON2(R.id.btn_2) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn2(isSelected);
        }
    },
    BUTTON3(R.id.btn_3) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn3(isSelected);
        }
    },
    BUTTON4(R.id.btn_4) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn4(isSelected);
        }
    },
    BUTTON5(R.id.btn_5) {
        @Override
        public void onClick(boolean isSelected) {
            action_btn5(isSelected);
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

    public abstract void onClick(boolean isSelected);

    public void action_btn0(boolean isSelected) {
        // TODO: implement
    }

    public void action_btn1(boolean isSelected) {
        if (isSelected) {
            EventBus.getDefault().post(new Event.ContentShowUserInfo());
        } else {
            EventBus.getDefault().post(new Event.ContentClear());
        }
    }

    public void action_btn2(boolean isSelected) {
        if (isSelected) {
            EventBus.getDefault().post(new Event.ContentShowPowerInfo());
        } else {
            EventBus.getDefault().post(new Event.ContentClear());
        }
    }

    public void action_btn3(boolean isSelected) {
        // TODO: implement
    }

    public void action_btn4(boolean isSelected) {
        // TODO: implement
    }

    public void action_btn5(boolean isSelected) {
        // TODO: implement
    }
}
