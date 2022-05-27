package enums;

public enum GamePreData {
    LEVEL1(10, 0.5, 1.5), LEVEL2(5, 1, 1), LEVEL3(2, 1.5, 0.5);

    private final int numberOfLives;
    private final double gettingDamagedRate;
    private final double hitDamageRate;

    GamePreData(int numberOfLives, double gettingDamagedRate, double hitDamageRate) {
        this.numberOfLives = numberOfLives;
        this.gettingDamagedRate = gettingDamagedRate;
        this.hitDamageRate = hitDamageRate;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public double getGettingDamagedRate() {
        return gettingDamagedRate;
    }

    public double getHitDamageRate() {
        return hitDamageRate;
    }
}