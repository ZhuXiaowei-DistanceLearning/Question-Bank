package com.zxw.leetcode.algorithm;

import com.zxw.common.datastruct.TreeNode;

import java.lang.annotation.Target;
import java.util.*;

/**
 * @author zxw
 * @date 2020/6/23 14:19
 */
public class Daily {
    public static void main(String[] args) {
        Daily daily = new Daily();
        daily.kidsWithCandies(new int[]{2, 3, 5, 1, 3}, 3);
        daily.shuffle(new int[]{2, 5, 1, 3, 4, 7}, 3);
        daily.subtractProductAndSum(234);
        daily.defangIPaddr("1.1.1.1");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.DATE));
        String s1 = new String("ab");
        String s2 = "ab";
        String s3 = s1 + "c";
        System.out.println(s3);
        System.out.println(s2 == s1);
        HashMap map = new HashMap();
//        for (int i = 0; i < 10000000; i++) {
//            map.put(i, i);
//        }
    }

    /**
     * 1480. 一维数组的动态和
     * 给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
     * 请返回 nums 的动态和。
     * 示例 1：
     * 输入：nums = [1,2,3,4]
     * 输出：[1,3,6,10]
     * 解释：动态和计算过程为 [1, 1+2, 1+2+3, 1+2+3+4] 。
     *
     * @param nums
     * @return
     */
    public int[] runningSum(int[] nums) {
        if (nums.length == 0) {
            return new int[]{0};
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 0; i < nums.length; i++) {
            dp[i] = dp[i - 1] + nums[i];
        }
        return dp;
    }

    /**
     * 1486. 数组异或操作
     * 给你两个整数，n 和 start 。
     * 数组 nums 定义为：nums[i] = start + 2*i（下标从 0 开始）且 n == nums.length 。
     * 请返回 nums 中所有元素按位异或（XOR）后得到的结果。
     * 示例 1：
     * 输入：n = 5, start = 0
     * 输出：8
     * 解释：数组 nums 为 [0, 2, 4, 6, 8]，其中 (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8 。
     * "^" 为按位异或 XOR 运算符。
     *
     * @param n
     * @param start
     * @return
     */
    public int xorOperation(int n, int start) {
        int[] dp = new int[n];
        dp[0] = start;
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] ^ (start + 2 * i);
        }
        return dp[n - 1];
    }

    /**
     * 1431. 拥有最多糖果的孩子
     * 给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
     * 对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。
     * 示例 1：
     * 输入：candies = [2,3,5,1,3], extraCandies = 3
     * 输出：[true,true,true,false,true]
     * 解释：
     * 孩子 1 有 2 个糖果，如果他得到所有额外的糖果（3个），那么他总共有 5 个糖果，他将成为拥有最多糖果的孩子。
     * 孩子 2 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
     * 孩子 3 有 5 个糖果，他已经是拥有最多糖果的孩子。
     * 孩子 4 有 1 个糖果，即使他得到所有额外的糖果，他也只有 4 个糖果，无法成为拥有糖果最多的孩子。
     * 孩子 5 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
     *
     * @param candies
     * @param extraCandies
     * @return
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> ans = new ArrayList<>();
        int max = candies[0];
        for (int i = 1; i < candies.length; i++) {
            max = Math.max(max, candies[i]);
        }
        for (int i = 0; i < candies.length; i++) {
            if (candies[i] + extraCandies >= max) {
                ans.add(true);
            } else {
                ans.add(false);
            }
        }
        return ans;
    }

    /**
     * 1342. 将数字变成 0 的操作次数
     * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
     * 示例 1：
     * 输入：num = 14
     * 输出：6
     * 解释：
     * 步骤 1) 14 是偶数，除以 2 得到 7 。
     * 步骤 2） 7 是奇数，减 1 得到 6 。
     * 步骤 3） 6 是偶数，除以 2 得到 3 。
     * 步骤 4） 3 是奇数，减 1 得到 2 。
     * 步骤 5） 2 是偶数，除以 2 得到 1 。
     * 步骤 6） 1 是奇数，减 1 得到 0 。
     *
     * @param num
     * @return
     */
    public int numberOfSteps(int num) {
        int count = 0;
        while (num != 0) {
            num = (num & 1) == 1 ? num - 1 : num / 2;
            count++;
        }
        return count;
    }

    /**
     * 771. 宝石与石头
     *  给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。 S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
     * 示例 1:
     * 输入: J = "aA", S = "aAAbbbb"
     * 输出: 3
     *
     * @param J
     * @param S
     * @return
     */
    public int numJewelsInStones(String J, String S) {
        int ans = 0;
        for (int i = 0; i < J.length(); i++) {
            for (int j = 0; j < S.length(); j++) {
                if (J.charAt(i) == S.charAt(j)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * LCP 01. 猜数字
     * 小A 和 小B 在玩猜数字。小B 每次从 1, 2, 3 中随机选择一个，小A 每次也从 1, 2, 3 中选择一个猜。他们一共进行三次这个游戏，请返回 小A 猜对了几次？
     * 输入的guess数组为 小A 每次的猜测，answer数组为 小B 每次的选择。guess和answer的长度都等于3。
     * 示例 1：
     * 输入：guess = [1,2,3], answer = [1,2,3]
     * 输出：3
     * 解释：小A 每次都猜对了。
     *
     * @param guess
     * @param answer
     * @return
     */
    public int game(int[] guess, int[] answer) {
        int ans = 0;
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == answer[i]) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1470. 重新排列数组
     * 给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
     * 请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。
     * 输入：nums = [2,5,1,3,4,7], n = 3
     * 示例 1：
     * 输出：[2,3,5,4,1,7]
     * 解释：由于 x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 ，所以答案为 [2,3,5,4,1,7]
     *
     * @param nums
     * @param n
     * @return
     */
    public int[] shuffle(int[] nums, int n) {
        int[] arr = new int[nums.length];
        int length = nums.length / 2;
        for (int i = 0; i < length; i++) {
            if (i == n - 1) {
                arr[nums.length - 1] = nums[i + length];
                arr[nums.length - 2] = nums[i];
                continue;
            }
            arr[i * 2] = nums[i];
            arr[i * 2 + 1] = nums[i + length];
        }
        return arr;
    }

    /**
     * 1281. 整数的各位积和之差
     * 给你一个整数 n，请你帮忙计算并返回该整数「各位数字之积」与「各位数字之和」的差。
     * 示例 1：
     * <p>
     * 输入：n = 234
     * 输出：15
     * 解释：
     * 各位数之积 = 2 * 3 * 4 = 24
     * 各位数之和 = 2 + 3 + 4 = 9
     * 结果 = 24 - 9 = 15
     *
     * @param n
     * @return
     */
    public int subtractProductAndSum(int n) {
        int mult = 1;
        int sum = 0;
        while (n > 0) {
            int mid = n % 10;
            n = n / 10;
            mult = mult * mid;
            sum = sum + mid;
        }
        return mult - sum;
    }

    /**
     * 1108. IP 地址无效化
     * 给你一个有效的 IPv4 地址 address，返回这个 IP 地址的无效化版本。
     * 所谓无效化 IP 地址，其实就是用 "[.]" 代替了每个 "."。
     * 示例 1：
     * 输入：address = "1.1.1.1"
     * 输出："1[.]1[.]1[.]1"
     *
     * @param address
     * @return
     */
    public String defangIPaddr(String address) {
        return address.replaceAll("\\.", "[.]");
    }

    /**
     * 1295. 统计位数为偶数的数字给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。
     * 示例 1：
     * 输入：nums = [12,345,2,6,7896]
     * 输出：2
     * 解释：
     * 12 是 2 位数字（位数为偶数） 
     * 345 是 3 位数字（位数为奇数）  
     * 2 是 1 位数字（位数为奇数） 
     * 6 是 1 位数字 位数为奇数） 
     * 7896 是 4 位数字（位数为偶数）  
     * 因此只有 12 和 7896 是位数为偶数的数字
     *
     * @param nums
     * @return
     */
    public int findNumbers(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (String.valueOf(nums[i]).length() % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 1365. 有多少小于当前数字的数字
     * 给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。
     * 换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，其中 j 满足 j != i 且 nums[j] < nums[i] 。
     * 以数组形式返回答案。
     * 示例 1：
     * 输入：nums = [8,1,2,2,3]
     * 输出：[4,0,1,1,3]
     * 解释：
     * 对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。
     * 对于 nums[1]=1 不存在比它小的数字。
     * 对于 nums[2]=2 存在一个比它小的数字：（1）。
     * 对于 nums[3]=2 存在一个比它小的数字：（1）。
     * 对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）。
     *
     * @param nums
     * @return
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] arr = new int[nums.length];
        for (int i = 0; i < arr.length; i++) {
            int c = 0;
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    continue;
                }
                if (nums[i] > nums[j]) {
                    c++;
                }
            }
            arr[i] = c;
        }
        return arr;
    }


    /**
     * 1313. 解压缩编码列表
     * 给你一个以行程长度编码压缩的整数列表 nums 。
     * 考虑每对相邻的两个元素 [freq, val] = [nums[2*i], nums[2*i+1]] （其中 i >= 0 ），每一对都表示解压后子列表中有 freq 个值为 val 的元素，你需要从左到右连接所有子列表以生成解压后的列表。
     * 请你返回解压后的列表。
     * 示例：
     * 输入：nums = [1,2,3,4]
     * 输出：[2,4,4,4]
     * 解释：第一对 [1,2] 代表着 2 的出现频次为 1，所以生成数组 [2]。
     * 第二对 [3,4] 代表着 4 的出现频次为 3，所以生成数组 [4,4,4]。
     * 最后将它们串联到一起 [2] + [4,4,4] = [2,4,4,4]。
     *
     * @param nums
     * @return
     */
    public int[] decompressRLElist(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length / 2; i++) {
            while (nums[i * 2] != 0) {
                list.add(nums[i * 2 + 1]);
                nums[i * 2]--;
            }
        }
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 1. 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] arr = new int[1024];
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (arr[diff & 1023] != 0) {
                return new int[]{arr[diff & 1023], i};
            }
            arr[nums[i] & 1023] = i + 1;
        }
        throw new RuntimeException("no tow sum");
    }

    /**
     * 26. 删除排序数组中的重复项
     * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * 示例 1:
     * 给定数组 nums = [1,1,2],
     * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int p = 0;
        int q = 1;
        while (q < nums.length) {
            if (nums[p] != nums[q]) {
                if (q - p > 1) {
                    nums[p + 1] = nums[q];
                }
                p++;
            }
            q++;
        }
        return p + 1;
    }

    /**
     * 27. 移除元素
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * 示例 1:
     * 给定 nums = [3,2,2,3], val = 3,
     * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int l = 0;
        int r = 0;
        while (r < nums.length) {
            if (nums[r] != val) {
                nums[l] = nums[r];
                l++;
            }
            r++;
        }
        return l;
    }

    /**
     * 28. 实现 strStr()
     * 实现 strStr() 函数。
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     * 输入: haystack = "hello", needle = "ll"
     * 示例 1:
     * 输出: 2
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    /**
     * 38. 外观数列
     * 给定一个正整数 n（1 ≤ n ≤ 30），输出外观数列的第 n 项。
     * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。前五项如下：
     * 注意：整数序列中的每一项将表示为一个字符串。
     * 1.     1
     * 2.     11
     * 3.     21
     * 4.     1211
     * 5.     111221
     * 第一项是数字 1
     * 描述前一项，这个数是 1 即 “一个 1 ”，记作 11
     * 描述前一项，这个数是 11 即 “两个 1 ” ，记作 21
     * 描述前一项，这个数是 21 即 “一个 2 一个 1 ” ，记作 1211
     * 描述前一项，这个数是 1211 即 “一个 1 一个 2 两个 1 ” ，记作 111221
     *
     * @param n
     * @return
     */
    public String countAndSay(int n) {
        return "";
    }

    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 你可以假设数组中无重复元素。
     * <p>
     * 示例 1:
     * <p>
     * 输入: [1,3,5,6], 5
     * 输出: 2
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {

        }
        return index;
    }

    /**
     * 96. 不同的二叉搜索树
     * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
     * 示例:
     * 输入: 3
     * 输出: 5
     * 解释:
     * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
     * <p>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        return 0;
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
     * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
     * 说明:
     * 返回的下标值（index1 和 index2）不是从零开始的。
     * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
     * 示例:
     * 输入: numbers = [2, 7, 11, 15], target = 9
     * 输出: [1,2]
     * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum2(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int diff = target - numbers[i];
            if (map.containsKey(diff)) {
                return new int[]{map.get(diff), i + 1};
            } else {
                map.put(numbers[i], i + 1);
            }
        }
        throw new RuntimeException();
    }

    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     * 示例 1:
     * 输入: [1,3,5,6], 5
     * 输出: 2
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert2(int[] nums, int target) {
        int l = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
            if (nums[i] < target) {
                l++;
            }
        }
        return l;
    }

    /**
     * 剑指 Offer 11. 旋转数组的最小数字
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
     * 示例 1：
     * 输入：[3,4,5,1,2]
     * 输出：1
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int res = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            res = Math.min(res, numbers[i]);
        }
        return res;
    }

    /**
     * 64. 最小路径和
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * <p>
     * 说明：每次只能向下或者向右移动一步。
     * <p>
     * 示例:
     * <p>
     * 输入:
     * [
     *   [1,3,1],
     * [1,5,1],
     * [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int l1 = grid.length;
        if (l1 == 0) {

        }
        int l2 = grid[0].length;
        int[][] dp = new int[l1][l2];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < l1; i++) {
            dp[i][0] = grid[i][0] + dp[i][0];
        }
        for (int i = 1; i < l2; i++) {
            dp[0][i] = grid[0][i] + dp[0][i];
        }
        for (int i = 1; i < l1; i++) {
            for (int j = 1; j < l2; j++) {
                dp[i][j] = Math.min(dp[i - 1][j] + grid[i][j], dp[i][j - 1] + grid[i][j]);
            }
        }
        return dp[l1 - 1][l2 - 1];
    }

    /**
     * 1025. 除数博弈
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     * 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
     * 选出任一 x，满足 0 < x < N 且 N % x == 0 。
     * 用 N - x 替换黑板上的数字 N 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 false。假设两个玩家都以最佳状态参与游戏。
     * 示例 1：
     * 输入：2
     * 输出：true
     * 解释：爱丽丝选择 1，鲍勃无法进行操作。
     *
     * @param N
     * @return
     */
    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }

    /**
     * 392. 判断子序列
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 示例 1:
     * s = "abc", t = "ahbgdc"
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) {
            return false;
        }
        int res = 0;
        for (int i = 0; i < t.length(); i++) {
            if (res == s.length()) {
                break;
            }
            if (s.charAt(res) == t.charAt(i)) {
                res++;
            }
        }
        return res == s.length();
    }

    /**
     * 104. 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 1512. 好数对的数目
     * 给你一个整数数组 nums 。
     * 如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
     * 返回好数对的数目。
     * 示例 1：
     * 输入：nums = [1,2,3,1,1,3]
     * 输出：4
     * 解释：有 4 组好数对，分别是 (0,3), (0,4), (3,4), (2,5) ，下标从 0 开始
     * 示例 2：
     * 输入：nums = [1,1,1,1]
     * 输出：6
     * 解释：数组中的每组数字都是好数对
     * 示例 3：
     * 输入：nums = [1,2,3]
     * 输出：0
     *
     * @param nums
     * @return
     */
    public int numIdenticalPairs(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j] && i < j) {
                    res++;
                }
            }
        }
        return res;
    }

}
