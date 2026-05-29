package CardioWorkout;

import HealthApp.Workout;

public abstract class CardioWorkout extends Workout {
    protected int velocity;
    protected double distance;

    protected CardioWorkout(String workoutName, int userWeight, int durationMinutes, int velocity) {
        super(workoutName, userWeight, durationMinutes);
        this.velocity = velocity;
        this.distance = velocity * (durationMinutes / 60.0);
    }

    public int getVelocity() {
        return velocity;
    }

    public double getDistance() {
        return distance;
    }

    protected void calculateCaloriesFromMets() {
        calories = (int) (mets * 3.5 * userWeight * durationMinutes / 200);
    }
}
