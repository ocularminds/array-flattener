# array-flattener
Production ready Flattener for array of arbitrarily nested arrays of integers into a flat array of integers

Use Case:
```java

INPUT {{1,2,{3}},4} -> [1,2,3,4]

int[] EXPECTED = new int[]{1, 2, 3, 4};
Object[] nestedArrays2 = new Object[]{new Object[]{1, 2, new Object[]{3}}, 4};
Flattener flattener = new Flattener(nestedArrays2);
int[] flattened = flattener.flatten();
assertEquals(4, flattened.length);
assertEquals(Arrays.toString(EXPECTED), flattener.toString());

```
