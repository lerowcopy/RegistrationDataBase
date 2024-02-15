package Application.AdditionalWindow.Admin;

import Application.Application;
import Application.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminWindow extends JFrame {

    private final DataBase dataBase;
    public AdminWindow () throws SQLException {
        dataBase = Application.instance.dataBase;
        JLabel nameUser = new JLabel("admin");
        JButton logOutBtn = new JButton("Log out");

        nameUser.setHorizontalAlignment(SwingConstants.CENTER);

        List<String> usersName = dataBase.getListName();
        List<JButton> usersBtn = new ArrayList<JButton>();

        JPanel panelForUsers = new JPanel();
        panelForUsers.setAutoscrolls(true);
        panelForUsers.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(panelForUsers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        int currentCountUsers = usersName.size();



        setSize(350, 100);
        setLayout(new GridBagLayout());
        setLocation(560, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 2, 1, 25, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(nameUser, gbc);

        gbc = new GridBagConstraints(
                2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );

        add(logOutBtn, gbc);

        gbc = new GridBagConstraints(
                0, 1, 4, 5, 1, 10,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0
        );



        for (int i = 0; i < currentCountUsers; ++i){
            JButton btn = new JButton(usersName.get(i));
            btn.setSize(new Dimension(300, 30));
            usersBtn.add(btn);

            panelForUsers.add(usersBtn.get(i));

        }

        add(scrollPane, gbc);

    }

    public static void main(String[] args) throws SQLException {
        AdminWindow wnd = new AdminWindow();
        wnd.setVisible(true);
    }
}
