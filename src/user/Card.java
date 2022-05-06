package user;

import java.util.Objects;

public class Card {
    private final CardNumber cardNumber;
    private final PinCode pinCode;
    private long money;

    public Card(CardNumber cardNumber, PinCode pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        money = 0;
    }

    public Card(CardNumber cardNumber, PinCode pinCode, long money) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.money = money;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public PinCode getPinCode() {
        return pinCode;
    }

    public long getMoney() {
        return money;
    }

    public void changeMoney(long money) {
        this.money += money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNumber.equals(card.cardNumber) && pinCode.equals(card.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, pinCode);
    }
}
