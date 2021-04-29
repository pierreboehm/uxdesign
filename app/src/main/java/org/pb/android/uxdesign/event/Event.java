package org.pb.android.uxdesign.event;

public class Event {

    public static class ShowUserStatus {
    }

    public static class ShowVitalStatus {
    }

    public static class ShowSystemStatus {
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

}
