package bg.sofia.uni.fmi.mjt.dungeons.common;

public class PlayerId {
    private int number;

    public PlayerId(int number) {
        if (!(0 <= number && number <= 9)) {
            throw new IllegalArgumentException("Number should be a single digit");
        }

        this.number = number;
    }

    public char toChar() {
        return (char) ('0' + number);
    }
}
