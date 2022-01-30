import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BillCalculatorTest {
    BillCalculator billCalculator;

    @Test
    public void testCalculate() {
        billCalculator = new BillCalculator();
        BigDecimal result = billCalculator.calculate("111111111111,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() +
                "111111111111,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() +
                "222222222222,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "333333333333,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "333333333333,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "111111111111,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "111111111111,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "444444444444,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "444444444444,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "444444444444,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "444444444444,13-01-2020 18:10:15,13-01-2020 18:12:57");
        Assert.assertEquals(new BigDecimal("24.40"), result);
    }

    @Test
    //Second number is "Most called number" because it is called same amount as first, but is bigger, therefore is free
    //First number is called for 2:42 minutes outside main interval (8:00-15:59), therefore the cost is calculated as 3 * 0,5
    public void testCalculateFromAssignment() {
        billCalculator = new BillCalculator();
        BigDecimal result = billCalculator.calculate("420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00");
        Assert.assertEquals(new BigDecimal("1.5"), result);
    }

    @Test
    //Only one number, bill is free, because the only number is most called
    public void testCalculatorOnlyOneNumber() {
        billCalculator = new BillCalculator();
        BigDecimal result = billCalculator.calculate("420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00");
        Assert.assertEquals(new BigDecimal("0"), result);
    }

    @Test
    public void testCalculatorInMainInterval() {
        billCalculator = new BillCalculator();
        BigDecimal result = billCalculator.calculate("420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() +
                "222222222222,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,18-01-2020 08:59:20,18-01-2020 09:10:00");
        Assert.assertEquals(new BigDecimal("6.20"), result);
    }

    @Test
    public void testXX() {
        billCalculator = new BillCalculator();
        BigDecimal result = billCalculator.calculate("111111111111,18-01-2020 08:59:20,18-01-2020 09:10:00" + System.lineSeparator() +
                "111111111111,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,13-01-2020 18:10:15,13-01-2020 18:12:57" + System.lineSeparator() +
                "222222222222,18-01-2020 08:59:20,18-01-2020 09:10:00");
        Assert.assertEquals(new BigDecimal("7.70"), result);
    }


}

