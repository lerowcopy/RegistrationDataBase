package Application.DataBase;

import Application.Application;
import Application.AdditionalWindow.RegistrationWindow;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

public class DataBase {
    public static Connection con = null;

    public Connection connect () throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:test.db");
        String sql = "CREATE TABLE IF NOT EXISTS Person (\n" +
                "    id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    Login      TEXT    UNIQUE,\n" +
                "    Password   TEXT,\n" +
                "    FirstName  TEXT,\n" +
                "    SecondName TEXT\n" +
                ")";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        return con;
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
        String md5Hex = bigInt.toString(16);
        while (md5Hex.length() < 32){
            md5Hex = "0" + md5Hex;
        }
        if (k == 0)return md5Hex.toUpperCase();
        else return hex(md5Hex, k - 1);
    }
}
