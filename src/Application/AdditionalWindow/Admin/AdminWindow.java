package Application.AdditionalWindow.Admin;

import Application.AdditionalWindow.Admin.User.UserInformationWindow;
import Application.Application;
import Application.DataBase.DataBase;
import Application.DataBase.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminWindow extends JFrame {

    public AdminWindow () throws SQLException {
        DataBase dataBase = Application.instance.dataBase;
        JLabel nameUser = new JLabel("admin");
        JLabel usersLabel = new JLabel("User's list:");
        JButton logOutBtn = new JButton("Log out");

        nameUser.setHorizontalAlignment(SwingConstants.CENTER);

        List<String> usersName = dataBase.getListName();
        List<JButton> usersBtn = new ArrayList<>();

        JPanel panelForUsers = new JPanel();
        panelForUsers.setAutoscrolls(true);
        panelForUsers.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelForUsers);

        int currentCountUsers = usersName.size();

        ActionListener btnAL = e -> {
            try {
                String name = ((JButton)e.getSource()).getText();
                Person user = Person.instance.get_by_name(name);
                UserInformationWindow wnd = new UserInformationWindow(user);
                wnd.setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };


        setSize(350, 200);
        setLayout(new GridBagLayout());
        setLocation(560, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Admin panel");

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 25, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(nameUser, gbc);

        gbc = new GridBagConstraints(
                2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 10, 1, 1), 0, 0
        );

        add(logOutBtn, gbc);

        gbc = new GridBagConstraints(
                0, 1, 4, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 1, 5, 1), 0, 0
        );

        add(usersLabel, gbc);

        for (int i = 0; i < currentCountUsers; ++i){
            JButton btn = new JButton(usersName.get(i));
            btn.setSize(new Dimension(300, 150));

            btn.addActionListener(btnAL);

            usersBtn.add(btn);

            panelForUsers.add(usersBtn.get(i));

        }

        gbc = new GridBagConstraints(
                0, 2, 4, 5, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(scrollPane, gbc);

        logOutBtn.addActionListener(e -> {
            dispose();
            try {
                Application wnd = new Application();
                wnd.setVisible(true);
            } catch (InterruptedException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });



    }
}
