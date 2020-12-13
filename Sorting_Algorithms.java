import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null.");
        }

        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j > -1 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }

    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null.");
        }
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    swapped = true;
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                }
            }
            if (!swapped) {
                break;
            }
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator may not be null");
        }

        if (arr.length <= 1) {
            return;
        }
        T[] left = (T[]) new Object[arr.length / 2];
        T[] right;
        if (arr.length % 2 == 0) {
            right = (T[]) new Object[arr.length / 2];
            for (int i = 0; i < arr.length / 2; i++) {
                right[i] = arr[i + (arr.length / 2)];
            }
        } else {
            right = (T[]) new Object[arr.length / 2 + 1];
            for (int i = 0; i < arr.length / 2 + 1; i++) {
                right[i] = arr[i + (arr.length / 2)];
            }
        }
        for (int i = 0; i < (arr.length / 2); i++) {
            left[i] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);
        merge(left, right, arr, comparator);
    }

    /***
     * Helper method to merge two arrays
     * @param left left array
     * @param right right array
     * @param arr array to add data to
     * @param c comparator
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] left, T[] right, T[] arr, Comparator<T> c) {
        boolean swapped;
        int i0 = 0;
        int i1 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i0 == left.length) {
                arr[i] = right[i1++];
            } else if (i1 == right.length) {
                arr[i] = left[i0++];
            } else if (c.compare(left[i0], right[i1]) < 0) {
                arr[i] = left[i0++];
            } else {
                arr[i] = right[i1++];
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        }
        LinkedList<Integer>[] counter = (LinkedList<Integer>[]) new LinkedList[19];
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            counter[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<Integer>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        arr[arrIdx++] = num;
                    }
                    buckets[i].clear();
                }
            }
            div *= 10;
        }
    }


    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i : data) {
            q.add(i);
        }
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = q.remove();
        }
        return arr;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Argument can be null.");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("K must be within range.");
        } else {
            return kthSelectHelper(k, arr, comparator, rand, 0, arr.length);
        }
    }

    /***
     * Helper method for kth select
     * @param k index to retrieve data from + 1
     * @param arr the array that should be modified after the method
     *            is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param left left array
     * @param right right array
     * @param <T> data type to sort
     * @return the kth smallest element
     */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator, Random rand, int left, int right) {
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIndex];
        int j = right - 1;
        T lowest = arr[left];
        arr[pivotIndex] = lowest;
        arr[left] = pivot;
        int i = left + 1;
        while (i <= j) {
            while (i <= j && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        arr[left] = arr[j];
        arr[j] = pivot;
        if (j == k - 1) {
            return pivot;
        } else if (j < k - 1) {
            return kthSelectHelper(k, arr, comparator, rand, j + 1, right);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, left, j);
        }
    }
}
