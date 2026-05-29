package HealthApp;

/*
 * 기능
1. 사용자 프로필 설정 (이름, 키, 몸무게)
2. 운동 기록 추가 (걷기, 달리기 사이클)
3. 운동별 계산 기능 (칼로리 등등)
4. 총 운동 시간, 거리, 칼로리 통계 보기
5. 운동 강도 분석 보기

부가기능(고민 중)
- 운동 기록 저장/불러오기
- 다중 프로필

 */

// 작성자 : 김기환
// App 구현

import java.util.Scanner;
import StrengthWorkout.*;
import CardioWorkout.*;

public class HealthApp {
    private static final String LINE = "─────────────────────────────────────────────────";
    private static final int MAX_PROFILE_COUNT = 10;

    private boolean running;
    private Scanner sc;
    private UserProfile[] profiles;
    private int profileCount;
    private UserProfile currentUser;

    public HealthApp() {
        sc = new Scanner(System.in);
        profiles = new UserProfile[MAX_PROFILE_COUNT];
    }

    void run() {
        running = true;
        while (running) {
            clearScreen();
            printMenu();

            int choice = inputInt("메뉴를 선택하십시오:");
            clearScreen();

            switch (choice) {
                case 1:
                    createProfile();
                    pause();
                    break;
                case 2:
                    selectProfile();
                    pause();
                    break;
                case 3:
                    editCurrentProfile();
                    pause();
                    break;
                case 4:
                    addWorkoutLog();
                    pause();
                    break;
                case 5:
                    calculateCalories();
                    pause();
                    break;
                case 6:
                    showTotalLog();
                    pause();
                    break;
                case 7:
                    analyzeIntensity();
                    pause();
                    break;
                case 8:
                    printMessage("프로그램을 종료합니다.");
                    running = false;
                    break;
                default:
                    printMessage("존재하지 않는 메뉴입니다.");
                    pause();
                    break;
            }
        }
        sc.close();
    }

    void createProfile() {
        if (profileCount >= profiles.length) {
            printMessage("프로필은 최대 " + MAX_PROFILE_COUNT + "개까지 생성할 수 있습니다.");
            return;
        }

        printSection("프로필 생성");

        String name = inputText("이름을 입력하십시오:");
        int age = inputInt("나이를 입력하십시오:");
        int height = inputInt("키를 입력하십시오(cm):");
        int weight = inputInt("몸무게를 입력하십시오(kg):");

        UserProfile profile = new UserProfile(name, age, height, weight);
        profiles[profileCount++] = profile;
        currentUser = profile;

        printMessage("프로필이 생성되었고 현재 프로필로 선택되었습니다.");
    }

    void selectProfile() {
        printSection("프로필 선택");
        if (!hasProfiles()) {
            return;
        }

        printProfileList();

        int select = inputInt("선택할 프로필 번호를 입력하십시오:");
        if (select < 1 || select > profileCount) {
            printMessage("존재하지 않는 프로필입니다.");
            return;
        }

        currentUser = profiles[select - 1];
        printMessage(currentUser.getName() + " 프로필이 선택되었습니다.");
    }

    void editCurrentProfile() {
        printSection("현재 프로필 수정");
        if (!hasCurrentProfile()) {
            return;
        }

        printCurrentProfile();

        String name = inputText("새 이름을 입력하십시오:");
        int age = inputInt("새 나이를 입력하십시오:");
        int height = inputInt("새 키를 입력하십시오(cm):");
        int weight = inputInt("새 몸무게를 입력하십시오(kg):");

        currentUser.setName(name);
        currentUser.setAge(age);
        currentUser.setHeight(height);
        currentUser.setWeight(weight);

        printMessage("현재 프로필이 수정되었습니다.");
    }

