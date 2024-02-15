package Application.AdditionalWindow.Admin.User;

import Application.Application;
import Application.DataBase.DataBase;
import Application.DataBase.Person;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ChangePasswordWindow extends JFrame {
    private final DataBase dataBase = Application.instance.dataBase;
    public ChangePasswordWindow (Person user){
        JLabel newPasswordL = new JLabel("New password:");
        JTextField newPassword = new JTextField();

        JButton cancel = new JButton("Cancel");
        JButton confirm = new JButton("Confirm");

        setTitle("Change password");
        setSize(300, 150);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 5, 2, 5), 0, 0
        );

        add(newPasswordL, gbc);

        gbc = new GridBagConstraints(
                0, 1, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 5, 2, 5), 0, 0
        );

        add(newPassword, gbc);

        gbc = new GridBagConstraints(
                0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 5, 2, 5), 0, 0
        );

        add(cancel, gbc);

        gbc = new GridBagConstraints(
                1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 5, 2, 5), 0, 0
        );

        add(confirm, gbc);

        confirm.addActionListener(e -> {
            try {
                if (newPassword.getText() != null){
                    dataBase.changePassword(user.loginP, newPassword.getText());
                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}
