package Application.DataBase;

import Application.Application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Person {
    public static final int FIRST_NAME_FILTER = 1;
    public static final int SECOND_NAME_FILTER = 2;
    public String loginP;
    public String passwordP;
    public String firstNameP;
    public String secondNameP;

    public static Person instance = new Person();

    private final DataBase dataBase = Application.instance.dataBase;

    public Person() {

    }

    public Person(String login, String password, String firstName, String secondName) {
        loginP = login;
        passwordP = password;
        firstNameP = firstName;
        secondNameP = secondName;
    }

    public Person get_by_name(String name) throws SQLException {

        String sql = String.format("SELECT * FROM Person WHERE login = '%s'", name);

        PreparedStatement ps = DataBase.con.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        String login = resultSet.getString(2);
        String password = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String secondName = resultSet.getString(5);

        return new Person(login, password, firstName, secondName);
    }

    public void save(String newLogin, String newFirstName, String newSecondName) throws SQLException {
        String sql = String.format("UPDATE Person SET login = '%s' WHERE login = '%s'", newLogin, loginP);
        dataBase.execute(sql);
        sql = String.format("UPDATE Person SET firstName = '%s' WHERE login = '%s'", newFirstName, newLogin);
        dataBase.execute(sql);
        sql = String.format("UPDATE Person SET secondName = '%s' WHERE login = '%s'", newSecondName, newLogin);
        dataBase.execute(sql);
    }

    public void delete(String login) throws SQLException {
        String sql = String.format("DELETE FROM Person WHERE login = '%s'", login);
        dataBase.execute(sql);

    }

    public static List<String> filter(int filter_type, String name) {
        String sql;
        return switch (filter_type) {
            case 1 -> {
                sql = String.format("SELECT * FROM Person WHERE firstName = '%s'", name);
                yield sqlFilterPart(sql);
            }
            case 2 -> {
                sql = String.format("SELECT * FROM Person WHERE secondName = '%s'", name);
                yield sqlFilterPart(sql);
            }
            default -> null;
        };
    }

    private static List<String> sqlFilterPart(String sql) {
        List<String> loginsList = new ArrayList<>();
        try (PreparedStatement p = DataBase.con.prepareStatement(sql)) {
            try (ResultSet resultSet = p.executeQuery()) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject;
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("firstName");
                    String secondName = resultSet.getString("secondName");

                    assert false;
                    loginsList.add(login);

                    jsonObject = new JSONObject();

                    jsonObject.put("id", id);
                    jsonObject.put("login", login);
                    jsonObject.put("password", password);
                    jsonObject.put("firstName", firstName);
                    jsonObject.put("secondName", secondName);

                    jsonArray.put(jsonObject);
                }
                assert false;
                JSONObject finallyJson = new JSONObject();
                finallyJson.put("List", jsonArray);
                String pathToSave = System.getProperty("user.dir") + "\\get.json";
                try (PrintWriter out = new PrintWriter(new FileWriter(pathToSave))) {
                    String outJ = finallyJson.toString(4);
                    out.write(outJ);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loginsList;
    }

}
