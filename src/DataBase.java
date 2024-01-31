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
        else if (Objects.equals(resultSet.getString("password"), MainWindow.instance.second_jtf.getText())){
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
                String password = RegistrationWindow.instance.second_jtx_reg.getText();
                sql = String.format("INSERT INTO users (login, password) VALUES ('%s', '%s')", login, password);
                PreparedStatement st = con.prepareStatement(sql);
                st.execute();
                return true;
            }
        }
        return false;
    }
}
