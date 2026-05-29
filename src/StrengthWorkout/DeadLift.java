package StrengthWorkout;

public class DeadLift extends StrengthWorkout {
    public DeadLift(int userWeight, int repetitions, int exerciseWeight, int numberOfSets, int durationMinutes) {
        super("데드리프트", userWeight, repetitions, exerciseWeight, numberOfSets, durationMinutes);
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
