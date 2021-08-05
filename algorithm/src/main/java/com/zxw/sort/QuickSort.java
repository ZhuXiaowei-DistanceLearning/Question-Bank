package com.zxw.sort;

/**
 * @author zxw
 * @date 2020/6/22 8:57
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49, 10};
        quickSort2(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void quickSort2(int[] arr, int l, int r) {
        if (l > r) {
            return;
        }
        int num = arr[l];
        int left = l;
        int right = r;
        while (left < right) {
            while (left < right && arr[right] > num) {
                right--;
            }
            while (left < right && arr[left] <= num) {
                left++;
            }
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
        arr[l] = arr[left];
        arr[left] = num;
        quickSort2(arr, l, left - 1);
        quickSort2(arr, left + 1, r);
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
                int temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
            }
        }
        arr[left] = arr[l];
        arr[l] = num;
        quickSort(arr, left, l - 1);
        quickSort(arr, l + 1, right);
    }
}
