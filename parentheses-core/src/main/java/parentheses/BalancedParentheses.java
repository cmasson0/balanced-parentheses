package parentheses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;


public class BalancedParentheses {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods
    //~ ----------------------------------------------------------------------------------------------------------------

    static boolean isBalanced(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }

    /*
     * Answer to the problem: "Count the number of expressions containing n pairs of parentheses which are correctly matched".
     */
    public static BigInteger numberOfBalancedStrings(int numberOfPairsOfParentheses) {
        if(numberOfPairsOfParentheses <0)
            throw new IllegalArgumentException("The number of pairs of parentheses must be >= 0");

        CatalanNumber cn = new CatalanNumber(new ArrayCache<>(numberOfPairsOfParentheses));

        // Need to compute in several iterations to avoid recursion to go too deep and cause StackOverflowError
        // For each loop, the CatalanNumber object keeps all the intermediate solutions in the cache, that are reused in the next iteration.
        // Computing the CatalanNumber in several steps adds very little overhead, and does not create redundant operations.
        // Note that the stack overflow error occurs much earlier due to the use of the Stream API in the recursion.
        int n = 500;
        while (n < numberOfPairsOfParentheses) {
            cn.compute(Math.min(n, numberOfPairsOfParentheses));
            n += 500;
        }

        return cn.compute(numberOfPairsOfParentheses);
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Inner Interfaces
    //~ ----------------------------------------------------------------------------------------------------------------

    private interface ICache<V> {
        V computeIfAbsent(final int index, Function<Integer, ? extends V> mappingFunction);
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Nested Classes
    //~ ----------------------------------------------------------------------------------------------------------------

    private static class CatalanNumber {
        private final ICache<BigInteger> cache;

        CatalanNumber(ICache<BigInteger> cache) {
            this.cache = cache;
        }

        // Note: We are emulating the computeIfAbsent method provided by the Map interface.
        // Unfortunately, we cannot use a map here as it is not allowed to modify the map itself in the function provided to the computeIfAbsent method.
        // This is what we do here as we are doing recursive calls that update the cache.
        BigInteger compute(int n) {
            final int bound = n - 1;

            return isInitialCase(n) ? BigInteger.ONE :
                    IntStream.rangeClosed(0, bound)
                            .mapToObj(i -> cache.computeIfAbsent(i, this::compute)
                                    .multiply(cache.computeIfAbsent(bound - i, this::compute)))
                            .reduce(BigInteger.ZERO, BigInteger::add);
        }

        private boolean isInitialCase(int n) {
            return (n == 0) || (n == 1);
        }
    }

    private static class ArrayCache<V> implements ICache<V> {

        private final V NO_VALUE = null;
        private final List<V> cache;

        ArrayCache(int size) {
            cache = new ArrayList<>(Collections.nCopies(size, NO_VALUE));
        }

        @Override
        public V computeIfAbsent(final int index, Function<Integer, ? extends V> mappingFunction) {
            if (index < 0)
                throw new IllegalArgumentException("cannot work on negative indices");

            if (index >= cache.size())
                throw new IllegalArgumentException("cache size too small.");

            V value = cache.get(index);
            if (Objects.equals(value, NO_VALUE)) {
                //System.out.println("Value not found in cache for index: " + index);
                value = mappingFunction.apply(index);
                cache.set(index, value);
            }
            return value;
        }
    }
}