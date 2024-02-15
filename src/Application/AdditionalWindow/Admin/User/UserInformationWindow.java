package Application.AdditionalWindow.Admin.User;

import Application.DataBase.Person;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserInformationWindow extends JFrame {

    public UserInformationWindow (Person user){
        JTextField login = new JTextField(user.loginP);
        JLabel loginL = new JLabel("Login");
        JTextField firstName = new JTextField(user.firstNameP);
        JLabel firstNameL = new JLabel("First name");
        JTextField secondName = new JTextField(user.secondNameP);
        JLabel secondNameL = new JLabel("Second name");

        JButton changePassword = new JButton("Change password");
        JButton save = new JButton("Save");
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
        add(firstNameL, gbc);

        gbc = new GridBagConstraints(
                0, 3, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(firstName, gbc);

        gbc = new GridBagConstraints(
                0, 4, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(secondNameL, gbc);

        gbc = new GridBagConstraints(
                0, 5, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(secondName, gbc);

        gbc = new GridBagConstraints(
                0, 6, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(changePassword, gbc);

        gbc = new GridBagConstraints(
                0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(cancel, gbc);

        gbc = new GridBagConstraints(
                1, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 2, 5), 0, 0
        );
        add(save, gbc);

        cancel.addActionListener(e -> dispose());

        save.addActionListener(e -> {
            try {
                user.save(login.getText(), firstName.getText(), secondName.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        changePassword.addActionListener(e -> {
            ChangePasswordWindow wnd = new ChangePasswordWindow(user);
            wnd.setVisible(true);
        });

    }
}
