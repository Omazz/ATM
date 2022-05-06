package bank;

import db.DataBaseHandler;
import user.Transaction;
import user.User;

import java.sql.ResultSet;
import java.util.ArrayList;


public class Bank {
    private DataBaseHandler dataBaseHandler;

    public Bank() {
        dataBaseHandler = new DataBaseHandler();
    }

    public void addUser(User user) {
        dataBaseHandler.addUser(user);
    }

    public boolean contains(User user) {
        if (dataBaseHandler.getUser(user.getPhoneNumber()) == null) {
            return false;
        }
        return true;
    }


    public User getUser(User user) {
        return dataBaseHandler.getUser(user.getPhoneNumber());
    }

    public boolean checkUser(User user) {
        return dataBaseHandler.checkUser(user.getPhoneNumber(), user.getCard().getPinCode());
    }

    public void changeWalletUser(User user, long money) {
        if (user.getCard().getMoney() > money || money > 0) {
            user.changeMoney(money);
            dataBaseHandler.updateUserWallet(user, money);
            user.addTransaction(new Transaction(user, money, true));
            dataBaseHandler.updateTrancastionsUser(user, new Transaction(user, money, true));
        } else {
            user.addTransaction(new Transaction(user, money, false));
        }
    }

    public void transferMoneyToUser(User from, User to, long money) {
        if (from.getCard().getMoney() > money) {
            from.changeMoney(-money);
            dataBaseHandler.updateUserWallet(from, -money);
            to.changeMoney(money);
            dataBaseHandler.updateUserWallet(to, money);
            Transaction transaction = new Transaction(from, to, money, true);
            from.addTransaction(transaction);
            dataBaseHandler.updateTrancastionsUser(from, transaction);
            to.addTransaction(transaction);
            dataBaseHandler.updateTrancastionsUser(to, transaction);
        } else {
            Transaction transaction = new Transaction(from, to, money, false);
            from.addTransaction(transaction);
            dataBaseHandler.updateTrancastionsUser(from, transaction);
            to.addTransaction(transaction);
            dataBaseHandler.updateTrancastionsUser(to, transaction);
        }
    }

}
