package org.pb.android.uxdesign.event;

import android.graphics.Bitmap;
import android.util.Pair;

import org.pb.android.uxdesign.data.user.UserData;
import org.pb.android.uxdesign.ui.ViewMode;

public class Event {

    public static class ShowUserStatus {
    }

    public static class ShowVitalStatus {
    }

    public static class ShowSystemStatus {
    }

    public static class ShowDialog {
        private final ViewMode viewMode;

        public ShowDialog(ViewMode viewMode) {
            this.viewMode = viewMode;
        }

        public ViewMode getViewMode() {
            return viewMode;
        }
    }

    public static class UserDataUpdate {
        private final UserData userData;

        public UserDataUpdate(UserData userData) {
            this.userData = userData;
        }

        public UserData getUserData() {
            return userData;
        }
    }

    public static class BpmUpdate {
        private final int bpmValue;

        public BpmUpdate(int bpmValue) {
            this.bpmValue = bpmValue;
        }

        public int getBpmValue() {
            return bpmValue;
        }
    }

    public static class StatusSystemProgressUpdate {
        private final int statusSystemProgress;

        public StatusSystemProgressUpdate(int statusSystemProgress) {
            this.statusSystemProgress = statusSystemProgress;
        }

        public int getStatusSystemProgress() {
            return statusSystemProgress;
        }
    }

    public static class DataProcessingProgressUpdate {
        private final int dataProcessingProgress;

        public DataProcessingProgressUpdate(int dataProcessingProgress) {
            this.dataProcessingProgress = dataProcessingProgress;
        }

        public int getDataProcessingProgress() {
            return dataProcessingProgress;
        }
    }

    public static class DataMonitoringProgressUpdate {
        private final int dataMonitoringProgress;

        public DataMonitoringProgressUpdate(int dataMonitoringProgress) {
            this.dataMonitoringProgress = dataMonitoringProgress;
        }

        public int getDataMonitoringProgress() {
            return dataMonitoringProgress;
        }
    }

    public static class ContentClear {
    }

    public static class ContentShowUserInfo {
    }

    public static class ContentShowPowerInfo {
    }

    public static class ContentShowApplicationInfo {
    }

    public static class ContentShowTimersInfo {
    }

    public static class ContentShowUnitInfo {
    }

    public static class ContentShowSustainmentInfo {
    }

    public static class ReportError {
    }

    public static class ReportFailure {
    }

    public static class ReportTimerStarted {
    }

    public static class ReportTimerStopped {
    }

    public static class DrawingCacheBitmapUpdate {
        private final Bitmap bitmap;
        private final String key;

        public DrawingCacheBitmapUpdate(String key, Bitmap bitmap) {
            this.key = key;
            this.bitmap = bitmap;
        }

        public Pair<String, Bitmap> getUpdateData() {
            return new Pair<>(key, bitmap);
        }
    }

    public static class IntegerValueUpdate {
        private final String key;
        private final Integer value;

        public IntegerValueUpdate(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public Pair<String, Integer> getUpdateData() {
            return new Pair<>(key, value);
        }
    }

    public static class FloatValueUpdate {
        private final String key;
        private final Float value;

        public FloatValueUpdate(String key, Float value) {
            this.key = key;
            this.value = value;
        }

        public Pair<String, Float> getUpdateData() {
            return new Pair<>(key, value);
        }
    }
}
