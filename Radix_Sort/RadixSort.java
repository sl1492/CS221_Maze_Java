/**
* @author Simeng Li
* @author Thando Tsela
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class RadixSort {
    private static int maxLength;


    /**
    * Uses standard least significant digit radix sort to sort the
    * words. This function doesn't change the passed in word list; it returns
    * a new list.
    *
    * @param A list of words. Each word contains only lower case letters in a-z.
    * @return A list with the same words as the input argument, in sorted order
    */
    public static List<String> radixSort(List<String> words) {
        decideMaxLength(words);
        List<String> paddedWords = padWords(words);
        // Creating 27 buckets: ' ', 'a', 'b', ..., 'z'.
        List<List<String>> buckets = createBuckets(27);
        for (int i = maxLength - 1; i >= 0; i--) {
            clearBuckets(buckets);
            for (String word: paddedWords) {
                if (word.charAt(i) == ' ') {
                    buckets.get(0).add(word);
                } else {
                    buckets.get(word.charAt(i) - 96).add(word); // Make sure you understand this useful trick.
                }
            }
            paddedWords = bucketsToList(buckets);
        }
        return stripWords(paddedWords);
    }

    /**
    * Create numBuckets buckets, represented by a list of lists.
    * @param numBuckets the number of buckets to create
    * @return a list of lists
    */
    private static List<List<String>> createBuckets(int numBuckets) {
        List<List<String>> buckets = new ArrayList<>();
        for (int i = 0; i < numBuckets; i++) {
            List<String> bucket = new ArrayList<>();
            buckets.add(bucket);
        }
        return buckets;
    }

    /**
    * set maxLength as the length of the longest word in the list.
    * @param words a list of strings
    */
    public static void decideMaxLength(List<String> words) {
        int variable = 0;
        for (String word : words) {
            if (word.length() > variable) {
                variable = word.length();
            }
        }
        maxLength = variable;
    }

    /**
    * Pad the words with spaces so that all words share the same length, maxLength.
    * This function doesn't modify the passed in word list; it returns a new list.
    * @param words a list of strings with mixed lengths
    * @return a list of strings, each of which is maxLength long
    */
    private static List<String> padWords(List<String> words) {
        List<String> padList = new ArrayList<>();
        for (String word : words) {
            if (word.length() < maxLength) {
                int difference = maxLength - word.length();
                String finalWord = word;
                for (int pad = 0; pad < difference; pad++) {
                    finalWord += " ";
                }
                padList.add(finalWord);
            }
            else {
                padList.add(word);
           }
        }
        return padList;
    }

    /**
    * Clear all buckets.
    * @param buckets the buckets to be cleared
    */

    private static void clearBuckets(List<List<String>> buckets) {
        for (List<String> bucket : buckets) {
            bucket.clear();
        }
    }

    /**
    * Place the content of the buckets to a list.
    * @param buckets a list of lists of strings.
    * @return a list of strings that keep the original order in each bucket
    */
    private static List<String> bucketsToList(List<List<String>> buckets) {
        List<String> allStrings = new ArrayList<>();
        for (List<String> bucket : buckets) {
            for (int i = 0; i < bucket.size(); i++) {
                allStrings.add(bucket.get(i));
            }
        }
        return allStrings;
    }

    /**
    * Strip the space(s) at the end of each word.
    * @param paddedWords words with space(s) at the end
    * @return words without spaces
    */
    private static List<String> stripWords(List<String> paddedWords) {
        List<String> unpaddedWords = new ArrayList<>();
        for (int i = 0; i < paddedWords.size(); i++) {
            String unpaddedStr = paddedWords.get(i).trim();
            unpaddedWords.add(unpaddedStr);
        }
        return unpaddedWords;
    }


    /**
    * Returns a list of words read from the given file name (all lowercase).
    * @param filename
    * @return A list of words
    */
    public static List<String> loadWords(String filename) {
        File inputFile = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        List<String> unsortedList = new ArrayList<>();
        int numberOfLines = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            unsortedList.add(line.toLowerCase());
            numberOfLines++;
        }
        scanner.close();
        return unsortedList;
    }


    // A function for testing your code
    public static void printWordList(List<String> words) {
      for (String word: words) {
          System.out.print(word + '\t');
      }
    }


    public static void main(String[] args) {
      // Feel free to modify main and add more testing code.
      List<String> wordList = new ArrayList<>();
      List<String> sortedList = new ArrayList<>();
      if (args.length == 1) {
          wordList = loadWords(args[0]);
          // Uncomment the line below after you've implemented loadWords()
          Collections.shuffle(wordList);
          System.out.println("--- Before radixSort ---\n");
          printWordList(wordList);
          sortedList = radixSort(wordList);
          System.out.println("\n--- After radixSort ---\n");
          printWordList(sortedList);

      } else {
          System.out.println("Usage: java RadixSort [filename]");
      }
    }
}
