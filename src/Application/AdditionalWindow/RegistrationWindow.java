package Application.AdditionalWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import Application.DataBase.DataBase;
import Application.Application;
import Application.DataBase.Person;

public class RegistrationWindow extends JFrame {
    public JTextField loginField = new JTextField();
    public JTextField passwordField = new JTextField();
    public JTextField firstNameField = new JTextField();
    public JTextField secondNameField = new JTextField();

    public JButton register = new JButton("register");
    private final DataBase dataBase;

    public RegistrationWindow() {
        dataBase = Application.instance.dataBase;

        CreateWindow();

        AddActionForComponent();
    }

    private void AddActionForComponent() {
        register.addActionListener(e -> {
            Person user = createPerson();
            try {
                if (dataBase.NewUser(user)) {
                    System.out.println("Пользователь успешно зарегистрирован");
                    Application.instance.windowR.dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void CreateWindow() {
        setSize(300, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(1000, 240);


        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        JLabel loginLabel = new JLabel("Login");
        add(loginLabel, gbc);

        gbc = new GridBagConstraints(
                0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(loginField, gbc);

        gbc = new GridBagConstraints(
                0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        JLabel passwordLabel = new JLabel("Password");
        add(passwordLabel, gbc);

        gbc = new GridBagConstraints(
                0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(passwordField, gbc);

        gbc = new GridBagConstraints(
                0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        JLabel firstNameLabel = new JLabel("First name");
        add(firstNameLabel, gbc);

        gbc = new GridBagConstraints(
                0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(firstNameField, gbc);

        gbc = new GridBagConstraints(
                0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        JLabel secondNameLabel = new JLabel("Second name");
        add(secondNameLabel, gbc);

        gbc = new GridBagConstraints(
                0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(secondNameField, gbc);

        gbc = new GridBagConstraints(
                0, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );
        add(register, gbc);
    }

    private Person createPerson(){
        String login = loginField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();

        return new Person(login, password, firstName, secondName);
    }
}
