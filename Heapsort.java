import java.io.*;
import java.util.*;

public class Heapsort {
    // Word Cleaner - reads and cleans words from a file
    class Wordcleaner {
        public static ArrayList<String> cleanFile(String filename) {
            ArrayList<String> words = new ArrayList<>();
            int lineCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    // Convert to lowercase and split into words
                    String[] rawWords = line.toLowerCase().split("\\s+");

                    for (String word : rawWords) {
                        // Clean each word: remove punctuation and keep only alphabetic characters
                        String cleaned = cleanWord(word);

                        // Add non-empty words to the list
                        if (!cleaned.isEmpty()) {
                            words.add(cleaned);
                        }
                    }
                    // Progress indicator for large files
                    if (lineCount % 1000 == 0) {
                        System.out.println("  Processed " + lineCount + " lines, found " + words.size() + " words so far...");
                    }
                }
                System.out.println("Completed Load!" + words.size() + "words from" + "joyce1922_ulysses-1.text");
                System.out.println(" Processed \" + lineCount + \" lines total.");
            } catch(FileNotFoundException e){
                System.err.println("Error: File not found - " + "joyce1922_ulysses-1.text");
                System.err.println("Current directory: " + System.getProperty("user.dir"));
                System.err.println("Please make sure '" + "joyce1922_ulysses-1.text" + "' is in the current directory.");

            } catch(IOException e){
                System.err.println("Error reading file: " + "joyce1922_ulysses-1.text");
                e.printStackTrace();
            }
            return words;

        }
        //cleaning the words by removing punctuations or non-alphabetical words
        private static String cleanWord(String word){
            // Remove all non-alphabetic characters
            return word.replaceAll("[^a-z]", "");
        }
        // Test method to see if the cleaner works
        public static void main(String[] args){
            // Test with a small sample
            String testLine = "Hello, world! This is a test: don't forget the apostrophes.";
            String[] testWords = testLine.toLowerCase().split("\\s+");

            System.out.println("Testing word cleaner:");
            for(String word: testWords){
                String cleaned = cleanWord(word);
                System.out.println("  '" + word + "' -> '" + cleaned + "'");
            }
        }

    }

}