    void addWorkoutLog() {
        if (!hasCurrentProfile()) {
            return;
        }

        printSection("운동 기록 추가");
        System.out.println("1. 무산소 운동");
        System.out.println("2. 유산소 운동");

        int select = inputInt("운동 종류를 선택하십시오:");
        clearScreen();

        if (select == 1) {
            addWeightTrainingLog();
        } else if (select == 2) {
            addCardioTrainingLog();
        } else {
            printMessage("존재하지 않는 운동 종류입니다.");
        }
    }

    void addWeightTrainingLog() {
        printSection("무산소 운동");

        System.out.println("1. 데드리프트");
        System.out.println("2. 벤치프레스");
        System.out.println("3. 푸쉬업");

        int select = inputInt("운동을 선택하십시오:");

        if (select < 1 || select > 3) {
            printMessage("존재하지 않는 운동입니다.");
            return;
        }

        int repetitions = inputInt("반복 횟수를 입력하십시오:");
        int weight = 0;
        if (select != 3) {
            weight = inputInt("무게를 입력하십시오(kg):");
        }
        int numberOfSets = inputInt("세트 수를 입력하십시오:");
        int workoutDuration = inputInt("총 운동 시간을 입력하십시오(분, 휴식 시간 포함):");

        switch (select) {
            case 1:
                currentUser.addWorkoutLog(new DeadLift(currentUser.getWeight(), repetitions, weight, numberOfSets, workoutDuration));
                printMessage("데드리프트 기록이 추가되었습니다.");
                break;
            case 2:
                currentUser.addWorkoutLog(new BenchPress(currentUser.getWeight(), repetitions, weight, numberOfSets, workoutDuration));
                printMessage("벤치프레스 기록이 추가되었습니다.");
                break;
            case 3:
                currentUser.addWorkoutLog(new PushUp(currentUser.getWeight(), repetitions, numberOfSets, workoutDuration));
                printMessage("푸쉬업 기록이 추가되었습니다.");
                break;
        }
    }

    void addCardioTrainingLog() {
        printSection("유산소 운동");

        System.out.println("1. 달리기");
        System.out.println("2. 사이클");
        System.out.println("3. 걷기");

        int select = inputInt("운동을 선택하십시오:");

        if (select < 1 || select > 3) {
            printMessage("존재하지 않는 운동입니다.");
            return;
        }

        int workoutDuration = inputInt("시간을 입력하십시오(분):");
        int velocity = inputInt("평균 속도를 입력하십시오:");

        switch (select) {
            case 1:
                currentUser.addWorkoutLog(new Running(currentUser.getWeight(), workoutDuration, velocity));
                printMessage("달리기 기록이 추가되었습니다.");
                break;
            case 2:
                currentUser.addWorkoutLog(new Cycling(currentUser.getWeight(), workoutDuration, velocity));
                printMessage("사이클 기록이 추가되었습니다.");
                break;
            case 3:
                currentUser.addWorkoutLog(new Walking(currentUser.getWeight(), workoutDuration, velocity));
                printMessage("걷기 기록이 추가되었습니다.");
                break;
        }
    }

    void calculateCalories() {
        printSection("운동별 계산 기능");
        if (!hasWorkoutLogs()) {
            return;
        }

        Workout[] workoutLogs = currentUser.getWorkoutLogs();
        for (int i = 0; i < workoutLogs.length; i++) {
            Workout workout = workoutLogs[i];
            System.out.printf("%d. %s: %.0f kcal%n",
                    i + 1, workout.getName(), workout.getCalories());
        }
    }

    void showTotalLog() {
        printSection("총 운동 시간, 거리, 칼로리 통계 보기");
        if (!hasWorkoutLogs()) {
            return;
        }

        int totalMinutes = 0;
        double totalDistance = 0;
        double totalCalories = 0;

        Workout[] workoutLogs = currentUser.getWorkoutLogs();
        
        for (int i = 0; i < workoutLogs.length; i++) {
        	Workout workout = workoutLogs[i];
            totalMinutes += workout.getDurationMinutes();
            totalCalories += workout.getCalories();

            if (workout instanceof CardioWorkout cardioWorkout) {
                totalDistance += cardioWorkout.getDistance();
            }
        }

        System.out.printf("총 운동 횟수: %d회%n", currentUser.getWorkoutCount());
        System.out.printf("총 운동 시간: %d분%n", totalMinutes);
        System.out.printf("총 이동 거리: %.2f km%n", totalDistance);
        System.out.printf("총 소모 칼로리: %.0f kcal%n", totalCalories);
    }

