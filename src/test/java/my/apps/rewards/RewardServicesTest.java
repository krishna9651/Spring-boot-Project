package my.apps.rewards;

import my.apps.rewards.dto.Record;
import my.apps.rewards.services.RewardServices;
import org.junit.Test;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;



public class RewardServicesTest {

    public static final double DIFF = 0.0001;

    @Test
    public void testCSVParse() throws ParseException {
        Map<String, List<Record>> output = RewardServices.parseTransactionRecords(
                List.of("02/14/2021,Chary,147\n",
                        "01/23/2021,Chary,109\n",
                        "12/09/2019,Rose,132"));

        assertEquals( 1 , output.size());
        assertEquals( 1 ,output.get("Chary").size());


    }

    @Test(expected = DateTimeParseException.class)
    public void testCSVParseThrowsParseException() throws DateTimeParseException {
        Map<String, List<Record>> output = RewardServices.parseTransactionRecords(
                List.of("01/14/202,Chary,147\n" +
                        "01/23/2021,Chary,109\n" +
                        "12/09/2019,Rose,132"));

    }

    @Test
    public void testRewardsPerCustomer() {
        Map<String, List<Record>> output = RewardServices.parseTransactionRecords(List.of("02/14/2021,Chary,147\n",
                "02/23/2021,Chary,109\n",
                "12/09/2021,Chary,132"));
        Map<String, Double> rewards = RewardServices.generateResults(output.get("Chary"));
        assertEquals(2, rewards.size());
        assertEquals(212, rewards.get("2021-Feb").doubleValue(), DIFF);

    }

    @Test
    public void testRewardByTotalAmount() {
        assertEquals(0, RewardServices.calculateRewardByAmount(0), DIFF);
        assertEquals(0, RewardServices.calculateRewardByAmount(40), DIFF);
        assertEquals(0, RewardServices.calculateRewardByAmount(50), DIFF);
        assertEquals(10, RewardServices.calculateRewardByAmount(60), DIFF);
        assertEquals(49, RewardServices.calculateRewardByAmount(99), DIFF);

        assertEquals(50, RewardServices.calculateRewardByAmount(100), DIFF);
        assertEquals(90, RewardServices.calculateRewardByAmount(120), DIFF);
        assertEquals(150, RewardServices.calculateRewardByAmount(150), DIFF);


    }

}
