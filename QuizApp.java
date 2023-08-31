import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    private List<User> users = new ArrayList<>();
    private List<Quiz> quizzes = new ArrayList<>();
    private int userIdCounter = 1;
    private int quizIdCounter = 1;
    private User currentUser = null;

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Online Quiz Application");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Take Quiz");
            System.out.println("4. Add Quiz");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    if (currentUser != null) {
                        takeQuiz();
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 4:
                    if (currentUser != null) {
                        addQuiz();
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private void registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = new User(userIdCounter++, username, password);
        users.add(newUser);
        System.out.println("User registered successfully.");
    }

    private void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful.");
                return;
            }
        }
        System.out.println("Login failed. Invalid username or password.");
    }

    private void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Quizzes:");
        for (Quiz quiz : quizzes) {
            System.out.println(quiz.getQuizId() + ". " + quiz.getTopic());
        }
        System.out.print("Enter the quiz number: ");
        int quizNumber = scanner.nextInt();

        if (quizNumber >= 1 && quizNumber <= quizzes.size()) {
            Quiz selectedQuiz = quizzes.get(quizNumber - 1);
            int score = 0;

            for (Question question : selectedQuiz.getQuestions()) {
                System.out.println(question.getText());
                for (int i = 0; i < question.getOptions().size(); i++) {
                    System.out.println((i + 1) + ". " + question.getOptions().get(i));
                }
                System.out.print("Enter your answer: ");
                int userAnswer = scanner.nextInt();

                if (userAnswer == question.getCorrectOption()) {
                    score++;
                }
            }

            System.out.println("Quiz completed! Your score: " + score);
        } else {
            System.out.println("Invalid quiz number.");
        }
    }

    private void addQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the quiz topic: ");
        String topic = scanner.nextLine();

        List<Question> questions = new ArrayList<>();
        int questionId = 1;

        System.out.print("Enter the number of questions: ");
        int numQuestions = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        for (int i = 0; i < numQuestions; i++) {
            System.out.print("Enter question text: ");
            String questionText = scanner.nextLine();

            List<String> options = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                System.out.print("Enter option " + (j + 1) + ": ");
                String option = scanner.nextLine();
                options.add(option);
            }

            System.out.print("Enter the correct option number (1-4): ");
            int correctOption = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            Question question = new Question(questionId++, questionText, options, correctOption);
            questions.add(question);
        }

        Quiz newQuiz = new Quiz(quizIdCounter++, topic, questions);
        quizzes.add(newQuiz);
        System.out.println("Quiz added successfully.");
    }

    public class User {
        private int userId;
        private String username;
        private String password;

        public User(int userId, String username, String password) {
            this.userId = userId;
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public class Question {
        private int questionId;
        private String text;
        private List<String> options;
        private int correctOption;

        public Question(int questionId, String text, List<String> options, int correctOption) {
            this.questionId = questionId;
            this.text = text;
            this.options = options;
            this.correctOption = correctOption;
        }

        public String getText() {
            return text;
        }

        public List<String> getOptions() {
            return options;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }

    public class Quiz {
        private int quizId;
        private String topic;
        private List<Question> questions;

        public Quiz(int quizId, String topic, List<Question> questions) {
            this.quizId = quizId;
            this.topic = topic;
            this.questions = questions;
        }

        public int getQuizId() {
            return quizId;
        }

        public String getTopic() {
            return topic;
        }

        public List<Question> getQuestions() {
            return questions;
        }
    }
}
