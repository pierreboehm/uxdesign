package org.pb.android.uxdesign.event;

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

}
