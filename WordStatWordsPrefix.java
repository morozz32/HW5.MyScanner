import java.util.Map;
import java.util.TreeMap;
import java.util.SortedMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordStatWordsPrefix {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Files' names are not written");
            return;
        }
        
        SortedMap<String, Integer> words = new TreeMap<>();
        try (MyScanner sc = new MyScanner(args[0], StandardCharsets.UTF_8, new MyScanner.Separator() {
                public boolean isSeparator(char c) {
                    return !(Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'');
                }
        })) {

            while (sc.hasNext()) {
                StringBuilder theWord = new StringBuilder(sc.next());
                addWord(words, theWord);
            }
        } catch (IOException e) {
            System.out.println("Input error :( " + e.getMessage());
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, Integer> s: words.entrySet()) {
                    writer.write(s.getKey() + " " + s.getValue());
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Output exception :( " + e.getMessage());
        }   
    }

    public static void addWord(Map<String, Integer> words, StringBuilder theWord) {
        if (theWord.length() >= 3) {
            theWord.setLength(3);
            String str = theWord.toString().toLowerCase();
            words.put(str, words.getOrDefault(str, 0) + 1);
        }
        theWord.setLength(0);
    }
}
