import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BillCalculator implements TelephoneBillCalculator{
    private String mostCommonNumber;
    @Override
    public BigDecimal calculate(String phoneLog) {
        List<Log> logs = parseInput(phoneLog);
        mostCommonNumber = findMostCommonNumber(logs);
        return calculatePrice(logs);
    }

    private String findMostCommonNumber(List<Log> logs) {
        String tmpMostCommon = logs.get(0).getNumber();
        int tmpRate = 0;
        int curRate = 0;
        if (logs.size() == 1) return logs.get(0).getNumber();
        for (int i = 0; i < logs.size() - 1; i++) {
            if (logs.get(i).getNumber().equals(logs.get(i+1).getNumber())) {
                curRate++;
            } else {
                if (curRate > tmpRate){
                    tmpRate = curRate;
                    tmpMostCommon = logs.get(i).getNumber();
                }
                curRate = 0;
            }
        }
        if (curRate > tmpRate){
            tmpMostCommon = logs.get(logs.size()-1).getNumber();
        }
        return tmpMostCommon;
    }

    private List<Log> parseInput(String input) {
        List<Log> logs = new ArrayList<>();
        String[] lines = input.split(System.lineSeparator());
        for (String line : lines) {
            logs.add(new Log(line));
        }
        Collections.sort(logs);
        return logs;
    }

    private BigDecimal calculatePrice(List<Log> logs){
        BigDecimal sum = new BigDecimal(0);
        for (Log log : logs) {
            if (!log.getNumber().equals(mostCommonNumber)){
                sum = sum.add(log.getPrice());
                System.out.println(log.getPrice());
            }
        }
        return sum;
    }
}
