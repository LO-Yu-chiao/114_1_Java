package exam;
import java.util.*;

abstract class Question {
    protected int id;
    protected String content;
    protected double fullScore;
    public Question(int id, String content, double fullScore) {
        this.id = id; this.content = content; this.fullScore = fullScore;
    }
    public void setId(int id) { this.id = id; }
    public double getFullScore() { return fullScore; }
    public abstract void display();
    public abstract String getCorrectAnswer(); // 取得正確答案字串

    public static String convertToGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        return "F";
    }
}

class TrueFalseQuestion extends Question implements AutoGradable {
    private boolean correctAnswer;
    public TrueFalseQuestion(int id, String content, double fullScore, boolean ans) {
        super(id, content, fullScore); this.correctAnswer = ans;
    }
    @Override public void display() { System.out.println("Q" + id + " [是非](" + fullScore + "分): " + content + " (O/X)"); }
    @Override public double grade(Answer a) { return (boolean)a.getContent() == correctAnswer ? fullScore : 0; }
    @Override public String getCorrectAnswer() { return correctAnswer ? "O" : "X"; }
}

class FillInBlankQuestion extends Question implements AutoGradable {
    private List<String> acceptedAnswers;
    public FillInBlankQuestion(int id, String content, double fullScore, List<String> accepted) {
        super(id, content, fullScore); this.acceptedAnswers = accepted;
    }
    @Override public void display() { System.out.println("Q" + id + " [填空](" + fullScore + "分): " + content); }
    @Override public double grade(Answer a) {
        String studentAns = ((String)a.getContent()).trim();
        for (String acc : acceptedAnswers) if (acc.equalsIgnoreCase(studentAns)) return fullScore;
        return 0;
    }
    @Override public String getCorrectAnswer() { return String.join(" 或 ", acceptedAnswers); }
}

class MultipleChoiceQuestion extends Question implements PartialCredit, Randomizable {
    private List<String> options;
    private Set<Integer> correctAnswers;
    private List<String> correctTexts = new ArrayList<>();

    public MultipleChoiceQuestion(int id, String content, double fullScore, List<String> opts, Set<Integer> correct) {
        super(id, content, fullScore);
        this.options = new ArrayList<>(opts);
        // 先把正確的文字存下來，因為隨機打亂後索引會變
        for (int idx : correct) this.correctTexts.add(opts.get(idx));
        updateCorrectIndices();
    }

    private void updateCorrectIndices() {
        this.correctAnswers = new HashSet<>();
        for (int i = 0; i < options.size(); i++) {
            if (correctTexts.contains(options.get(i))) correctAnswers.add(i);
        }
    }

    @Override public void shuffle() { Collections.shuffle(options); updateCorrectIndices(); }

    @Override public void display() {
        System.out.println("Q" + id + " [複選](" + fullScore + "分): " + content + " (複選數字中間需空格)");
        for(int i=0; i<options.size(); i++) System.out.println("  (" + i + ") " + options.get(i));
    }

    @Override public double calculatePartialScore(Answer a) {
        Set<Integer> studentAns = (Set<Integer>) a.getContent();
        if (studentAns.isEmpty()) return 0;
        double unit = fullScore / (correctAnswers.isEmpty() ? 1 : correctAnswers.size());
        double total = 0;
        for (Integer ans : studentAns) {
            if (correctAnswers.contains(ans)) total += unit;
            else total -= (unit * 0.5);
        }
        return Math.max(0, total);
    }

    // --- 這裡改為：數字編號 + 文字內容 ---
    @Override public String getCorrectAnswer() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (correctAnswers.contains(i)) {
                result.add("(" + i + ") " + options.get(i));
            }
        }
        return String.join(", ", result);
    }
}