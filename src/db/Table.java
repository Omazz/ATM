package db;

import user.Transaction;

public enum Table {
    USER_TABLE("users"), USERS_ID("idusers"), USERS_FIRSTNAME("firstName"),
    USERS_LASTNAME("lastName"), USERS_PHONENUMBER("phoneNumber"),
    USERS_CARDNUMBER("cardNumber"), USERS_PINCODE("pinCode"), USERS_MONEY("money"),
    TRANSACTIONS_TABLE("transactions"), TRANSACTIONS_ID("idtransactions"),
    TRANSACTIONS_IDUSERS("users_idusers"), TRANSACTIONS_TEXT("text"), TRANSACTIONS_DATE("date"),
    TRANSACTIONS_CORRECT("correct");

    private String string;

    Table(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
