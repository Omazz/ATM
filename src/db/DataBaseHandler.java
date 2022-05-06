package db;
import java.time.LocalDate;
import user.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseHandler {
    private Connection dbConnection;
    private String dbHost = "localhost";
    private String dbPort = "3306";
    private String dbLogin = "root";
    private String dbPassword = "root";
    private String dbName = "atm";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        StringBuilder connectionString = new StringBuilder();
        connectionString.append("jdbc:mysql://").append(dbHost).append(":").append(dbPort).append("/").append(dbName);
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString.toString(), dbLogin, dbPassword);
        return dbConnection;
    }

    public void addUser(User user) {
        String insert = "INSERT INTO " + Table.USER_TABLE + "(" + Table.USERS_FIRSTNAME + "," + Table.USERS_LASTNAME + ","
                + Table.USERS_PHONENUMBER + "," + Table.USERS_CARDNUMBER + ","
                + Table.USERS_PINCODE + "," + Table.USERS_MONEY + ")" + " VALUES(?,?,?,?,?,?)";
        //System.out.println(insert);
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getPhoneNumber());
            prSt.setString(4, user.getCard().getCardNumber().toString());
            prSt.setString(5, user.getCard().getPinCode().toString());
            prSt.setLong(6, user.getCard().getMoney());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser(String phoneNumber) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Table.USER_TABLE + " WHERE " + Table.USERS_PHONENUMBER + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, phoneNumber);
            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            String firstName = "", lastName = "", cardNumber = "", pinCode = "";
            long money = 0;
            boolean isEmpty = true;
            while (resultSet.next()) {
                isEmpty = false;
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
                cardNumber = resultSet.getString(5);
                pinCode = resultSet.getString(6);
                money = resultSet.getLong(7);
            }
            if (isEmpty) {
                return null;
            } else {
                ArrayList<Transaction> transactions = getTransactions(phoneNumber);
                return new User(firstName, lastName, phoneNumber, new Card(new CardNumber(cardNumber.toCharArray()), new PinCode(pinCode.toCharArray()), money), transactions);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<Transaction> getTransactions(String phoneNumber) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Table.TRANSACTIONS_TABLE + " WHERE " + Table.TRANSACTIONS_IDUSERS + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, getId(phoneNumber));
            resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                 transactions.add(new Transaction(resultSet.getString(3), resultSet.getObject(4, LocalDate.class), resultSet.getBoolean(5)));
            }
            return transactions;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean checkUser(String phoneNumber, PinCode pinCode) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Table.USER_TABLE + " WHERE " + Table.USERS_PHONENUMBER + "=? AND " +
                Table.USERS_PINCODE + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, phoneNumber);
            prSt.setString(2, pinCode.toString());
            resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean cardNumberAlreadyUsed(String cardNumber) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Table.USER_TABLE + " WHERE " + Table.USERS_CARDNUMBER + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, cardNumber);
            resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public void updateUserWallet(User user, long value) {
        String update = "UPDATE " + Table.USER_TABLE + " SET " + Table.USERS_MONEY + "=" + user.getCard().getMoney() + " WHERE " + Table.USERS_PHONENUMBER + "="
                + "\"" + user.getPhoneNumber() + "\"";
        System.out.println(update);
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTrancastionsUser(User user, Transaction transaction) {
        ResultSet resultSet = null;
        String update = "INSERT INTO " + Table.TRANSACTIONS_TABLE + "(" + Table.TRANSACTIONS_IDUSERS + ","
                + Table.TRANSACTIONS_TEXT + "," + Table.TRANSACTIONS_DATE + "," + Table.TRANSACTIONS_CORRECT + ")" + " VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, getId(user.getPhoneNumber()));
            prSt.setString(2, transaction.getText());
            prSt.setDate(3, Date.valueOf(transaction.getDate()));
            prSt.setBoolean(4, transaction.isCorrect());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getId(String phoneNumber) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Table.USER_TABLE + " WHERE " + Table.USERS_PHONENUMBER + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, phoneNumber);
            resultSet = prSt.executeQuery();
            int userId = 0;
            while (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
            return userId;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