    void analyzeIntensity() {
        printSection("운동 강도 분석 보기");
        if (!hasWorkoutLogs()) {
            return;
        }

        Workout[] workoutLogs = currentUser.getWorkoutLogs();
        for (int i = 0; i < workoutLogs.length; i++) {
            Workout workout = workoutLogs[i];
            System.out.printf("%d. %s: %s (MET %.1f)%n",
                    i + 1, workout.getName(), workout.getIntensity(), workout.getMets());
        }
    }

    void printMenu() {
        printSection("Health App");
        if (currentUser == null) {
            System.out.println("현재 프로필: 없음");
        } else {
            System.out.printf("현재 프로필: %s%n", currentUser.getName());
        }
        System.out.println("1. 프로필 생성");
        System.out.println("2. 프로필 선택");
        System.out.println("3. 현재 프로필 수정");
        System.out.println("4. 운동 기록 추가");
        System.out.println("5. 운동별 계산 기능");
        System.out.println("6. 총 운동 시간, 거리, 칼로리 통계 보기");
        System.out.println("7. 운동 강도 분석 보기");
        System.out.println("8. 프로그램 종료");
    }

    void printProfileList() {
        for (int i = 0; i < profileCount; i++) {
            UserProfile profile = profiles[i];
            String selected = profile == currentUser ? " *" : "";
            System.out.printf("%d. %s (%d세, %dcm, %dkg, 운동 기록 %d개)%s%n",
                    i + 1,
                    profile.getName(),
                    profile.getAge(),
                    profile.getHeight(),
                    profile.getWeight(),
                    profile.getWorkoutCount(),
                    selected);
        }
    }

    void printCurrentProfile() {
        System.out.printf("이름: %s%n", currentUser.getName());
        System.out.printf("나이: %d세%n", currentUser.getAge());
        System.out.printf("키: %dcm%n", currentUser.getHeight());
        System.out.printf("몸무게: %dkg%n", currentUser.getWeight());
        System.out.printf("운동 기록: %d개%n", currentUser.getWorkoutCount());
    }

    void printSection(String title) {
        System.out.println(LINE);
        System.out.println(title);
        System.out.println(LINE);
    }

    void printMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    boolean hasProfiles() {
        if (profileCount == 0) {
            printMessage("생성된 프로필이 없습니다.");
            return false;
        }

        return true;
    }

    boolean hasCurrentProfile() {
        if (currentUser == null) {
            printMessage("먼저 프로필을 생성하거나 선택하십시오.");
            return false;
        }

        return true;
    }

    boolean hasWorkoutLogs() {
        if (!hasCurrentProfile()) {
            return false;
        }

        if (currentUser.getWorkoutCount() == 0) {
            printMessage("저장된 운동 기록이 없습니다.");
            return false;
        }

        return true;
    }

    void prompt() {
        System.out.print("> ");
    }

    int inputInt(String message) {
        while (true) {
            System.out.println(LINE);
            System.out.println(message);
            prompt();

            int input = sc.nextInt();
            sc.nextLine();
            
            return input;
        }
    }

	String inputText(String message) {
        System.out.println(LINE);
        System.out.println(message);
        prompt();

        String input = sc.nextLine();
        
        return input;
    }

    void pause() {
        System.out.println(LINE);
        System.out.println("계속하려면 Enter를 누르십시오.");
        prompt();
        sc.nextLine();
    }

    static void clearScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.print("\n");
        }
    }
}
