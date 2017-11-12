package com.Studying;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void iterator() throws Exception {
        BinaryTree<Integer> testTree = new BinaryTree<>();
        testTree.add(50);
        testTree.add(30);
        testTree.add(60);
        testTree.add(10);
        testTree.add(40);
        testTree.add(55);
        testTree.add(70);
        testTree.add(5);
        testTree.add(18);
        testTree.add(35);
        testTree.add(43);
        testTree.add(57);
        testTree.add(2);
        testTree.add(7);
        testTree.add(16);
        testTree.add(20);
        testTree.add(47);
        testTree.add(14);
        testTree.add(15);

        int[] firstTestArray = {50, 30, 10, 5, 2, 7, 18, 16, 14, 15, 20, 40, 35, 43, 47, 60, 55, 57, 70};
        int[] secondTestArray = {50, 30, 14, 5, 2, 7, 18, 16, 15, 20, 43, 35, 47, 60, 57};

        int[] firstIterArray = new int[19];
        int[] secondIterArray = new int[15];

        int k = 0;
        Iterator<Integer> iter = testTree.iterator();
        while(iter.hasNext()) {
            firstIterArray[k++] = iter.next();
        }

        assertArrayEquals(firstTestArray, firstIterArray);

        ArrayList<Integer> testIntegers = new ArrayList<>();
        testIntegers.add(10);
        testIntegers.add(40);
        testIntegers.add(55);
        testIntegers.add(70);

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