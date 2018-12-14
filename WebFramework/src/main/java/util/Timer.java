package util;

import java.text.DecimalFormat;

public class Timer {

    public static final String TIME_FORMAT = "#0.000";
    private long start;
    private long end;
    private long totalTime = 0;

    public void start() {
        start = System.nanoTime();
    }

    public void end() {
        end = System.nanoTime();
    }

    public String duration() {
        totalTime += (end - start);
        return new DecimalFormat(TIME_FORMAT).format((double) (end - start) / 1000000000);
    }

    public void reset() {
        start = 0;
        end = 0;
    }

    public String totalTime() {
        return new DecimalFormat(TIME_FORMAT).format((double) (totalTime) / 1000000000);
    }

    public String averageTime(int repetitions) {
        return new DecimalFormat(TIME_FORMAT).format((double) ((totalTime) / (repetitions)) / 1000000000);
    }
}
