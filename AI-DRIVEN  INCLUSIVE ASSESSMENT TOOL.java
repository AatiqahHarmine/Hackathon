import java.util.*;

class Candidate {
    String name, id, mode;
    boolean isPWD;
    int mcqScore = 0, descriptiveScore = 0, practicalScore = 0, vivaScore = 0;
    boolean examCompleted = false;

    public Candidate(String name, String id, boolean isPWD, String mode) {
        this.name = name;
        this.id = id;
        this.isPWD = isPWD;
        this.mode = mode;
    }

    public int getTotalScore() {
        return mcqScore + descriptiveScore + practicalScore + vivaScore;
    }

    public double getPercentage() {
        return getTotalScore() / 4.0;
    }

    public void printResult() {
        System.out.println("\n=== 📊 Result for " + name + " (ID: " + id + ") ===");
        System.out.println("📌 Assessment Mode: " + mode);
        System.out.println("♿ PWD Candidate: " + (isPWD ? "Yes" : "No"));
        System.out.println("📕 MCQ Score: " + mcqScore);
        System.out.println("📝 Descriptive Score: " + descriptiveScore);
        System.out.println("🔧 Practical Score: " + practicalScore);
        System.out.println("🎤 Viva Score: " + vivaScore);
        System.out.println("🟢 Total Score: " + getTotalScore() + "/400");
        System.out.printf("🏆 Percentage: %.2f%%\n", getPercentage());
    }
}

