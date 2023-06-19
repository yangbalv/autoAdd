package main;

import java.util.Arrays;

public class Main {

//    有一堆木头，每块木头的重量都是正整数。
//    每一回合，从中选出两块最重的木头，然后将它们一起粉碎。假设木头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
//            1、如果 x == y，那么两块木头都会被完全粉碎；
//            2、如果 x != y，那么重量为 x 的木头将会完全粉碎，而重量为 y 的木头新重量为 y-x。
//    最后，最多只会剩下一块木头。返回此木头的重量。如果没有木头剩下，就返回 0。

    public static void main(String[] args) {
        Main mains = new Main();
        System.out.println(mains.getLast(new int[]{1, 9, 5, 6, 63, 165, 6}));
//        System.out.println(mains.getLast(new int[]{0, 0, 1, 1}));
    }

    public int getLast(int[] arr) {
        int res = 0;
        int l = arr.length - 2;
        int r = arr.length - 1;

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        while (arr[l] != 0) {
            arr[r] = arr[r] - arr[l];
            arr[l] = 0;
            Arrays.sort(arr);
        }

        return arr[r];
    }
}
