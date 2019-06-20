import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class CounterWord {
    public static Map<String, Integer> sortByValueJava8(HashMap<String, Integer> hm) {
        Map<String, Integer> sortedHM = hm
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        return sortedHM;
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public void readTextByLine(String fileName) throws Exception {
        String filePath = Paths.get(fileName).toAbsolutePath().toString();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        HashMap<String, Integer> wordFrequency = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+");
            for (String s : words) {
                if (!s.isEmpty()) {
                    if (wordFrequency.containsKey(s)) {
                        int frequency = wordFrequency.get(s);
                        wordFrequency.replace(s, frequency + 1);
                    } else {
                        wordFrequency.put(s, 1);
                    }
                }
            }
        }

        reader.close();

        Map<String, Integer> sorted = sortByValue(wordFrequency);
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}