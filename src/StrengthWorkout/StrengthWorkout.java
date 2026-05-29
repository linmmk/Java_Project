package StrengthWorkout;

import HealthApp.Workout;

public abstract class StrengthWorkout extends Workout {
    protected int repetitions;
    protected int exerciseWeight;
    protected int numberOfSets;

    protected StrengthWorkout(String workoutName, int userWeight, int repetitions,
            int exerciseWeight, int numberOfSets, int durationMinutes) {
        super(workoutName, userWeight, durationMinutes);
        this.repetitions = repetitions;
        this.exerciseWeight = exerciseWeight;
        this.numberOfSets = numberOfSets;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getExerciseWeight() {
        return exerciseWeight;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public int getVolumeLoad() {
        return repetitions * exerciseWeight * numberOfSets;
    }

    protected void calculateMetsFromVolumeLoad() {
        int volumeLoad = getVolumeLoad();

        if (volumeLoad < 1000) {
            mets = 3.5;
        } else if (volumeLoad < 3000) {
            mets = 5.0;
        } else {
            mets = 6.0;
        }
    }

    protected void calculateCaloriesFromMets() {
        calories = (int) (mets * 3.5 * userWeight * durationMinutes / 200);
    }
}