public class Hackathon {
    static Scanner scanner = new Scanner(System.in);
    static List<Candidate> candidates = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🚀 AI-Driven Inclusive Assessment Tool");
        while (true) {
            System.out.println("\n📋 Main Menu:");
            System.out.println("1️⃣ Candidate Registration");
            System.out.println("2️⃣ Mode Selection");
            System.out.println("3️⃣ Assessment Creation");
            System.out.println("4️⃣ Conduct Assessment");
            System.out.println("5️⃣ Real-Time Analytics");
            System.out.println("6️⃣ Feedback to Candidates");
            System.out.println("7️⃣ Exit");
            System.out.print("👉 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerCandidate();
                case 2 -> selectMode();
                case 3 -> createAssessment();
                case 4 -> conductAssessment();
                case 5 -> viewAnalytics();
                case 6 -> provideFeedback();
                case 7 -> {
                    System.out.println("✅ Exiting. Thank you!");
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    public static void registerCandidate() {
        System.out.print("🆕 Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("🆔 Enter Candidate ID: ");
        String id = scanner.nextLine();
        System.out.print("♿ Is the candidate PWD? (yes/no): ");
        boolean isPWD = scanner.nextLine().equalsIgnoreCase("yes");

        candidates.add(new Candidate(name, id, isPWD, ""));
        System.out.println("✅ Candidate Registered Successfully!");
    }

    public static void selectMode() {
        System.out.print("🔍 Enter Candidate ID: ");
        String id = scanner.nextLine();
        Candidate c = findCandidateById(id);

        if (c == null) {
            System.out.println("❌ Candidate not found.");
            return;
        }

        System.out.print("🎯 Select Mode (Online/Offline/Blended): ");
        c.mode = scanner.nextLine();
        System.out.println("✅ Mode Selected: " + c.mode);
    }

    public static void createAssessment() {
        System.out.print("🔍 Enter Candidate ID: ");
        String id = scanner.nextLine();
        Candidate c = findCandidateById(id);

        if (c == null || c.mode.isEmpty()) {
            System.out.println("⚠ Please register & select mode first.");
            return;
        }

        System.out.println("📜 Assessment Created for " + c.name + " in " + c.mode + " Mode.");
    }

    public static void conductAssessment() {
        System.out.print("🔍 Enter Candidate ID: ");
        String id = scanner.nextLine();
        Candidate c = findCandidateById(id);

        if (c == null || c.mode.isEmpty()) {
            System.out.println("⚠ Please register & select mode first.");
            return;
        }

        if (c.examCompleted) {
            System.out.println("⚠ Exam already conducted for this candidate.");
            return;
        }

        System.out.println("📝 Conducting Assessment for " + c.name);

        c.mcqScore = conductMCQExam();
        c.descriptiveScore = conductDescriptiveExam();
        c.practicalScore = conductPracticalExam();
        c.vivaScore = conductVivaExam();

        c.examCompleted = true;
        System.out.println("✅ Assessment Completed Successfully.");
    }

    public static int conductMCQExam() {
        String[] questions = {
            "Q1. What does a computer do?\n a) Cook food\n b) Wash clothes\n c) Process data\n d) Clean the house",
            "Q2. Which of these is a part of a computer?\n a) Banana\n b) Keyboard\n c) Pillow\n d) Cup",
            "Q3. What is used to see on the computer?\n a) Monitor\n b) Fan\n c) Radio\n d) Bottle"
        };
        char[] answers = {'c', 'b', 'a'};
        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            System.out.println("\n" + questions[i]);
            System.out.print("📝 Your Answer: ");
            char ans = scanner.nextLine().toLowerCase().charAt(0);
            if (ans == answers[i]) {
                score += 33;
            }
        }
        score += 1;
        return getAdaptiveScore(score);
    }

    public static int conductDescriptiveExam() {
        String[] questions = {
    "Q1. Describe your favorite hobby and why you enjoy it.",
    "Q2. What is the importance of good communication skills?"
};

String[][] keywords = {
    {"hobby", "enjoy", "interest", "relax", "activity", "passion"},
    {"communication", "skills", "express", "listen", "important", "understand"}
};


        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("✏ Your Answer: ");
            String answer = scanner.nextLine().toLowerCase();
            int matched = 0;
            for (String keyword : keywords[i]) {
                if (answer.contains(keyword)) matched++;
            }
            score += (matched * 100) / keywords[i].length / questions.length;
        }
        return getAdaptiveScore(score);
    }

    public static int conductPracticalExam() {
        String[] tasks = {
    "Q1. Write a note on how to make a cup of tea.",
    "Q2. Describe steps to organize your study table."
};

String[][] keywords = {
    {"boil", "water", "tea", "milk", "sugar", "cup"},
    {"clean", "books", "arrange", "stationery", "organize", "neat"}
};


        int score = 0;
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(tasks[i]);
            System.out.print("💡 Your Code Description: ");
            String answer = scanner.nextLine().toLowerCase();
            int matched = 0;
            for (String keyword : keywords[i]) {
                if (answer.contains(keyword)) matched++;
            }
            score += (matched * 100) / keywords[i].length / tasks.length;
        }
        return getAdaptiveScore(score);
    }

    public static int conductVivaExam() {
        String[] questions = {
            "Q1. What is your biggest achievement so far?",
            "Q2. What are your hobbies and how do they help you grow?"
        };
        String[][] keywords = {
            {"achievement", "success", "goal", "milestone"},
            {"hobby", "learn", "skill", "grow", "develop"}
        };

        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("🎙 Your Answer: ");
            String answer = scanner.nextLine().toLowerCase();
            int matched = 0;
            for (String keyword : keywords[i]) {
                if (answer.contains(keyword)) matched++;
            }
            score += (matched * 100) / keywords[i].length / questions.length;
        }
        return getAdaptiveScore(score);
    }

    public static int getAdaptiveScore(int baseScore) {
        return Math.min(100, baseScore + 5); 
    }

    public static void viewAnalytics() {
        if (candidates.isEmpty()) {
            System.out.println("⚠ No candidates available.");
            return;
        }
        for (Candidate c : candidates) {
            if (c.examCompleted) {
                System.out.printf("📊 Candidate: %s | Score: %d/400 | 📈 %.2f%%\n",
                        c.name, c.getTotalScore(), c.getPercentage());
            }
        }
    }

    public static void provideFeedback() {
        System.out.print("🔍 Enter Candidate ID: ");
        String id = scanner.nextLine();
        Candidate c = findCandidateById(id);

        if (c == null || !c.examCompleted) {
            System.out.println("⚠ No results available for this candidate.");
            return;
        }

        c.printResult();
        if (c.getPercentage() >= 75) {
            System.out.println("🌟 Excellent Performance! Keep it up!");
        } else if (c.getPercentage() >= 50) {
            System.out.println("✅ Good Job! Try to improve in weak areas.");
        } else {
            System.out.println("⚠ Needs Improvement. Focus on concepts.");
        }
    }

    public static Candidate findCandidateById(String id) {
        for (Candidate c : candidates) {
            if (c.id.equalsIgnoreCase(id)) return c;
        }
        return null;
    }
}
