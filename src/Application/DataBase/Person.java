package Application.DataBase;

import java.util.List;

public class Person {
    public String loginP;
    public String passwordP;
    public String firstNameP;
    public String secondNameP;

    public Person (String login, String password, String firstName, String secondName){
        loginP = login;
        passwordP = password;
        firstNameP = firstName;
        secondNameP = secondName;
    }
}
