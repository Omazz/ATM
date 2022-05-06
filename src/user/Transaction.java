package user;

import java.time.LocalDate;
import java.time.ZoneId;

public class Transaction {
    private final String text;
    private final ZoneId zoneId = ZoneId.of("Europe/Moscow");
    private final LocalDate date;
    private final boolean correct;


    public Transaction(String text, LocalDate date, boolean correct) {
        this.text = text;
        this.date = date;
        this.correct = correct;
    }

    public Transaction(User from, User to, long value, boolean correct) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from '").append(from).append("' to '").append(to).append("' for the amount of ").append(value);
        text = stringBuilder.toString();
        this.correct = correct;
        date = LocalDate.now(zoneId);
    }

    public Transaction(User user, long value, boolean correct) {
        StringBuilder stringBuilder = new StringBuilder();
        if (value > 0) {
            stringBuilder.append("user '").append(user).append("' replenished wallet for the amount of ").append(value);
        } else {
            stringBuilder.append("user '").append(user).append("' withdrew money from wallet for the amount of ").append(-value);
        }
        text = stringBuilder.toString();
        this.correct = correct;
        date = LocalDate.now(zoneId);
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (correct) {
            stringBuilder.append("SUCCESSFULLY\n");
        } else {
            stringBuilder.append("FAILED\n");
        }
        stringBuilder.append(text);
        stringBuilder.append("\n").append(date);
        return stringBuilder.toString();
    }
}
