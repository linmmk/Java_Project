package CardioWorkout;

public class Walking extends CardioWorkout {
    public Walking(int userWeight, int durationMinutes, int velocity) {
        super("걷기", userWeight, durationMinutes, velocity);
        calculateMets();
        calculateCalories();
    }

    @Override
    protected void calculateMets() {
        mets = (velocity * 0.95) + 1;
    }

    @Override
    protected void calculateCalories() {
        calculateCaloriesFromMets();
    }
}
