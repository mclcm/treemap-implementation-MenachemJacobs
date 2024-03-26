import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author downey
 */
public class MyTreeMapTest {

    private MyTreeMap<String, Integer> map;

    /**
     * @throws java.lang.Exception may throw exception if key value is illegal
     */
    @Before
    public void setUp() throws Exception {
        map = new MyTreeMap<>();
        MyTreeMap<String, Integer>.Node node08 = map.makeNode("08", 8);

        MyTreeMap<String, Integer>.Node node03 = map.makeNode("03", 3);
        MyTreeMap<String, Integer>.Node node10 = map.makeNode("10", 10);
        node08.left = node03;
        node08.right = node10;

        MyTreeMap<String, Integer>.Node node01 = map.makeNode("01", 1);
        MyTreeMap<String, Integer>.Node node06 = map.makeNode("06", 6);
        MyTreeMap<String, Integer>.Node node14 = map.makeNode("14", 14);
        node03.left = node01;
        node03.right = node06;
        node10.right = node14;

        MyTreeMap<String, Integer>.Node node04 = map.makeNode("04", 4);
        MyTreeMap<String, Integer>.Node node07 = map.makeNode("07", 7);
        MyTreeMap<String, Integer>.Node node13 = map.makeNode("13", 13);
        node06.left = node04;
        node06.right = node07;
        node14.left = node13;

        map.setTree(node08, 9);
    }

    /**
     * Test method for {@link MyTreeMap#clear()}.
     */
    @Test
    public void testClear() {
        map.clear();
        assertEquals(0, map.size());
    }

    /**
     * Test method for {@link MyTreeMap#containsKey(java.lang.Object)}.
     */
    @Test
    public void testContainsKey() {
        assertTrue(map.containsKey("03"));
        assertFalse(map.containsKey("05"));
    }

    /**
     * Test method for {@link MyTreeMap#containsValue(java.lang.Object)}.
     */
    @Test
    public void testContainsValue() {
        assertTrue(map.containsValue(3));
        assertFalse(map.containsValue(5));
    }

    /**
     * Test method for {@link MyTreeMap#get(java.lang.Object)}.
     */
    @Test
    public void testGet() {
        assertEquals(1, map.get("01"));
        assertEquals(3, map.get("03"));
        assertEquals(4, map.get("04"));
        assertEquals(6, map.get("06"));
        assertEquals(7, map.get("07"));
        assertEquals(8, map.get("08"));
        assertEquals(10, map.get("10"));
        assertEquals(13, map.get("13"));
        assertEquals(14, map.get("14"));

        assertNull(map.get("02"));
        assertNull(map.get("05"));
    }

    /**
     * Test method for {@link MyTreeMap#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    /**
     * Test method for {@link MyTreeMap#keySet()}.
     */
    @Test
    public void testKeySet() {
        Set<String> keySet = map.keySet();
        assertEquals(9, keySet.size());
        assertTrue(keySet.contains("03"));
        assertFalse(keySet.contains("05"));

        List<String> list = new ArrayList<>();
        list.addAll(keySet);

        // check that the keys are in ascending order
        for (int i = 0; i < list.size() - 1; i++) {
            System.out.println(list.get(i));
            assertTrue( list.get(i).compareTo(list.get(i + 1)) <= 0);
        }
        // TODO: fight with hamcrest
        // Collections.sort(list);
        // assertThat(keySet, contains(list));
    }

    /**
     * Test method for {@link MyTreeMap#put(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public void testPut() {
        map.put("06", 66);
		assertEquals(9, map.size());
		assertEquals(66, map.get("06"));

        map.put("05", 5);
		assertEquals(10, map.size());
		assertEquals(5, map.get("05"));
    }

    /**
     * Test method for {@link MyTreeMap#putAll(java.util.Map)}.
     */
    @Test
    public void testPutAll() {
        Map<String, Integer> m = new HashMap<>();
        m.put("02", 2);
        m.put("05", 5);
        m.put("12", 12);
        map.putAll(m);
		assertEquals(12, map.size());
    }

    /**
     * Test method for {@link MyTreeMap#remove(java.lang.Object)}.
     */
    @Test
    public void testRemove() {
        // nothing to test, since this method is not implemented
    }

    /**
     * Test method for {@link MyTreeMap#size()}.
     */
    @Test
    public void testSize() {
		assertEquals(9, map.size());
    }

    /**
     * Test method for {@link MyTreeMap#values()}.
     */
    @Test
    public void testValues() {
        Collection<Integer> keySet = map.values();
		assertEquals(9, keySet.size());
		assertTrue(keySet.contains(3));
		assertFalse(keySet.contains(5));
    }
}