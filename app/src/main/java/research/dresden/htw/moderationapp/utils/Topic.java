package research.dresden.htw.moderationapp.utils;

public enum Topic {

        DISCUSSION("discussion"), GREEN("GREEN");

        private final String topicAsString;

    Topic(String topicAsString) {
            this.topicAsString = topicAsString;
        }

        @Override
        public String toString() {
            return topicAsString;
        }
    }