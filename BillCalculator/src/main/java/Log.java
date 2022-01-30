import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Log implements Comparable<Log> {
    private String number;
    private LocalDateTime start, end;

    //In real project would be better to fetch from config file
    private static final double RATE_IN_MAIN_INTERVAL = 1.0;
    private static final double RATE_OUTSIDE_MAIN_INTERVAL = 0.5;
    private static final double RATE_AFTER_FIVE_MINUTES = 0.2;
    private static final LocalTime START_OF_MAIN_INTERVAL = LocalTime.of(8, 0, 0);
    private static final LocalTime END_OF_MAIN_INTERVAL = LocalTime.of(15, 59, 59);

    public Log() {
    }

    public Log(String input) {
        String[] tmpInput = input.split(",");
        number = tmpInput[0];
        start = LocalDateTime.parse(tmpInput[1], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        end = LocalDateTime.parse(tmpInput[2], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    //Only for tests
    public Log(String number, LocalDateTime start, LocalDateTime end) {
        this.number = number;
        this.start = start;
        this.end = end;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(Log o) {
        return -this.number.compareTo(o.getNumber());
    }

    public BigDecimal getPrice() {
        var sum = new BigDecimal("0");
        sum = sum.add(getFirstFiveMinutesPrice());
        if (!start.plusMinutes(5).isBefore(end)) {
            return sum;
        }
        sum = sum.add(getRestTimePrice());
        return sum.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getRestTimePrice() {
        Duration duration = Duration.between(start.plusMinutes(5), end);
        long minutes = duration.toMinutes();
        double restPrice = minutes * RATE_AFTER_FIVE_MINUTES;
        if (duration.getSeconds() != 0) restPrice += RATE_AFTER_FIVE_MINUTES;
        return BigDecimal.valueOf(restPrice);
    }

    private BigDecimal getFirstFiveMinutesPrice() {
        double sum = 0;
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        for (int i = 0; i < 5; i++) {
            var timeToCompare = startTime.plusMinutes(i);
            if (timeToCompare.isAfter(endTime))
                break;

            if ((timeToCompare.isBefore(START_OF_MAIN_INTERVAL) || timeToCompare.isAfter(END_OF_MAIN_INTERVAL))) {
                sum += RATE_OUTSIDE_MAIN_INTERVAL;
            } else {
                sum += RATE_IN_MAIN_INTERVAL;
            }
        }
        return BigDecimal.valueOf(sum);
    }

}
