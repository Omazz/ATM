package user;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final Card card;
    private ArrayList<Transaction> transactions;


    public User(String firstName, String lastName, String phoneNumber, Card card, ArrayList<Transaction> transactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.card = card;
        this.transactions = transactions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Card getCard() {
        return card;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add((transaction));
    }

    public void changeMoney(long value) {
        card.changeMoney(value);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(card, user.card) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstName).append(" ").append(lastName).append(" ").append(phoneNumber);
        return stringBuilder.toString();
    }
}
