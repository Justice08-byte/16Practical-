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
    // tryHeapsort - implementation of a bottom-up and top-down construction
    class tryHeapsort{

        // ============== HEAP UTILITY METHODS ===============

        /* Swap two elements in an array*/

        private static void swap(String[] arr, int i, int j){
            String temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        private static void heapifyDown(String[] arr, int n, int i){
            int largest = i; // Initialize largest as root
            int left = 2 * i + 1; // left child
            int right = 2 * i + 2; // right child

            // If left child is larger than root
            if (right < n && arr[right].compareTo(arr[largest]) > 0) {
                largest = right;
            }
            //If largest is not root
            if (largest != i) {
                swap(arr, i, largest);
                // Recursively heapify the affected subtree
                heapifyDown(arr, n, largest);
            }
        }
        private static void heapifyUp(String[] arr, int i){
            int parent = (i - 1) / 2;

            if (i > 0 && arr[i].compareTo(arr[parent]) > 0) {
                swap(arr, i, parent);
                heapifyUp(arr, parent);
            }
        }
        // ============= HEAP CONSTRUCTION METHODS ================

        // Heap from the bottom up

        private static void buildHeapBottomUp(String[] arr){
            int n = arr.length;
            // Start from the last non-leaf node and heapify down
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapifyDown(arr, n, i);
            }
        }

        // Heap from top-down
        private static void buildHeapTopDown(String[] arr){
            int n = arr.length;
            // The heap starts empty (size 0) and grows
            for (int heapSize = 1; heapSize < n; heapSize++) {
                // Bubble up the new element at index heapSize
                int current = heapSize;
                int parent = (current - 1) / 2;

                // Bubble up the new element
                while (current > 0 && arr[current].compareTo(arr[parent]) > 0) {
                    swap(arr, current, parent);
                    current = parent;
                    parent = (current - 1) / 2;
                }
            }
        }

        // ================== SORTING METHODS ===================

        // The main sorting algorithm

        private static void sortHeap(String[] arr){
            int n = arr.length;

            // One by one extract elements from heap
            for (int i = n - 1; i > 0; i--) {
                // Move current root to end
                swap(arr, 0, i);
                // call max heapify on the reduced heap
                heapifyDown(arr, i, 0);
            }
        }

        // Combined method for bottom-up approach

        public static String[] sortBottomUp(String[] unsortedArray){
            String[] arr = unsortedArray.clone(); // Work on a copy
            buildHeapBottomUp(arr); // Step 1: Build heap (O(n))
            sortHeap(arr);          // Step 2: Sort (O(n log n))
            return arr;
        }

        // Combined method for Top-down approach
        public static String[] sortTopDown(String[] unsortedArray){
            String[] arr = unsortedArray.clone(); // Work on a copy
            buildHeapTopDown(arr); // Step 1: Build heap (O(n log n))
            sortHeap(arr);         // Step 2: Sort (O(n log n)) - reuse the same sorting method
            return arr;
        }

        // ================ TESTING AND VERIFICATION =====================

        private static void testWithSmallArray(){
            System.out.println("\n=== TEST WITH SMALL ARRAY ===");
            String[] smallTest = {"zebra", "apple", "monkey", "dog", "cat", "ball", "elephant"};
            System.out.println("Original array: " + Arrays.toString(smallTest));

            String[] bottomUpResult = sortBottomUp(smallTest);
            System.out.println("Bottom-up sorted: " + Arrays.toString(bottomUpResult));

            String[] topDownResult = sortTopDown(smallTest);
            System.out.println("Top-down sorted:  " + Arrays.toString(topDownResult));

            boolean identical = Arrays.equals(bottomUpResult, topDownResult);
            System.out.println("Both methods produce " + (identical ? "IDENTICAL" : "DIFFERENT") + " results.");
        }
        // verifying if the array is properly sorted

        private static boolean isSorted(String[] arr){
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    return false;
                }
            }
            return true;
        }
    }

}