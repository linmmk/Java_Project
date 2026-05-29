package CardioWorkout;

public class Running extends CardioWorkout {
    public Running(int userWeight, int durationMinutes, int velocity) {
        super("달리기", userWeight, durationMinutes, velocity);
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
