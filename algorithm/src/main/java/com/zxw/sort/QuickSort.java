package com.zxw.sort;

import com.zxw.leetcode.type.tree.LeetCodeWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author zxw
 * @date 2020/6/22 8:57
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49, 10};
        System.out.println(Arrays.toString(arr));
        int[] nums = LeetCodeWrapper.stringToIntegerArray("[2,0,2,1,1,0]");
//        quickSort3(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
        quickSort2(arr, 0, arr.length - 1);
        quickSort2(nums, 0, nums.length - 1);
    }

    public static void quickSort2(int[] arr, int l, int r) {
        if (l > r) {
            return;
        }
        int num = arr[l];
        int left = l + 1;
        int right = r;
        while (true) {
            while (left <= right && arr[left] < num) {
                left++;
            }
            while (right <= r && arr[right] > num) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
                left++;
                right--;
            } else {
                break;
            }
        }
        swap(arr, l, right);
        quickSort2(arr, l, right - 1);
        quickSort2(arr, right + 1, r);
    }

    private static void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        System.out.println(Arrays.toString(arr));
        printSwap(arr, left, right);
    }

    static void printSwap(int[] arr, int left, int right) {
        String leftSpace = getLeftSpace(Arrays.copyOfRange(arr, 0, left), 0);
        String rightSpace = getLeftSpace(Arrays.copyOfRange(arr, 0, right), leftSpace.length() + 1);
        System.out.println(leftSpace + "\uD83E\uDC45" + rightSpace + "\uD83E\uDC45");
    }

    static String getLeftSpace(int[] arr, int num) {
        int i = StringUtils.length(Arrays.toString(arr)) + 1;
        return StringUtils.repeat(" ", i - num);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int num = arr[left];
        int l = left;
        int r = right;
        while (l < r) {
            // 如果大于基准点则左侧
            while (l < r && arr[r] > num) {
                r--;
            }
            // 如果小于基准点则右移
            while (l < r && arr[l] <= num) {
                l++;
            }
            if (l < r) {
                swap(arr, l, r);
            }
        }
        arr[left] = arr[l];
        arr[l] = num;
        quickSort(arr, left, l - 1);
        quickSort(arr, l + 1, right);
    }

    public static void quickSort3(int[] nums, int l, int r) {
        if (l > r) {
            return;
        }
        int left = l;
        int right = r;
        int num = nums[l];
        while (left < right) {
            // 找到左边大于基准数的数
            while (left < right && nums[left] <= num) {
                left++;
            }
            // 找到右边小于基准数的数
            while (left < right && nums[right] > num) {
                right--;
            }
            if (left < right) {
                swap(nums, left, right);
                printSwap(nums, left, right);
            }
        }
        // 最后交换中位数
        nums[l] = nums[left];
        nums[left] = num;
        quickSort3(nums, l, left - 1);
        quickSort3(nums, left + 1, r);
    }
}
