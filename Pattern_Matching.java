import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Joseph Guo
 * @version 1.0
 * @userid jguo345
 * @GTID 903437631
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or 0.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null.");
        }
        List<Integer> list = new ArrayList<>();
        int t = 0;
        while (t <= text.length() - pattern.length()) {
            int i = 0;
            while (i < pattern.length() && comparator.compare(text.charAt(t + i), pattern.charAt(i)) == 0) {
                i++;
            }
            if (i == pattern.length()) {
                list.add(t);
            }
            t++;
        }
        return list;
    }


    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Pattern or comparator cannot be null");
        }
        int[] failTable = new int[pattern.length()];
        int j = 1;
        int i = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                failTable[j] = i;
                j++;
            } else {
                if (i != 0) {
                    i = failTable[i - 1];
                } else {
                    failTable[j] = 0;
                    j++;
                }
            }
        }
        return failTable;

    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or of length 0.");
        }
        if (comparator == null || text == null) {
            throw new IllegalArgumentException("Text or Comparator cannot be null.");
        }
        if ((text.length() - pattern.length()) < 0) {
            List<Integer> indexes = new ArrayList<>();
            return indexes;
        }
        int[] failTable = buildFailureTable(pattern, comparator);
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i <= (text.length() - pattern.length())) {
            while (j < pattern.length()
                    && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    indexes.add(i);
                }
                int nextAlignment = failTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
        return indexes;

    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cant be null.");
        }
        Map<Character, Integer> lot = new HashMap<>();
        if (pattern.length() == 0) {
            return lot;
        }
        for (int i = 0; i < pattern.length(); i++) {
            lot.put(pattern.charAt(i), null);
        }
        int j = 0;
        while (j < pattern.length()) {
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(j) == pattern.charAt(i)) {
                    lot.put(pattern.charAt(j), i);
                }
            }
            j++;
        }
        return lot;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or empty.");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator cannot be null.");
        }
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        List<Integer> indexes = new ArrayList<>();

        int i = pattern.length() - 1;
        int j = pattern.length() - 1;
        while (i < text.length()) {
            char txt = text.charAt(i);
            char pat = pattern.charAt(j);
            if (comparator.compare(txt, pat) == 0) {
                if (j == 0) {
                    indexes.add(i);
                    j = pattern.length() - 1;
                    i += pattern.length();
                } else {
                    i--;
                    j--;
                }
            } else {
                int last = lastTable.getOrDefault(text.charAt(i), -1);
                i += pattern.length() - Math.min(j, 1 + last);
                j = pattern.length() - 1;
            }
        }
        return indexes;
    }
}
