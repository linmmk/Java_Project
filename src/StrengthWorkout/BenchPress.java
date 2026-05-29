package StrengthWorkout;

public class BenchPress extends StrengthWorkout {
    public BenchPress(int userWeight, int repetitions, int exerciseWeight, int numberOfSets, int durationMinutes) {
        super("벤치프레스", userWeight, repetitions, exerciseWeight, numberOfSets, durationMinutes);
        calculateMets();
        calculateCalories();
    }

    @Override
    protected void calculateMets() {
        calculateMetsFromVolumeLoad();
    }

    @Override
    protected void calculateCalories() {
        calculateCaloriesFromMets();
    }
}
