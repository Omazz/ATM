package user;

import java.util.Arrays;

public class PinCode {
    public static final int LENGTH = 4;
    private final char[] value;

    public PinCode(char[] value) {
        if (value.length != LENGTH) {
            throw  new RuntimeException("Incorrect pin-code length!");
        }
        this.value = new char[value.length];
        for (int i = 0; i < value.length; ++i) {
            this.value[i] = value[i];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinCode pinCode = (PinCode) o;
        return Arrays.equals(value, pinCode.value);
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
