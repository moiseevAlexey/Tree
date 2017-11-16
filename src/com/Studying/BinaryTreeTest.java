package com.Studying;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void iterator() throws Exception {
        BinaryTree<Integer> testTree = new BinaryTree<>();

        testTree.add(28);
        testTree.add(31);
        testTree.add(30);
        testTree.add(77);
        testTree.add(11);
        testTree.add(57);
        testTree.add(77);
        testTree.add(54);
        testTree.add(7);
        testTree.add(69);
        testTree.add(38);
        testTree.add(28);
        testTree.add(94);
        testTree.add(8);
        testTree.add(69);
        testTree.add(95);
        testTree.add(88);
        testTree.add(49);
        testTree.add(18);
        testTree.add(11);

        int[] firstTestArray = {7, 8, 11, 18, 28, 30, 31, 38, 49, 54, 57, 69, 77, 88, 94, 95};
        int[] secondTestArray = {7, 11, 18, 30, 31, 49, 54, 69, 77, 88, 94, 95};

        int[] firstIterArray = new int[16];
        int[] secondIterArray = new int[12];

        int k = 0;
        Iterator<Integer> iter = testTree.iterator();
        while(iter.hasNext()) {
            firstIterArray[k++] = iter.next();
        }

        assertArrayEquals(firstTestArray, firstIterArray);

        ArrayList<Integer> testIntegers = new ArrayList<>();
        testIntegers.add(38);
        testIntegers.add(8);
        testIntegers.add(28);
        testIntegers.add(57);

        iter = testTree.iterator();
        while(iter.hasNext()) {
            Integer i = iter.next();
            if (testIntegers.contains(i)) {
                iter.remove();
            }
        }

        k = 0;
        iter = testTree.iterator();
        while(iter.hasNext()) {
            secondIterArray[k++] = iter.next();
        }

        assertArrayEquals(secondTestArray, secondIterArray);
    }
}