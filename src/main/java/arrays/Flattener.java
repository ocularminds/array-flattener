package arrays;

import java.util.*;

/**
 * Common utility class to flatten an array of arbitrarily nested arrays of
 * integers into a flat array of integers. e.g. [[1,2,[3]],4] -> [1,2,3,4]
 *
 * @author Festus Jejelowo
 */
public final class Flattener {

    private final int[][] integerArrays;
    private final Object[] objectArrays;
    private boolean isFlattened = false;
    private int[] flattened;
    private final String ERROR_PREFIX = "Invalid integer value for ";

    public Flattener(final int[][] input) {
        this.integerArrays = input;
        objectArrays = null;
    }

    public Flattener(final Object[] input) {
        this.integerArrays = null;
        objectArrays = input;
    }

    /**
     * Given arbitrary input arrays Determine the depth of the array which
     * translate highest length of the arrays; Iterate through the collection
     * picking each array at every index and add to the collections
     *
     * Since Java does not have mixed-type arrays (where an item could be either
     * an int or an int[]). A List objects feature is used to translate
     * arbitrary array to mixed Java Object array. The flatten method then tests
     * for type of input
     *
     *
     * @return 1 dimensional array of integers
     */
    public int[] flatten() {
        List<Integer> result;
        result = integerArrays != null ? flatten(this.integerArrays) : flatten(this.objectArrays);
        flattened = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            flattened[i] = result.get(i);
        }
        isFlattened = true;
        return flattened;
    }

    /**
     * Given arbitrary input arrays Determine the depth of the array which
     * translate highest length of the arrays; Iterate through the collection
     * picking each array at every index and add to the collections.
     *
     * @return 1 dimensional array of integers
     */
    private List<Integer> flatten(int[][] arrays) {
        int size = getSize(arrays);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int[] array : arrays) {
                if (i < array.length) {
                    result.add(array[i]);
                }
            }
        }
        return result;
    }


    /**
     * Given arbitrary input arrays objects, recursively check if object
     * is an Array or Not. If not an array and is Integer, add to the collection.
     *
     * @return 1 dimensional array of integers
     */
    private List<Integer> flatten(Object[] objects) {
        List<Integer> elements = new ArrayList<>();
        for (Object object : objects) {
            if (object instanceof Integer) {
                elements.add((Integer) object);
            } else if (object instanceof Object[]) {
                elements.addAll(flatten((Object[]) object));
            } else {
                throw new NumberFormatException(
                        ERROR_PREFIX + object.getClass().getSimpleName() + " " + object
                );
            }

        }
        return elements;
    }

    private int getSize(int[][] arrays) {
        int length = 0;
        for (int[] array : arrays) {
            length = array.length > length ? array.length : length;
        }
        return length;
    }

    @Override
    public String toString() {
        if (!isFlattened) {
            flatten();
        }
        return Arrays.toString(flattened);
    }
}
