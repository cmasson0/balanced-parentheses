package parentheses;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class BalancedParenthesesTest {

    private static final List<Integer> SOLUTIONS = List.of(1,1, 2,5,14,42,132,429,1430);
    private static final NumberFormat FORMATTER = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    @Test
    void testSimpleBalancedString() {
        assertTrue(BalancedParentheses.isBalanced("()"), "String should be balanced.");
    }

    @Test
    void testSimpleBalancedStringWithOtherChars() {
        assertTrue(BalancedParentheses.isBalanced("(a)"), "String should be balanced.");
    }

    @Test
    void testComplexBalancedString() {
        assertTrue(BalancedParentheses.isBalanced("(()(()))()"), "String should be balanced.");
    }

    @Test
    void testSimpleUnbalancedString() {
        assertFalse(BalancedParentheses.isBalanced("("), "String should be unbalanced.");
    }

    @Test
    void testComplexUnbalancedString() {
        assertFalse(BalancedParentheses.isBalanced("(()(())()"), "String should be unbalanced.");
    }

    @Test
    void emptyStringIsBalanced() {
        assertTrue(BalancedParentheses.isBalanced(""), "Empty string should be balanced.");
    }

    @Test
    void cannotComputeNumberOfSolutionsForNegativeNumberOfPairsOfParentheses() {
        assertThrows(IllegalArgumentException.class, () -> BalancedParentheses.numberOfBalancedStrings(-1));
    }

    @Test
    void numberOfSolutionsForSmallString() {
        System.out.println(BalancedParentheses.numberOfBalancedStrings(7).intValue());
    }

    @Test
    void numberOfSolutionsForSmallStrings() {
        for(int problemSize = 0; problemSize < SOLUTIONS.size(); problemSize++) {
            assertEquals(SOLUTIONS.get(problemSize).intValue(), BalancedParentheses.numberOfBalancedStrings(problemSize).intValue(), "Incorrect number of solutions for problemSize: " + problemSize);
        }
    }

    @Test
    void numberOfSolutionsForABigString() {
        int size = 1000;
        BigInteger result = BalancedParentheses.numberOfBalancedStrings(size);
        assertEquals("2046105521468021692642519982997827217179245642339057975844538099572176010191891863964968" +
                "026156453752449015750569428595097318163634370154637380666882886375203359653243390929717431080443509007" +
                "504772912973142253209352126946839844796747697638537600100637918819326569730982083021538057087711176285" +
                "7779092758696486368748568059565800576731736556668870034939446501641533969109270374063017990525846636110" +
                "16897272893305532116292143271037140718751625839812072682464343153792956281748582435751481498598087586998" +
                "603921577523657477775758899987954012641033870640665444651660246024318184109046864244732001962029120", result.toString());

        System.out.println("number of balanced strings with size " + size + ": " + FORMATTER.format(result));
    }
}