package bank;

import user.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ATM {
    private final Bank bank;
    private User currentUser;
    Pattern pattern = Pattern.compile("^7\\d{10}$");

    public ATM() {
        bank = new Bank();
        currentUser = null;
    }

    public void addNewUser(String firstName, String lastName, String phoneNumber, char[] pinCode) {
        User user = new User(firstName, lastName, phoneNumber, new Card(new CardNumber(),new PinCode(pinCode)), new ArrayList<>());
        if(!bank.contains(user)) {
            bank.addUser(user);
        }
    }

    public void tryLogin(String firstName, String lastName, String phoneNumber, char[] pinCode) {
        User user = new User(firstName, lastName, phoneNumber, new Card(new CardNumber(), new PinCode(pinCode)), new ArrayList<>());
        if(bank.checkUser(user)) {
            currentUser = bank.getUser(user);
        }
    }

    public void changeWallet(long money) {
        if (currentUser != null) {
            bank.changeWalletUser(currentUser, money);
        }
    }

    public long lookWallet() {
        if (currentUser != null) {
           return currentUser.getCard().getMoney();
        }
        return -1;
    }

    public void transferMoney(String firstName, String lastName, String phoneNumber, long money) {
        if (currentUser != null) {
            User to = new User(firstName, lastName, phoneNumber, new Card(new CardNumber(), new PinCode(new char[PinCode.LENGTH])), new ArrayList<>());
            if (bank.contains(to)) {
                bank.transferMoneyToUser(currentUser, bank.getUser(to), money);
            }
        }
    }

    public String lookTransactions(){
        if (currentUser != null) {
            ArrayList<Transaction> transactions = currentUser.getTransactions();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0;i < transactions.size(); ++i) {
                stringBuilder.append(transactions.get(i).toString()).append("\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }

    private boolean isCorrectPhoneNumber(String phoneNumber) {
        return pattern.matcher(phoneNumber).matches();
    }

    public void exit() {
        currentUser = null;
    }
}
