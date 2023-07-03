package ssq.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumber {
    public static final int redNumber = 33;
    public static final int blueNumber = 16;

    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {

            System.out.println(random.nextInt(redNumber));
        }
    }

    public static void createNumber() {
        Set<Integer> redSet = new HashSet<>();
        int[] reds = new int[6];
        Random random = new Random();

        for (int i = 1; i <= reds.length; i++) {
            int randomNum = random.nextInt(redNumber);
            while (redSet.contains(randomNum)) {
                System.out.println("红球号码(" + randomNum + ")已经出现,重新生成");
                randomNum = random.nextInt(redNumber);
            }
            System.out.println("第" + i + "个红球号码是：" + (randomNum + 1));
            redSet.add(randomNum);
        }

        int randomNum = random.nextInt(blueNumber);
        System.out.println("蓝球号码是：" + (randomNum + 1));
    }


}
