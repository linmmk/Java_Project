package HealthApp;

import java.util.Arrays;

// 작성자 : 20243087 김기환
// App 구현

public class UserProfile {
    private String name;
    private int age;
    private int height;
    private int weight;
    private Workout[] workoutLogs;
    private int top = -1;

    UserProfile(String name, int age, int height, int weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        workoutLogs = new Workout[100];
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getWorkoutCount() {
        return top + 1;
    }

    public Workout[] getWorkoutLogs() {
        return Arrays.copyOf(workoutLogs, getWorkoutCount());
    }

    public void addWorkoutLog(Workout log) {
        if (getWorkoutCount() >= workoutLogs.length) {
            throw new IllegalStateException("운동 기록은 최대 100개까지 저장할 수 있습니다.");
        }
        workoutLogs[++top] = log;
    }
}
