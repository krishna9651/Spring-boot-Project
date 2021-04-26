package my.apps.rewards;

import my.apps.rewards.dto.Record;
import my.apps.rewards.services.RewardServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingDouble;


@SpringBootApplication
public class MainLauncher
        implements CommandLineRunner {

    private static Logger log = LoggerFactory
            .getLogger(MainLauncher.class);


    @Override
    public void run(String... args) {
        log.info("Starting initializer ");



        for (int i = 0; i < args.length; ++i) {
            log.info("args[{}]: {}", i, args[i]);
        }


		log.info("Validate Arguments");

        if (args.length != 1) {
			log.error("Provide the .csv file as an input argument! The format must in syntax: <date>,<Name>,<Amount>");
            return;
        }

        try {
			log.info("Open the csv file ");

            List<String> result = Files.readAllLines(Paths.get(args[0]));


			log.info("Parse  data into DB");
            Map<String, List<Record>> map = RewardServices.parseTransactionRecords(result);


            log.info("Print rewards for each customer");

            RewardServices.printResults(map);

        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nUnable to open the input file at " + args[0]);

        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage() + "\nUnable to parse the data file");
        }

    }


    public static void main(String[] args) {
        SpringApplication.run(MainLauncher.class, args);
    }

}
