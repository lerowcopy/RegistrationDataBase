package Application.AdditionalWindow.Admin.User;

import Application.AdditionalWindow.Admin.AdminWindow;
import Application.Application;
import Application.DataBase.DataBase;
import Application.DataBase.Person;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class NewUserWindow extends JFrame {
    private final DataBase dataBase = Application.instance.dataBase;
    private final AdminWindow adminWindow;
    public NewUserWindow(AdminWindow window){
        adminWindow = window;

        JTextField login = new JTextField();
        JLabel loginL = new JLabel("Login");
        JTextField password = new JTextField();
        JLabel passwordL = new JLabel("Password");
        JTextField firstName = new JTextField();
        JLabel firstNameL = new JLabel("First name");
        JTextField secondName = new JTextField();
        JLabel secondNameL = new JLabel("Second name");

        JButton add = new JButton("add");
        JButton cancel = new JButton("Cancel");

        setSize(300, 400);
        setLocation(910, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setTitle("User");

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(loginL, gbc);

        gbc = new GridBagConstraints(
                0, 1, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(login, gbc);

        gbc = new GridBagConstraints(
                0, 2, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(passwordL, gbc);

        gbc = new GridBagConstraints(
                0, 3, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(password, gbc);

        gbc = new GridBagConstraints(
                0, 4, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(firstNameL, gbc);

        gbc = new GridBagConstraints(
                0, 5, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(firstName, gbc);

        gbc = new GridBagConstraints(
                0, 6, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(secondNameL, gbc);

        gbc = new GridBagConstraints(
                0, 7, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(secondName, gbc);


        gbc = new GridBagConstraints(
                0, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(cancel, gbc);

        gbc = new GridBagConstraints(
                1, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(add, gbc);

        cancel.addActionListener(e -> dispose());

        add.addActionListener(e -> {
            try {
                if(dataBase.NewUser(new Person(login.getText(), password.getText(), firstName.getText(), secondName.getText()))){
                    adminWindow.usersName = dataBase.getListName();
                    int index = 0;
                    for (int i = 0; i < adminWindow.usersName.size(); ++i){
                        if (adminWindow.usersName.get(i).equals(login.getText())){
                            index = i;
                            break;
                        }
                    }
                    adminWindow.NewButton(index);
                }
                dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
