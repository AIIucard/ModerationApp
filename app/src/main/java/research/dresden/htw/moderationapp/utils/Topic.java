package research.dresden.htw.moderationapp.utils;

public enum Topic {

        NEW_DISCUSSION("new_discussion"), START_DISCUSSION("start_discussion"), END_DISCUSSION("end_discussion"), REMAINING_TIME("remaining_time"), START_PAUSE("start_pause"), END_PAUSE("end_pause"), TOPIC("topic"), SILENCE("silence");

        private final String topicAsString;

    Topic(String topicAsString) {
            this.topicAsString = topicAsString;
        }

        @Override
        public String toString() {
            return topicAsString;
        }
    }