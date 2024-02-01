import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

public class DataBase {
    public static Connection con = null;

    public Connection connect () throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:test.db");
        String sql = "CREATE TABLE IF NOT EXISTS users(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    login TEXT UNIQUE,\n" +
                "    password TEXT\n" +
                ");";
        PreparedStatement st = con.prepareStatement(sql);
        st.execute();
        return con;
    }

    public String login() throws SQLException {
        String sql = "SELECT password FROM users WHERE login = " + "'" + MainWindow.instance.first_jtf.getText() +  "'";
        PreparedStatement s = con.prepareStatement(sql);
        ResultSet resultSet = s.executeQuery();
        if (!resultSet.next()) {
            return "IL";
        }
        else if (Objects.equals(resultSet.getString("password"), hex(MainWindow.instance.second_jtf.getText(), 5))){
            return "OK";
        }
        else{
            return "IP";
        }
    }

    public boolean Registration () throws  SQLException{
        String sql = "SELECT login FROM users WHERE login = " + "'" + RegistrationWindow.instance.first_jtx_reg.getText() + "'";
        PreparedStatement s = con.prepareStatement(sql);
        ResultSet resultSet = s.executeQuery();

        if (resultSet.next()){
            System.out.println("Данный пользователь уже существует. Попробуйте другой.");
        }
        else{
            if (RegistrationWindow.instance.second_jtx_reg.getText().length() <= 3){
                System.out.println("Введите другой пароль. Минимальный размер пароля 3 символа");
            }
            else if (RegistrationWindow.instance.first_jtx_reg.getText().isEmpty()){
                System.out.println("Введите другой логин. Минимальный размер логина 1 символ");
            }
            else{
                String login = RegistrationWindow.instance.first_jtx_reg.getText();
                login = login.replaceAll(" ", "");
                String password = hex(RegistrationWindow.instance.second_jtx_reg.getText(), 5);
                sql = String.format("INSERT INTO users (login, password) VALUES ('%s', '%s')", login, password);
                PreparedStatement st = con.prepareStatement(sql);
                st.execute();
                return true;
            }
        }
        return false;
    }

    public String hex(String str, int k){

        byte[] messageDigest = new byte[0];
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
