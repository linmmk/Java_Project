package HealthApp;

public abstract class Workout {
    protected String workoutName;
    protected int userWeight;
    protected int durationMinutes;
    protected double calories;
    protected double mets;

    protected Workout(String workoutName, int userWeight, int durationMinutes) {
        this.workoutName = workoutName;
        this.userWeight = userWeight;
        this.durationMinutes = durationMinutes;
    }

    public String getName() {
        return workoutName;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getCalories() {
        return calories;
    }

    public double getMets() {
        return mets;
    }

    public String getIntensity() {
        if (mets < 3) {
            return "저강도";
        } else if (mets < 6) {
            return "중강도";
        } else {
            return "고강도";
        }
    }

    protected abstract void calculateMets();

    protected abstract void calculateCalories();
}
