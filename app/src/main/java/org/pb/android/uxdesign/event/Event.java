package org.pb.android.uxdesign.event;

public class Event {

    public static class ShowUserStatus {
    }

    public static class ShowVitalStatus {
    }

    public static class ShowSystemStatus {
    }

    public static class VitalGraph {
        private final boolean start;

        public VitalGraph(boolean start) {
            this.start = start;
        }

        public boolean shouldStart() {
            return start;
        }
    }

    public static class VitalStatusState {
        private final boolean discharged;

        public VitalStatusState(boolean discharged) {
            this.discharged = discharged;
        }

        public boolean isDischarged() {
            return discharged;
        }
    }

}
