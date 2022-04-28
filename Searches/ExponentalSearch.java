package com.thealgorithms.searches;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import com.thealgorithms.devutils.searches.SearchAlgorithm;

import static java.lang.String.format;

class ExponentialSearch implements SearchAlgorithm {

    public static void main(String[] args) {
        Random r = ThreadLocalRandom.current();

        int size = 100;
        int maxElement = 100000;

        Integer[] integers
                = IntStream.generate(() -> r.nextInt(maxElement))
                        .limit(size)
                        .sorted()
                        .boxed()
                        .toArray(Integer[]::new);

        // The element that should be found
        int shouldBeFound = integers[r.nextInt(size - 1)];

        ExponentialSearch search = new ExponentialSearch();
        int atIndex = search.find(integers, shouldBeFound);

        System.out.println(
                format(
                        "Should be found: %d. Found %d at index %d. An array length %d",
                        shouldBeFound, integers[atIndex], atIndex, size));

        int toCheck = Arrays.binarySearch(integers, shouldBeFound);
        System.out.println(
                format(
                        "Found by system method at an index: %d. Is equal: %b", toCheck, toCheck == atIndex));

    }

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array[0] == key) {
            return 0;
        }
        if (array[array.length - 1] == key) {
            return array.length;
        }

        int range = 1;

        while (range < array.length && array[range].compareTo(key) <= -1) {
            range = range * 2;
        }

        return Arrays.binarySearch(array, range / 2, Math.min(range, array.length), key);
    }
}
