import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {


        CounterWord counterWord = new CounterWord();
        try {
            Instant start = Instant.now();

            counterWord.readTextByLine("BTVN-5.txt");

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("\nExecution Time: " + timeElapsed + " millisecond");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
