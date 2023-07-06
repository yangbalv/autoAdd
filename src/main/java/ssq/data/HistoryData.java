package ssq.data;

import ssq.utils.RandomNumber;

import java.util.Arrays;

public class HistoryData {
    private int[] redNumbers = new int[RandomNumber.redNumber];
    private int[] blueNumbers = new int[RandomNumber.blueNumber];

    public int[] getRedNumbers() {
        return redNumbers;
    }

    public void setRedNumbers(int[] redNumbers) {
        this.redNumbers = redNumbers;
    }

    public int[] getBlueNumbers() {
        return blueNumbers;
    }

    public void setBlueNumbers(int[] blueNumbers) {
        this.blueNumbers = blueNumbers;
    }

    @Override
    public String toString() {
        String message =
                "历史的红球的数据是：\n" +
                        "1\t" + "2\t" + "3\t" + "4\t" + "5\t" + "6\t" + "7\t" + "8\t" + "9\t" + "10\t" + "11\t" + "12\t" + "13\t" + "14\t" + "15\t" + "16\t" + "17\t" + "18\t" + "19\t" + "20\t" + "21\t" + "22\t" + "23\t" + "24\t" + "25\t" + "26\t" + "27\t" + "28\t" + "29\t" + "30\t" + "31\t" + "32\t" + "33\n" +
                        redNumbers[0] + "\t" + redNumbers[1] + "\t" + redNumbers[2] + "\t" + redNumbers[3] + "\t" + redNumbers[4] + "\t" + redNumbers[5] + "\t" + redNumbers[6] + "\t" + redNumbers[7] + "\t" + redNumbers[8] + "\t" + redNumbers[9] + "\t" + redNumbers[10] + "\t" + redNumbers[11] + "\t" + redNumbers[12] + "\t" + redNumbers[13] + "\t" + redNumbers[14] + "\t" + redNumbers[15] + "\t" + redNumbers[16] + "\t" + redNumbers[17] + "\t" + redNumbers[18] + "\t" + redNumbers[19] + "\t" + redNumbers[20] + "\t" + redNumbers[21] + "\t" + redNumbers[22] + "\t" + redNumbers[23] + "\t" + redNumbers[24] + "\t" + redNumbers[25] + "\t" + redNumbers[26] + "\t" + redNumbers[27] + "\t" + redNumbers[28] + "\t" + redNumbers[29] + "\t" + redNumbers[30] + "\t" + redNumbers[31] + "\t" + redNumbers[32] + "\n" +
                        "历史的蓝球的数据是：\n" +
                        "1\t" + "2\t" + "3\t" + "4\t" + "5\t" + "6\t" + "7\t" + "8\t" + "9\t" + "10\t" + "11\t" + "12\t" + "13\t" + "14\t" + "15\t" + "16\n" +
                        blueNumbers[0] + "\t" + blueNumbers[1] + "\t" + blueNumbers[2] + "\t" + blueNumbers[3] + "\t" + blueNumbers[4] + "\t" + blueNumbers[5] + "\t" + blueNumbers[6] + "\t" + blueNumbers[7] + "\t" + blueNumbers[8] + "\t" + blueNumbers[9] + "\t" + blueNumbers[10] + "\t" + blueNumbers[11] + "\t" + blueNumbers[12] + "\t" + blueNumbers[13] + "\t" + blueNumbers[14] + "\t" + blueNumbers[15];
        return message;
    }
}
