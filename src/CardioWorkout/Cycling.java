package CardioWorkout;

public class Cycling extends CardioWorkout {
    public Cycling(int userWeight, int durationMinutes, int velocity) {
        super("사이클", userWeight, durationMinutes, velocity);
        calculateMets();
        calculateCalories();
    }

    @Override
    protected void calculateMets() {
        if (velocity < 16) {
            mets = 4.0;
        } else if (velocity <= 19) {
            mets = 6.0;
        } else if (velocity <= 22) {
            mets = 8.0;
        } else if (velocity <= 25) {
            mets = 10.0;
        } else if (velocity <= 30) {
            mets = 12.0;
        } else {
            mets = 15.8;
        }
    }

    @Override
    protected void calculateCalories() {
        calculateCaloriesFromMets();
    }
}
