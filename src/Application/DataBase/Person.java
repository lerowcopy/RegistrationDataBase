package Application.DataBase;

import Application.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    public String loginP;
    public String passwordP;
    public String firstNameP;
    public String secondNameP;

    public static Person instance = new Person();

    private final DataBase dataBase = Application.instance.dataBase;

    public Person (){

    }

    public Person (String login, String password, String firstName, String secondName){
        loginP = login;
        passwordP = password;
        firstNameP = firstName;
        secondNameP = secondName;
    }

    public Person get_by_name (String name) throws SQLException {

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
}
