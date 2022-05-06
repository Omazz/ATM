package user;

import db.DataBaseHandler;

import java.util.Arrays;

public class CardNumber {
    public static final int LENGTH = 16;
    private final char[] value;
    private DataBaseHandler dataBaseHandler;

    public CardNumber() {
        value = new char[LENGTH];
        dataBaseHandler = new DataBaseHandler();
        do {
            for (int i = 0; i < LENGTH; ++i) {
                value[i] = getRandomNumber();
            }
        } while (dataBaseHandler.cardNumberAlreadyUsed(this.toString()));
    }

    public CardNumber(char[] value) {
        this.value = value;
        dataBaseHandler = new DataBaseHandler();

    }

    private char getRandomNumber() {
        return (char) ((Math.random() * (('9' - '0') + 1)) + '0');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LENGTH; ++i) {
            stringBuilder.append(value[i]);
        }
        return stringBuilder.toString();
    }
}
