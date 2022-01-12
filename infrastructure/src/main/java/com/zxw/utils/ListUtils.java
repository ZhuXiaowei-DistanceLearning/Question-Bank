package com.zxw.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxw
 * @date 2022/1/12 9:19
 */
public class ListUtils {
    public static String[] attr1 = new String[]{"A", "B", "C", "D", "E", "F", null};
    public static String[] attr2 = new String[]{"1", "2", "3", "D", "E", "F", null};
    public static List<String> list1 = Arrays.asList(attr1);
    public static List<String> list2 = Arrays.asList(attr2);

    // 交集：[null, D, E, F]
    // 补集：[A, 1, B, 2, C, 3]
    // 并集：[null, A, 1, B, 2, C, 3, D, E, F]
    // list1的差集：[A, B, C]
    // list2的差集：[1, 2, 3]
    public static void main(String[] args) {
        System.out.println("交集：" + CollectionUtils.intersection(list1, list2)); // 交集
        System.out.println("补集：" + CollectionUtils.disjunction(list1, list2)); // 补集
        System.out.println("并集：" + CollectionUtils.union(list1, list2)); // 并集
        System.out.println("list1的差集：" + CollectionUtils.subtract(list1, list2)); // list1的差集
        System.out.println("list2的差集：" + CollectionUtils.subtract(list2, list1)); // list2的差集
    }


    /**
     *  交集：[null, D, E, F]
     * 补集：[1, A, 2, B, 3, C]
     * 并集：[null, 1, A, 2, B, 3, C, D, E, F]
     * list1的差集[A, B, C]
     * list2的差集[1, 2, 3]
     * list1的差集：[A, B, C]
     * list2的差集：[1, 2, 3]
     */
    public static void test1() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(attr1));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(attr2));

        System.out.println("交集：" + CollectionUtil.intersection(list1, list2)); // 交集
        System.out.println("补集：" + CollectionUtil.disjunction(list1, list2)); // 补集
        System.out.println("并集：" + CollectionUtil.union(list1, list2)); //并集
        System.out.println("list1的差集"+CollectionUtil.subtract(list1,list2));
        System.out.println("list2的差集"+CollectionUtil.subtract(list2,list1));
        System.out.println("list1的差集：" + CollectionUtil.subtractToList(list1, list2));
        System.out.println("list2的差集：" + CollectionUtil.subtractToList(list2, list1));
    }

    /**
     * 交集：[D, E, F, null]
     * 集合list1的差集：[A, B, C]
     * 集合list2的差集：[1, 2, 3]
     * 并集：[A, B, C, D, E, F, null, 1, 2, 3]
     */
    public static void test2() {
        List<Object> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
        System.out.println("交集：" + intersection);

        List<String> subtract1 = list1.stream().filter(s -> !list2.contains(s)).collect(Collectors.toList());
        System.out.println("集合list1的差集：" + subtract1);
        List<String> subtract2 = list2.stream().filter(s -> !list1.contains(s)).collect(Collectors.toList());
        System.out.println("集合list2的差集：" + subtract2);

        List<String> union1 = list1.parallelStream().collect(Collectors.toList());
        List<String> union2 = list2.parallelStream().collect(Collectors.toList());
        union1.addAll(union2);
        List<String> union3 = union1.stream().distinct().collect(Collectors.toList());
        System.out.println("并集：" + union3);
    }

    /**
     * 交集：[D, E, F, null]
     * 并集：[null, A, 1, B, 2, C, 3, D, E, F]
     * 集合A的差集：[A, B, C]
     * 集合B的差集：[1, 2, 3]
     */
    public static void test4() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(attr1));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(attr2));
        list1.retainAll(list2);
        System.out.println("交集：" + list1);

        ArrayList<String> list3 = new ArrayList<>(Arrays.asList(attr1));
        ArrayList<String> list4 = new ArrayList<>(Arrays.asList(attr2));
        HashSet<Object> set = new HashSet<>();
        set.addAll(list3);
        set.addAll(list4);
        System.out.println("并集：" + set);

        ArrayList<String> list5 = new ArrayList<>(Arrays.asList(attr1));
        ArrayList<String> list6 = new ArrayList<>(Arrays.asList(attr2));
        list5.removeAll(list6);
        System.out.println("集合A的差集：" + list5);
        ArrayList<String> list7 = new ArrayList<>(Arrays.asList(attr1));
        ArrayList<String> list8 = new ArrayList<>(Arrays.asList(attr2));
        list8.removeAll(list7);
        System.out.println("集合B的差集：" + list8);
    }

}
