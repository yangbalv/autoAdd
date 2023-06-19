package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main2 {

    //
//    描述
//    密码要求:
//
//            1.长度超过8位
//
//2.包括大小写字母.数字.其它符号,以上四种至少三种
//
//3.不能有长度大于2的包含公共元素的子串重复 （注：其他符号不含空格或换行）
//
//    数据范围：输入的字符串长度满足 1 \le n \le 100 \1≤n≤100
//    输入描述：
//    一组字符串。
//
//    输出描述：
//    如果符合要求输出：OK，否则输出NG
//            示例1
//    输入：
//            021Abc9000
//021Abc9Abc1
//021ABC9000
//021$bc9000
//            复制
//    输出：
//    OK
//            NG
//    NG
//            OK
    public static void main(String[] args) {
        Main2 main2 = new Main2();
        System.out.println(main2.testPassword("021ABC9000"));


    }

    public String testPassword(String password) {
        Map<Character, Integer> map = new HashMap<>();
        int[] counts = new int[4];
        int len = password.length();
        if (len < 8 || len > 100) {
            return "NG";
        } else {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isDigit(c)) {
                    counts[0] = 1;
                } else if (Character.isUpperCase(c)) {
                    counts[1] = 1;
                } else if (Character.isLowerCase(c)) {
                    counts[2] = 1;
                } else {
                    counts[3] = 1;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < counts.length; i++) {
            count = count + counts[i];
        }
        if (count < 3) {
            return "NG";
        }
        for (int j = 0; j < password.length() -4; j++) {
            for (int k = j + 2; k < password.length(); k++) {
                String util = password.substring(j, k);
                String passwordx = password.substring(j + 1);
                if (passwordx.contains(util)) {
                    System.out.println(util);
                    return "NG";
                }
            }
        }
        return "OK";
    }
}
