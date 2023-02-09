package bg.sofia.uni.fmi.mjt.dungeons.common;

import java.io.Serializable;

public class PlayerId implements Serializable {
    private int number;
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 9;

    public PlayerId(int number) {
        if (!(0 <= MIN_NUMBER && number <= MAX_NUMBER)) {
            throw new IllegalArgumentException("Number should be a single digit");
        }

        this.number = number;
    }

    public char toChar() {
        return (char) ('0' + number);
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof PlayerId other) {
            return (number == other.number);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
