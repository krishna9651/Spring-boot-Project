package my.apps.rewards.services;

import my.apps.rewards.MainLauncher;
import my.apps.rewards.dto.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingDouble;


public class RewardServices {
    private static Logger log = LoggerFactory
            .getLogger(RewardServices.class);

    static DateTimeFormatter EXPECTED_DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("yyyy-MMM");

    static LocalDate cutoff = LocalDate.now().minusMonths(3);

    public static Map<String, List<Record>> parseTransactionRecords(List<String> records) throws DateTimeParseException {
        Map<String, List<Record>> txns = new HashMap<>();
        for (String line : records) {
            String[] terms = line.split(",");
            String name = terms[1];
            LocalDate txnDate = LocalDate.parse(terms[0], EXPECTED_DATE);
            if (txnDate.isAfter(cutoff)) {
                String month = txnDate.format(OUTPUT_DATE);

                Record t = new Record(month, Double.parseDouble(terms[2]));

                List<Record> list = txns.get(name);
                if (list == null)
                    list = new LinkedList<>();
                list.add(t);
                txns.put(name, list);

            }
            else
                log.warn("The given record is outside the given date range "+ line );


        }
        return txns;

    }


    public static void printResults(Map<String, List<Record>> map) {
        for (String eachPerson : map.keySet()) {
            Map<String, Double> reward = generateResults(map.get(eachPerson));
            System.out.printf("Person Name: %s:  %n", eachPerson);

            double total = 0;

            for (String month : reward.keySet()) {
                double monthlyreward = reward.get(month);
                total += monthlyreward;
                System.out.printf("\t %s  : %s %n", month, monthlyreward);
            }
            System.out.printf("\t Rewards: %.2f%n%n", total);

        }
    }

    public static Map<String, Double> generateResults(List<Record> customerTransactionList) {
        return customerTransactionList.stream().collect(Collectors.groupingBy(Record::getYearMoth, TreeMap::new,summingDouble(t->RewardServices.calculateRewardByAmount(t.getAmount()))));

    }
    public static double calculateRewardByAmount(double amount) {
        return amount < 50 ? 0 : amount < 100.0 ? (amount - 50) : 50 + (amount - 100) * 2;
    }
}

   


