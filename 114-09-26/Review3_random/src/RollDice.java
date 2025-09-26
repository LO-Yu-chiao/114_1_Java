import java.security.SecureRandom;

public class RollDice {
    public static void main(String[] args) {
        SecureRandom randomNumbers = new SecureRandom();

        // 建立長度為 7 的陣列，索引 1~6 分別對應骰子點數
        int[] frequency = new int[7];

        // 擲 60,000,000 次骰子
        for (int roll = 0; roll < 60000000; roll++) {
            int face = 1 + randomNumbers.nextInt(6); // 產生 1~6
            frequency[face]++; // 將該點數出現次數 +1
        }

        // 輸出結果
        System.out.printf("%s%10s%n", "點數", "出現次數");
        for (int face = 1; face < frequency.length; face++) {
            System.out.printf("%4d%10d%n", face, frequency[face]);
        }
    }
}
