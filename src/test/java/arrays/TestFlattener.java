package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Unit test cases for the array Flattener class.
 *
 * @author Festus Jejelowo
 */
public class TestFlattener {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testIntegerArraySuccess() {
        int[][] nestedArrays = {{1, 2, 24}, {12, 1241, 122}, {3, 2}, {7}};
        Flattener flattener = new Flattener(nestedArrays);
        assertEquals(9, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    @Test
    public void testEmptyIntegerArraySuccess() {
        int[][] nestedArrays = {};
        Flattener flattener = new Flattener(nestedArrays);
        assertEquals(0, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    @Test
    public void testSimpleMixedObjectArray() {
        //{{1,2,{3}},4} -> [1,2,3,4]
        int[] EXPECTED = new int[]{1, 2, 3, 4};
        Object[] nestedArrays2 = new Object[]{new Object[]{1, 2, new Object[]{3}}, 4};
        Flattener flattener = new Flattener(nestedArrays2);
        int[] flattened = flattener.flatten();
        assertEquals(4, flattened.length);
        assertEquals(Arrays.toString(EXPECTED), flattener.toString());
    }

    @Test
    public void testEmptyObjectArraySuccess() {
        Object[] nestedArrays = {};
        Flattener flattener = new Flattener(nestedArrays);
        assertEquals(0, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    @Test
    public void testLinearObjectArraySuccess() {
        Object[] nestedArrays = {34, 687, 12};
        Flattener flattener = new Flattener(nestedArrays);
        assertEquals(3, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    @Test
    public void testObjectArraySuccess() {
        Object[] nestedArrays3 = new Object[]{
            new Object[]{1, 8, new Object[]{9, 0, 5}, new Object[]{3}}, 4
        };
        Flattener flattener = new Flattener(nestedArrays3);
        assertEquals(7, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    @Test(expected = NumberFormatException.class)
    public void testObjectContainingInvalidIntegerArray() {
        Object[] nestedArrays3 = new Object[]{
            new Object[]{1, "8", new Object[]{"9", 0, 5}, new Object[]{3}}, 4
        };
        Flattener flattener = new Flattener(nestedArrays3);
        flattener.flatten();
    }

    @Test
    public void whenExceptionThrownThenApplyRuleAndCheckActuallMessage() {
        exceptionRule.expect(NumberFormatException.class);
        exceptionRule.expectMessage("Invalid integer value for String 8");
        Object[] nestedArrays3 = new Object[]{
            new Object[]{1, "8", new Object[]{"9", 0, 5}, new Object[]{3}}, 4
        };
        Flattener flattener = new Flattener(nestedArrays3);
        flattener.flatten();
    }

    @Test
    public void throwExceptionAndApplyRuleWhenInputIsNumberButNotInteger() {
        exceptionRule.expect(NumberFormatException.class);
        Object[] nestedArrays3 = new Object[]{
            new Object[]{1, 43.78, new Object[]{1.2f, 0, 5}, new Object[]{3}}, 4
        };
        Flattener flattener = new Flattener(nestedArrays3);
        flattener.flatten();
    }

    @Test
    public void testHugeObjectArrayPerformance() {

        int MAX = 100000;
        Object[] maxNestedObjectArray = generateMaxIntegerObjectArray(MAX);
        Flattener flattener = new Flattener(maxNestedObjectArray);
        assertEquals(MAX, flattener.flatten().length);
        assertEquals(Arrays.toString(flattener.flatten()), flattener.toString());
    }

    //Divide array into unequal block of arrays up to maximum integer value;
    private Object[] generateMaxIntegerObjectArray(int MAX) {
        List<Integer> integerBlocks = new ArrayList<>();
        integerBlocks.add((int) (0.07 * MAX));
        integerBlocks.add((int) (0.13 * MAX));
        integerBlocks.add((int) (0.21 * MAX));
        integerBlocks.add((int) (0.26 * MAX));
        integerBlocks.add((int) (0.33 * MAX));

        Object[] arbitraryArray = {
            toArray(1, integerBlocks.get(0)),
            new Object[]{
                new Object[]{
                    toArray(integerBlocks.get(0), integerBlocks.get(1)),
                    toArray(integerBlocks.get(1), integerBlocks.get(2)),
                    toArray(integerBlocks.get(2), integerBlocks.get(3))
                },
                toArray(integerBlocks.get(3), integerBlocks.get(4))
            }
        };
        return arbitraryArray;

    }

    private Object[] toArray(int start, int size) {
        int end = start + size;
        List<Object> objects = new ArrayList<>();
        for (int x = start; x < end; x++) {
            objects.add((Object) x);
        }
        return (Object[]) objects.toArray(new Object[objects.size()]);
    }
}
