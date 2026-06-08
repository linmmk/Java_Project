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
import java.util.Arrays;
import java.util.StringTokenizer;
import StrengthWorkout.*;
import CardioWorkout.*;
import java.util.InputMismatchException

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
                    showUserRanking();
                    pause();
                    break;
                case 9:
                	exportToMarkdown();
                	pause();
                	break;
                case 10:
                	importFromMarkdown();
                	pause();
                	break;
                case 11:
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

            if (workout instanceof CardioWorkout) {
                CardioWorkout cardioWorkout = (CardioWorkout) workout;
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

    void showUserRanking() {
        printSection("사용자 랭킹 - 총 소모 칼로리 기준");
        if (!hasProfiles()) {
            return;
        }

        UserProfile[] rankingProfiles = Arrays.copyOf(profiles, profileCount);

        // 정렬
        for (int i = 0; i < rankingProfiles.length - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < rankingProfiles.length; j++) {
                if (rankingProfiles[j].getTotalCalories() > rankingProfiles[maxIndex].getTotalCalories()) {
                    maxIndex = j;
                }
            }

            UserProfile temp = rankingProfiles[i];
            rankingProfiles[i] = rankingProfiles[maxIndex];
            rankingProfiles[maxIndex] = temp;
        }

        // 정렬된 배열 뽑기
        for (int i = 0; i < rankingProfiles.length; i++) {
            UserProfile profile = rankingProfiles[i];
            String selected = profile == currentUser ? " *" : "";
            System.out.printf("%d위. %s - %.0f kcal, 운동 기록 %d개%s%n",
                    i + 1,
                    profile.getName(),
                    profile.getTotalCalories(),
                    profile.getWorkoutCount(),
                    selected);
        }
    }    
    
    void exportToMarkdown() {
    	printSection("내보내기 - 아래의 내용을 복사");
        if (!hasProfiles()) {
            return;
        }

        System.out.println("| profileNo | selected | name | age | height | profileWeight | workoutType | userWeight | durationMinutes | velocity | repetitions | exerciseWeight | numberOfSets |");
        System.out.println("| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |");

        for (int i = 0; i < profileCount; i++) {
            UserProfile profile = profiles[i]; // 프로필 하나 선택
            Workout[] workoutLogs = profile.getWorkoutLogs(); // 프로필 안의 운동 기록 불러옴

            // 선택된 프로필에 대해 운동 기록 순회함
            if (workoutLogs.length == 0) {
            	// 운동 기록이 없으면 null 전달
                printMarkdownRow(i + 1, profile, null);
            } else {
                for (Workout workout : workoutLogs) {
                	// 운동 기록이 있으면 운동 기록 배열인 workout 전달
                    printMarkdownRow(i + 1, profile, workout);
                }
            }
        }
    }
    
    void importFromMarkdown() {
    	printSection("불러오기 - Markdown 표 형식으로 입력");
        System.out.println("Markdown 표를 붙여넣고 마지막 줄에 END를 입력하십시오.");
        System.out.println("불러오기에 성공하면 기존 프로필과 운동 기록은 입력한 내용으로 교체됩니다.");

        UserProfile[] importedProfiles = new UserProfile[MAX_PROFILE_COUNT];
        int[] profileNumbers = new int[MAX_PROFILE_COUNT];
        int importedProfileCount = 0;
        int selectedProfileIndex = -1;
        int importedWorkoutCount = 0;
        int skippedRowCount = 0;

        while (true) {
            String line = sc.nextLine();

            if (line.equalsIgnoreCase("END")) {
                break;
            }

            String[] columns = parseMarkdownColumns(line);
            if (columns == null || isMarkdownHeaderOrSeparator(columns)) {
                continue;
            }

            try {
                int profileNo = Integer.parseInt(columns[0]);
                int selected = Integer.parseInt(columns[1]);
                String name = columns[2];
                int age = Integer.parseInt(columns[3]);
                int height = Integer.parseInt(columns[4]);
                int profileWeight = Integer.parseInt(columns[5]);

                int profileIndex = findProfileIndex(profileNumbers, importedProfileCount, profileNo);
                if (profileIndex == -1) {
                    if (importedProfileCount >= MAX_PROFILE_COUNT) {
                        skippedRowCount++;
                        continue;
                    }

                    importedProfiles[importedProfileCount] = new UserProfile(name, age, height, profileWeight);
                    profileNumbers[importedProfileCount] = profileNo;
                    profileIndex = importedProfileCount++;
                }

                if (selected == 1 && selectedProfileIndex == -1) {
                    selectedProfileIndex = profileIndex;
                }

                Workout workout = createWorkoutFromMarkdown(columns);
                if (workout != null) {
                    importedProfiles[profileIndex].addWorkoutLog(workout);
                    importedWorkoutCount++;
                }
            } catch (RuntimeException e) {
                skippedRowCount++;
            }
        }

        if (importedProfileCount == 0) {
            printMessage("불러올 수 있는 프로필이 없습니다. 기존 데이터는 유지됩니다.");
            return;
        }

        profiles = importedProfiles;
        profileCount = importedProfileCount;

        if (selectedProfileIndex >= 0 && selectedProfileIndex < profileCount) {
            currentUser = profiles[selectedProfileIndex];
        } else {
            currentUser = profiles[0];
        }

        System.out.printf("프로필 %d개, 운동 기록 %d개를 불러왔습니다.%n",
                profileCount, importedWorkoutCount);
        if (skippedRowCount > 0) {
            System.out.printf("형식이 맞지 않아 건너뛴 행: %d개%n", skippedRowCount);
        }
    }

    void printMarkdownRow(int profileNo, UserProfile profile, Workout workout) {
        String selected = profile == currentUser ? "1" : "0";
        String workoutType = getWorkoutType(workout);
        
        int userWeight = (workout == null) ? 0 : workout.getUserWeight();
        int durationMinutes = (workout == null) ? 0 : workout.getDurationMinutes();
        int velocity = 0;
        int repetitions = 0;
        int exerciseWeight = 0;
        int numberOfSets = 0;

        if (workout instanceof CardioWorkout) {
            CardioWorkout cardioWorkout = (CardioWorkout) workout;
            velocity = cardioWorkout.getVelocity();
        } else if (workout instanceof StrengthWorkout) {
            StrengthWorkout strengthWorkout = (StrengthWorkout) workout;
            repetitions = strengthWorkout.getRepetitions();
            exerciseWeight = strengthWorkout.getExerciseWeight();
            numberOfSets = strengthWorkout.getNumberOfSets();
        }
        
        
        System.out.printf("| %d | %s | %s | %d | %d | %d | %s | %d | %d | %d | %d | %d | %d |%n",
                profileNo,
                selected,
                escapeMarkdownCell(profile.getName()),
                profile.getAge(),
                profile.getHeight(),
                profile.getWeight(),
                workoutType,
                userWeight,
                durationMinutes,
                velocity,
                repetitions,
                exerciseWeight,
                numberOfSets);
    }

    String getWorkoutType(Workout workout) {
        if (workout == null) {
            return "NONE";
        } else if (workout instanceof Running) {
            return "RUNNING";
        } else if (workout instanceof Cycling) {
            return "CYCLING";
        } else if (workout instanceof Walking) {
            return "WALKING";
        } else if (workout instanceof DeadLift) {
            return "DEADLIFT";
        } else if (workout instanceof BenchPress) {
            return "BENCH_PRESS";
        } else if (workout instanceof PushUp) {
            return "PUSH_UP";
        }

        return "NONE";
    }

    String escapeMarkdownCell(String value) {
        return value.replace("|", "/").replace("\n", " ");
    }

    String[] parseMarkdownColumns(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        String[] columns = new String[13];
        int index = 0;

        while (tokenizer.hasMoreTokens() && index < columns.length) {
            columns[index++] = tokenizer.nextToken().trim();
        }

        if (index != columns.length) {
            return null;
        }

        return columns;
    }

    boolean isMarkdownHeaderOrSeparator(String[] columns) {
        return columns[0].equalsIgnoreCase("profileNo") || columns[0].startsWith("---");
    }

    int findProfileIndex(int[] profileNumbers, int importedProfileCount, int profileNo) {
        for (int i = 0; i < importedProfileCount; i++) {
            if (profileNumbers[i] == profileNo) {
                return i;
            }
        }

        return -1;
    }

    Workout createWorkoutFromMarkdown(String[] columns) {
        String workoutType = columns[6];
        int userWeight = Integer.parseInt(columns[7]);
        int durationMinutes = Integer.parseInt(columns[8]);
        int velocity = Integer.parseInt(columns[9]);
        int repetitions = Integer.parseInt(columns[10]);
        int exerciseWeight = Integer.parseInt(columns[11]);
        int numberOfSets = Integer.parseInt(columns[12]);

        switch (workoutType) {
            case "NONE":
                return null;
            case "RUNNING":
                return new Running(userWeight, durationMinutes, velocity);
            case "CYCLING":
                return new Cycling(userWeight, durationMinutes, velocity);
            case "WALKING":
                return new Walking(userWeight, durationMinutes, velocity);
            case "DEADLIFT":
                return new DeadLift(userWeight, repetitions, exerciseWeight, numberOfSets, durationMinutes);
            case "BENCH_PRESS":
                return new BenchPress(userWeight, repetitions, exerciseWeight, numberOfSets, durationMinutes);
            case "PUSH_UP":
                return new PushUp(userWeight, repetitions, numberOfSets, durationMinutes);
            default:
                throw new IllegalArgumentException("알 수 없는 운동 종류입니다.");
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
        System.out.println("8. 사용자 랭킹 보기");
        System.out.println("9. 내보내기");
        System.out.println("10. 불러오기");
        System.out.println("11. 프로그램 종료");
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
            
            try {
                // 정상적으로 숫자가 입력되면 값을 반환하고 루프 탈출
                int input = sc.nextInt();
                sc.nextLine(); // 엔터 찌꺼기 비우기
                return input;
                
            } catch (java.util.InputMismatchException e) {
                // 숫자가 아닌 문자가 입력되었을 때
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요!");
                sc.nextLine(); //
            }

            int input = sc.nextInt();
            sc.nextLine();
            
            return input;
        }
    }

	String inputText(String message) {
        System.out.println(LINE);
        System.out.println(message);
        prompt();

        try {
                String input = sc.nextLine(); // 일단 다 받아들임

                //입력값이 비어있는가
                if (input.trim().isEmpty()) {
                    throw new Exception("입력값이 비어있습니다."); 
                }

                
                // ".*\\d.*"는 문자열 안에 숫자(0~9)가 단 하나라도 포함되어 있는지 확인하는 자바의 기능(정규표현식)
                if (input.matches(".*\\d.*")) {
                    throw new Exception("문자 입력란에 숫자가 포함될 수 없습니다.");
                }

                return input; // 숫자가 없으면 무사히 통과하고 메서드 종료

            } catch (Exception e) {
                // 위에서 던진 에러를 여기서 잡음
                System.out.println("잘못된 입력입니다!");
            }

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
