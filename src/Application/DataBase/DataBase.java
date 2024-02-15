package Application.DataBase;

import Application.Application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBase {
    public static Connection con = null;

    public void connect () throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:test.db");
        String sql = """
                CREATE TABLE IF NOT EXISTS Person (
                    id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    Login      TEXT    UNIQUE,
                    Password   TEXT,
                    FirstName  TEXT,
                    SecondName TEXT
                )""";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
    }

    public String login() throws SQLException {
        String sql = "SELECT password FROM Person WHERE login = " + "'" + Application.instance.first_jtf.getText() +  "'";
        PreparedStatement s = con.prepareStatement(sql);
        ResultSet resultSet = s.executeQuery();
        if (!resultSet.next()) {
            return "IL";
        }
        else if (Objects.equals(resultSet.getString("password"), hex(Application.instance.second_jtf.getText(), 5))){
            if (Application.instance.first_jtf.getText().equals("admin")){
                return "admin";
            }
            return "OK";
        }
        else{
            return "IP";
        }
    }

    public boolean Registration (Person person) throws  SQLException{
        String login = person.loginP;
        String password = person.passwordP;
        String firstName = person.firstNameP;
        String secondName = person.secondNameP;

        String sql = "SELECT login FROM Person WHERE login = " + "'" + login + "'";
        PreparedStatement s = con.prepareStatement(sql);
        ResultSet resultSet = s.executeQuery();

        if (resultSet.next()){
            System.out.println("Данный пользователь уже существует. Попробуйте другой.");
        }
        else{
            if (password.length() <= 3){
                System.out.println("Введите другой пароль. Минимальный размер пароля 3 символа");
            }
            else if (login.isEmpty()){
                System.out.println("Введите другой логин. Минимальный размер логина 1 символ");
            }
            else{
                password = hex(password, 5);
                login = login.replaceAll(" ", "");

                sql = String.format("INSERT INTO Person (login, password, firstName, secondName) VALUES ('%s', '%s', '%s', '%s')", login, password, firstName, secondName);
                PreparedStatement st = con.prepareStatement(sql);
                st.execute();
                return true;
            }
        }
        return false;
    }

    public List<String> getListName () throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT login FROM PERSON";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String hex(String str, int k){

        byte[] messageDigest = new byte[0];
        str += "abrakadabra";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(str.getBytes());
            messageDigest = md.digest();

        }catch (NoSuchAlgorithmException e){
            System.err.println(e.getMessage());
        }
        BigInteger bigInt = new BigInteger(1, messageDigest);
        StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));
        while (md5Hex.length() < 32){
            md5Hex.insert(0, "0");
        }
        if (k == 0)return md5Hex.toString().toUpperCase();
        else return hex(md5Hex.toString(), k - 1);
    }

    public void changePassword (String login, String password) throws SQLException {
        String newPassword = hex(password, 5);
        String sql = String.format("UPDATE Person SET password = '%s' WHERE login = '%s'", newPassword, login);

        PreparedStatement ps = con.prepareStatement(sql);
        ps.execute();
    }

    public void execute(String sql) throws SQLException {
        con.prepareStatement(sql).execute();
    }

}
