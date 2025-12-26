package exam;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Question> bank = new ArrayList<>();

        // 1. 系統標題展示
        System.out.println("════════════════════════════════════════");
        System.out.println("        📝 線上考試系統 (題庫版)");
        System.out.println("      技術點：檔案讀取、計時、自動閱卷");
        System.out.println("════════════════════════════════════════\n");

        // 2. 讀取 QA.txt (自動判斷路徑：優先檢查 src 資料夾)
        File file = new File("src/QA.txt");
        if (!file.exists()) {
            file = new File("QA.txt");
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split("\\|");

                // 根據第一個欄位判斷題型
                if (p[0].equals("TF")) {
                    bank.add(new TrueFalseQuestion(0, p[1], Double.parseDouble(p[2]), Boolean.parseBoolean(p[3])));
                } else if (p[0].equals("FIB")) {
                    // 解析填空題 (支援多個逗號分隔的正確答案)
                    List<String> ansList = Arrays.asList(p[3].split(","));
                    bank.add(new FillInBlankQuestion(0, p[1], Double.parseDouble(p[2]), ansList));
                } else if (p[0].equals("MCQ")) {
                    // 解析複選題
                    Set<Integer> correct = new HashSet<>();
                    for (String s : p[3].split(" ")) {
                        correct.add(Integer.parseInt(s));
                    }
                    bank.add(new MultipleChoiceQuestion(0, p[1], Double.parseDouble(p[2]), Arrays.asList(p[4].split(",")), correct));
                }
            }
        } catch (Exception e) {
            System.out.println("❌ 讀取 QA.txt 失敗！請檢查檔案位置與格式。");
            return;
        }

        System.out.print("請輸入考生姓名: ");
        String name = sc.nextLine();

        // 3. 隨機抽題 (10 題抽 5 題，並打亂選項)
        Collections.shuffle(bank);
        List<Question> paper = new ArrayList<>();
        for (int i = 0; i < 5 && i < bank.size(); i++) {
            Question q = bank.get(i);
            q.setId(i + 1); // 重新設定顯示題號 Q1-Q5
            if (q instanceof Randomizable) {
                ((Randomizable) q).shuffle(); // 若是複選題則打亂選項順序
            }
            paper.add(q);
        }

        // 🕒 【ExamSession 開始計時】
        long startTime = System.currentTimeMillis();
        System.out.println("\n--- 考試開始 (系統已開始計時) ---");

        // 4. 互動式作答流程
        Map<Integer, Answer> studentAnswers = new HashMap<>();
        for (Question q : paper) {
            q.display();
            System.out.print("你的答案是: ");
            String input = sc.nextLine().trim();

            if (q instanceof TrueFalseQuestion) {
                // 支援 O/X 作答
                boolean val = input.equalsIgnoreCase("o") || input.equalsIgnoreCase("true") || input.equals("是");
                studentAnswers.put(q.id, new Answer(val));
            } else if (q instanceof FillInBlankQuestion) {
                studentAnswers.put(q.id, new Answer(input));
            } else if (q instanceof MultipleChoiceQuestion) {
                // 解析複選題輸入 (例如: 0 2)
                Set<Integer> choices = new HashSet<>();
                if (!input.isEmpty()) {
                    for (String s : input.split("\\s+")) {
                        try {
                            choices.add(Integer.parseInt(s));
                        } catch (Exception e) {
                            // 忽略非數字輸入
                        }
                    }
                }
                studentAnswers.put(q.id, new Answer(choices));
            }
        }

        // 🕒 【ExamSession 結束計時】
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000; // 計算總秒數

        // 5. 評閱與正確答案顯示
        double finalScore = 0;
        double maxScore = 0;
        System.out.println("\n【考試評閱報告】");
        for (Question q : paper) {
            maxScore += q.getFullScore();
            Answer ans = studentAnswers.get(q.id);
            double earned = (q instanceof AutoGradable) ?
                    ((AutoGradable) q).grade(ans) :
                    ((PartialCredit) q).calculatePartialScore(ans);

            // 顯示得分與正確答案回饋
            System.out.printf("第 %d 題得分: %.1f / %.1f", q.id, earned, q.getFullScore());
            if (earned == q.getFullScore()) {
                System.out.println(" ✅ (全對)");
            } else if (earned > 0) {
                System.out.println(" ⚠️ (部分正確) [解答: " + q.getCorrectAnswer() + "]");
            } else {
                System.out.println(" ❌ (未得分) [解答: " + q.getCorrectAnswer() + "]");
            }
            finalScore += earned;
        }

        // 6. 最終成績統計與存檔
        double pct = (finalScore / (maxScore == 0 ? 1 : maxScore)) * 100;
        String grade = Question.convertToGrade(pct);

        System.out.println("\n════════════════════════════════════════");
        System.out.printf("  考生姓名: %s%n", name);
        System.out.printf("  作答用時: %d 秒%n", duration);
        System.out.printf("  最終總分: %.1f / %.1f%n", finalScore, maxScore);
        System.out.printf("  成績等級: %s%n", grade);
        System.out.println("════════════════════════════════════════");

        try (Formatter fmt = new Formatter(new FileWriter("exam_results.txt", true))) {
            fmt.format("學生: %-8s | 分數: %4.1f/%4.1f | 用時: %d秒 | 等級: %-2s | 時間: %tc%n",
                    name, finalScore, maxScore, duration, grade, new Date());
            System.out.println("系統訊息：成績數據已更新至數據庫。");
        } catch (IOException e) {
            System.out.println("系統訊息：成績存檔失敗。");
        }

        // 7. 結束語
        System.out.println("\n        ✨ 謝謝作答！祝您期末順利 ✨");
        System.out.println("════════════════════════════════════════");
    }
}