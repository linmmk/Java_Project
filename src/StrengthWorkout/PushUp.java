package StrengthWorkout;

public class PushUp extends StrengthWorkout {
    // 푸쉬업은 몸무게가 전부 부하로 전해지지 않으므로, 일반적인 푸쉬업에서의 부하 계수인 70%를 사용하여 실제 부하를 weight 변수에 반영
	private static final double BODY_WEIGHT_LOAD_RATIO = 0.7; 

    public PushUp(int userWeight, int repetitions, int numberOfSets, int durationMinutes) {
        super("푸쉬업", userWeight, repetitions, userWeight, numberOfSets, durationMinutes);
        calculateMets();
        calculateCalories();
    }

    @Override
    public int getVolumeLoad() {
        return (int) Math.round(super.getVolumeLoad() * BODY_WEIGHT_LOAD_RATIO);
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
